import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { SelectModule } from 'ng-select';

import { ProjectCheckoutComponent } from './project-checkout.component';
import { ProjectCheckoutModal } from './project-checkout.modal';

import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule, SelectModule, FormsModule],
  declarations: [ProjectCheckoutModal, ProjectCheckoutComponent],
  exports: [ProjectCheckoutModal]
})
export class ProjectCheckoutModule {
}
