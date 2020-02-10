import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { GenerateDeployableModal } from './generate-deployable.modal';

import { CommitSharedModule } from '../commit-shared/commit-shared.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    CommitSharedModule
  ],
  declarations: [GenerateDeployableModal],
  exports: [GenerateDeployableModal]
})
export class GenerateDeployableModule {

}
