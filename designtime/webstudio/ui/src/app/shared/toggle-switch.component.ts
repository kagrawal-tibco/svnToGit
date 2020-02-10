import { AfterViewInit, Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Observable, Subscription } from 'rxjs';
declare var jQuery: any;

export type Size = 'mini' | 'small' | 'normal' | 'large';

@Component({
  selector: 'toggle-switch',
  template: `
  <mat-slide-toggle
          class="primary"
          [checked]="curr"
          [disabled]="readonly">
      </mat-slide-toggle>
  `
})
export class SwitchComponent implements OnInit, OnChanges, AfterViewInit, OnDestroy {
  public curr: boolean;

  id: number;
  @Input()
  size: Size;

  @Input()
  value: boolean;

  @Input()
  onText: string;

  @Input()
  offText: string;

  @Input()
  readonly: boolean;

  @Input()
  readonlyObs?: Observable<boolean>;

  @Output()
  valueChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  // private switch: any;
  private readonlySub: Subscription;

  constructor(public i18n: I18n) {
    // we need unique Id for each, otherwise jQuery will not be able to find it correctly.
    // this.id = SwitchComponent.cnt;
    // SwitchComponent.cnt++;
  }

  ngOnInit() {
    if (!this.onText) {
      this.onText = this.i18n('ON');
    }
    if (!this.offText) {
      this.offText = this.i18n('OFF');
    }
  }

  ngAfterViewInit() {
    this.curr = this.value;
    // this.switch = jQuery(`#switch${this.id}`).bootstrapSwitch({
    //   state: this.value,
    //   readonly: this.readonly,
    //   size: this.size,
    //   labelText: this.value ? this.offText : this.onText,
    //   onText: this.onText,
    //   onColor: 'primary',
    //   offText: this.offText,
    //   offColor: 'primary',
    //   animate: false,
    //   onSwitchChange: (event, state) => {
    //     if (state !== this.curr) {
    //       this.switch.bootstrapSwitch('labelText', state ? this.offText : this.onText);
    //       this.curr = state;
    //       this.valueChange.emit(state);
    //     }
    //   },
    // });
  }

  ngOnChanges(changes: SimpleChanges) {
    // if (this.switch) {
    //   // when it's readonly, we need to enable to toggle the value.
    //   if (changes.readonly) {
    //     this.switch.bootstrapSwitch('toggleReadonly');
    //   }
    //   if (changes.readonlyObs) {
    //     this.readonlySub = changes.readonlyObs.currentValue.subscribe(rO => {
    //       if (rO !== this.readonly) {
    //         this.readonly = rO;
    //         this.switch.bootstrapSwitch('toggleReadonly');
    //       }
    //     });
    //   }

    //   if (this.value !== this.curr) {
    //     this.curr = this.value;
    //     this.switch.bootstrapSwitch('toggleState');
    //     this.switch.bootstrapSwitch('labelText', changes.value.currentValue ? this.offText : this.onText);
    //   }
    // }
  }

  ngOnDestroy() {
    // if (this.switch) {
    //   this.switch.bootstrapSwitch('destroy');
    // }
    if (this.readonlySub) {
      this.readonlySub.unsubscribe();
    }
  }
}
