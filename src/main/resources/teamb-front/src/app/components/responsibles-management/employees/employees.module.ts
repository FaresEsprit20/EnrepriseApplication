import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesRoutingModule } from './employees-routing.module';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { EmployeesComponent } from './employees-list/employees.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';



@NgModule({
  declarations: [
    AddEmployeeComponent,
    EmployeesComponent,
    UpdateEmployeeComponent
  ],
  imports: [
    CommonModule,
    EmployeesRoutingModule
  ],
  exports: [
    AddEmployeeComponent,
    EmployeesComponent,
    UpdateEmployeeComponent
  ]
})
export class EmployeesModule { }
