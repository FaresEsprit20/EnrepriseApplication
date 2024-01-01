import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResponsiblesManagementRoutingModule } from './responsibles-management-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CreateResponsibleComponent } from './create-responsible/create-responsible.component';
import { SharedModule } from '../../shared/modules/shared.module';
import { UpdateResponsibleComponent } from './update-responsible/update-responsible.component';




@NgModule({
  declarations: [
    CreateResponsibleComponent,
    UpdateResponsibleComponent
  ],
  imports: [
    CommonModule,
    ResponsiblesManagementRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  exports: [
    CreateResponsibleComponent,
    UpdateResponsibleComponent
  ]
})
export class ResponsiblesManagementModule { }
