import { NgModule } from '@angular/core';

import { VerifyConfigModal } from './verify-config.modal';
import { VerifyComponent } from './verify.component';
import { VerifyService } from './verify.service';

import { SharedModule } from '../shared/shared.module';
import { TerminalModule } from '../terminal/terminal.module';

@NgModule({
  imports: [SharedModule, TerminalModule],
  declarations: [VerifyComponent, VerifyConfigModal],
  exports: [VerifyComponent, VerifyConfigModal],
  providers: [VerifyService],
})
export class VerifyModule { }
