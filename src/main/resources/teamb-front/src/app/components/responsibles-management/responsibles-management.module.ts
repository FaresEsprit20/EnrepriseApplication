import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResponsiblesManagementRoutingModule } from './responsibles-management-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ResponsiblesManagementRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class ResponsiblesManagementModule { }
