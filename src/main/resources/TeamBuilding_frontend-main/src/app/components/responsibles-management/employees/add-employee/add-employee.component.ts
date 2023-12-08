import { Component, OnInit } from '@angular/core';
import { FormBuilder,  FormControl,  FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DepartmentService } from 'src/app/shared/services/department.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';


@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit {
  userDepartments: any[]; // Define an array to store departments
  UserForm: FormGroup;

  constructor(
    private employeeService: EmployeeService,
    private departmentService: DepartmentService, // Corrected the variable name
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.UserForm = this.formBuilder.group({
      id: [''],
      email: ['', Validators.required],
      dateN: ['', Validators.required],
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      tel: ['', Validators.required],
      poste: ['', Validators.required],
      departement: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.getDepartments(); // Corrected the method name
  }

  getDepartments() {
    this.departmentService.findAllDepartments().subscribe({
      next: (res) => {
        this.userDepartments = res; // Assign the response to userDepartments
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  saveUser() {
    const employee = this.UserForm.value;
    this.employeeService.saveEmployee(employee).subscribe({
      next: (res) => {
        console.log('Utilisateur enregistré avec succès', res);
        this.router.navigate(['/user']);
      },
      error: (err) => {
        console.error('Erreur lors de l\'enregistrement de l\'utilisateur', err);
      }
    });
  }

  onSubmit() {
    console.log(this.UserForm);
    this.saveUser();
  }

  onFormSubmit() {
    console.log(this.UserForm.value);
    this.saveUser();
    this.router.navigate(['/user']);
  }
}
