import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatListModule } from '@angular/material';

import { NgDragDropModule } from 'ng-drag-drop';
import { TooltipModule } from 'ngx-bootstrap';

import { ContentPaneComponent } from './content-pane.component';
import { ContentTreeComponent } from './content-tree.component';

@NgModule({
  imports: [
    CommonModule, NgDragDropModule, TooltipModule, MatListModule
  ],
  exports: [ContentPaneComponent],
  declarations: [ContentPaneComponent, ContentTreeComponent]
})
export class ContentPaneModule { }
