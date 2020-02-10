
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { take } from 'rxjs/operators';

import { AuthService } from './auth.service';

import { AlertService } from '../core/alert.service';
import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { Login } from '../models/login';
import { SignupForm } from '../models/signup-form';

export type Mode = 'SIGN_IN' | 'SIGN_UP';

@Component({
  selector: 'login-or-signup',
  templateUrl: './login-or-signup.component.html',
  styleUrls: ['./login-or-signup.component.css'],
})
export class LoginOrSignupComponent implements OnInit {
  public social: string;
  public identity: string;
  public allowSignup: boolean;
  public loginForm: Login;
  public signupForm: SignupForm;
  private mode: Mode;
  private binding: string;

  constructor(
    private rest: RestService,
    private alert: AlertService,
    private auth: AuthService,
    private log: Logger,
    private router: Router,
    private route: ActivatedRoute,
    public i18n: I18n
  ) {
  }

  ngOnInit() {
    this.route.queryParams.pipe(take(1)).toPromise().then(params => {
      this.social = params['social'];
      this.binding = params['binding'];
      this.identity = params['identity'];
      this.allowSignup = params['allowSignup'] === 'true';
      this.setInSignIn();
    });
  }

  onLogin(form: Login) {
    if (form) {
      this.auth.login(form)
        .then(ok => {
          if (ok) {
            this.auth.bindIdentity(this.binding);
          }
        });
    } else {
      this.alert.flash(this.i18n('Please enter a valid Identity Nickname.'), 'warning');
    }
  }

  onSignup(form: SignupForm) {
    if (form) {
      return this.auth.signup(form, this.binding)
        .then(userRecord => {
          if (userRecord) {
            if (userRecord.enabled) {
              this.auth
                .login({
                  userName: form.userName,
                  password: form.password,
                  forceLogin: true
                })
                .then(ok => {
                  if (ok) {
                    this.alert.flash(this.i18n('A user profile for [{{username}}] was created.', { username: userRecord.username }), 'success');
                  }
                });
            } else {
              const msg = this.i18n('A user profile for [{{username}}] was created but is currently disabled. Please contact the administrator to activate your profile.', { username: userRecord.username });
              this.alert.flash(msg, 'warning');
              this.router.navigateByUrl('/login');
            }
          }
        });
    } else {
      this.log.warn(this.i18n('Invalid form is passed from signup page'));
    }
  }

  getNgClass(mode: Mode) {
    return {
      notActive: mode !== this.mode,
      clickable: mode !== this.mode,
      btn: mode !== this.mode,
      signIn: mode === 'SIGN_IN',
      signUp: mode === 'SIGN_UP',
      switchHeader: true
    };
  }

  setInSignIn() {
    this.mode = 'SIGN_IN';
    this.loginForm = new Login();
  }

  setInSignUp() {
    this.mode = 'SIGN_UP';
    this.signupForm = new SignupForm();
    this.signupForm.userName = this.identity;
  }

  isInSignIn() {
    return this.mode === 'SIGN_IN';
  }

  isInSignUp() {
    return this.mode === 'SIGN_UP';
  }

  isSignUpFormValid(form: SignupForm) {
    return form && form.userName && form.password && form.password === form.passwordRepeat;
  }

  getSignUpMessage(allow: Boolean): string {
    let msg;
    if (allow) {
      msg = this.i18n('We could not find any AMS user profile connected with your {{0}} account.\
      Please either sign in your existing AMS account or \
      create a new AMS account and it will be connected to your {{0}} account automatically.', { 0: this.social });
    } else {
      msg = this.i18n('We could not find any AMS user profile connected with your {{0}} account. \
      Please sign in your AMS account and it will be automatically connected to your {{0}} account.\
        If you do not have an AMS account, please contact your administrator.', { 0: this.social });
    }

    return msg;
  }

}
