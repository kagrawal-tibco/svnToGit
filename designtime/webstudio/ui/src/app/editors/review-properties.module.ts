import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AgGridModule } from 'ag-grid-angular/main';
import { AccordionModule } from 'ngx-bootstrap';
import { TabsModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { ReviewPropertiesComponent } from './review-properties.component';

import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule, BrowserModule, FormsModule, AgGridModule.withComponents([]), AccordionModule.forRoot(), TabsModule],
  providers: [],
  declarations: [ReviewPropertiesComponent],
  exports: [ReviewPropertiesComponent]
})
export class ReviewPropertiesModule { }
