import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-downvote',
  templateUrl: './downvote.component.html',
  styleUrls: ['./downvote.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DownvoteComponent implements OnInit, OnDestroy {

  @Input() id: number;
  @Input() downvotes: number;
  @Input() userVoted: boolean;
  @Input() vote: boolean;
  @Output() voteChange = new EventEmitter<number>();

  constructor(){}

  
  downvote(): void {
    console.log('DownvoteComponent - downvote method');
    console.log('DownvoteComponent - userVoted:', this.userVoted, 'vote:', this.vote);
    console.log('Downvote component initialized - userVoted:', this.userVoted, 'vote:', this.vote);
    if (!this.userVoted || this.vote) {
      console.log('DownvoteComponent - emitted');
      this.userVoted = true;
      this.vote = false;
      console.log('DownvoteComponent - userVoted:', this.userVoted, 'vote:', this.vote);
      this.voteChange.emit(this.id);
    }
  }

  ngOnInit() {
    console.log('Downvote component initialized');
  }
  
  ngOnDestroy(): void {
    console.log('Downvote component Destroyed');
  }


  
}
