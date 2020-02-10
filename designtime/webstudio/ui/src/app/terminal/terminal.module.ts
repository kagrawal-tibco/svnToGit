import { NgModule } from '@angular/core';

import { TerminalComponent } from './terminal.component';
import { TerminalService } from './terminal.service';

import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule],
  declarations: [TerminalComponent],
  exports: [TerminalComponent],
  providers: [TerminalService]
})
export class TerminalModule {

}
