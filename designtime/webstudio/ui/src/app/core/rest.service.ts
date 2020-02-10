import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment';
import * as FileSaver from 'file-saver';
import { CookieService } from 'ng2-cookies';
import { of as observableOf, Observable, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { BEUser } from 'app/models-be/user-be';

import { AlertService } from './alert.service';
import { AuthStateService } from './auth-state.service';
import { Logger } from './logger.service';

import { BuildInfoRecord, LoginRecord } from '../models/dto';
import { Response } from '../models/response';
import { User } from '../models/user';

@Injectable()
export class RestService {
  public ongoingRequests: Subject<number>;
  public downloadSuccess: Subject<boolean>;
  public buildInfo: BuildInfoRecord;
  protected aboutUrl = `/ws/api/amsinfo/buildInfo`;
  private currentCnt = 0;

  constructor(
    protected http: HttpClient,
    protected alert: AlertService,
    protected authState: AuthStateService,
    protected log: Logger,
    protected cookieService: CookieService,
    public i18n: I18n
  ) {
    this.initialize();
  }

  static get AUTHORIZATION() {
    return 'Authorization';
  }

  static get CONTENT_TYPE() {
    return 'Content-Type';
  }

  static get ACCEPT() {
    return 'Accept';
  }

  static get CONTENT_LENGTH() {
    return 'Content-Length';
  }

  static get BASE_URL() {
    return '/ws/api';
  }

  url(s: string): string {
    if (!s.startsWith('/')) {
      s = '/' + s;
    }
    return encodeURI(RestService.BASE_URL + s);
  }

  login(user: string, pw: string, force?: boolean): Observable<Response> {
    const b64 = btoa(user + ':' + pw);

    const opts = {
      params: new HttpParams(),
      headers: new HttpHeaders()
    };

    // let opts = new http.RequestOptions();
    // opts.headers = new http.Headers();
    this.prepareContentType(opts);
    opts.headers.set(RestService.AUTHORIZATION, 'Basic ' + b64);
    if (force) {
      opts.params.set('force', 'true');
    }
    const url = this.url('/login');
    return this.http.get<any>(url, opts).pipe(
      map(res => {
        const data = Response.create(res);
        if (data.ok()) {
          const record: LoginRecord = data.first();
          const token = record.apiToken;
          const userInfo: User = {
            id: record.userInfo.entityId,
            userName: record.userInfo.username,
            email: record.userInfo.email,
            roles: record.userInfo.roles
          };
          this.authState.success(token, userInfo);
          if (data.responseMessage) {
            this.displayServerMessage(data.responseMessage, this.i18n);
          }
          return data;
        }
      }),
      catchError(err => this.handleError(err)));
  }

  logout(): Observable<Response> {
    const url = this.url('/logout');
    return this.get(url).pipe(
      map(res => {
        this.authState.logout();
        return res;
      }),
      catchError(err => this.handleError(err)));
  }

  handleError(raw?: HttpResponse<any>): Observable<Response> {
    const response = Response.create(raw);
    if (response) {
      if (response.unauthenticated()) {
        // authentication fail, we redirect to login page.
        if (this.authState.currentState.action === 'SUCCESS') {
          this.alert.flash(`${response.errorMessage}`, 'warning');
        }
        this.authState.logout();
      } else {
        switch (raw.status) {
          case 500:
            // server error, shall let users know
            this.alert.flash(('Server Error: ') + response.errorMessage, 'error', true, -1);
            break;
          default:
            // for other cases we warn users about the problem
            this.alert.flash(`${response.errorMessage}`, 'warning');
            break;
        }
      }
      return observableOf(response);
    } else if (raw instanceof HttpResponse) {
      if (this.isServerUp()) {
        this.alert.flash(raw.statusText, 'warning');
      }
      return observableOf(Response.error());
    } else {
      this.log.warn('error: ', raw);
    }
  }

  get(url: string, options?: any, showErrorMsg?: boolean): Observable<Response> {
    url = this.url(url);
    if (!options) {
      options = {}; // new http.RequestOptions();
    }
    this.prepareHeader(options);
    this.beforeRequest();
    return this.http.get<any>(url, options).pipe(
      map(res => {
        this.afterRequest();
        const data = Response.create(res);
        if (!data.ok()) {
          this.log.err(this.i18n('Error response with a success http status code: {{res}}', { res: res }));
          if (data.errorCode === 'ERR_1102' || data.errorCode === 'ERR_3100') {
            showErrorMsg = false;
          }
          if (data.errorMessage && showErrorMsg) {
            this.displayServerErrorMessage(data, this.i18n);
          }
          if (environment.enableTCEUI && data.errorCode && (data.errorCode === 'ERR_1102' || data.errorCode === 'ERR_3100')) {
            const protocol = window.location.protocol;
            const hostname = window.location.hostname;
            const tceHomeLocation = protocol + '//' + hostname + '/index.html';
            window.location.href = tceHomeLocation;
          }
        }
        if (data.responseMessage) {
          this.alert.flash(data.responseMessage, 'success');
        }
        return data;
      }),
      catchError(e => {
        this.afterRequest();
        return this.handleError(e);
      }));
  }

  delete(url: string, payload?: any, options?: any, showErrorMsg?: boolean): Observable<Response> {
    url = this.url(url);
    if (!options) {
      options = {}; // new http.RequestOptions();
    }
    this.prepareHeader(options);
    this.beforeRequest();
    if (payload) {
      options.body = JSON.stringify(payload);
    }
    return this.http.delete<any>(url, options).pipe(
      map(res => {
        this.afterRequest();
        const data = Response.create(res);
        if (!data.ok()) {
          this.log.err(this.i18n('Error response with a success http status code: {{res}}', { res: res }));
          if (data.errorMessage && showErrorMsg && data.errorCode !== 'ERR_1102') {
            // this.alert.flash(`${data.errorMessage}`, 'error', true, -1);
            this.displayServerErrorMessage(data, this.i18n);
          }
          this.checkServerError(data);
        }
        if (data.responseMessage) {
          // this.alert.flash(data.responseMessage, 'success');
          this.displayServerMessage(data.responseMessage, this.i18n);
        }
        return data;
      }),
      catchError(e => {
        this.afterRequest();
        return this.handleError(e);
      }));
  }

  post(url: string, payload: any, options?: any, showErrorMsg?: boolean): Observable<Response> {
    url = this.url(url);
    if (!options) {
      options = {}; // new http.RequestOptions();
    }
    this.prepareHeader(options);
    this.beforeRequest();
    return this.http.post<any>(url, JSON.stringify(payload), options).pipe(
      map(res => {
        this.afterRequest();
        const data = Response.create(res);
        if (!data.ok()) {
          this.log.err(this.i18n('Error response with a success http status code: {{res}}', { res: res }));
          if (data.errorMessage && showErrorMsg && data.errorCode !== 'ERR_1102') {
            // this.alert.flash(`${data.errorMessage}`, 'error', true, -1);
            this.displayServerErrorMessage(data, this.i18n);
          }
          this.checkServerError(data);
        }
        if (data.responseMessage) {
          // this.alert.flash(data.responseMessage, 'success');
          this.displayServerMessage(data.responseMessage, this.i18n);
        }
        return data;
      }),
      catchError(err => {
        this.afterRequest();
        return this.handleError(err);
      }));
  }

  put(url: string, payload: any, options?: any, showErrorMsg?: boolean, showSuccessMessage?: boolean): Observable<Response> {
    url = this.url(url);
    if (!options) {
      options = {}; // new http.RequestOptions();
    }
    if (showSuccessMessage === undefined) {
      showSuccessMessage = true; // by default show a success message
    }
    this.prepareHeader(options);
    this.beforeRequest();
    return this.http.put<any>(url, JSON.stringify(payload), options).pipe(
      map(res => {
        this.afterRequest();
        const data = Response.create(res);
        if (!data.ok()) {
          this.log.err(this.i18n('Error response with a success http status code: {{res}}', { res: res }));
          if (data.errorMessage && showErrorMsg && data.errorCode !== 'ERR_1102') {
            // this.alert.flash(`${data.errorMessage}`, 'error', true, -1);
            this.displayServerErrorMessage(data, this.i18n);
          }
          this.checkServerError(data);
        }
        if (data.responseMessage && showSuccessMessage) {
          // this.alert.flash(data.responseMessage, 'success');
          this.displayServerMessage(data.responseMessage, this.i18n);
        }
        return data;
      }),
      catchError(err => {
        this.afterRequest();
        return this.handleError(err);
      }));
  }

  checkServerError(data: Response) {
    if (environment.enableTCEUI && data.errorCode && data.errorCode === 'ERR_1102') {
      const protocol = window.location.protocol;
      const hostname = window.location.hostname;
      const tceHomeLocation = protocol + '//' + hostname + '/index.html';
      window.location.href = tceHomeLocation;
    }
  }

  download(url: string, fileName: string, contentType: string, showErrorMsg?: boolean) {
    url = this.url(url);
    // if (!options) {
    //   options = new http.RequestOptions();
    // }
    const options = {
      params: new HttpParams({
      }),
      responseType: 'blob'
    };

    this.prepareHeader(options);
    this.beforeRequest();
    return this.http.get(url, {
      params: new HttpParams(),
      responseType: 'blob'
    })
      .subscribe(data => {
        this.afterRequest();
        const blob = new Blob([data], { type: contentType });
        FileSaver.saveAs(blob, fileName);
      }),
      error => {
        this.log.err(this.i18n('Error downloading file {{file}} ', { file: fileName }));
        if (showErrorMsg) {
          this.alert.flash(this.i18n('Error downloading file {{file}} ', { file: fileName }), 'error', true, -1);
        }
      };
  }

  multiExport(url: string, fileName: string, data: any, contentType: string, options?: any, showErrorMsg?: boolean) {
    url = this.url(url);
    if (!options) {
      options = {
        params: new HttpParams(),
        responseType: 'blob'
      };
    }

    this.prepareHeader(options);
    this.beforeRequest();
    return this.http.post(url, data, options)
      .subscribe(dataBlob => {
        this.afterRequest();
        const blob = new Blob([dataBlob], { type: contentType });
        FileSaver.saveAs(dataBlob, fileName);
      }),
      error => {
        this.log.err(this.i18n('Error downloading file {{file}} ', { file: fileName }));
        if (showErrorMsg) {
          this.alert.flash(this.i18n('Error downloading file {{file}} ', { file: fileName }), 'error', true, -1);
        }
      };
  }

  backup(url: string, fileName: string, contentType: string, options?: any, showErrorMsg?: boolean) {
    url = this.url(url);
    if (!options) {
      options = {
        params: new HttpParams(),
        responseType: 'blob'
      };
    }

    this.prepareHeader(options);
    this.beforeRequest();
    return this.http.get(url, options)
      .subscribe((dataBlob) => {
        this.afterRequest();
        FileSaver.saveAs(dataBlob, fileName);
        this.downloadSuccess.next(true);
      }, (error) => {
        this.log.err('Error downloading file :' + fileName);
        this.downloadSuccess.next(false);
        if (showErrorMsg) {
          this.alert.flash('Error downloading file ' + fileName, 'error', true, -1);
        }
      });
  }

  displayServerMessage(responseMessage: string, i18n: I18n) {
    if (responseMessage.search('Rename completed successfully.') !== -1) {
      this.alert.flash(i18n('Rename completed successfully.'), 'success');
    } else if (responseMessage.search('Add to Recently Opened Artifacts successful') !== -1) {
      this.alert.flash(i18n('Add to Recently Opened Artifacts successful'), 'success');
    } else if (responseMessage.search('Checkout successful') !== -1) {
      this.alert.flash(i18n('Checkout successful'), 'success');
    } else if (responseMessage.search('Artifacts deleted successfully') !== -1) {
      this.alert.flash(i18n('Artifacts deleted successfully'), 'success');
    } else if (responseMessage.search('Group Successfully Added') !== -1) {
      this.alert.flash(i18n('Group Successfully Added'), 'success');
    } else if (responseMessage.search('Group Successfully Deleted') !== -1) {
      this.alert.flash(i18n('Group Successfully Deleted'), 'success');
    } else if (responseMessage.search('Deployment Config successfully deleted.') !== -1) {
      this.alert.flash(i18n('Deployment Config successfully deleted.'), 'success');
    } else if (responseMessage.search('Deployment Config successfully updated.') !== -1) {
      this.alert.flash(i18n('Deployment Config successfully updated.'), 'success');
    } else if (responseMessage.search('Deployment Config successfully created.') !== -1) {
      this.alert.flash(i18n('Deployment Config successfully created.'), 'success');
    } else if (responseMessage.search('Deployable generated successfully.') !== -1) {
      this.alert.flash(i18n('Deployable generated successfully.'), 'success');
    } else if (responseMessage.search('Save successfully completed') !== -1) {
      this.alert.flash(i18n('Save successfully completed'), 'success');
    } else if (responseMessage.search('Rename completed successfully') !== -1) {
      this.alert.flash(i18n('Rename completed successfully'), 'success');
    } else if (responseMessage.search('Selected artifacts reverted successfully') !== -1) {
      this.alert.flash(i18n('Selected artifacts reverted successfully'), 'success');
    } else if (responseMessage.search('User Preferences successfully updated') !== -1) {
      this.alert.flash(i18n('User Preferences successfully updated'), 'success');
    } else if (responseMessage.search('Acl updated successfully') !== -1) {
      this.alert.flash(i18n('Acl updated successfully'), 'success');
    } else {
      this.alert.flash(responseMessage, 'success');
    }
  }

  displayServerErrorMessage(data: Response, i18n: I18n) {
    if (data.errorCode.search('ERR_1105') !== -1) {
      this.alert.flash(this.i18n('User Group space locked'), 'error', true, -1);
    } else if (data.errorCode.search('ERR_1111') !== -1) {
      this.alert.flash(this.i18n('This User Group already exists.'), 'error', true, -1);
    } else if (data.errorCode.search('ERR_1211') !== -1) {
      this.alert.flash(this.i18n('User do not have this permission'), 'error', true, -1);
    } else if (data.errorCode.search('ERR_1106') !== -1) {
      this.alert.flash(this.i18n('User Dashboard space does not exist'), 'error', true, -1);
    } else if (data.errorCode.search('ERR_1002') !== -1) {
      this.alert.flash(this.i18n('Workspace locked already'), 'error', true, -1);
    } else if (data.errorCode.search('ERR_1152') !== -1) {
      const projectName = data.errorMessage.split('project')[1].split('not')[0];
      this.alert.flash(
        this.i18n('EAR for project {{0}} not present at deploy location, generate EAR.', { 0: projectName }), 'error', true, -1);
    } else {
      this.alert.flash(`${data.errorMessage}`, 'error', true, -1);
    }
  }

  beforeRequest() {
    this.currentCnt++;
    this.ongoingRequests.next(this.currentCnt);
  }

  afterRequest() {
    this.currentCnt--;
    this.ongoingRequests.next(this.currentCnt);
  }

  prepareContentType(opt: any) {
    if (!opt.headers) {
      opt.headers = new HttpHeaders();
    }
    opt.headers.append(RestService.CONTENT_TYPE, 'application/json');
    opt.headers.append(RestService.ACCEPT, 'application/json');
  }

  prepareHeader(opt: any): boolean {
    this.prepareContentType(opt);
    if (this.authState.currentState.action !== 'INIT') {
      opt.headers.append(RestService.AUTHORIZATION, this.authTokenHeader);
      return true;
    }
    return false;
  }

  get authTokenHeader(): string {
    return 'Token ' + this.authState.currentState.authToken;
  }

  get userName(): string {
    return this.authState.currentState.userInfo.userName;
  }

  get displayName(): string {
    const user = <BEUser>this.authState.currentState.userInfo;
    let userName = user.userName;
    if (user.lastName) {
      userName = user.lastName;
      if (user.firstName) {
        userName += (', ' + user.firstName);
      }
    }
    return userName;
  }

  getBuildInfo(): BuildInfoRecord {
    return this.buildInfo;
  }

  setBuildInfo(bInfo: BuildInfoRecord) {
    this.buildInfo = bInfo;
  }

  checkForExternalLogin() {
  }

  isServerUp(): boolean {
    return true;
  }

  checkTroposphereToken(): Promise<boolean> {
    return Promise.resolve(true);
  }

  deleteUserCookies() {
    return;
  }

  protected initialize() {
    this.ongoingRequests = new Subject<number>();
    this.ongoingRequests.next(0);

    this.downloadSuccess = new Subject<boolean>();

    const options = {}; // new http.RequestOptions();
    this.prepareContentType(options);
    this.http.get<any>(this.aboutUrl, options).toPromise()
      .then(res => {
        const data = Response.create(res);
        if (data.ok()) {
          this.setBuildInfo(data.record[0]);
        }
      }, err => this.log.err(this.i18n('Fail to get build info because: {{err}}', { err: err })));
  }
}
