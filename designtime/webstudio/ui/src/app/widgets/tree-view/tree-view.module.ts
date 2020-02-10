import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { TreeView } from './tree-view';
import { TreeViewImpl } from './tree-view-impl';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [TreeView],
  declarations: [TreeView, TreeViewImpl]
})
export class TreeViewModule { }
