import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BrowserModule } from '@angular/platform-browser';

import { AgGridModule } from 'ag-grid-angular/main';
import { AccordionModule } from 'ngx-bootstrap';

import { AddDomainEntryModal } from './domain-add-edit-entry.modal';
import { DomainModelComponent } from './domainmodel-editor.component';
import { DomainModelEditorService } from './domainmodel-editor.service';

import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    BrowserModule,
    FormsModule,
    MatGridListModule,
    MatInputModule,
    MatListModule,
    MatDividerModule,
    MatIconModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatSelectModule,
    MatToolbarModule,
    MatCheckboxModule,
    AgGridModule.withComponents([AddDomainEntryModal]),
    AccordionModule.forRoot(),
  ],
  providers: [DomainModelEditorService],
  declarations: [DomainModelComponent, AddDomainEntryModal],
  bootstrap: [DomainModelComponent],

})
export class DomainModelModule { }
