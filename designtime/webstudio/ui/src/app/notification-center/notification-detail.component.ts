import { Component, Input } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from '../../environments/environment';
import { RestService } from '../core/rest.service';
import { BEBuildInfoRecord } from '../models/dto';
import { Notification } from '../models/notification';

@Component({
  selector: 'notification-detail',
  templateUrl: './notification-detail.component.html',
  styleUrls: ['./notification-detail.component.css']
})
export class NotificationDetailComponent {

  @Input()
  public notification: Notification;

  constructor(private _rest: RestService, public i18n: I18n) { }

  get severity() {
    switch (this.notification.icon) {
      case 'danger':
        return 'Error';
      case 'success':
      case 'info':
        return 'Info';
      case 'warning':
        return 'Warning';
    }
    return 'Unknown';
  }

  get dateTimeString() {
    return new Date(Number(this.notification.timestamp)).toISOString();
  }

  get buildVersion() {
    const buildInfo = this._rest.getBuildInfo();
    if (buildInfo) {
      if (environment.enableBEUI) {
        return this._rest.buildInfo.version + '.' + (<BEBuildInfoRecord>buildInfo).build;
      } else {
        return this._rest.buildInfo.version;
      }
    } else {
      return '';
    }
  }

  get buildDate() {
    const buildInfo = this._rest.getBuildInfo();
    if (buildInfo) {
      if (environment.enableBEUI) {
        return (<BEBuildInfoRecord>buildInfo).buildDate;
      } else {
        return this._rest.buildInfo.timestamp;
      }
    } else {
      return '';
    }
  }

  get commitInfo() {
    const buildInfo = this._rest.getBuildInfo();
    if (buildInfo) {
      if (environment.enableBEUI) {
        return (<BEBuildInfoRecord>buildInfo).gitCommitId;
      } else {
        return this._rest.buildInfo.gitCommitId;
      }
    } else {
      return '';
    }
  }

  copyToClipboard() {
    const table = document.getElementById('detailTable');
    let range, sel;
    if (document.createRange && window.getSelection) {
      range = document.createRange();
      sel = window.getSelection();
      sel.removeAllRanges();
      try {
        range.selectNodeContents(table);
        sel.addRange(range);
      } catch (e) {
        range.selectNode(table);
        sel.addRange(range);
      }
      /* Copy the text inside the text field */
      document.execCommand('Copy');
      sel.removeAllRanges();
    }
  }

}
