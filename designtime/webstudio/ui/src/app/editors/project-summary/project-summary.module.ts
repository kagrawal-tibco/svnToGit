import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { BrowserModule } from '@angular/platform-browser';

import { AgGridModule } from 'ag-grid-angular/main';
import { AccordionModule } from 'ngx-bootstrap';

import { ProjectSummaryComponent } from './project-summary.component';

import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [SharedModule,
    BrowserModule,
    FormsModule,
    AgGridModule.withComponents([]),
    AccordionModule.forRoot(),
    MatGridListModule,
    MatDividerModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule],
  providers: [],
  declarations: [ProjectSummaryComponent],
  bootstrap: [ProjectSummaryComponent]
})
export class ProjectSummaryModule { }
