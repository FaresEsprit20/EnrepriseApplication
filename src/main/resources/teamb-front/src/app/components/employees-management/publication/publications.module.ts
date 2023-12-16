import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PublicationsRoutingModule } from './publications-routing.module';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UpdatePublicationComponent } from './update-publication/update-publication.component';
import { AddBlogComponent } from './add-blog/add-blog.component';
import { BlogDetailsComponent } from './blog-details/blog-details.component';
import { BlogListComponent } from './blog-list/blog-list.component';


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
    HttpClientModule
  ],
  exports: [
    AddBlogComponent,
    BlogDetailsComponent,
    BlogListComponent,
    UpdatePublicationComponent
  ]
})
export class PublicationsModule { }
