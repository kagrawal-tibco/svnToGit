import { NgModule } from '@angular/core';

import 'codemirror/mode/javascript/javascript';

import { MetadataEditorComponent } from './metadata-editor.component';

import { TextEditorModule } from '../text/text-editor.module';

@NgModule({
  imports: [TextEditorModule],
  declarations: [MetadataEditorComponent],
  exports: [MetadataEditorComponent]
})
export class MetadataEditorModule {
}
