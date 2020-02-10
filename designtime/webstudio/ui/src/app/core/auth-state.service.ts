import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { environment } from 'environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';

import { Logger } from './logger.service';

import { User } from '../models/user';

export const AUTH_STATE_PARAM_NAME = 'AMS_AUTH_STATE';

export type AuthAction = 'INIT' | 'RESUME' | 'SUCCESS';

const log = new Logger();

export class Auth {
  static DEFAULT = new Auth('', {} as User, 'INIT');

  static get INITIAL_STATE() {
    const old = localStorage.getItem(AUTH_STATE_PARAM_NAME);
    if (old != null) {
      try {
        const obj: Auth = JSON.parse(old);
        return new Auth(obj.authToken, obj.userInfo, 'RESUME');
      } catch (_) {
        return Auth.DEFAULT;
      }
    } else {
      return this.DEFAULT;
    }
  }
  constructor(
    public authToken: string,
    public userInfo: User,
    public action: AuthAction
  ) { }

  clone(): Auth {
    const ret = new Auth(this.authToken, this.userInfo, this.action);
    return ret;
  }

  persist(): Auth {
    try {
      localStorage.setItem(AUTH_STATE_PARAM_NAME, this.serialize());
    } catch (e) {
      log.debug(`Your web browser does not support caching authentication token locally. \n
      In Safari, the most common cause of this is using "Private Browsing Mode".`);
    }
    return this;
  }

  serialize() {
    return JSON.stringify(this);
  }
}

@Injectable()
export class AuthStateService {
  private _state = new BehaviorSubject<Auth>(Auth.INITIAL_STATE);
  constructor(
    private router: Router
  ) { }

  success(token: string, userInfo: User) {
    const state = new Auth(token, userInfo, 'SUCCESS');
    state.persist();
    this._state.next(state);
  }

  resume() {
    const latest = this._state.getValue();
    const next = latest.clone();
    next.action = 'SUCCESS';
    this._state.next(next);
  }

  logout() {
    localStorage.removeItem(AUTH_STATE_PARAM_NAME);
    this._state.next(Auth.DEFAULT);
    if (environment.enableTCEUI) {
      const protocol = window.location.protocol;
      const hostname = window.location.hostname;
      const tceHomeLocation = protocol + '//' + hostname + '/index.html';
      window.location.href = tceHomeLocation;
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  timeout() {
    this.logout();
  }

  get state(): Observable<Auth> {
    return this._state;
  }

  get currentState(): Auth {
    return this._state.getValue();
  }
}
