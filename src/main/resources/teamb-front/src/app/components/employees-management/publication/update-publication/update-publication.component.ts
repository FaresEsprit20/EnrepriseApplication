import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PublicationService } from '../../../../shared/services/publication.service';
import { BlogListComponent } from '../blog-list/blog-list.component';

@Component({
  selector: 'app-update-publication',
  templateUrl: './update-publication.component.html',
  styleUrl: './update-publication.component.css'
})
export class UpdatePublicationComponent implements OnInit, OnDestroy {

  isLoaded = false
  blog: any;
  reactiveForm: FormGroup;
  submitError: string = null;
  isSubmitClicked = false;
  isValid:boolean = false

  constructor(private route:ActivatedRoute,private router: Router, private formBuilder: FormBuilder, private blogService: PublicationService) { }

  ngOnDestroy() {
    this.blogService.ngOnDestroy();
  }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.loadData()
  }


  loadData() {
    const id = this.route.snapshot.params['id'];
    this.blogService.findPublicationById(id).subscribe(
      (res) => {
        console.log('Publication loaded:', res);
        this.blog = res;
        this.reactiveForm = this.formBuilder.group({
          title: [this.blog?.name, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
          content: [this.blog?.description, [Validators.required, Validators.minLength(10)]]
        });
        this.isLoaded = true;
      },
      (error) => {
        console.error('Error loading publication:', error);
        this.isLoaded = true;
      }
    );
  }

  
  onSubmit() {
    const id = this.route.snapshot.params['id'];
    this.isSubmitClicked = true
    if (this.reactiveForm.valid) {
      // Reset submitError
      this.submitError = null;
      const body = {
        name: this.reactiveForm.controls['title'].value,
        description: this.reactiveForm.controls['content'].value
      };

      this.blogService.updatePublication(id,body).subscribe((res) => {
          console.log("submitted  .....")
          this.isValid = true
          setTimeout(() => {
               this.router.navigateByUrl("/employees/blogs/list")
          },3000)
        },
        (error) => {
          this.submitError = `Submit failed. Please try again. ${error}`;
        },
      );
    } else {
      // Form is not valid, display an alert
      this.submitError = 'Please fill in the required fields correctly.';
    }
  }


}
