
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { take } from 'rxjs/operators';

import { AuthService } from './auth.service';

import { AlertService } from '../core/alert.service';
import { AuthStateService } from '../core/auth-state.service';
import { ValidateTokenRecord } from '../models/dto';
import { User } from '../models/user';

@Component({
  selector: 'validate-token',
  template: '<p>Validating Authentication Information</p>'
})
export class ValidateTokenComponent implements OnInit {
  constructor(
    private auth: AuthService,
    private authState: AuthStateService,
    private alert: AlertService,
    private router: Router,
    private route: ActivatedRoute
  ) {

  }

  ngOnInit() {
    this.route.queryParams.pipe(take(1)).toPromise()
      .then(params => {
        const token = params['authToken'];
        const msgType = params['msgType'];
        const msg = params['msg'];
        if (token) {
          this.auth.validateToken(token)
            .then((result: ValidateTokenRecord) => {
              if (result) {
                const userInfo: User = {
                  id: result.userInfo.entityId,
                  userName: result.userInfo.username,
                  email: result.userInfo.email,
                  roles: result.userInfo.roles
                };
                this.authState.success(result.authToken, userInfo);
                this.router.navigate(['/dashboard']);
              } else {
                this.router.navigate(['/login']);
              }
            });
        }
        if (msgType && msg) {
          this.alert.flash(msg, msgType);
          console.log(msg);
        }
      });
  }
}
