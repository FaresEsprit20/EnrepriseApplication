import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PublicationService } from '../../../../shared/services/publication.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/ui/alert.service';


@Component({
  selector: 'app-add-blog',
  templateUrl: './add-blog.component.html',
  styleUrls: ['./add-blog.component.css']
})
export class AddBlogComponent implements OnInit, OnDestroy {

  reactiveForm: FormGroup;
  submitError: string = null;
  isSubmitClicked = false;
  isValid:boolean = false
  private blogSubscription: Subscription;

  constructor(private router: Router, private formBuilder: FormBuilder, private blogService: PublicationService, public alertService:AlertService) { }

  ngOnDestroy(): void {
    this.blogSubscription?.unsubscribe()
  }

  ngOnInit() {
    this.reactiveForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      content: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  onSubmit() {
    this.isSubmitClicked = true
    if (this.reactiveForm.valid) {
      // Reset submitError
      this.submitError = null;
      const body = {
        name: this.reactiveForm.controls['title'].value,
        description: this.reactiveForm.controls['content'].value
      };
       this.blogSubscription = this.blogService.createPublication(body).subscribe(() => {
          console.log("submitted  .....")
          this.isValid = true
          this.alertService.setInfoMessages("Blog Created Successfully")
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
      // Form is not valid, display an alert
      this.submitError = 'Please fill in the required fields correctly.';
      this.alertService.setWarnMessages(this.submitError)
    }
  }


}
