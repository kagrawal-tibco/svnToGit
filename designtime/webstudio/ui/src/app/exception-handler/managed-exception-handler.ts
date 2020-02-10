import { ErrorHandler } from '@angular/core';
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AlertService } from '../core/alert.service';
import { Logger } from '../core/logger.service';
import { Notification } from '../models/notification';

@Injectable()
export class ManagedExceptionHandler extends ErrorHandler {
  constructor(
    private log: Logger,
    private alert: AlertService,
    public i18n: I18n
  ) {
    super();
  }
  handleError(error: any) {
    let detail = '';
    if (error.rejection) {
      detail = error.rejection.message;
    } else {
      detail = error.message;
    }

    const message = this.i18n('An error has been detected in the browser.Before continuing, we recommend that you save any unsaved work.\nError message: ') + detail;
    const notification: Notification = <Notification>{
      id: 'errorid',
      read: false,
      content: message,
      link: ['/'],
      timestamp: Date.now().toString(),
      details: error,
      severity: 'danger',
      icon: 'fa fa-exclamation-circle'
    };
    this.alert.flashDetail(notification, 'error', true, -1, 'Browser error');
    super.handleError(error);
  }
}
