import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { SortPipe } from 'src/app/pipes/sort.pipe';
import { PublicationService } from 'src/app/shared/services/publication.service';
import { RatingService } from 'src/app/shared/services/rating.service';



@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css'],
  providers: [SortPipe],
})
export class BlogListComponent {

  blogs: any;
  searchResult: any;
  selectedSortOption = 'new';

  constructor(
    private datePipe: DatePipe,
    private sortPipe: SortPipe,
    private blogService: PublicationService,
    private ratingService: RatingService
  ) {}

  ngOnInit() {
    this.blogService.findAllPublications().subscribe((res) => {
      this.blogs = res;
/*       this.blogs.forEach((blog) => {
        if (blog.description.length > 100) {
          blog.preview = blog.description.substring(0, 145) + '....';
        } else {
          blog.preview = blog.description;
        }
        blog.formattedDate = this.datePipe.transform(
          blog.created_at,
          'MMM d, y, h:mm:ss a'
        );
      }); */
    },
      (error) => {
        console.error('Error loading publications:', error);
      });
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

  handleSortedBlogs(sortedBlogs) {
    this.searchResult = sortedBlogs;
  }

  handleSearch(searchTerm: string) {
    this.searchResult = this.blogs.filter((blog) => {
      return (
        blog.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        blog.author.toLowerCase().includes(searchTerm.toLowerCase()) ||
        blog.content.toLowerCase().includes(searchTerm.toLowerCase())
      );
    });
  }

  
}
