import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { ModalModule } from 'ngx-modialog';

import { PasswordButton, PasswordEditor, PasswordEditorModal } from './password-editor.component';
import { EqualValidator } from './password-validator.directive';
@NgModule({
  imports: [
    ModalModule.withComponents([PasswordEditorModal]),
    FormsModule
  ],
  declarations: [
    PasswordEditor,
    PasswordEditorModal,
    EqualValidator,
    PasswordButton
  ],
  providers: [
  ],
  exports: [
    PasswordButton, PasswordEditor, PasswordEditorModal
  ]
})
export class PasswordEditorModule {

}
