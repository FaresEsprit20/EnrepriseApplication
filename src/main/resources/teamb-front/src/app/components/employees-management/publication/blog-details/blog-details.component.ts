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
    console.log('Upvote event received for ID:', id);
    this.ratingService.upvotePublication(id).subscribe(
      () => {
        console.log('Upvoted successfully!');
        this.updateVotesCount(id);
        this.toastService.showToast('Upvoted successfully!');
      },
      (error) => {
        console.error('Error upvoting publication:', error);
        this.toastService.showToast('Error upvoting. Please try again.');
      }
    );
  }

  onDownvote(id: number): void {
    console.log('Downvote event received for ID:', id);
    this.ratingService.downvotePublication(id).subscribe(
      () => {
        console.log('Downvoted successfully!');
        this.updateVotesCount(id);
        this.toastService.showToast('Downvoted successfully!');
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
        console.log('Votes count updated:', votesCount);
        this.blog = {
          ...this.blog,
          upVotes: votesCount.upVotes,
          downVotes: votesCount.downVotes,
        };
        this.cdRef.detectChanges();
      },
      (error) => {
        console.error('Error fetching votes count:', error);
      }
    );
  }
  
}
