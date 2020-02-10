import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LoginOrSignupComponent } from './login-or-signup.component';
import { LoginComponent } from './login.component';
import { ValidateTokenComponent } from './validate-token.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: LoginComponent
      },
      {
        path: 'redirect/:url',
        component: LoginComponent
      },
      {
        path: 'validateToken',
        component: ValidateTokenComponent
      },
      {
        path: 'select',
        component: LoginOrSignupComponent
      },
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class AuthRoutingModule {

}
