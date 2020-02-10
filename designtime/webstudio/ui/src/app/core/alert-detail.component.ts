import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Toast } from 'angular2-toaster';

import { Notification } from '../models/notification';

@Component({
  selector: 'app-alert-detail',
  templateUrl: './alert-detail.component.html',
  styleUrls: ['./alert-detail.component.css']
})
export class AlertDetailComponent implements OnInit {

  public toast: Toast = null;
  public expanded = false;

  constructor(public i18n: I18n) { }

  ngOnInit() {
  }

  get message() {
    const data = this.toast.data as Notification;
    return data.content;
  }
  toggle() {
    this.expanded = !this.expanded;
  }
}
