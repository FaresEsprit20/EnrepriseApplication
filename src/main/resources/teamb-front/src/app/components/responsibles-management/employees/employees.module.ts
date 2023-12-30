import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesRoutingModule } from './employees-routing.module';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { EmployeesListComponent } from './employees-list/employees-list.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AnimateOnScrollModule } from 'primeng/animateonscroll';
import { SharedModule } from 'primeng/api';
import { EmployeeDetailsComponent } from './employee-details/employee-details.component';



@NgModule({
  declarations: [
    AddEmployeeComponent,
    EmployeesListComponent,
    UpdateEmployeeComponent,
    EmployeeDetailsComponent
  ],
  imports: [
    CommonModule,
    EmployeesRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    AnimateOnScrollModule
  ],
  exports: [
    AddEmployeeComponent,
    EmployeesListComponent,
    UpdateEmployeeComponent,
    EmployeeDetailsComponent
  ]
})
export class EmployeesModule { }
