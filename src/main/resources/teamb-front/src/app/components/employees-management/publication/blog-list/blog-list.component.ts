import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { SortPipe } from '../../../../pipes/sort.pipe';
import { PublicationService } from '../../../../shared/services/publication.service';
import { Router } from '@angular/router';
import { RatingService } from '../../../../shared/services/rating.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/ui/alert.service';
import { MessageService } from 'primeng/api';




@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css'],
  providers: [SortPipe,MessageService],
  changeDetection: ChangeDetectionStrategy.Default,
})
export class BlogListComponent implements OnInit, OnDestroy {
  
  isLoading: boolean = true;
  blogs: any;
  searchResult: any;
  selectedSortOption = 'new';
  sortedBlogs: any[];
  private blogSubscription: Subscription;
  private upvoteSubscription: Subscription;
  private downvoteSubscription: Subscription;
  private updateVotesCountSubscription: Subscription;

  constructor(
    private router: Router,
    private blogService: PublicationService,
    private ratingService: RatingService,
    public alertService:AlertService,
    private messageService: MessageService
  ) {
   
  }


  ngOnInit() {
    this.loadBlogs()
  }

  ngOnDestroy(): void {
   this.blogSubscription?.unsubscribe()
   this.upvoteSubscription?.unsubscribe()
   this.downvoteSubscription?.unsubscribe()
   this.updateVotesCountSubscription?.unsubscribe()
  }

  navigateToAddBlog() {
    this.router.navigate(['/employees/blogs/create']);
  }

  loadBlogs(): void {
    this.isLoading = true;
    this.blogSubscription = this.blogService.findAllPublications().subscribe(
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
        this.handleSortedBlogs()
      },
      (error) => {
        this.isLoading = false;
        console.error('Error loading publications:', error);
      });
  }




onUpvote(id: number): void {
  console.log(`[BlogListComponent] Initiating upvote for publicationId: ${id}`);

  this.upvoteSubscription = this.ratingService.upvotePublication(id).subscribe(
    () => {
      console.log(`[BlogListComponent] Upvote successful for publicationId: ${id}`);
      this.updateVotesCount(id, true, true);
      //this.toastService.showToast('Upvoted successfully!');
      this.alertService.setInfoToast('Upvoted successfully',"Upvote")
      this.messageService.add(this.alertService.message)
    },
    (error) => {
      console.error(`[BlogListComponent] Error upvoting publicationId: ${id}`, error);
      //this.toastService.showToast('Error upvoting. Please try again.');
      this.alertService.setErrorToast('Error upvoting. Please try again.', "Error")
      this.messageService.add(this.alertService.message)
     
    },
    () => {
      console.log(`[BlogListComponent] Upvote request completed for publicationId: ${id}`);
    }
  );
}



onDownvote(id: number): void {
    console.log(`[BlogListComponent] Initiating downvote for publicationId: ${id}`);
  
    this.downvoteSubscription = this.ratingService.downvotePublication(id).subscribe(
      () => {
        console.log(`[BlogListComponent] Downvote successful for publicationId: ${id}`);
        this.updateVotesCount(id, true, false);
       // this.toastService.showToast('Downvoted successfully!');
       this.alertService.setErrorToast('Downvoted successfully!',"Downvote")
       this.messageService.add(this.alertService.message)
      },
      (error) => {
        console.error(`[BlogListComponent] Error downvoting publicationId: ${id}`, error);
        //this.toastService.showToast('Error downvoting. Please try again.');
        this.alertService.setErrorToast('Error downvoting. Please try again.','Error')
        this.messageService.add(this.alertService.message)
      },
      () => {
        console.log(`[BlogListComponent] Downvote request completed for publicationId: ${id}`);
      }
    );
  }

  
  private updateVotesCount(publicationId: number, userVoted:boolean, vote:boolean): void {
    this.updateVotesCountSubscription =  this.ratingService.getVoteCounts(publicationId).subscribe(
      (votesCount) => {
        const updatedBlogs = this.blogs.map((blog) =>
          blog.id === publicationId
            ? { ...blog, upVotes: votesCount.upVotes, downVotes: votesCount.downVotes, userVoted: userVoted, vote:  vote}
            : blog
        );
        this.blogs = updatedBlogs;
       // this.cdRef.detectChanges();
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
    if(this.searchResult?.length ==0 ){
      this.alertService.setWarnMessages('No results found. Please try a different search.')
    }
    this.handleSortedBlogs();
  }



}
