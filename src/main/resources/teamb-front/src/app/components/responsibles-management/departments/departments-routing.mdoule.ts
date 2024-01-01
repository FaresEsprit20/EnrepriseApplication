import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { DepartmentListComponent } from "./department-list/department-list.component";
import { CreateDepartmentComponent } from "./create-department/create-department.component";
import { UpdateDepartmentComponent } from "./update-department/update-department.component";


const routes: Routes = [
    { path: 'list', component: DepartmentListComponent },
    { path: 'create', component: CreateDepartmentComponent },
    { path: 'details/:id/update', component: UpdateDepartmentComponent },
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class DepartmentsRoutingModule { }
  