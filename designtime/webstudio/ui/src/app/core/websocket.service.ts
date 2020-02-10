
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment.prod';
import { interval as observableInterval, timer as observableTimer, BehaviorSubject, Observable, ReplaySubject, Subject, Subscription } from 'rxjs';
import { filter, map, take, takeWhile } from 'rxjs/operators';

import { AlertService } from './alert.service';
import { ArtifactService } from './artifact.service';
import { AuthStateService } from './auth-state.service';
import { Logger } from './logger.service';
import { ProjectService } from './project.service';
import { RestService } from './rest.service';

import { Notification } from '../models/notification';

export type Kind = 'ERROR' | 'INFO' | 'NOTIFICATION' | 'INIT' | 'OPEN' | 'RESPONSE' | 'HEARTBEAT' | 'CLEANUP';

export interface Message {
  kind: Kind;
  payload: string;
}

export interface MarkAsRead {
  ids: string[];
}

@Injectable()
export class WebSocketService {

  get isReady(): BehaviorSubject<boolean> {
    return this._isReady;
  }

  get webSocketId(): string {
    return this._webSocketId;
  }
  private socket: WebSocket;
  private heartbeat: Subscription;
  private heartbeatInterval = 30000;
  private heartbeatMessage = JSON.stringify({ kind: 'HEARTBEAT' });
  private inFlyHeartbeatCount = 0;
  private missedHeartbeatCap = 5;
  private failureCnt = 0;
  private failureCap = 3;

  private notifications: Subject<Notification>;
  private channel: Subject<Message>;
  private message = new ReplaySubject<Message>();

  private _isReady = new BehaviorSubject<boolean>(false);
  private _webSocketId: string;

  constructor(
    protected rest: RestService,
    protected project: ProjectService,
    protected authState: AuthStateService,
    protected alert: AlertService,
    protected log: Logger,
    protected artifact: ArtifactService,
    public i18n: I18n
  ) { }

  init(): Promise<void> {
    this._isReady.next(false);
    if (WebSocket) {
      return this.makeWebSocketConnection().pipe(
        filter(ok => ok),
        take(1),
        map(ok => this.log.debug('Establish WebSocket connection successfully')))
        .toPromise();
    } else {
      this.log.warn('Browser does not support WebSocket, notifiaction service might not be usable.');
      return Promise.resolve();
    }
  }

  reset() {
    if (this.socket) {
      this.socket.close(1000, 'User logout.');
      this.socket = null;
    }
    if (this.heartbeat) {
      this.heartbeat.unsubscribe();
      this.heartbeat = null;
    }
  }

  /**
   * Subscribe messages
   */
  subscribe(subscriber: (Message) => void): Subscription {
    return this.channel.subscribe(subscriber);
  }

  messages(): Observable<Message> {
    return this.message;
  }

  /**
   * Mark the notification as read
   */
  markAsRead(ids: string[]) {
    this.socket.send(JSON.stringify(<MarkAsRead>{ ids: ids }));
  }

  /**
   * Make WebSocket connection
   * @return {Promise<boolean>}      [will be resolved when the WebSocket is READY]
   */
  private makeWebSocketConnection(): Observable<boolean> {
    const url = this.createWebSocketURL(this.authState.currentState.authToken);
    // reset previous connection and clear heartbeat subscription
    this.reset();
    this.notifications = new Subject<Notification>();
    this.channel = new Subject<Message>();
    this.socket = new WebSocket(url);
    if (this.socket) {
      this.setUpHandlers();
      this.setUpHeartbeat();
    }
    return this.isReady;
  }

  private setUpHeartbeat() {
    this.heartbeat = observableInterval(this.heartbeatInterval).pipe(
      // will stop pinging if missed too many pings or connection closed.
      takeWhile(() => this.socket && this.socket.readyState === this.socket.OPEN && this.inFlyHeartbeatCount <= this.missedHeartbeatCap))
      .subscribe(() => {
        // increment the ping counter
        this.inFlyHeartbeatCount++;
        this.socket.send(this.heartbeatMessage);
      });
  }

  private handleMessage(msg: Message) {
    if (msg.kind === 'RESPONSE') {
      this.message.next(msg);
    } else if (msg.kind === 'OPEN') {
      this._webSocketId = msg.payload;
      // tell others the socket is ready to be used.
      this._isReady.next(true);
    } else if (msg.kind === 'CLEANUP') {
      this.artifact.markArtifactsStaleByProject(msg.payload);
      this.alert.flash('Updated project \'' + msg.payload + '\'. Reopen the editor to see changes.', 'success');
    } else {
      this.channel.next(msg);
    }
  }

  private setUpHandlers() {
    this.socket.onmessage = (evt: MessageEvent) => {
      let msg: Message;
      try {
        msg = JSON.parse(evt.data);
      } catch (e) {
        this.log.warn('Unable to parse incoming message because:', e);
      }
      if (msg.kind === 'HEARTBEAT') {
        // every time receive a pong, clear the counter
        this.inFlyHeartbeatCount = 0;
      } else {
        this.handleMessage(msg);
      }
    };
    this.socket.onopen = (evt: Event) => {
      this.failureCnt = 0;
      this.log.debug('socket open');
    };

    this.socket.onclose = (evt: CloseEvent) => {
      this.log.debug('socket close because:', evt);
      switch (evt.code) {
        // logout
        case 1000:
          this.socket = null;
          if (this.heartbeat) {
            this.heartbeat.unsubscribe();
            this.heartbeat = null;
          }
          break;
        // auth fail
        case 1008:
          this.authState.logout();
          this.rest.deleteUserCookies();
          if (evt.reason.search('Authentication Failed') !== -1) {
            this.alert.flash(this.i18n('Authentication Failed, User is not logged in.'), 'error', true, -1);
          } else {
            this.alert.flash(evt.reason, 'warning');
          }
          break;
        // server shut down
        case 1006:
          if (this.failureCnt < this.failureCap) {
            // exponetial backoff
            observableTimer(1000 * Math.pow(2, this.failureCnt)).toPromise().then(() => this.init());
            this.failureCnt++;
          } else {
            if (environment.enableTCEUI) {
              this.alert.flash(this.i18n('Error connecting to server. TIBCO Cloud Events WebStudio Server has shut down.'), 'warning');
            } else {
              this.alert.flash(this.i18n('Error connecting to server. TIBCO BusinessEvents WebStudio Server has shut down.'), 'warning');
            }
            this.authState.logout();
            this.rest.deleteUserCookies();
          }
          break;
      }
    };

    this.socket.onerror = (evt: ErrorEvent) => {
      this.log.debug('socket error because:', evt);
    };
  }

  private createWebSocketURL(authToken: string): string {
    const l = window.location;
    const secChannel = environment.enableTCEUI ? 'WS_CH_WebstudioChannel' : 'WS_CH_Secure_WebstudioChannel';
    let ret = (l.protocol === 'https:') ?
      `wss://${l.host}/WebStudio/WebStudio/Core/Channels/${secChannel}/WS_DS_SUBSCRIBE` :
      `ws://${l.host}/WebStudio/WebStudio/Core/Channels/WS_CH_WebstudioChannel/WS_DS_SUBSCRIBE`;
    ret += ('?token=' + authToken);
    return ret;
  }
}
