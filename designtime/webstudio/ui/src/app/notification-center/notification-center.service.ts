import { Injectable } from '@angular/core';

import { Subject } from 'rxjs';

import { Logger } from '../core/logger.service';
import { WebSocketService } from '../core/websocket.service';
import { Notification } from '../models/notification';

export class NotifyChange {
  notification: Notification;
}

@Injectable()
export class NotificationCenterService {

  public static notificationDialogOpen = false;

  private notifications: Notification[] = [];
  private _subscribers: Subject<NotifyChange> = new Subject<NotifyChange>();

  constructor(
    private log: Logger,
    private websocket: WebSocketService
  ) { }

  init() {
    this.notifications = [];
  }

  getNotifications(): Notification[] {
    return this.notifications;
  }

  notify(): Subject<NotifyChange> {
    return this._subscribers;
  }

  remove(notification: Notification) {
    this.websocket.markAsRead([notification.id]);
    const idx = this.notifications.indexOf(notification);
    if (idx !== -1) {
      this.notifications.splice(idx, 1);
    }
  }

  prepend(item: Notification) {
    this.notifications.unshift(item);
    this._subscribers.next({
      notification: item
    });
  }

  clearAll() {
    this.websocket.markAsRead(this.notifications.map(item => item.id));
    this.notifications = [];
  }

}
