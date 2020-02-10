import { HttpClient } from '@angular/common/http';
import {
  async,
  inject,
  TestBed
} from '@angular/core/testing';
import { Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { CookieService } from 'ng2-cookies';
import 'rxjs/Rx';

import { AlertService } from './alert.service';
import { AuthStateService } from './auth-state.service';
import { Logger } from './logger.service';
import { RestService } from './rest.service';

import { TestUtil } from '../util/test-util';

describe('Rest Service', () => {
  beforeEach(() => {
    const router = <Router>{
      navigate: (any) => { },
      navigateByUrl: (any) => { }
    };
    const alertService = {
      flash: (any) => Promise.resolve(null),
    };
    TestBed.configureTestingModule({
      providers: [
        Logger,
        I18n,
        CookieService,
        // MockBackend,
        {
          provide: Router,
          useValue: router
        },
        {
          provide: AlertService,
          useValue: alertService
        },
        AuthStateService,
        // BaseRequestOptions,
        {
          provide: HttpClient,
          useFactory: (backend, options) => {
            return new HttpClient(backend);
          },
          // deps: [MockBackend, BaseRequestOptions]
        },
        RestService,
      ]
    });
  });

  // The mock response
  const token = 'atokenisagoodtoken';

  // beforeEach(async(inject([MockBackend, RestService], (backend: MockBackend, service: RestService) => {
  //   backend.connections.subscribe(
  //     (c: MockConnection) => {
  //       if (c.request.url.search('UNAUTHORIZED') !== -1) {
  //         TestUtil.mockRespond(c, [], 'ERR_1003', this.I18n('User needs to login first.'));
  //       } else {
  //         let payload: any[];
  //         if (c.request.url.search('login') !== -1) {
  //           payload = [{ userInfo: { entityId: 'someid' }, apiToken: token }];
  //         } else if (c.request.url.search('logout') !== -1) {
  //           payload = [{ message: this.I18n('Log out successfully' )}];
  //         } else if (c.request.method === RequestMethod.Get) {
  //           payload = [{ message: this.I18n('Get successfully' )}];
  //         } else if (c.request.method === RequestMethod.Post) {
  //           payload = [{ message: this.I18n('Post successfully' )}];
  //         }
  //         TestUtil.mockRespond(c, payload);
  //       }
  //     });
  //   return service.login('admin', 'admin', true)
  //     .toPromise()
  //     .then(res => {
  //       expect(res).toBeTruthy();
  //     });
  // })));

  it('can logout', async(inject([RestService], (service: RestService) => {
    return service.logout()
      .toPromise()
      .then(res => {
        expect(res).toBeTruthy();
      });
  })));

  it('can get', async(inject([RestService], (service: RestService) => {
    return service.get('/project')
      .toPromise()
      .then(res => {
        expect(res.first().message).toBe(this.I18n('Get successfully'));
      })
      .catch(err => fail('Should not have error'));
  })));

  it('and should be able to post', async(inject([RestService], (service: RestService) => {
    const p = {
      comments: 'a project',
      description: 'a project',
      artifacts: [],
      roleItem: [],
    };
    return service.post('project/project_1/import', p)
      .toPromise()
      .then(res => {
        expect(res.first().message).toBe(this.I18n('Post successfully'));
      })
      .catch(err => fail(this.I18n('Should not have error, but got: ') + err));
  })));
});
