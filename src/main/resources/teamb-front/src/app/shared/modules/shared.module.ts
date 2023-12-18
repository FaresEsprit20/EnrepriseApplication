import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material/material.module';
import { SortPipe } from '../../pipes/sort.pipe';
import { DownvoteComponent } from '../ui/downvote/downvote.component';
import { SearchComponent } from '../ui/search/search.component';
import { UpvoteComponent } from '../ui/upvote/upvote.component';
import { AlertComponent } from './custom-ui/alerts/alert.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HttpRequestInterceptor } from '../../core/interceptors/httpRequest-Interceptor';
import { AddressService } from '../services/address.service';
import { AuthService } from '../services/auth.service';
import { DepartmentService } from '../services/department.service';
import { EmployeeService } from '../services/employee.service';
import { EnterpriseService } from '../services/enterprise.service';
import { PublicationService } from '../services/publication.service';
import { RatingService } from '../services/rating.service';
import { ResponsibleService } from '../services/responsible.service';
import { ToastService } from '../ui/toast.service';



@NgModule({
  declarations: [
    SortPipe,
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    AlertComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule
  ],
  exports: [
    SortPipe,
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    AlertComponent
  ]/* ,
  providers: [
      AuthService,
      AddressService,
      DepartmentService,
      EmployeeService,
      EnterpriseService,
      PublicationService,
      RatingService,
      ResponsibleService,
      ToastService,
   ] */
})
export class SharedModule { }
