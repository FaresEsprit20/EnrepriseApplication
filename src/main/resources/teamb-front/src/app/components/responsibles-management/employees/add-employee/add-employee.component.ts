import { Component, OnDestroy, OnInit } from '@angular/core';
import { EmployeeService } from '../../../../shared/services/employee.service';
import { Subscription } from 'rxjs';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from '../../../../shared/ui/alert.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styles: [
    `:host /deep/ .custom-calendar {
      width: 100% !important;
  }
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
  }`
  ],
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit, OnDestroy {

  reactiveForm: FormGroup;
  submitError: string = null;
  isSubmitClicked = false;
  isValid: boolean = false
  isSpinnerLoading = false;
  private empSubscription: Subscription;

  constructor(private router: Router, private formBuilder: FormBuilder, private empService: EmployeeService,
    public alertService: AlertService) { }


  ngOnInit() {
    this.reactiveForm = this.formBuilder.group({
      firstname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      lastname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email]],
      occupation: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      tel: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      passwordConfirm: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      date: ['', [Validators.required]],
    });
  }

  ngOnDestroy(): void {
    this.empSubscription?.unsubscribe()
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

        this.empSubscription = this.empService.registerEmployee(body).subscribe(() => {
          console.log("submitted  .....")
          this.isValid = true
          this.isSpinnerLoading = false
          this.alertService.setInfoMessages("Blog Created Successfully")
          setTimeout(() => {
            this.router.navigateByUrl("/employees/blogs/list")
          }, 3000)
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
