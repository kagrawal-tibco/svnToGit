import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { FilterPipe } from '../filter/filter.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [FilterPipe],
  exports: [FilterPipe]
})
export class SharedPipeModule {
  // This module is used as a bucket to hold all the pipes that will be made available.
  // In order to create a new pipe, use the following command fom the ams-web/src directory
  // ng g pipe pipes/{pipe-folder-name}/{pipe-name} -m app/pipes/shared-pipe/shared-pipe.module.ts
}
