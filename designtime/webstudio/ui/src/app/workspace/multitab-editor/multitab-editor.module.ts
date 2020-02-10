import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';

import { AngularSplitModule } from 'angular-split';
import { TabsModule } from 'ngx-bootstrap';

import { MultitabEditorComponent } from './multitab-editor.component';
import { MultitabEditorService } from './multitab-editor.service';

import { ArtifactEditorModule } from '../../artifact-editor/artifact-editor.module';
import { ArtifactTestDataService } from '../../artifact-editor/artifact-testdata.service';
@NgModule({
  imports: [
    CommonModule,
    MatTabsModule,
    MatIconModule,
    TabsModule,
    ArtifactEditorModule,
    AngularSplitModule
  ],
  declarations: [MultitabEditorComponent],
  exports: [MultitabEditorComponent],
  providers: [MultitabEditorService, ArtifactTestDataService]
})
export class MultitabEditorModule {

}
