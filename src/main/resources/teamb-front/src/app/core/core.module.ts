import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CoreRoutingModule } from './core-routing.module';
import { HeaderComponent } from './structure/header/header.component';
import { LoginComponent } from './auth/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/modules/shared.module';




@NgModule({
  declarations: [
    HeaderComponent,
    LoginComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CoreRoutingModule,
    SharedModule
  ],
  exports: [
    HeaderComponent,
    LoginComponent
  ]
})
export class CoreModule { }
