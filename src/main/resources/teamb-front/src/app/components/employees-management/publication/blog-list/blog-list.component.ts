import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { SortPipe } from '../../../../pipes/sort.pipe';
import { PublicationService } from '../../../../shared/services/publication.service';
import { Router } from '@angular/router';
import { RatingService } from '../../../../shared/services/rating.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/ui/alert.service';
import { MessageService } from 'primeng/api';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

interface Filters {
  name: string;
  code: string;
}

@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styles: [ `
  :host {
      @keyframes slidedown-icon {
          0% {
              transform: translateY(0);
          }

          50% {
              transform: translateY(20px);
          }

          100% {
              transform: translateY(0);
          }
      }

      .slidedown-icon {
          animation: slidedown-icon;
          animation-duration: 3s;
          animation-iteration-count: infinite;
      }

      .box {
          background-image: radial-gradient(var(--primary-300), var(--primary-600));
          border-radius: 50% !important;
          color: var(--primary-color-text);
      }
  }
`],
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
  options:Filters[]
  private blogSubscription: Subscription;
  private upvoteSubscription: Subscription;
  private downvoteSubscription: Subscription;
  private updateVotesCountSubscription: Subscription;
  sanitizedContent: SafeHtml | undefined;

  constructor(
    private router: Router,
    private blogService: PublicationService,
    private ratingService: RatingService,
    public alertService:AlertService,
    private messageService: MessageService,
    private sanitizer: DomSanitizer
  ) {
   
  }


  ngOnInit() {
    this.options = [
      { name: 'Newest', code: 'new' },
      { name: 'Oldest', code: 'old' }
  ];
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
          // Ensure userVoted and vote properties are set correctly
          blog.userVoted = blog.userVoted || false;
          blog.vote = blog.vote || false;
          // Sanitize the content
          blog.description = this.sanitizeContent(blog.description);
          this.isLoading = false;
          return blog;
        });
        this.handleSortedBlogs();
      },
      (error) => {
        this.isLoading = false;
        console.error('Error loading publications:', error);
      }
    );
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

  sanitizeContent(content: string): string {
    // Check if the content contains HTML tags
    const containsHtml = /<[a-z/][\s\S]*>/i.test(content);
  
    // Truncate the content if it's plain text
    if (!containsHtml && content.length > 150) {
      return content.substring(0, 145) + '....';
    }
  
    // If it contains HTML, sanitize and truncate it
    if (containsHtml) {
      // Create a div element
      const div = document.createElement('div');
  
      // Set the innerHTML of the div to your content
      div.innerHTML = content;
  
      // Get the text content from the div
      const textContent = div.textContent || '';
  
      // Truncate the text content
      const truncatedHtml = this.truncateText(textContent, 150);
  
      return truncatedHtml;
    }
  
    // If it's plain text, return truncated text
    return content.length > 150 ? content.substring(0, 145) + '....' : content;
  }
  
  truncateText(text: string, maxLength: number): string {
    return text.length > maxLength ? text.substring(0, maxLength) + '....' : text;
  }
  
  


}
