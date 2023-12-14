import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomUIModule } from './custom-ui/custom-ui.module';
import { SortPipe } from 'src/app/pipes/sort.pipe';
import { MaterialModule } from './material/material.module';



@NgModule({
  declarations: [
    SortPipe
  ],
  imports: [
    CommonModule,
    CustomUIModule,
    MaterialModule
  ],
  exports: [
    MaterialModule,
    CustomUIModule,
    SortPipe
  ]
})
export class SharedModule { }
