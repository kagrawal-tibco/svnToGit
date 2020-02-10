
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { map } from 'rxjs/operators';

import { UserService } from 'app/core/user.service';

import { RestService } from '../core/rest.service';
import { IdentityProviderRecord, SignUpRequest, UserRecord, ValidateTokenRecord } from '../models/dto';
import { Login } from '../models/login';
import { SignupForm } from '../models/signup-form';

@Injectable()
export class AuthService {
  private _idProviders: string[] = [];

  constructor(
    protected rest: RestService,
    protected router: Router,
    protected user: UserService
  ) {
    this.fetchIdProviders();
  }

  public fetchIdProviders() {
    this.rest.get('/oidc/providers').toPromise()
      .then(res => {
        if (res.ok()) {
          this._idProviders = res.record.map((record: IdentityProviderRecord) => record.name);
        }
      });
  }

  get idProviders() {
    return this._idProviders;
  }

  public validateToken(token: string): Promise<ValidateTokenRecord> {
    return this.rest.get(`/login/validateToken?authToken=${token}`)
      .toPromise()
      .then(res => {
        if (res.ok()) {
          return res.record[0];
        }
      });
  }

  public bindIdentity(binding: string): Promise<boolean> {
    return this.rest.post(`/oidc/bindIdentity?oidcIdentity=${binding}`, {}).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  public signup(form: SignupForm, bindingId?: string): Promise<UserRecord> {
    const payload: SignUpRequest = {
      username: form.userName,
      password: form.password,
      bindingId: bindingId
    };
    return this.rest.post('/login/signup', payload).pipe(map(res => res.record[0])).toPromise();
  }

  public login(form: Login, redirect?: string): Promise<boolean> {
    return this.rest
      .login(form.userName, form.password, true)
      .toPromise()
      .then((res) => {
        if (res.ok()) {
          if (redirect) {
            this.router.navigateByUrl(redirect);
          } else {
            // think about checking whether the user has approval permissions
            // before redirecting to the dashboard, else go to workspace?
            // this.user.hasPermission('');
            this.router.navigate(['/dashboard']);
          }
        } else {
          /** noop */
        }
        return res.ok();
      });

  }

  public convertProviderToUIClass(idProvider: string) {
    const idProviderLowerCase = idProvider.toLocaleLowerCase();
    switch (idProviderLowerCase) {
      case 'adn':
      case 'bitbucket':
      case 'dropbox':
      case 'facebook':
      case 'flickr':
      case 'foursquare':
      case 'github':
      case 'google':
      case 'instagram':
      case 'linkedin':
      case 'microsoft':
      case 'odnoklassniki':
      case 'openid':
      case 'pinterest':
      case 'reddit':
      case 'soundcloud':
      case 'tumblr':
      case 'twitter':
      case 'vimeo':
      case 'vk':
      case 'yahoo':
        return idProviderLowerCase;
      default:
        return 'simple';
    }
  }

}
