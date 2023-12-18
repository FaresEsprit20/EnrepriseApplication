import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationService } from '../../../../shared/services/publication.service';
import { RatingService } from '../../../../shared/services/rating.service';
import { ToastService } from '../../../../shared/ui/toast.service';

@Component({
  selector: 'app-blog-details',
  templateUrl: './blog-details.component.html',
  styleUrls: ['./blog-details.component.css'],
})
export class BlogDetailsComponent implements OnInit, OnDestroy {

  blog: any;
  isLoaded: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private blogService: PublicationService,
    private ratingService: RatingService,
    private cdRef: ChangeDetectorRef,
    private toastService: ToastService
  ) {}

  ngOnDestroy(): void {
    this.blogService.ngOnDestroy();
    this.ratingService.ngOnDestroy();
    this.cdRef.detach();
  }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.blogService.findPublicationById(id).subscribe(
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
    this.ratingService.upvotePublication(id).subscribe(
      () => {
        this.updateVotesCount(id,true,true);
        this.toastService.showToast('Upvoted successfully!');
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
        this.updateVotesCount(id,true,false);
        this.toastService.showToast('Downvoted successfully!');
        this.cdRef.detectChanges();
      },
      (error) => {
        console.error('Error downvoting publication:', error);
        this.toastService.showToast('Error downvoting. Please try again.');
      }
    );
  }

  private updateVotesCount(publicationId: number, userVoted: boolean, vote: boolean): void {
    this.ratingService.getVoteCounts(publicationId).subscribe(
      (votesCount) => {
        // Assuming this.blog is the single blog you are displaying
        this.blog = { ...this.blog, upVotes: votesCount.upVotes, downVotes: votesCount.downVotes, userVoted: userVoted, vote: vote };
        this.cdRef.detectChanges();
      },
      (error) => {
        console.error('Error fetching votes count:', error);
      }
    );
  }
  

  
}
