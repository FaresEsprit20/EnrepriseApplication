import { ChangeDetectionStrategy, Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['alert.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AlertComponent {
  @Input() isVisible: boolean = false;
  @Input() alertClass: string = 'alert-primary';
  @Input() heading: string = 'Alert';
  @Input() message: string = '';
  @Input() additionalText: string = '';

  @Output() close = new EventEmitter<void>();

  closeAlert() {
    this.close.emit();
  }

  
}
