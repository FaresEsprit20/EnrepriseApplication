import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomUIModule } from './custom-ui/custom-ui.module';
import { SortPipe } from 'src/app/pipes/sort.pipe';



@NgModule({
  declarations: [
    SortPipe
  ],
  imports: [
    CommonModule,
    CustomUIModule,
  ],
  exports: [
    CustomUIModule,
    SortPipe
  ]
})
export class SharedModule { }
