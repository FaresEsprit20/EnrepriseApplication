import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UpdateEmployeeComponent } from '../responsibles-management/employees/update-employee/update-employee.component';

const routes: Routes = [
  { path: 'update', component: UpdateEmployeeComponent },
  { path: 'blogs', loadChildren: () => import('./publication/publications.module').then((m) => m.PublicationsModule) },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EmployeesManagementRoutingModule { }



