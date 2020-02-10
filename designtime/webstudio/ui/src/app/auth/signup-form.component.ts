import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AuthService } from './auth.service';

import { AlertService } from '../core/alert.service';
import { SignupForm } from '../models/signup-form';

@Component({
  selector: 'signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css'],
})
export class SignupFormComponent implements OnInit, AfterViewInit {
  @Input()
  public form: SignupForm;

  @Input()
  public signupButton: string;

  @Output()
  private formSubmit = new EventEmitter<SignupForm>();

  @ViewChild('usernameInput', { static: false })
  private usernameInput: ElementRef;

  constructor(
    private auth: AuthService,
    private alert: AlertService,
    public i18n: I18n
  ) { }

  onSignup() {
    if (this.form.userName && this.form.password && this.form.passwordRepeat) {
      if (this.form.password === this.form.passwordRepeat) {
        this.formSubmit.emit(this.form);
      } else {
        this.alert.flash(this.i18n('Please repeat the password correctly.'), 'warning');
      }
    } else {
      this.alert.flash(this.i18n('Please enter username and password correctly.'), 'warning');
    }
  }

  ngOnInit() {
    if (!this.signupButton) {
      this.signupButton = this.i18n('Sign Up');
    }
    if (!this.form) {
      this.form = new SignupForm();
    }
  }

  ngAfterViewInit() {
    this.usernameInput.nativeElement.focus();
  }
}
