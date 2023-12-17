import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResponsiblesManagementRoutingModule } from './responsibles-management-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';



@NgModule({
  imports: [
    CommonModule,
    ResponsiblesManagementRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ResponsiblesManagementModule { }
