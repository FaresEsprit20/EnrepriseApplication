import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateResponsibleComponent } from './create-responsible/create-responsible.component';
import { UpdateEmployeeComponent } from './employees/update-employee/update-employee.component';

const routes: Routes = [
  { path: 'details/:id/update', component: UpdateEmployeeComponent },
  { path: 'enterprises', loadChildren: () => import('./enterprises/enterprises.module').then((m) => m.EnterprisesModule) },
  { path: 'management/employees', loadChildren: () => import('./employees/employees.module').then((m) => m.EmployeesModule) },
  { path: 'create', component:  CreateResponsibleComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ResponsiblesManagementRoutingModule { }
