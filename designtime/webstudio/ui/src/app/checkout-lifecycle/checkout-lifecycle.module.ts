import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { CheckoutCommitComponent } from './checkout-commit.component';
import { CheckoutExternalSyncComponent } from './checkout-external-sync.component';
import { CheckoutRevertComponent } from './checkout-revert.component';
import { CheckoutSyncComponent } from './checkout-sync.component';

import { CommitSharedModule } from '../commit-shared/commit-shared.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    CommitSharedModule
  ],
  declarations: [
    CheckoutCommitComponent,
    CheckoutRevertComponent,
    CheckoutSyncComponent,
    CheckoutExternalSyncComponent
  ]
})
export class CheckoutLifecycleModule {

}
