import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './core/structure/header/header.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './core/auth/login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddBlogComponent } from './components/employees-management/publication/add-blog/add-blog.component';
import { BlogDetailsComponent } from './components/employees-management/publication/blog-details/blog-details.component';
import { BlogListComponent } from './components/employees-management/publication/blog-list/blog-list.component';
import { AddEmployeeComponent } from './components/responsibles-management/employees/add-employee/add-employee.component';
import { EmployeesComponent } from './components/responsibles-management/employees/employees-list/employees.component';
import { UpdateEmployeeComponent } from './components/responsibles-management/employees/update-employee/update-employee.component';
import { EmployeesManagementModule } from './components/employees-management/employees-management.module';
import { ResponsiblesManagementModule } from './components/responsibles-management/responsibles-management.module';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/modules/shared.module';
import { HttpRequestInterceptor } from './core/interceptors/httpRequest-Interceptor';




@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BlogListComponent,
    BlogDetailsComponent,
    AddBlogComponent,
    EmployeesComponent, 
    UpdateEmployeeComponent,
    AddEmployeeComponent,
    LoginComponent
  ],
  imports: [
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    SharedModule,
    EmployeesManagementModule,
    ResponsiblesManagementModule,
    CoreModule
    
  ],
  providers: [ 
    { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
    DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
