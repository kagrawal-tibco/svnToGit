import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';
import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { TreeNode } from '../../../widgets/tree-view/tree-node';
import { EventType, TreeViewEvent } from '../../../widgets/tree-view/tree-view-event';

export class NavigationMode {

  private static basic_tree_nav = new NavigationMode('Basic tree navigation (no groups)', 'All projects', 'fa fa-code-fork');
  private static tree_nav = new NavigationMode('Show resources as a tree', 'Project artifacts', 'fa fa-indent');
  private static list_nav = new NavigationMode('Show resources as a flattened list', 'Artifacts', 'fa fa-bars');
  private static breadcrumb_nav = new NavigationMode('Show breadcrumbs', 'Project artifacts', 'fa fa-ellipsis-h');
  active = false;

  static get BASIC_TREE_NAV() {
    return NavigationMode.basic_tree_nav;
  }

  static get TREE_NAV() {
    return NavigationMode.tree_nav;
  }

  static get LIST_NAV() {
    return NavigationMode.list_nav;
  }

  static get BREADCRUMB_NAV() {
    return NavigationMode.breadcrumb_nav;
  }

  static get MODES(): Array<NavigationMode> {
    const arr = new Array<NavigationMode>();
    arr.push(NavigationMode.TREE_NAV);
    arr.push(NavigationMode.LIST_NAV);
    arr.push(NavigationMode.BREADCRUMB_NAV);
    arr.push(NavigationMode.BASIC_TREE_NAV);
    return arr;
  }
  constructor(public displayString: string, public initialBreadcrumb: string, public icon: string) {
  }
}

@Component({
  selector: 'content-tree',
  templateUrl: './content-tree.component.html',
  styleUrls: ['./content-tree.component.css', './content-pane.component.css', '../content-group.component.css'],
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
    ]),
    trigger('expansionChangedSpan', [
      state('0', style({
        paddingTop: '.25em',
        marginRight: '.5em',
        transform: 'rotate(0deg)'
      })),
      state('1', style({
        paddingTop: '0em',
        marginRight: '.25em',
        transform: 'rotate(-90deg)'
      })),
      transition('1 => 0', animate('200ms', style({
        paddingTop: '.25em',
        marginRight: '.5em',
        transform: 'rotate(0deg)'
      }))
      ),
      transition('0 => 1', animate('200ms', style({
        paddingTop: '0em',
        marginRight: '.25em',
        transform: 'rotate(-90deg)'
      }))
      ),
    ]),
    trigger('expansionChanged', [
      state('0', style({
        opacity: '.7',
        overflow: 'hidden',
        height: '0px',
        display: 'none',
        paddingTop: '0px',
        borderLeft: '2px solid transparent'
      })),
      state('1', style({
        opacity: '1',
        overflow: 'hidden',
        height: '*',
        display: 'block',
        paddingTop: '5px',
        borderLeft: '2px solid #2f79b9'
      })),
      transition('1 => 0', animate('400ms ease-in-out')),
      transition('0 => 1', animate('400ms ease-in-out')),
    ]),
    //    trigger('stateChanged', [
    //      transition(':enter', [
    //        animate('2.3s', keyframes([
    //          style({
    //            visibility: 'visible',
    //            opacity: 0,
    //            transform: 'translateX(100%)',
    //            offset: 0
    //          }),
    //          style({
    //            transform: 'translateX(-25px)',
    //            opacity: 1,
    //            offset: .30
    //          }),
    //          style({
    //            transform: 'translateX(0)',
    //            offset: 1
    //          })
    //        ]))
    //      ]),
    //      transition(':leave', [
    //        animate('2.3s ease-out', keyframes([
    //          style({
    //            transform: 'translateX(0)',
    //            offset: 0
    //          }),
    //          style({
    //            opacity: 1,
    //            transform: 'translateX(25px)',
    //            offset: .70
    //          }),
    //          style({
    //            opacity: 0,
    //            transform: 'translateX(-100%)',
    //            offset: 1
    //          })
    //        ]))
    //      ]),
    //    ])
  ]
})
export class ContentTreeComponent implements OnInit {
  @Input()
  input: Array<TreeNode> = new Array<TreeNode>();
  @Input()
  eventEmitter: EventEmitter<TreeViewEvent>;
  @Input()
  navMode: NavigationMode;

  constructor(public i18n: I18n) { }

  ngOnInit() {

  }

  toggleState(item: TreeNode, e?: Event) {
    if (e) {
      e.preventDefault();
      e.stopPropagation();
      this.eventEmitter.emit(new TreeViewEvent(EventType.CLICK, item, e as MouseEvent));
    }
  }

  getItemLockStatus(item: TreeNode): boolean {
    if (item.payload.locked) {
      return true;
    } else {
      return false;
    }
  }

  rightClick(node: TreeNode, event: MouseEvent) {
    event.preventDefault();
    event.stopPropagation();
    this.eventEmitter.emit(TreeViewEvent.rightClick(node, event));
  }

  treeNav(): boolean {
    return this.navMode === NavigationMode.TREE_NAV;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
