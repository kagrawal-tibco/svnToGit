import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';

import { UserService } from 'app/core/user.service';

import { AuthRoutingModule } from './auth-routing.module';
import { AuthService } from './auth.service';
import { LoginFormComponent } from './login-form.component';
import { LoginOrSignupComponent } from './login-or-signup.component';
import { LoginComponent } from './login.component';
import { SignupFormComponent } from './signup-form.component';
import { ValidateTokenComponent } from './validate-token.component';

import { environment } from '../../environments/environment';
import { BEAuthService } from '../core-be/be.auth.service';
import { RestService } from '../core/rest.service';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule, FormsModule, AuthRoutingModule, MatInputModule],
  declarations: [
    LoginComponent,
    LoginFormComponent,
    SignupFormComponent,
    LoginOrSignupComponent,
    ValidateTokenComponent
  ],
  exports: [LoginComponent],
  providers: [
    // AuthService
    {
      provide: AuthService,
      useFactory: authServiceFactory,
      deps: [RestService, Router, UserService]
    }
  ]
})
export class AuthModule {
}

export function authServiceFactory(rest: RestService, router: Router, user: UserService) {
  if (environment.enableBEUI) {
    return new BEAuthService(rest, router, user);
  } else {
    return new AuthService(rest, router, user);
  }
}
