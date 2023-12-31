import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'enterprises', loadChildren: () => import('./enterprises/enterprises.module').then((m) => m.EnterprisesModule) },
  { path: 'departments', loadChildren: () => import('./departments/departments.module').then((m) => m.DepartmentsModule) },
  { path: 'management/employees', loadChildren: () => import('./employees/employees.module').then((m) => m.EmployeesModule) },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ResponsiblesManagementRoutingModule { }
