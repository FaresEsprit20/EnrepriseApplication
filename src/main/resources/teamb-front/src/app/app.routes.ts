import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: 'authentication', loadChildren: () => import('./core/core.module').then((m) => m.CoreModule) },
   /*  { path: 'employees', loadChildren: () => import('./components/employees-management/employees-management.module').then((m) => m.EmployeesManagementModule) },
    { path: 'responsibles', loadChildren: () => import('./components/responsibles-management/responsibles-management.module').then((m) => m.ResponsiblesManagementModule) }, */
    { path: '', redirectTo: '/authentication/login', pathMatch: 'full' }
];
