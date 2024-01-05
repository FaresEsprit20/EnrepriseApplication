import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateResponsibleComponent } from './create-responsible/create-responsible.component';
import { UpdateResponsibleComponent } from './update-responsible/update-responsible.component';
import { ResponsiblesListComponent } from './responsibles-list/responsibles-list.component';
import { StatsComponent } from './stats/stats.component';

const routes: Routes = [
  { path: 'update', component: UpdateResponsibleComponent },
  { path: 'list', component: ResponsiblesListComponent },
  { path: 'stats', component: StatsComponent},
  { path: 'enterprises', loadChildren: () => import('./enterprises/enterprises.module').then((m) => m.EnterprisesModule) },
  { path: 'management/employees', loadChildren: () => import('./employees/employees.module').then((m) => m.EmployeesModule) },
  { path: 'create', component:  CreateResponsibleComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ResponsiblesManagementRoutingModule { }
