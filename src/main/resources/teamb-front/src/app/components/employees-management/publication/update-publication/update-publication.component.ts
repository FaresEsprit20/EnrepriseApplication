import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PublicationService } from '../../../../shared/services/publication.service';
import { BlogListComponent } from '../blog-list/blog-list.component';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/ui/alert.service';

@Component({
  selector: 'app-update-publication',
  templateUrl: './update-publication.component.html',
  styles: [
    `
  :host {
      @keyframes slidedown-icon {
          0% {
              transform: translateY(0);
          }

          50% {
              transform: translateY(20px);
          }

          100% {
              transform: translateY(0);
          }
      }

      .slidedown-icon {
          animation: slidedown-icon;
          animation-duration: 3s;
          animation-iteration-count: infinite;
      }

      .box {
          background-image: radial-gradient(var(--primary-300), var(--primary-600));
          border-radius: 50% !important;
          color: var(--primary-color-text);
      }
  }
`
  ],
  styleUrl: './update-publication.component.css'
})
export class UpdatePublicationComponent implements OnInit, OnDestroy {

  isLoaded = false
  blog: any;
  reactiveForm: FormGroup;
  submitError: string = null;
  isSubmitClicked = false;
  isValid:boolean = false
  private blogSubscription: Subscription;
  private blogOpSubscription: Subscription;

  
  constructor(private route:ActivatedRoute,private router: Router, private formBuilder: FormBuilder, private blogService: PublicationService,
    public alertService:AlertService) { }

  ngOnDestroy() {
   this.blogSubscription?.unsubscribe()
   this.blogOpSubscription?.unsubscribe()
  }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.loadData()
  }


  loadData() {
    const id = this.route.snapshot.params['id'];
  this.blogSubscription = this.blogService.findPublicationById(id).subscribe(
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
      this.blogOpSubscription = this.blogService.updatePublication(id,body).subscribe((res) => {
          console.log("submitted  .....")
          this.isValid = true
          this.alertService.setInfoMessages('Blog Updated Successfully')
          setTimeout(() => {
               this.router.navigateByUrl("/employees/blogs/list")
          },3000)
        },
        (error) => {
          this.submitError = `Submit failed. Please try again. ${error}`;
          this.alertService.setErrorMessages(this.submitError)
        },
      );
    } else {
      this.submitError = 'Please fill in the required fields correctly.';
      this.alertService.setWarnMessages(this.submitError)
    }
  }


}
