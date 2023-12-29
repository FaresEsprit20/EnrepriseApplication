import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchComponent implements OnInit, OnDestroy {
  
  @Input() searchTerm = '';
  @Output() search = new EventEmitter<string>();

  onSearchTermChange() {
    this.search.emit(this.searchTerm);
  }

  ngOnInit() {
    console.log('')
  }

  ngOnDestroy(): void {
    console.log('search destroyed')
  }
}
