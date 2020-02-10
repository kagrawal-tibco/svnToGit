import { Injectable } from '@angular/core';

import { BodyOutputType, Toast, ToasterService } from 'angular2-toaster';

import { AlertDetailComponent } from './alert-detail.component';

import { Logger } from '../core/logger.service';
import { Notification } from '../models/notification';

export type AlertType = 'info' | 'success' | 'warning' | 'error';

export interface Alert {
  type: AlertType;
  msg: string;
  closable?: boolean;
  state: 'hidden' | 'show';
  displayed?: boolean;
}

/**
 * For delivering messages, including errors, to user
 */
@Injectable()
export class AlertService {
  flashes: Alert[] = [];
  pins: Alert[] = [];
  constructor(
    private toaster: ToasterService,
    private log: Logger
  ) {
  }

  init() {
    this.flashes = [];
    this.pins = [];
  }

  pin(msg: string, pinType: AlertType, closable: boolean) {
    this.pins.push({
      msg: msg,
      type: pinType,
      closable: closable,
      state: 'show',
    });
  }

  closePin(pin: Alert) {
    pin.state = 'hidden';
    const idx = this.pins.findIndex(p => p === pin);
    if (idx !== -1) {
      this.pins.splice(idx, 1);
    }
  }

  flash(msg: string, flashType: AlertType, closable = true, ttl = 5000, title = ''): Promise<Toast> {
    if (flashType === 'error' || flashType === 'warning') {
      this.log.err(msg);
    }
    return this.toaster.popAsync({
      type: flashType,
      title: title,
      timeout: ttl,
      body: msg,
      bodyOutputType: BodyOutputType.TrustedHtml
    }).toPromise();
  }

  flashDetail(notification: Notification, flashType: AlertType, closable = true, ttl = 5000, title = ''): Promise<Toast> {
    if (flashType === 'error' || flashType === 'warning') {
      this.log.err(notification.details);
    }
    return this.toaster.popAsync({
      type: flashType,
      title: title,
      timeout: ttl,
      body: AlertDetailComponent,
      bodyOutputType: BodyOutputType.Component,
      data: notification,
      showCloseButton: true,
      clickHandler: (toast, isCloseButton) => {
        return isCloseButton;
      }
    }).toPromise();
  }

  closeFlash(flash: Alert) {
    flash.state = 'hidden';
    const idx = this.flashes.findIndex(f => f === flash);
    if (idx !== -1) {
      this.flashes.splice(idx, 1);
    }
  }
}
