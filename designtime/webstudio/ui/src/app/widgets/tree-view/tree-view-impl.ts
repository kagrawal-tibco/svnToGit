// import { Logger } from '../../core/logger.service';
import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';
import { Component, EventEmitter, Input, ViewEncapsulation } from '@angular/core';

import { TreeNode } from './tree-node';
import { TreeViewEvent } from './tree-view-event';

@Component({
  selector: 'tree-view-impl',
  templateUrl: './tree-view-impl.html',
  styleUrls: ['./tree-view-impl.css'],
  encapsulation: ViewEncapsulation.None,
  animations: [
    trigger('filtered', [
      state('filtered', style({
        opacity: '0',
        overflow: 'hidden',
        height: '0px'
      })),
      state('unfiltered', style({
        height: '*',
        overflow: 'visible',
      })),
      transition(':enter', [
        animate('.3s', keyframes([
          style({
            visibility: 'visible',
            opacity: 0,
            transform: 'translateX(100%)',
            offset: 0
          }),
          style({
            transform: 'translateX(-25px)',
            opacity: 1,
            offset: .30
          }),
          style({
            transform: 'translateX(0)',
            offset: 1
          })
        ]))
      ]),
      transition(':leave', [
        animate('.3s ease-out', keyframes([
          style({
            transform: 'translateX(0)',
            offset: 0
          }),
          style({
            opacity: 1,
            transform: 'translateX(25px)',
            offset: .70
          }),
          style({
            opacity: 0,
            transform: 'translateX(-100%)',
            offset: 1
          })
        ]))
      ]),
      transition('filtered => unfiltered', [
        animate('.3s', keyframes([
          style({
            visibility: 'visible',
            overflow: 'visible',
            opacity: .5,
            transform: 'translateX(100%)',
            offset: 0,
            height: '0px'
          }),
          style({
            transform: 'translateX(-25px)',
            opacity: 1,
            offset: .30,
            height: '*'
          }),
          style({
            transform: 'translateX(0)',
            offset: 1,
            height: '*'
          })
        ]))
      ]),
      transition('unfiltered => filtered', [
        animate('.3s ease-out', keyframes([
          style({
            transform: 'translateX(0)',
            offset: 0
          }),
          style({
            opacity: 1,
            transform: 'translateX(25px)',
            offset: .70
          }),
          style({
            opacity: 0,
            transform: 'translateX(-100%)',
            offset: 1,
            height: '0px'
          })
        ]))])
    ])]
})
export class TreeViewImpl {
  @Input()
  input: TreeNode;
  @Input()
  eventEmitter: EventEmitter<TreeViewEvent>;

  constructor(
  ) { }

  click(node: TreeNode, event: MouseEvent) {
    if (!node.disabledSelect) {
      event.stopPropagation();
      node.activate();
      this.eventEmitter.emit(TreeViewEvent.singleClick(node, event));
    }
  }

  dblclick(node: TreeNode, event: MouseEvent) {
    if (!node.disabledSelect) {
      if (node.isFolder) {
        this.toggleExpand(node, event);
      }
      this.click(node, event);
    }
  }

  rightClick(node: TreeNode, event: MouseEvent) {
    if (!node.disabledSelect) {
      event.preventDefault();
      event.stopPropagation();
      this.eventEmitter.emit(TreeViewEvent.rightClick(node, event));
    }
  }

  toggleExpand(node: TreeNode, event: Event) {
    event.stopPropagation();
    if (node.expanded) {
      node.collapse();
    } else {
      node.expand();
    }
  }

  dbltoggle(node: TreeNode, event: Event) {
    this.toggleExpand(node, event);
  }

  padding(node: TreeNode) {
    return node.level * 1.5 + 'em';
  }

  setPadding(node: TreeNode) {
    if (this.isRtl()) {
      return {
        'padding-right': this.padding(node)
      };
    } else {
      return {
        'padding-left': this.padding(node)
      };
    }
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
