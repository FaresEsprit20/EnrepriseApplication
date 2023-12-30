import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortPipe } from '../../pipes/sort.pipe';
import { DownvoteComponent } from '../ui/downvote/downvote.component';
import { SearchComponent } from '../ui/search/search.component';
import { UpvoteComponent } from '../ui/upvote/upvote.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { SortingComponent } from '../ui/sort/sort.component';
import { PrimeNGUIModule } from './primeng/primeNg.module';
import { DateAgoPipe } from '../../pipes/date-ago.pipe';
import { SkeletonComponent } from '../ui/skeleton/skeleton.component';

@NgModule({
  declarations: [
    SortPipe,
    DateAgoPipe,
    SortingComponent,
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    SkeletonComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
   //prime NG
    PrimeNGUIModule,
  ],
  exports: [
    SortPipe,
    DateAgoPipe,
    SortingComponent,
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    SkeletonComponent,
    PrimeNGUIModule
  ]
})
export class SharedModule { }
