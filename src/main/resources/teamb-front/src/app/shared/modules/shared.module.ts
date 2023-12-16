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
import { HttpClientModule } from '@angular/common/http';



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
  ]
})
export class SharedModule { }
