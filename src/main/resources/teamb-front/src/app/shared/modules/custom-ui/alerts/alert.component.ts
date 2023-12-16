import { ChangeDetectionStrategy, Component, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['alert.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AlertComponent implements OnInit, OnDestroy {
  
  @Input() isVisible: boolean = false;
  @Input() alertClass: string = 'alert-primary';
  @Input() heading: string = 'Alert';
  @Input() message: string = '';
  @Input() additionalText: string = '';
  @Output() close = new EventEmitter<void>();

  
  closeAlert() {
    this.close.emit();
  }

  ngOnInit() {
    console.log('')
  }

  ngOnDestroy(): void {
    console.log('')
  }
  

}
