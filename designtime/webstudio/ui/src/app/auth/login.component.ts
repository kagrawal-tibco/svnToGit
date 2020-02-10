
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { take } from 'rxjs/operators';

import { AuthService } from './auth.service';

import { environment } from '../../environments/environment';
import { AuthStateService } from '../core/auth-state.service';
import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { HEADER_HEIGHT } from '../layout/properties';
import { BEBuildInfoRecord } from '../models/dto';
import { Login } from '../models/login';

@Component({
  selector: 'auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [],
})
export class LoginComponent implements OnInit, AfterViewInit {

  form = new Login();
  showLogin = false;

  @ViewChild('usernameInput', { static: false })
  usernameInput: ElementRef;

  private url: string;

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
    this.route.params.pipe(take(1)).toPromise()
      .then(params => {
        this.url = params['url'];
        this.authState.state.subscribe(auth => {
          if (auth.action !== 'INIT') {
            if (this.url) {
              this.router.navigateByUrl(this.url);
            } else {
              this.showLogin = false;
              this.router.navigate(['/dashboard']);
            }
          } else {
          }
        });
      });
  }

  ngAfterViewInit() {
    if (this.usernameInput) {
      this.usernameInput.nativeElement.focus();
    }
  }

  onLogin(form: Login) {
    this.authService.login(form, this.url);
  }

  signInWith(idProvider: string) {
    const url = `${window.location.protocol}//${window.location.host}/ws/api/oidc/authorize?identityProvider=${idProvider}`;
    window.location.href = url;
  }

  getSocialClass(idProvider: string) {
    return this.authService.convertProviderToUIClass(idProvider);
  }

  get logoWidth() {
    const h = window.window.innerHeight - HEADER_HEIGHT - 20;
    const w = (window.window.innerWidth / 2) - 20;
    const min = h > w ? w : h;
    return min > 450 ? 450 : min;
  }

  get pageHeight() {
    return window.window.innerHeight - HEADER_HEIGHT + 47;
  }

  get buildInfo() {
    const buildInfo = this.rest.getBuildInfo();
    if (buildInfo) {
      return (environment.enableBEUI) ? (this.rest.buildInfo.version + '.' + (<BEBuildInfoRecord>buildInfo).build) : this.rest.buildInfo.version;
    } else {
      return '';
    }
  }

  get idProviders() {
    return this.authService.idProviders;
  }

  get productName() {
    if (environment.enableTCEUI) {
      return 'Cloud Events WebStudio';
    }
    return (environment.enableBEUI) ? 'BusinessEvents WebStudio' : 'Artifact Management Server';
  }

  get productImage() {
    return (environment.enableBEUI) ? 'assets/img/webstudio-logo@3x.png' : 'assets/img/ams-logo@3x.png';
  }

  get showLoginPage() {
    return environment.enableTCEUI ? this.showLogin : true;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
