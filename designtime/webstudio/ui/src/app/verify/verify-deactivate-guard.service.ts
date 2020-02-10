import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanDeactivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';

import { VerifyComponent } from './verify.component';

import { ModalService } from '../core/modal.service';

@Injectable()
export class VerifyDeactivateGuard implements CanDeactivate<VerifyComponent> {
  constructor(
    private router: Router,
    private modal: ModalService
  ) { }

  canDeactivate(cmp: VerifyComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    if (cmp.isActive) {
      return this.modal.confirm()
        .message('Please confirm you want to leave now, which will terminate the current session.')
        .open().result
        .then(result => true, err => false);
    } else {
      return Promise.resolve(true);
    }
  }
}
