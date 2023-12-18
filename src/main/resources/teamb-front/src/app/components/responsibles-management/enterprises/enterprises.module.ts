import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnterprisesRoutingModule } from './enterprises-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    EnterprisesRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class EnterprisesModule { }
