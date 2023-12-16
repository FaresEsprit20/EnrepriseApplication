import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesManagementRoutingModule } from './employees-management-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';



@NgModule({
  imports: [
    CommonModule,
    EmployeesManagementRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class EmployeesManagementModule { }
