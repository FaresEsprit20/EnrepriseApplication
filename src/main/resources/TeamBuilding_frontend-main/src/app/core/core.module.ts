import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CoreRoutingModule } from './core-routing.module';



@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    CoreRoutingModule
  ]
})
export class CoreModule { }
