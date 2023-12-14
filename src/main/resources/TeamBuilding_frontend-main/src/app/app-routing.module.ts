import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

/* const routes: Routes = [
  { path: '', component: LoginComponent },

  { path: 'readmore/:id', component: BlogDetailsComponent },
  { path: 'add', component: AddBlogComponent },
  { path: 'employees', component: EmployeesComponent },
  { path: 'updateEmployee' , component:UpdateEmployeeComponent}
]; */

const routes: Routes = [
  { path: 'authentication', loadChildren: () => import('./core/core.module').then((m) => m.CoreModule) },
  { path: 'employees', loadChildren: () => import('./components/employees-management/employees-management.module').then((m) => m.EmployeesManagementModule) },
  { path: 'responsibles', loadChildren: () => import('./components/responsibles-management/responsibles-management.module').then((m) => m.ResponsiblesManagementModule) },
  // ... (other routes)
  { path: '', redirectTo: '/authentication/login', pathMatch: 'full' },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
