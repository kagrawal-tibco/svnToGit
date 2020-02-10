import { Component, EventEmitter, Input, Output } from '@angular/core';

import { NavigationMode } from './content-tree.component';

import { TreeNode } from '../../../widgets/tree-view/tree-node';
import { EventType, TreeViewEvent } from '../../../widgets/tree-view/tree-view-event';

@Component({
  selector: 'content-pane',
  templateUrl: './content-pane.component.html',
  styleUrls: ['./content-pane.component.css', '../content-group.component.css']
})
export class ContentPaneComponent {
  @Input()
  input: Array<TreeNode> = new Array<TreeNode>();
  @Input()
  navMode: NavigationMode;
  @Output('tree-view-event')
  eventEmitter: EventEmitter<TreeViewEvent> = new EventEmitter<TreeViewEvent>();
  constructor() {
  }
}
