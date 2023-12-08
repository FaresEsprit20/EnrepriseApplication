import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material/material.module';
import { CustomUIModule } from './custom-ui/custom-ui.module';
import { SortPipe } from 'src/app/pipes/sort.pipe';



@NgModule({
  declarations: [
    SortPipe
  ],
  imports: [
    CommonModule,
    MaterialModule,
    CustomUIModule,
  ],
  exports: [
    CommonModule,
    MaterialModule,
    CustomUIModule,
    SortPipe
  ]
})
export class SharedModule { }
