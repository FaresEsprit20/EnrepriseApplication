import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PublicationService } from '../../../../shared/services/publication.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/ui/alert.service';


@Component({
  selector: 'app-add-blog',
  templateUrl: './add-blog.component.html',
  styles: [
    `:host {
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
  }`
  ],
  styleUrls: ['./add-blog.component.css']
})
export class AddBlogComponent implements OnInit, OnDestroy {

  reactiveForm: FormGroup;
  submitError: string = null;
  isSubmitClicked = false;
  isValid:boolean = false
  isSpinnerLoading = false;
  private blogSubscription: Subscription;

  constructor(private router: Router, private formBuilder: FormBuilder, private blogService: PublicationService,
     public alertService:AlertService) { }

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
      this.isSpinnerLoading = true
      //spinner load
      setTimeout(() => {

       this.blogSubscription = this.blogService.createPublication(body).subscribe(() => {
          console.log("submitted  .....")
          this.isValid = true
          this.isSpinnerLoading = false
          this.alertService.setInfoMessages("Blog Created Successfully")
          setTimeout(() => {
               this.router.navigateByUrl("/employees/blogs/list")
          },3000)
        },
        (error) => {
          this.isSpinnerLoading = false
          this.submitError = `Submit failed. Please try again. ${error}`;
          this.alertService.setErrorMessages(this.submitError)
        },
      );

       //spinner load end
      }, 3000)
    } else {
      this.isSpinnerLoading = false
      // Form is not valid, display an alert
      this.submitError = 'Please fill in the required fields correctly.';
      this.alertService.setWarnMessages(this.submitError)
    }
  }


}
