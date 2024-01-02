import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResponsiblesManagementRoutingModule } from './responsibles-management-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CreateResponsibleComponent } from './create-responsible/create-responsible.component';
import { SharedModule } from '../../shared/modules/shared.module';
import { UpdateResponsibleComponent } from './update-responsible/update-responsible.component';
import { ResponsiblesListComponent } from './responsibles-list/responsibles-list.component';
import { StatsComponent } from './stats/stats.component';




@NgModule({
  declarations: [
    CreateResponsibleComponent,
    UpdateResponsibleComponent,
    ResponsiblesListComponent,
    StatsComponent
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
    UpdateResponsibleComponent,
    ResponsiblesListComponent,
    StatsComponent
  ]
})
export class ResponsiblesManagementModule { }
