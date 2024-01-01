import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EnterpriseListComponent } from './enterprise-list/enterprise-list.component';
import { CreateEnterpriseComponent } from './create-enterprise/create-enterprise.component';
import { EnterpriseDetailsComponent } from './enterprise-details/enterprise-details.component';
import { UpdateEnterpriseComponent } from './update-enterprise/update-enterprise.component';


const routes: Routes = [
  { path: 'list', component: EnterpriseListComponent },
  { path: 'create', component:  CreateEnterpriseComponent},
  { path: 'details/:id', component:  EnterpriseDetailsComponent},
  { path: 'details/:id/update', component:  UpdateEnterpriseComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EnterprisesRoutingModule { }
