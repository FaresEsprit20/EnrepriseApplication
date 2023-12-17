import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesManagementRoutingModule } from './employees-management-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';



@NgModule({
  imports: [
    CommonModule,
    EmployeesManagementRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class EmployeesManagementModule { }
