import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { SortPipe } from 'src/app/pipes/sort.pipe';
import { PublicationService } from 'src/app/shared/services/publication.service';
import { RatingService } from 'src/app/shared/services/rating.service';
import { Router } from '@angular/router';



@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css'],
  providers: [SortPipe],
})
export class BlogListComponent {


onCloseAlert() {
console.log("clicked")
}

  blogs: any;
  searchResult: any;
  selectedSortOption = 'new';
  sortedBlogs: any[];

  constructor(
    private router: Router,
    private blogService: PublicationService,
    private ratingService: RatingService
  ) {}

  ngOnInit() {
    this.blogService.findAllPublications().subscribe((res) => {
      this.blogs = res;
 
       this.blogs.forEach((blog) => {
        if (blog.description.length > 100) {
          blog.description= blog.description.substring(0, 145) + '....';
        } else {
          blog.description = blog.description;
        }
        
      }); 
    },
      (error) => {
        console.error('Error loading publications:', error);
      });
  }

  navigateToAddBlog() {
    this.router.navigate(['/employees/blogs/create']);
  }

  onSortOptionChange() {
    this.sortBlogs();
  }

  handleSortedBlogs() {
    this.sortBlogs();
  }

  private sortBlogs() {
    this.sortedBlogs = this.searchResult || this.blogs;
    if (this.selectedSortOption === 'new') {
      this.sortedBlogs = this.sortedBlogs.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
    } else if (this.selectedSortOption === 'old') {
      this.sortedBlogs = this.sortedBlogs.sort((a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
    }
  }

  handleSearch(searchTerm: string) {
    this.searchResult = this.blogs.filter((blog) => {
      return (
        blog.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        blog.employeeFirstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        blog.employeeLastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        blog.description.toLowerCase().includes(searchTerm.toLowerCase())
      );
    });

    this.handleSortedBlogs();
  }


  



  onUpvote(id) {
    /* this.ratingService.upvotePublication(id).subscribe(() => {
      const index = this.blogs.findIndex((blog) => blog._id === id);
      this.blogs[index].upvotes += 1;
    }); */
  }
  onDownvote(id) {
    /* this.ratingService.downvotePublication(id).subscribe(() => {
      const index = this.blogs.findIndex((blog) => blog._id === id);
      this.blogs[index].downvotes += 1;
    }); */
  }


}
