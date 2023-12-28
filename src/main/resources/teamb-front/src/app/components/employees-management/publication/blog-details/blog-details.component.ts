import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationService } from '../../../../shared/services/publication.service';
import { RatingService } from '../../../../shared/services/rating.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-blog-details',
  templateUrl: './blog-details.component.html',
  styleUrls: ['./blog-details.component.css'],
  changeDetection: ChangeDetectionStrategy.Default,
})
export class BlogDetailsComponent implements OnInit, OnDestroy {

  blog: any;
  isLoaded: boolean = false;
  private blogSubscription: Subscription;
  private ratingUpdateSubscription: Subscription;
  private ratingUpSubscription: Subscription;
  private ratingDownSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private blogService: PublicationService,
    private ratingService: RatingService
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
        this.isLoaded = true;
      },
      (error) => {
        console.error('Error loading publication:', error);
        this.isLoaded = true;
      }
    );
  }

  onUpvote(id: number): void {
  this.ratingUpSubscription = this.ratingService.upvotePublication(id).subscribe(
      () => {
        this.updateVotesCount(id,true,true);
      //  this.toastService.showToast('Upvoted successfully!');
      },
      (error) => {
        console.error('Error upvoting publication:', error);
       // this.toastService.showToast('Error upvoting. Please try again.');
      }
    );
  }

  onDownvote(id: number): void {
    this.ratingDownSubscription = this.ratingService.downvotePublication(id).subscribe(
      () => {
        this.updateVotesCount(id,true,false);
       // this.toastService.showToast('Downvoted successfully!');
      },
      (error) => {
        console.error('Error downvoting publication:', error);
       // this.toastService.showToast('Error downvoting. Please try again.');
      }
    );
  }

  private updateVotesCount(publicationId: number, userVoted: boolean, vote: boolean): void {
    this.ratingUpdateSubscription = this.ratingService.getVoteCounts(publicationId).subscribe(
      (votesCount) => {
        this.blog = { ...this.blog, upVotes: votesCount.upVotes, downVotes: votesCount.downVotes, userVoted: userVoted, vote: vote };
      },
      (error) => {
        console.error('Error fetching votes count:', error);
      }
    );
  }
  

  
}
