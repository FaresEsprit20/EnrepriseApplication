import { ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-downvote',
  templateUrl: './downvote.component.html',
  styleUrls: ['./downvote.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DownvoteComponent implements OnInit, OnDestroy{
 
  @Input() id: number;
  @Input() downvotes: number;
  @Input() userVoted: boolean;
  @Input() count: number = 0;  
  @Input() vote: boolean;
  @Output() voteChange = new EventEmitter<number>();

  constructor(){}

  downvote(): void {
    if (!this.userVoted || this.vote) {
      console.log('DownvoteComponent - emitted');
      this.userVoted = false;
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
