import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material/material.module';
import { SortPipe } from '../../pipes/sort.pipe';
import { DownvoteComponent } from '../ui/downvote/downvote.component';
import { SearchComponent } from '../ui/search/search.component';
import { UpvoteComponent } from '../ui/upvote/upvote.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { SortingComponent } from '../ui/sort/sort.component';
import { PrimeNGUIModule } from './primeng/primeNg.module';

@NgModule({
  declarations: [
    SortPipe,
    SortingComponent,
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
   //prime NG
    PrimeNGUIModule
  ],
  exports: [
    SortPipe,
    SortingComponent,
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    PrimeNGUIModule
  ]
})
export class SharedModule { }
