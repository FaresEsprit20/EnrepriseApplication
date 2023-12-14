import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpvoteComponent } from '../../ui/upvote/upvote.component';
import { DownvoteComponent } from '../../ui/downvote/downvote.component';
import { SearchComponent } from '../../ui/search/search.component';
import { FormsModule } from '@angular/forms';
import { AlertComponent } from './alerts/alert.component';





@NgModule({
  declarations: [
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    AlertComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    UpvoteComponent,
    DownvoteComponent,
    SearchComponent,
    AlertComponent
  ]
})
export class CustomUIModule { }
