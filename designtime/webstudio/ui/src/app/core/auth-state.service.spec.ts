import {
  async,
  TestBed
} from '@angular/core/testing';
import { Router } from '@angular/router';

import { Auth, AuthStateService, AUTH_STATE_PARAM_NAME } from './auth-state.service';
import { Logger } from './logger.service';

import { User } from '../models/user';

describe('AuthStateService', () => {
  let service: AuthStateService;
  let router: Router;
  beforeEach(() => {
    router = <Router>{
      navigate: (any) => { },
      navigateByUrl: (any) => { }
    };
    spyOn(router, 'navigate');
    spyOn(router, 'navigateByUrl');
    TestBed.configureTestingModule({
      providers: [
        Logger,
        AuthStateService,
        {
          provide: Router,
          useValue: router
        }
      ]
    });
  });

  describe('when init with dirty localStorage', () => {
    let auth: Auth;
    beforeEach(() => {
      auth = new Auth('admin', <User>{ userName: 'agoodtoken' }, 'SUCCESS');
      auth.persist();
      service = TestBed.get(AuthStateService);
    });

    it('shall be in RESUME state initially', () => {
      expect(service.currentState.action).toBe('RESUME');
      expect(service.currentState.authToken).toBe(auth.authToken);
      expect(service.currentState.userInfo).toEqual(auth.userInfo);
    });

    it('shall be in SUCCESS state when after resuming', async(() => {
      service.state
        .filter(state => state.action !== 'RESUME')
        .toPromise()
        .then(state => {
          expect(state.action).toBe('SUCCESS');
        });
      service.resume();
      expect(localStorage.getItem(AUTH_STATE_PARAM_NAME)).toBe(JSON.stringify(auth));
    }));

    it('shall be INIT when resuming fail', (done) => {
      service.logout();
      expect(service.currentState.action).toBe('INIT');
      service.state
        .filter(state => state.action !== 'RESUME')
        .subscribe(state => {
          expect(state.action).toBe('INIT');
          done();
        });
      expect(localStorage.getItem(AUTH_STATE_PARAM_NAME)).toBeFalsy();
    });
  });

  describe('when init with clean localStorage', () => {
    beforeEach(() => {
      localStorage.clear();
      service = TestBed.get(AuthStateService);
    });

    it('shall be INIT initially', () => {
      expect(service.currentState.action).toBe('INIT');
    });

    it('shall be turned into SUCCESS when succeed', () => {
      const token = 'agoodtoken';
      const user = <User>{ userName: 'agoodpersion' };
      service.success(token, user);
      expect(service.currentState.action).toBe('SUCCESS');
      expect(service.currentState.userInfo).toEqual(user);
      expect(service.currentState.authToken).toBe(token);
      expect(localStorage.getItem(AUTH_STATE_PARAM_NAME)).toBe(service.currentState.serialize());
    });

    it('shall remain INIT when not succeed', () => {
      service.logout();
      expect(service.currentState.action).toBe('INIT');
      expect(service.currentState.userInfo).toEqual({});
      expect(service.currentState.authToken).toBe('');
      expect(localStorage.getItem(AUTH_STATE_PARAM_NAME)).toBeFalsy();
    });

  });
});
