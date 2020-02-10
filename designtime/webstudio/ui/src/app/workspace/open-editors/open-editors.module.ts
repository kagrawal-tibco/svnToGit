import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatExpansionModule } from '@angular/material';

import { OpenEditors } from './open-editors.component';

@NgModule({
  imports: [CommonModule, MatExpansionModule],
  declarations: [OpenEditors],
  exports: [OpenEditors]
})
export class OpenEditorsModule {

}
