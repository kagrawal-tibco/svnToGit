import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggle, MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';

import { SelectModule } from 'ng-select';
import { DatepickerModule, PopoverModule, TimepickerModule } from 'ngx-bootstrap';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { SharedPipeModule } from './../pipes/shared-pipe/shared-pipe.module';
import { AddUserModal } from './add-user.modal';
import { DateTimePickerComponent } from './be-date-time-picker/DateTimePickerComponent';
import { ClickOutsideDirective } from './click-outside.directive';
import { ClosableComponent } from './closable.component';
import { ConfirmDeleterComponent } from './confirm-deleter.component';
import { ConfirmDeleterModal } from './confirm-deleter.modal';
import { DatetimeEditorComponent } from './datetime-editor.component';
import { DatetimePickerComponent } from './datetime-picker.component';
import { InputPasswordWithConfirmComponent } from './input-password-with-confirm.component';
import { InputWithConfirmComponent } from './input-with-confirm.component';
import { MultiChoicesComponent } from './multi-choices.component';
import { MultiSelectComponent } from './multi-select.component';
import { NoDuplicateDirective } from './no-duplicate.directive';
import { PasswordEditorModule } from './password-editor/password-editor.module';
import { SelectWithConfirmComponent } from './select-with-confirm.component';
import { SwitchComponent } from './toggle-switch.component';
import { TriButtonConfirmModal } from './tri-button-confirm.modal';
import { ValidDateTimeDirective } from './valid-datetime.directive';
import { ValidPathDirective } from './valid-path.directive';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    DatepickerModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule,
    PopoverModule,
    ReactiveFormsModule,
    SelectModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatButtonModule,
    MatDatepickerModule,
    MatSlideToggleModule,
    MatNativeDateModule,
    ModalModule.withComponents([
      TriButtonConfirmModal,
      ConfirmDeleterModal,
      AddUserModal
    ]),
    SharedPipeModule,
    PasswordEditorModule
  ],
  declarations: [
    AddUserModal,
    SwitchComponent,
    DatetimePickerComponent,
    DatetimeEditorComponent,
    MultiChoicesComponent,
    MultiSelectComponent,
    TriButtonConfirmModal,
    ConfirmDeleterComponent,
    ConfirmDeleterModal,
    NoDuplicateDirective,
    ValidPathDirective,
    ValidDateTimeDirective,
    ClickOutsideDirective,
    InputWithConfirmComponent,
    InputPasswordWithConfirmComponent,
    SelectWithConfirmComponent,
    ClosableComponent,
    DateTimePickerComponent
  ],
  exports: [
    AddUserModal,
    NoDuplicateDirective,
    ValidPathDirective,
    MatSlideToggle,
    DatetimePickerComponent,
    DatetimeEditorComponent,
    ClosableComponent,
    ValidDateTimeDirective,
    ClickOutsideDirective,
    SwitchComponent,
    MultiChoicesComponent,
    MultiSelectComponent,
    TriButtonConfirmModal,
    InputWithConfirmComponent,
    InputPasswordWithConfirmComponent,
    SelectWithConfirmComponent,
    FormsModule,
    CommonModule,
    SharedPipeModule,
    PasswordEditorModule,
    DateTimePickerComponent
  ]
})
export class SharedModule {

}
