import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublicationsRoutingModule } from './publications-routing.module';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UpdatePublicationComponent } from './update-publication/update-publication.component';
import { AddBlogComponent } from './add-blog/add-blog.component';
import { BlogDetailsComponent } from './blog-details/blog-details.component';
import { BlogListComponent } from './blog-list/blog-list.component';
import { SharedModule } from '../../../shared/modules/shared.module';
import { AnimateOnScrollModule } from 'primeng/animateonscroll';

@NgModule({
  declarations: [
    AddBlogComponent,
    BlogDetailsComponent,
    BlogListComponent,
    UpdatePublicationComponent
  ],
  imports: [
    CommonModule,
    PublicationsRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    AnimateOnScrollModule
  ],
  exports: [
    AddBlogComponent,
    BlogDetailsComponent,
    BlogListComponent,
    UpdatePublicationComponent
  ]
})
export class PublicationsModule { }
