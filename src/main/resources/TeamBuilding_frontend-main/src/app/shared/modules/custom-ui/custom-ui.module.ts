import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpvoteComponent } from '../../ui/upvote/upvote.component';
import { DownvoteComponent } from '../../ui/downvote/downvote.component';
import { SearchComponent } from '../../ui/search/search.component';
import { SortComponent } from '../../ui/sort/sort.component';
import { FormsModule } from '@angular/forms';




@NgModule({
  declarations: [
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    SortComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    SortComponent
  ]
})
export class CustomUIModule { }
