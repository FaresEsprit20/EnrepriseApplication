import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { EnterpriseService } from '../../../../shared/services/enterprise.service';
import { AlertService } from '../../../../shared/ui/alert.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../../../../shared/services/auth.service';

@Component({
  selector: 'app-create-enterprise',
  templateUrl: './create-enterprise.component.html',
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
  styleUrl: './create-enterprise.component.css'
})
export class CreateEnterpriseComponent implements OnInit, OnDestroy {

  reactiveForm: FormGroup;
  submitError: string = null;
  isSubmitClicked = false;
  isValid: boolean = false;
  isSpinnerLoading = false;
  private empSubscription: Subscription;
  userId:number

  constructor(private router: Router, private formBuilder: FormBuilder, private enterpriseService:EnterpriseService, 
    public alertService: AlertService, private authService:AuthService) { }

  ngOnInit() {
    this.userId = Number.parseInt(this.authService.getUserId())
    this.reactiveForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      dep: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
    });
  }
  

  ngOnDestroy(): void {
    this.empSubscription?.unsubscribe();
  }

  onSubmit() {
    this.isSubmitClicked = true;

    if (this.reactiveForm.valid) {
      console.log('Form is valid. Proceeding with submission...');

      this.submitError = null;

      const body = {
        enterpriseName: this.reactiveForm.controls['name'].value,
        enterpriseLocal: this.reactiveForm.controls['dep'].value,
        responsibleId: this.userId
      };

      console.log('Submitting body:', body);

      this.isSpinnerLoading = true;

      setTimeout(() => {
        this.empSubscription = this.enterpriseService.createEnterprise(body).subscribe(
          () => {
            console.log("Submitted successfully.");
            this.isValid = true;
            this.isSpinnerLoading = false;
            this.alertService.setInfoMessages("Enterprise Created Successfully");
            setTimeout(() => {
              this.router.navigateByUrl("/responsibles/enterprises/list");
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
