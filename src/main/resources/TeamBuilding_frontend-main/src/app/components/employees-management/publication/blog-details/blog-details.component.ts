import { Component, OnDestroy, OnInit, Optional } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationService } from 'src/app/shared/services/publication.service';
import { RatingService } from 'src/app/shared/services/rating.service';


@Component({
  selector: 'app-blog-details',
  templateUrl: './blog-details.component.html',
  styleUrls: ['./blog-details.component.css'],
})
export class BlogDetailsComponent implements OnInit,OnDestroy {
  blog: any;
  formattedDate: string | null = '';
  

  constructor(
    private route: ActivatedRoute,
    private blogService: PublicationService,
    private ratingService: RatingService
  ) {}

  ngOnDestroy(): void {
    this.blogService.ngOnDestroy()
    this.ratingService.ngOnDestroy()
  }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.blogService.findPublicationById(id).subscribe((res) => {
      console.log(res)
      this.blog = res;
    },
      (error) => {
        console.error('Error loading publication:', error);
      });
  }


  onVote(vote:any) {
    
    const id = this.route.snapshot.params['id'];
    this.ratingService.upvotePublication(id).subscribe((res) => {
      console.log(res)
      this.blog = res;
    },
      (error) => {
        console.error('Error loading publication:', error);
      });
    
  }



  
}
