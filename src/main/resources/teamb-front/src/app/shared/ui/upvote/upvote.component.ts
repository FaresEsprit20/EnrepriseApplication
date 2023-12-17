import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';

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
  @Input() vote: boolean;
  @Output() voteChange = new EventEmitter<number>();

  ngOnChanges(changes: SimpleChanges): void {
    console.log('UpvoteComponent - ngOnChanges', changes);
  }


  upvote(): void {
    console.log("emitted");
    this.voteChange.emit(this.id);
  }

  ngOnInit() {
    console.log('upVote component initialized');
  }

  ngOnDestroy(): void {
    console.log('Downvote component Destroyed');
  }

}
