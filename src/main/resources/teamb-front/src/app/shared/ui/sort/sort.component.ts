import { Component, Input, Output, EventEmitter, OnInit, OnDestroy, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-sorting',
  templateUrl: './sort.component.html',
  styleUrls: ['./sort.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SortingComponent implements OnInit,OnDestroy {

  
  @Input() selectedSortOption: string;
  @Output() sortOptionChange = new EventEmitter<string>();

  ngOnInit(): void {
    // Set the default value to "new" if not provided
    if (!this.selectedSortOption) {
      this.selectedSortOption = 'new';
      this.emitSortOption();
    }
  }

  onSortOptionChange(): void {
    this.selectedSortOption = this.selectedSortOption === 'new' ? 'old' : 'new';
    this.emitSortOption();
  }

  private emitSortOption(): void {
    this.sortOptionChange.emit(this.selectedSortOption);
  }

  ngOnDestroy(): void {
    console.log('sort destroyed')
  }

  
}