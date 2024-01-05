import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesRoutingModule } from './employees-routing.module';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { EmployeesListComponent } from './employees-list/employees-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AnimateOnScrollModule } from 'primeng/animateonscroll';
import { EmployeeDetailsComponent } from './employee-details/employee-details.component';
import { SharedModule } from '../../../shared/modules/shared.module';



@NgModule({
  declarations: [
    AddEmployeeComponent,
    EmployeesListComponent,
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
    EmployeeDetailsComponent
  ]
})
export class EmployeesModule { }
