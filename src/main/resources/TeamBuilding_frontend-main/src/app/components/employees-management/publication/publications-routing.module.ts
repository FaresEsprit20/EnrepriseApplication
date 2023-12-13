import { NgModule } from '@angular/core';
import { BlogListComponent } from './blog-list/blog-list.component';
import { Routes, RouterModule } from '@angular/router';
import { BlogDetailsComponent } from './blog-details/blog-details.component';
import { AddBlogComponent } from './add-blog/add-blog.component';


const routes: Routes = [
  { path: 'details/:id', component: BlogDetailsComponent },
  { path: 'list', component: BlogListComponent },
  { path: 'create', component: AddBlogComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PublicationsRoutingModule { }
