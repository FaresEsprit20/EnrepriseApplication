import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnterprisesRoutingModule } from './enterprises-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'primeng/api';
import { CreateEnterpriseComponent } from './create-enterprise/create-enterprise.component';
import { UpdateEnterpriseComponent } from './update-enterprise/update-enterprise.component';
import { EnterpriseDetailsComponent } from './enterprise-details/enterprise-details.component';
import { EnterpriseListComponent } from './enterprise-list/enterprise-list.component';



@NgModule({
  declarations: [
    CreateEnterpriseComponent,
    UpdateEnterpriseComponent,
    EnterpriseDetailsComponent,
    EnterpriseListComponent
  ],
  imports: [
    CommonModule,
    EnterprisesRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  exports: [
    CreateEnterpriseComponent,
    UpdateEnterpriseComponent,
    EnterpriseDetailsComponent,
    EnterpriseListComponent
  ]
})
export class EnterprisesModule { }
