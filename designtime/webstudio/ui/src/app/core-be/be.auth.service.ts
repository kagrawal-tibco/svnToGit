import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from 'app/core/user.service';

import { AuthService } from '../auth/auth.service';
import { RestService } from '../core/rest.service';

@Injectable()
export class BEAuthService extends AuthService {

  constructor(
    rest: RestService,
    router: Router,
    user: UserService
  ) {
    super(rest, router, user);
  }

  public fetchIdProviders() {
    return;
  }
}
