import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'primeng/api';
import { DepartmentsRoutingModule } from './departments-routing.mdoule';
import { CreateDepartmentComponent } from './create-department/create-department.component';
import { DepartmentListComponent } from './department-list/department-list.component';
import { UpdateDepartmentComponent } from './update-department/update-department.component';



@NgModule({
  declarations: [
    CreateDepartmentComponent,
    DepartmentListComponent,
    UpdateDepartmentComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    DepartmentsRoutingModule,
    SharedModule
  ],
  exports: [
    CreateDepartmentComponent,
    DepartmentListComponent,
    UpdateDepartmentComponent
  ]
})
export class DepartmentsModule { }
