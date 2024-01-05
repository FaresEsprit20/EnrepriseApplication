import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../shared/ui/alert.service';
import { ResponsibleService } from '../../../shared/services/responsible.service';

@Component({
  selector: 'app-create-responsible',
  templateUrl: './create-responsible.component.html',
  styles: [
    `:host ::ng-deep .custom-calendar  {
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
  encapsulation: ViewEncapsulation.None,
  providers: [DatePipe],
  styleUrl: './create-responsible.component.css'
})
export class CreateResponsibleComponent implements OnInit, OnDestroy {

  reactiveForm: FormGroup;
  submitError: string = null;
  isSubmitClicked = false;
  isValid: boolean = false;
  isSpinnerLoading = false;
  private empSubscription: Subscription;

  constructor(private router: Router, private formBuilder: FormBuilder, private empService: ResponsibleService, 
    public alertService: AlertService,private datePipe: DatePipe) { }

  ngOnInit() {
    this.reactiveForm = this.formBuilder.group({
      firstname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100), Validators.pattern('^[a-zA-Z ]*$')]],
      lastname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100), Validators.pattern('^[a-zA-Z ]*$')]],
      email: ['', [Validators.required, Validators.email]],
      occupation: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100), Validators.pattern('^[a-zA-Z ]*$')]],
      tel: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^[0-9]*$')]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      passwordConfirm: ['', [Validators.required, Validators.minLength(3)]],
      date: ['', [Validators.required]],
    }, { validators: this.passwordsMatchValidator.bind(this) });
  }
  
  
  passwordsMatchValidator(formGroup: FormGroup): { [key: string]: boolean } | null {
    const password = formGroup.get('password').value;
    const confirmPassword = formGroup.get('passwordConfirm').value;
  
    if (password !== confirmPassword) {
      formGroup.get('passwordConfirm').setErrors({ passwordsNotMatched: true });
      return { passwordsNotMatched: true };
    } else {
      formGroup.get('passwordConfirm').setErrors(null);
      return null;
    }
  }
  

  passwordsMatch(): boolean {
    const password = this.reactiveForm.get('password').value;
    const confirmPassword = this.reactiveForm.get('passwordConfirm').value;
    return password === confirmPassword;
  }

  ngOnDestroy(): void {
    this.empSubscription?.unsubscribe();
  }

  onSubmit() {
    this.isSubmitClicked = true;

    if (this.reactiveForm.valid && this.passwordsMatch()) {
      console.log('Form is valid. Proceeding with submission...');

      this.submitError = null;

      const body = {
        firstname: this.reactiveForm.controls['firstname'].value,
        lastname: this.reactiveForm.controls['lastname'].value,
        email: this.reactiveForm.controls['email'].value,
        occupation: this.reactiveForm.controls['occupation'].value,
        tel: this.reactiveForm.controls['tel'].value,
        password: this.reactiveForm.controls['password'].value,
        passwordMatches: this.reactiveForm.controls['passwordConfirm'].value,
        birthDate: this.datePipe.transform(this.reactiveForm.controls['date'].value, 'yyyy-MM-dd'),
      };

      console.log('Submitting body:', body);

      this.isSpinnerLoading = true;

      setTimeout(() => {
        this.empSubscription = this.empService.registerResponsible(body).subscribe(
          () => {
            console.log("Submitted successfully.");
            this.isValid = true;
            this.isSpinnerLoading = false;
            this.alertService.setInfoMessages("Responsible Created Successfully");
            setTimeout(() => {
              this.router.navigateByUrl("/responsibles/management/employees/list");
            }, 3000);
          },
          (error) => {
            this.isSpinnerLoading = false;
            this.submitError = `Submit failed. Please try again. ${error}`;
            this.alertService.setErrorMessages(this.submitError);
          }
        );
      }, 3000);
    } else {
      this.isSpinnerLoading = false;
      this.submitError = 'Please fill in the required fields correctly.';
      this.alertService.setWarnMessages(this.submitError);
      console.log('Form is invalid. Cannot submit.');

      // Troubleshooting: Output specific validation errors
      Object.keys(this.reactiveForm.controls).forEach(field => {
        const control = this.reactiveForm.get(field);
        console.log(`${field} errors:`, control.errors);
      });
    }
  }

 

  
}
