import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-leasha-button',
  templateUrl: './leasha-button.component.html',
  styleUrl: './leasha-button.component.scss',
})
export class LeashaButtonComponent {
  @Input() routerLink?: string | any[] | null;
  @Input() disabled?: boolean;
  @Input() modalId?: string;
  @Input() buttonText?: string;
  @Input() customClass?: string;
  @Input() type: string = 'button';

  @Output() clickEvent = new EventEmitter<void>();

  getButtonClasses() {
    let classes = 'btn btn-style';
    if (this.customClass) {
      classes += ` ${this.customClass}`;
    }
    return classes;
  }

  getButtonType(): string {
    return this.type ?? 'button';
  }

  onClick() {
    this.clickEvent.emit();
  }
}
