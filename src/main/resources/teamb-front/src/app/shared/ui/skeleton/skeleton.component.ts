import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-skeleton',
  templateUrl: './skeleton.component.html',
  styleUrl: './skeleton.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SkeletonComponent implements OnInit, OnDestroy {

  constructor(){
    
  }

  ngOnInit(): void {
    console.log("skeleton started")
  }
  ngOnDestroy(): void {
   console.log("skeleton destroyed")
  }

}
