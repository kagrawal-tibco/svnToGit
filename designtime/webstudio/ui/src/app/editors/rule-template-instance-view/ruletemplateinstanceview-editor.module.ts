/**
 * @Author: Rahil Khera
 */

import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AccordionModule } from 'ngx-bootstrap';

import { RTIViewClient } from './RTIViewClient';
import { RuleTemplateInstanceViewEditorService } from './rule-template-instance-view.service';

import { DateTimePickerComponent } from '../../shared/be-date-time-picker/DateTimePickerComponent';
import { SharedModule } from '../../shared/shared.module';
import { ReviewPropertiesModule } from '../review-properties.module';
@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    SharedModule,
    AccordionModule,
    ReviewPropertiesModule
  ],
  declarations: [
    RTIViewClient],
  providers: [RuleTemplateInstanceViewEditorService],
  bootstrap: [RTIViewClient],
  entryComponents: [RTIViewClient, DateTimePickerComponent]
})
export class RuleTemplateInstanceViewModule { }
