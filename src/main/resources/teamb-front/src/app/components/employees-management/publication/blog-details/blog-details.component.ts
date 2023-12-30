import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationService } from '../../../../shared/services/publication.service';
import { RatingService } from '../../../../shared/services/rating.service';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/ui/alert.service';
import { MessageService } from 'primeng/api';
import { MenuItem } from 'primeng/api';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-blog-details',
  templateUrl: './blog-details.component.html',
  styles: [
    `
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
`
  ],
  styleUrls: ['./blog-details.component.css'],
  providers:[MessageService],
  changeDetection: ChangeDetectionStrategy.Default,
})
export class BlogDetailsComponent implements OnInit, OnDestroy {

  blog: any;
  isLoaded: boolean = false;
  private blogSubscription: Subscription;
  private ratingUpdateSubscription: Subscription;
  private ratingUpSubscription: Subscription;
  private ratingDownSubscription: Subscription;
  items: MenuItem[] | undefined;
  activeItem: MenuItem | undefined;
  sanitizedContent: SafeHtml | undefined;

  constructor(
    private route: ActivatedRoute,
    private blogService: PublicationService,
    private ratingService: RatingService,
    private alertService:AlertService,
    private messageService:MessageService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnDestroy(): void {
   this.blogSubscription?.unsubscribe()
   this.ratingUpdateSubscription?.unsubscribe()
   this.ratingUpSubscription?.unsubscribe()
   this.ratingDownSubscription?.unsubscribe()
  }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];

   this.blogSubscription = this.blogService.findPublicationById(id).subscribe(
      (res) => {
        console.log('Publication loaded:', res);
        this.blog = res;
        this.blog.userVoted = this.blog.userVoted || false;
        this.blog.vote = this.blog.vote || false;
        this.sanitizedContent = this.sanitizeContent(this.blog.description);
        this.isLoaded = true;
      },
      (error) => {
        console.error('Error loading publication:', error);
        this.isLoaded = true;
      }
    );
  }

  onUpvote(id: number): void {
    console.log(`[BlogListComponent] Initiating upvote for publicationId: ${id}`);
  
    this.ratingUpSubscription = this.ratingService.upvotePublication(id).subscribe(
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
    
       this.ratingDownSubscription = this.ratingService.downvotePublication(id).subscribe(
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

    
    private updateVotesCount(publicationId: number, userVoted: boolean, vote: boolean): void {
   this.ratingUpdateSubscription =  this.ratingService.getVoteCounts(publicationId).subscribe(
        (votesCount) => {
          this.blog = {
            ...this.blog,
            upVotes: votesCount.upVotes,
            downVotes: votesCount.downVotes,
            userVoted: userVoted,
            vote: vote
          };
          // Trigger change detection
          this.blog = { ...this.blog }; // Create a new object reference
          console.log(" testing the change")
          console.log(this.blog)
        },
        (error) => {
          console.error('Error fetching votes count:', error);
        }
      );
    }
    
  
    sanitizeContent(content: string): SafeHtml | string {
      // Check if the content contains HTML tags
      const containsHtml = /<[a-z/][\s\S]*>/i.test(content);
  
      if (containsHtml) {
        // If it contains HTML, sanitize it
        return this.sanitizer.bypassSecurityTrustHtml(content);
      } else {
        // If it's plain text, return it as is
        return content;
      }
    }


}
