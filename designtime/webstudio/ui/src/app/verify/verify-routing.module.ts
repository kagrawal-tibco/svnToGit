import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VerifyDeactivateGuard } from './verify-deactivate-guard.service';
import { VerifyComponent } from './verify.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'revision/:id',
        component: VerifyComponent,
        canDeactivate: [VerifyDeactivateGuard],
        data: { artifactKind: 'REVISION' }
      },
      {
        path: 'checkout/:id',
        component: VerifyComponent,
        canDeactivate: [VerifyDeactivateGuard],
        data: { artifactKind: 'CHECKOUT' }
      },
    ])
  ],
  providers: [VerifyDeactivateGuard],
  exports: [
    RouterModule
  ]
})
export class VerifyRoutingModule {

}
