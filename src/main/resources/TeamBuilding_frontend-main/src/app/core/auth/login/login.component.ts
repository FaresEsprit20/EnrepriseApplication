import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/shared/services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})

export class LoginComponent implements OnInit {

  requestForm: FormGroup;
  imageUrl: string = 'assets/templates/template/images/bg-1.jpg';
  submitError: string = null;
  isSubmitClicked = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService) { } 

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    this.requestForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      rememberMe: [true]
    });
  }

  // Getters for form controls in the template
  get email() { return this.requestForm.get('email'); }
  get password() { return this.requestForm.get('password'); }
  get rememberMe() { return this.requestForm.get('rememberMe'); }

  isInvalid(controlName: string): boolean {
    const control = this.requestForm.get(controlName);
    return control.invalid && (control.dirty || control.touched);
  }
  
  onSubmit() {
    this.isSubmitClicked = true;
    if (this.requestForm.valid) {
      // Reset submitError
      this.submitError = null;
      // Call the authenticate method from AuthService
      const formData = {
        email: this.requestForm.controls['email'].value,
        password: this.requestForm.controls['password'].value
      }
      this.authService.authenticate(formData).subscribe(
        (response) => {
          // Handle successful authentication, e.g., navigate to a different page
          console.log('Authentication successful');
          this.authService.setAccessToken(response.access_token)
          //this.authService.setRefreshToken(response.refresh_token)
        },
        (error) => {
          // Handle authentication error, e.g., display an error message
          console.error('Authentication failed', error);
          this.submitError = 'Authentication failed. Please check your credentials and try again.';
        }
      );
    } else {
      // Form is not valid, display an alert
      this.submitError = 'Please fill in the required fields correctly.';
    }
  }

  
}
