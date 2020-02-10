import {
  Component,
} from '@angular/core';

import { ToasterConfig } from 'angular2-toaster';
import { environment } from 'environments/environment';

import { Alert, AlertService } from './alert.service';

import { Logger } from '../core/logger.service';

@Component({
  selector: 'alert-display',
  templateUrl: './alert-display.component.html',
  styleUrls: ['./alert-display.component.css'],
  animations: [
  ],
})
export class AlertDisplayComponent {
  public toasterconfig: ToasterConfig = new ToasterConfig({
    limit: environment.enableBEUI ? 1 : 4,
    positionClass: 'toast-top-center',
    defaultTypeClass: 'flash',
    animation: 'fade',
    tapToDismiss: true,
    preventDuplicates: true,
    mouseoverTimerStop: true,
    showCloseButton: {
      'error': true
    }
  });
  constructor(
    private log: Logger,
    private alert: AlertService
  ) {

  }

  get pins() {
    return this.alert.pins;
  }

  closePin(pin: Alert) {
    this.alert.closePin(pin);
  }

  get flashes() {
    return this.alert.flashes;
  }

  closeFlash(flash: Alert) {
    return this.alert.closeFlash(flash);
  }
}
