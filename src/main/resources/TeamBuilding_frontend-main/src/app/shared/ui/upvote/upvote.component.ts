import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-upvote',
  templateUrl: './upvote.component.html',
  styleUrls: ['./upvote.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UpvoteComponent implements OnInit, OnDestroy{

  @Input() id: number;
  @Input() upvotes: number;
  @Input() userVoted: boolean;
  @Input() count: number = 0;  
  @Output() vote = new EventEmitter<number>();

  upvote(): void {
    console.log("emitted")
      this.vote.emit(this.id);
  }

  ngOnInit() {
    console.log('upVote component initialized');
  }

  ngOnDestroy(): void {
    console.log('Downvote component Destroyed');
  }

}
