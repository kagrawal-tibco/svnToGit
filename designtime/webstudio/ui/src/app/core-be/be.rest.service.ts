
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment';
import { CookieService } from 'ng2-cookies';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { AlertService } from '../core/alert.service';
import { AuthStateService } from '../core/auth-state.service';
import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { BEUser } from '../models-be/user-be';
import { BEBuildInfoRecord } from '../models/dto';
import { Response } from '../models/response';

export const WS_INFO_USER = 'WS_INFO_USER';
export const WS_SESSION_ID = 'WS_SESSION_ID';
export const WS_INFO_DM_INSTALLED = 'WS_INFO_DM_INSTALLED';
export const WS_INFO_LOCKING_ENABLED = 'WS_INFO_LOCKING_ENABLED';

@Injectable()
export class BERestService extends RestService {
  constructor(
    http: HttpClient,
    alert: AlertService,
    authState: AuthStateService,
    log: Logger,
    cookieService: CookieService,
    i18n: I18n
  ) {
    super(http, alert, authState, log, cookieService, i18n);
  }

  initialize() {
    if (environment.enableTCEUI) {
      this.aboutUrl = encodeURI(RestService.BASE_URL + '/about.json');
    } else {
      this.aboutUrl = this.url(`about.json`);
    }
    super.initialize();
  }

  url(s: string): string {
    if (this.authState.currentState.action !== 'INIT') {
      s = '/' + this.authTokenHeader + '/' + s;
    } else if (!s.startsWith('/')) {
      s = '/' + s;
    }
    return encodeURI(RestService.BASE_URL + s);
  }

  login(user: string, pw: string, force?: boolean): Observable<Response> {
    const b64 = btoa(pw);

    const opts = {
      params: new HttpParams().set('password', b64).set('forceLogin', force ? 'true' : 'false')
    };

    this.prepareContentType(opts);
    const url = this.url(`${user}/login.json`);
    return this.http.get<any>(url, opts).pipe( // TODO : map to User object
      map(res => {
        const data = Response.create(res);
        if (data.ok()) {
          const token = data.first().apiToken;
          this.authState.success(token, this.createUser(user, data));
          if (data.responseMessage) {
            this.alert.flash(data.responseMessage, 'success');
            // console.log(data.responseMessage);
          }
        } else {
          if (data.errorCode.match('ERR_1100')) {
            this.alert.flash(
              this.i18n('Server Error: Authentication Failed. Please retry or contact the server administrator.'), 'error', true, -1);
          } else {
            this.alert.flash(this.i18n('Server Error: ') + data.errorMessage, 'error', true, -1);
          }
        }
        return data;
      }),
      catchError(err => this.handleError(err)));
  }

  logout(): Observable<Response> {
    return this.get(`logout.json`).pipe(
      map(res => {
        this.authState.logout();
        this.deleteUserCookies();
        return res;
      }),
      catchError(err => this.handleError(err)));
  }

  prepareHeader(opt: any): boolean {
    this.prepareContentType(opt);

    if ((this.authState.currentState.action !== 'INIT') && this.authState.currentState.userInfo) {
      opt.headers.append('username', this.authState.currentState.userInfo.userName);
      return true;
    }
    return false;
  }

  get authTokenHeader(): string {
    return this.authState.currentState.authToken;
  }

  setBuildInfo(bInfo: BEBuildInfoRecord) {
    this.buildInfo = bInfo;
  }

  checkForExternalLogin() {
    let userName = this.cookieService.get(WS_INFO_USER);
    if (userName) {
      userName = decodeURI(userName);
      const apiToken = this.cookieService.get(WS_SESSION_ID);

      const user = {
        userName: userName,
        isDMInstalled: (this.cookieService.get(WS_INFO_DM_INSTALLED) === 'true') ? true : false,
        isLockingEnabled: (this.cookieService.get(WS_INFO_LOCKING_ENABLED) === 'true') ? true : false,
        email: undefined,
        id: undefined,
        roles: []
      };
      this.authState.success(apiToken, user);
    }
  }

  deleteUserCookies() {
    this.cookieService.delete(WS_INFO_USER, '/WebStudio');
    this.cookieService.delete(WS_SESSION_ID, '/WebStudio');
    this.cookieService.delete(WS_INFO_DM_INSTALLED, '/WebStudio');
    this.cookieService.delete(WS_INFO_LOCKING_ENABLED, '/WebStudio');
  }

  checkTroposphereToken(): Promise<boolean> {
    const options = {
    };
    this.prepareContentType(options);
    return this.http.get<any>(encodeURI(RestService.BASE_URL + '/checkToken.json'), options).toPromise()
      .then(res => {
        const data = Response.create(res);
        if (data.ok()) {
          const token = data.first().apiToken;
          if (token) {
            this.authState.success(token, this.createUser(data.first().uname, data));
          }
        } else {
          if (data.errorCode !== 'ERR_3104' && data.errorCode !== 'ERR_3100') {
            this.alert.flash(`${data.errorMessage}`, 'error', true, -1);
          }
        }
        return Promise.resolve(true);
      }, err => {
        this.log.err('Fail to get token info because: ' + err);
        return Promise.resolve(true);
      });
  }

  private createUser(userName: string, data: Response): BEUser {
    this.addUserCookies(userName, data);

    return {
      userName: userName ? userName : data.first().userName,
      isDMInstalled: data.first().isDMInstalled,
      isLockingEnabled: data.first().isLockingEnabled,
      email: data.first().email ? data.first().email : undefined,
      firstName: data.first().firstName ? data.first().firstName : undefined,
      lastName: data.first().lastName ? data.first().lastName : undefined,
      subscriptionId: data.first().subscriptionId ? data.first().subscriptionId : undefined,
      id: undefined,
      roles: []
    };
  }

  private addUserCookies(user: string, data: Response) {
    if (data) {
      this.cookieService.set(WS_INFO_USER, encodeURI(user), undefined, '/WebStudio');
      this.cookieService.set(WS_SESSION_ID, data.first().apiToken, undefined, '/WebStudio');
      this.cookieService.set(WS_INFO_DM_INSTALLED, data.first().isDMInstalled, undefined, '/WebStudio');
      this.cookieService.set(WS_INFO_LOCKING_ENABLED, data.first().isLockingEnabled, undefined, '/WebStudio');
    }
  }

}
