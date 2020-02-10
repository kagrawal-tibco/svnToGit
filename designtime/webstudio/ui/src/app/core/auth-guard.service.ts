import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot, CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';

import { environment } from 'environments/environment.be';

import { AuthStateService } from './auth-state.service';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private authState: AuthStateService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const url: string = state.url;

    return this.checkLogin(url);
  }

  checkLogin(url: string): boolean {
    switch (this.authState.currentState.action) {
      case 'INIT':
        if (url) {
          this.router.navigate(['/login/redirect/', url]);
        } else {
          if (environment.enableTCEUI) {
            const protocol = window.location.protocol;
            const hostname = window.location.hostname;
            const tceHomeLocation = protocol + '//' + hostname + '/index.html';
            window.location.href = tceHomeLocation;
          } else {
            this.router.navigate(['/login']);
          }
        }
        return false;
      case 'RESUME':
        this.router.navigate(['/redirect', url]);
        return false;
      case 'SUCCESS':
        return true;
    }
  }
}
