import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-upvote',
  templateUrl: './upvote.component.html',
  styleUrls: ['./upvote.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UpvoteComponent implements OnInit, OnDestroy {

  @Input() id: number;
  @Input() upvotes: number;
  @Input() userVoted: boolean;
  @Input() vote: boolean;
  @Output() voteChange = new EventEmitter<number>();

  constructor(){}


  upvote(): void {
    if (!this.userVoted || !this.vote) {
      console.log('UpvoteComponent - emitted');
      this.userVoted = true;
      this.vote = true;
      console.log('UpvoteComponent - userVoted:', this.userVoted, 'vote:', this.vote);
      this.voteChange.emit(this.id);
    }
  }

  
  ngOnInit() {
    console.log('upVote component initialized');
  }

  ngOnDestroy(): void {
    console.log('Downvote component Destroyed');
  }


}
