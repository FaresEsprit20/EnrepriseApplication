import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'blogs', loadChildren: () => import('./publication/publications.module').then((m) => m.PublicationsModule) },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EmployeesManagementRoutingModule { }



