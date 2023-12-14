import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';

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
  @Output() vote = new EventEmitter<number>();

  downvote(): void {
    console.log("emitted")
      this.vote.emit(this.id);
  }

  ngOnInit() {
    console.log('Downvote component initialized');
  }
  ngOnDestroy(): void {
    console.log('Downvote component Destroyed');
  }


  
}
