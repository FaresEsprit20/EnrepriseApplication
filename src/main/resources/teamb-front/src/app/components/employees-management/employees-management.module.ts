import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesManagementRoutingModule } from './employees-management-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/modules/shared.module';



@NgModule({
  imports: [
    CommonModule,
    EmployeesManagementRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ]
})
export class EmployeesManagementModule { }
