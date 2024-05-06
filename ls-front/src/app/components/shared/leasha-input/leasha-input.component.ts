import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-leasha-input',
  templateUrl: './leasha-input.component.html',
  styleUrl: './leasha-input.component.scss',
})
export class LeashaInputComponent {
  @Input() label?: string;
  @Input() type: string = 'text';
  @Input() id?: string;
  @Input() name?: string;
  @Input() placeholder?: string;

  value: string = '';
}
