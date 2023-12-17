import { ChangeDetectorRef, Component, OnDestroy, OnInit, NgZone } from '@angular/core';
import { SortPipe } from '../../../../pipes/sort.pipe';
import { PublicationService } from '../../../../shared/services/publication.service';
import { Router } from '@angular/router';
import { RatingService } from '../../../../shared/services/rating.service';
import { ToastService } from '../../../../shared/ui/toast.service';




@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css'],
  providers: [SortPipe],
})
export class BlogListComponent implements OnInit, OnDestroy{
  
  isLoading: boolean = true;
  blogs: any;
  searchResult: any;
  selectedSortOption = 'new';
  sortedBlogs: any[];

  constructor(
    private router: Router,
    private blogService: PublicationService,
    private ratingService: RatingService,
    private cdRef: ChangeDetectorRef,
    private toastService:ToastService,
    private zone: NgZone
  ) {
   
  }


  ngOnInit() {
    this.loadBlogs()
  }

  ngOnDestroy(): void {
    this.blogService.ngOnDestroy()
    this.ratingService.ngOnDestroy()
    this.cdRef.detach()
  }

  navigateToAddBlog() {
    this.router.navigate(['/employees/blogs/create']);
  }

  loadBlogs(): void {
    this.isLoading = true;
    this.blogService.findAllPublications().subscribe(
      (data: any[]) => {
        this.blogs = data.map(blog => {
          // Truncate description if it's longer than 100 characters
          blog.description = blog.description.length > 100
            ? blog.description.substring(0, 145) + '....'
            : blog.description;
          // Ensure userVoted and vote properties are set correctly
          blog.userVoted = blog.userVoted || false;
          blog.vote = blog.vote || false;
          this.isLoading = false;
          return blog;
        });
      },
      (error) => {
        this.isLoading = false;
        console.error('Error loading publications:', error);
      });
  }

  
  onUpvote(id: number): void {
    this.ratingService.upvotePublication(id).subscribe(
      () => {
        this.updateVotesCount(id);
        this.toastService.showToast('Upvoted successfully!');
        this.zone.run(() => {}); // NgZone.run
        this.cdRef.detectChanges();
      },
      (error) => {
        console.error('Error upvoting publication:', error);
        this.toastService.showToast('Error upvoting. Please try again.');
      }
    );
  }

  onDownvote(id: number): void {
    this.ratingService.downvotePublication(id).subscribe(
      () => {
        this.updateVotesCount(id);
        this.toastService.showToast('Downvoted successfully!');
        this.zone.run(() => {}); // NgZone.run
        this.cdRef.detectChanges();
      },
      (error) => {
        console.error('Error downvoting publication:', error);
        this.toastService.showToast('Error downvoting. Please try again.');
      }
    );
  }

  private updateVotesCount(publicationId: number): void {
    this.ratingService.getVoteCounts(publicationId).subscribe(
      (votesCount) => {
        const updatedBlogs = this.blogs.map((blog) =>
          blog.id === publicationId
            ? { ...blog, upVotes: votesCount.upVotes, downVotes: votesCount.downVotes }
            : blog
        );
        this.blogs = updatedBlogs;
        this.zone.run(() => {}); // NgZone.run
        this.cdRef.detectChanges();
      },
      (error) => {
        console.error('Error fetching votes count:', error);
      }
    );
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



}
