import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AuthService } from './auth.service';

import { AuthStateService } from '../core/auth-state.service';
import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { Login } from '../models/login';

@Component({
  selector: 'login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements AfterViewInit, OnInit {

  @Input()
  signinButton: string;

  @Input()
  form: Login;

  @Output()
  formSubmit = new EventEmitter<Login>();

  @ViewChild('usernameInput', { static: false })
  usernameInput: ElementRef;

  constructor(
    private router: Router,
    private log: Logger,
    private rest: RestService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private authState: AuthStateService,
    public i18n: I18n
  ) {
  }

  ngOnInit() {
    if (!this.signinButton) {
      this.signinButton = this.i18n('Sign In');
    }
    if (!this.form) {
      this.form = new Login();
    }
  }

  ngAfterViewInit() {
    if (this.usernameInput) {
      // wrap in setTimeout as it causes the label to float and will throw an error otherwise
      setTimeout(() => { this.usernameInput.nativeElement.focus(); }, 0);
    }
  }

  onLogin() {
    this.formSubmit.emit(this.form);
  }
}
