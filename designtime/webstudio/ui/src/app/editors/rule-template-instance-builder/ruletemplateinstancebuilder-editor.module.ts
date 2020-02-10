/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:06:51+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-28T21:00:04+05:30
 */

import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu';
import { BrowserModule } from '@angular/platform-browser';

import { PopoverModule } from 'ngx-bootstrap';
import { AccordionModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { ActionClient } from './ActionClient';
import { MultiFilterClient } from './MultiFilterClient';
import { SingleFilterClient } from './SingleFilterClient';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder.component';
import { RuleTemplateInstanceBuilderEditorService } from './rule-template-instance-builder.service';

import { RTIConfilctResolverModal } from '../../editables/rule-template-instance/differ/rti-conflict-resolver.modal';
import { SharedModule } from '../../shared/shared.module';
import { ReviewPropertiesComponent } from '../review-properties.component';
import { ReviewPropertiesModule } from '../review-properties.module';

@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    AccordionModule.forRoot(),
    ReviewPropertiesModule,
    ModalModule.withComponents([RTIConfilctResolverModal]),
    SharedModule,
    MatMenuModule
  ],
  declarations: [RuleTemplateInstanceBuilder,
    MultiFilterClient,
    SingleFilterClient,
    ActionClient,
    RTIConfilctResolverModal],
  bootstrap: [RuleTemplateInstanceBuilder],
  entryComponents: [MultiFilterClient, SingleFilterClient],
  providers: [RuleTemplateInstanceBuilderEditorService]
})
export class RuleTemplateInstanceBuilderModule { }
