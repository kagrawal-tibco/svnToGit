import { Component, EventEmitter, Input, Output } from '@angular/core';

import { TreeNode } from './tree-node';
import { TreeViewEvent } from './tree-view-event';

@Component({
  selector: 'tree-view',
  templateUrl: './tree-view.html',
  styleUrls: ['./tree-view.css'],
})
export class TreeView {
  @Input()
  input: TreeNode[];
  @Input()
  emptyMsg: string;
  @Output('tree-view-event')
  eventEmitter: EventEmitter<TreeViewEvent> = new EventEmitter<TreeViewEvent>();
}
