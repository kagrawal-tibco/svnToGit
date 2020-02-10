import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';
import { AfterViewInit, Component, Injectable, OnDestroy, OnInit } from '@angular/core';
import { Input } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';
import { Subscription } from 'rxjs';

import { UserService } from 'app/core/user.service';

import { ContentGroup, ProjectsGroup } from './content-group';
import { ContentModelService, GroupChange } from './content-model.service';
import { ContentPaneComponent } from './content-pane/content-pane.component';
import { NavigationMode } from './content-pane/content-tree.component';
import { CreateGroupModal, CreateGroupModalContext } from './create-group/create-group.modal';
import { EditGroupModal, EditGroupModalContext } from './edit-group/edit-group.modal';

import { environment } from '../../../environments/environment';
import { ArtifactImporterModal, ArtifactImporterModalContext } from '../../artifact-importer/artifact-importer.modal';
import { BEImportProjectComponent, BEImportProjectContext } from '../../be-project-importer/be-project-importer.modal';
import { AlertService } from '../../core/alert.service';
import { ArtifactService } from '../../core/artifact.service';
import { Logger } from '../../core/logger.service';
import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { RestService } from '../../core/rest.service';
import { SettingsService } from '../../core/settings.service';
import { BESettings } from '../../models-be/settings-be';
import { Artifact, ArtifactType } from '../../models/artifact';
import { Notification } from '../../models/notification';
import { ProjectMeta } from '../../models/project-meta';
import { ProjectCheckoutModal, ProjectCheckoutModalContext } from '../../project-checkout/project-checkout.modal';
import { ProjectImporterModal, ProjectImporterModalContext } from '../../project-importer/project-importer.modal';
import { TreeManager } from '../../widgets/tree-view/tree-manager';
import { NodeType, TreeNode } from '../../widgets/tree-view/tree-node';
import { EventType, TreeViewEvent } from '../../widgets/tree-view/tree-view-event';
import { MultitabEditorService } from '../multitab-editor/multitab-editor.service';
import { Context, ContextMenuService, ItemMap } from '../project-explorer/context-menu.service';
import { EditMenubuilderService } from '../project-explorer/edit-menubuilder-service';
import { ProjectExplorerService } from '../project-explorer/project-explorer.service';
import { VisitEntry, VisitHistoryService } from '../visit-history/visit-history.service';
import { WorkspaceService } from '../workspace.service';

export class ContentFilter {
  currentFilter: RegExp; // current compiled filter expression

  constructor(private _groupService: ContentModelService,
    private _contentGrpComp: ContentGroupComponent, public i18n: I18n) { }

  test(name: string): boolean {
    if (this.currentFilter) {
      return !this.currentFilter.test(name.toUpperCase());
    }
    return false;
  }
  addResources(group: ContentGroup, activeNodes: Array<TreeNode>) {
    group.resources.forEach(res => {
      const node = this._contentGrpComp.getNodeForArtifact(res);
      //      this._contentGrpComp.getNodeForArtifact(res).then(node => {
      if (node && !activeNodes.includes(node)) {

        switch (node.payload.type.displayString) {
          case 'Decision Table': node.payload.type.displayString = this.i18n('Decision Table'); break;
          case 'Rule Template Instance': node.payload.type.displayString = this.i18n('Rule Template Instance'); break;
          case 'Concept': node.payload.type.displayString = this.i18n('Concept'); break;
          case 'Event': node.payload.type.displayString = this.i18n('Event'); break;
          case 'Rule Template': node.payload.type.displayString = this.i18n('Rule Template'); break;
          case 'Domain Model': node.payload.type.displayString = this.i18n('Domain Model'); break;
          case 'Rule Function': node.payload.type.displayString = this.i18n('Rule Function'); break;
          case 'Rule': node.payload.type.displayString = this.i18n('Rule'); break;
          case 'Channel': node.payload.type.displayString = this.i18n('Channel'); break;
          case 'Scorecard': node.payload.type.displayString = this.i18n('Scorecard'); break;
          case 'Rule Template View': node.payload.type.displayString = this.i18n('Rule Template View'); break;
          case 'Time Event': node.payload.type.displayString = this.i18n('Time Event'); break;
          case 'Process': node.payload.type.displayString = this.i18n('Process'); break;
          case 'Folder': node.payload.type.displayString = this.i18n('Folder'); break;
          case 'Project Summary': node.payload.type.displayString = this.i18n('Project Summary'); break;
        }
        activeNodes.push(node);
      }
      //      });
    });
  }

  resetFilterCount() {
    this._contentGrpComp.filteredCount = 0;
    this._contentGrpComp.unfilteredCount = 0;
  }

  processNodes(nodes: TreeNode[]) {
    this.resetFilterCount();
    const activeNodes = new Array<TreeNode>();
    this._groupService.groups.
      filter(grp => grp.active).
      forEach(grp => {
        this.addResources(grp, activeNodes);
      });
    nodes.forEach(node => {
      this.process(node, activeNodes);
    });
    if (this._contentGrpComp.filteredCount > 0) {
      this._contentGrpComp.placeholder = this.i18n('Filtering {{0}} artifacts', { 0: this._contentGrpComp.filteredCount });
    } else {
      this._contentGrpComp.placeholder = this.i18n('Enter filter text...');
    }
  }

  process(item: TreeNode, activeNodes: TreeNode[]) {
    if (item.type === NodeType.ROOT) {
      if (this._contentGrpComp._settingsService.latestSettings.hideInExplorer[item.id]) {
        // item belongs to a hidden project, filter it and return
        item.state = 'filtered';
        return;
      }
    }

    if (item.isFolder) {
      item.children.forEach(child => this.process(child, activeNodes));
      // see if all children have been filtered.  If so, hide the folder as well
      if (!this._contentGrpComp._settingsService.latestSettings.showEmptyFolders && !this.hasVisibleChildren(item)) {
        item.state = 'filtered';
      } else {
        item.state = 'unfiltered';
      }
      return;
    }

    // check whether item is hidden
    if (item.type === NodeType.LEAF) {
      const art = item.payload as Artifact;
      if (this._contentGrpComp._settingsService.latestSettings.hideInExplorer[art.projectId]) {
        // item belongs to a hidden project, filter it and return
        item.state = 'filtered';
        return;
      }
    }

    // first, filter the active groups
    if (!activeNodes.find(it => { return item.id === it.id; })) {
      if (item.type === NodeType.LEAF) {
        item.state = 'filtered';
        return;
      }
    }
    // now check this.settings.latestSettings.onlyDisplayCheckedOutArtifacts preference
    const onlyDisplayCheckedOutArtifact = this._contentGrpComp._settingsService.latestSettings.onlyDisplayCheckedOutArtifacts;
    if (item.type === NodeType.LEAF) {
      const art = item.payload as Artifact;
      if (onlyDisplayCheckedOutArtifact && !art.isCheckedOutArtifact) {
        item.state = 'filtered';
        return;
      }
    }

    // finally, filter by the filter expression
    if (this.currentFilter) {
      if (item.type === NodeType.LEAF) {
        const art = item.payload as Artifact;

        let name = art.name;
        const ext = art.type.extensions.find(e => name.endsWith(e));
        if (ext) {
          name += '.' + ext;
        }
        if (this.test(name)) {
          item.state = 'filtered';
          this._contentGrpComp.filteredCount++;
        } else {
          item.state = 'unfiltered';
          this._contentGrpComp.unfilteredCount++;
        }

        return;
      }
    }
    if (item.type === NodeType.LEAF) {
      item.state = 'unfiltered';
    }

  }

  setRegExpFilter(filter: RegExp) {
    this.currentFilter = filter;
  }

  private hasVisibleChildren(parent: TreeNode): boolean {
    if (!this.currentFilter) {
      // there is no current filter, always return true
      return true;
    }
    let found = null;
    parent.children.forEach(child => {
      if (child.type === NodeType.INTERNAL) {
        if (this.hasVisibleChildren(child)) {
          found = true;
          return;
        }
      } else if (child.state === 'unfiltered') {
        found = true;
        return;
      }
    });
    if (found) {
      // we found an unfiltered child, show the parent
      return true;
    }
    return false;
  }

}

@Injectable()
@Component({
  selector: 'content-group',
  templateUrl: './content-group.component.html',
  styleUrls: ['./content-group.component.css', '../workspace.component.css'],
  //  providers: [ContentModelService],
  animations: [
    trigger('sidebarState', [
      state('visible', style({
        // paddingLeft: 12,
        opacity: 1,
        transform: 'translateX(0)',
      })),
      state('hidden', style({
        // paddingLeft: 8,
        opacity: .8,
        transform: 'translateX(-95%)',
      })),
      transition('hidden => visible', [
        animate('.75s', keyframes([
          style({
            visibility: 'visible',
            opacity: .8,
            transform: 'translateX(-95%)',
            offset: 0
          }),
          style({
            transform: 'translateX(0)',
            opacity: 1,
            offset: 1
          })
        ]))
      ]),
      transition('visible => hidden', [
        animate('.75s ease-out', keyframes([
          style({
            transform: 'translateX(0)',
            offset: 0
          }),
          // style({opacity: 1, transform: 'translateX(25px)', offset: .70}),
          style({
            opacity: .8,
            transform: 'translateX(-95%)',
            offset: 1
          })
        ]))
      ]),
    ]),
    trigger('expansionChanged', [
      state('0', style({
        overflow: 'hidden',
        height: '0px',
        padding: '0px'
      })),
      state('1', style({
        overflow: 'auto',
        height: '*',
        padding: '12px'
      })),
      transition('1 => 0', animate('250ms ease')),
      transition('0 => 1', animate('250ms ease-in-out')),
    ]),
    trigger('groupState', [
      state('inactive', style({
        // background: "#2a2c36",
        color: '#F9F9F9',
        paddingLeft: 12,
        // border: "2px solid #F9F9F9",
        // borderLeft: "6px solid transparent"
      })),
      state('active', style({
        // background: "#4f4b5d",
        // background: "#2a2c36",
        // color: "#2f79b9",
        paddingLeft: 8,
        borderLeft: '6px solid #2f79b9',
        // border: "2px solid #2f79b9"
      })),
    ])
  ]
})
export class ContentGroupComponent implements OnInit, AfterViewInit, OnDestroy {

  get isBEUI(): boolean {
    return environment.enableBEUI;
  }

  get isTCEUI(): boolean {
    return environment.enableTCEUI;
  }

  get addArtifactLabel(): string {
    if (this.isTCEUI) {
      return this.i18n('Checkout Project');
    }
    return this.user.isAdmin ? this.i18n('Checkout\/Import Project ') : this.i18n('Checkout Project');
  }

  public placeholder = this.i18n('Enter filter text...');

  @Input()
  docked: boolean;

  breadcrumbs: Array<string> = new Array<string>();
  breadcrumbMap: Map<string, TreeNode> = new Map<string, TreeNode>();
  delay = 30;
  navigationMode: NavigationMode; // the currently selected navigation mode
  currentSettignsNavigationMode: NavigationMode; // the currently selected navigation mode in settings.
  navigationModes: Array<NavigationMode> = NavigationMode.MODES;
  filteredCount = 0;
  unfilteredCount = 0;
  contentFilter = new ContentFilter(this._contentService, this, this.i18n);

  // subscriptions
  groupChangeSubscription: Subscription;
  activationSubscription: Subscription;

  //  projectMap: Map<string, TreeNode> = new Map<string, TreeNode>();
  visibleItems: TreeNode[] = [];
  currentItems: TreeNode[] = [];
  manager: TreeManager = new TreeManager(this.i18n);

  // scrolling constants
  throttle = 300;
  scrollDistance = 2;
  displayCount = 30;

  // the filtered list of the currently selected artifacts (a subset of all currentItems)
  //  filteredItems: Array<TreeNode> = new Array<TreeNode>(); get rid of this, use css to filter
  sidebarState: string;
  cgExpanded = true;
  displayAccessMenu = 'false';

  private artifactCtxMenu: ContextMenuService;
  private groupCtxMenu: ContextMenuService;

  constructor(private _projectService: ProjectService,
    private _artifactService: ArtifactService,
    public _contentService: ContentModelService,
    public _historyService: VisitHistoryService,
    public _projectExplorerService: ProjectExplorerService,
    public _settingsService: SettingsService,
    private _editorService: MultitabEditorService,
    private _workspaceService: WorkspaceService,
    public _contentPane: ContentPaneComponent,
    private _editCtxMenu: EditMenubuilderService,
    private modal: ModalService,
    private alert: AlertService,
    private _log: Logger,
    private rest: RestService,
    public user: UserService,
    public i18n: I18n) {
    this.sidebarState = 'visible';
    this.groupChangeSubscription = this._contentService.groupChanges()
      .subscribe(change => {
        setTimeout(() => {
          this.processChanges(change);
        }, 0);
      });
    this.activationSubscription = this._workspaceService.active.subscribe(a => this.activate(a));
    this.currentSettignsNavigationMode = NavigationMode.LIST_NAV;
  }

  ngOnInit() {
    // this needs to be wrapped in a setTimeout to avoid an issue with the nav-bar,
    // where calling a rest service attempts to update the navbar after the navbar
    // has already been checked for changes
    setTimeout(() => {
      this._contentService.init().
        then(() => {
          let activeMode: NavigationMode;
          this.navigationModes.forEach(mode => {
            if (mode.active) {
              activeMode = mode;
            }
          });
          if (!activeMode) {
            if (environment.enableBEUI) {
              activeMode = this.setNavigationStyle();
            } else {
              activeMode = NavigationMode.TREE_NAV;
            }
            this.navigationMode = null; // this will ensure the tree_nav mode will get activated
          }
          if (environment.enableBEUI) {
            const groups = this._contentService.groups;
            for (let i = 0; i < groups.length; i++) {
              if (groups[i].groupId === 'Business$Rules' || groups[i].groupId === 'Decision$Tables') {
                groups[i].active = true;
              } else {
                groups[i].active = false;
              }
              if (groups[i].groupId === 'Business$Rules') {
                groups[i].groupName = this.i18n('Business Rules');
              } else if (groups[i].groupId === 'Decision$Tables') {
                groups[i].groupName = this.i18n('Decision Tables');
              } else if (groups[i].groupName === 'All Projects') {
                groups[i].groupName = this.i18n('All Projects');
              }
            }
          } else if (this._contentService.projectsGroup) {
            this._contentService.projectsGroup.active = true;
          }
          this.setNavigationMode(activeMode); // this will populate the contents pane
          //        this.toggleGroup(this._contentService.projectsGroup);
        });
    });

  }
  ngOnDestroy() {
    if (this.groupChangeSubscription) {
      this.groupChangeSubscription.unsubscribe();
    }
    if (this.activationSubscription) {
      this.activationSubscription.unsubscribe();
    }
  }

  basicTreeNav(): boolean {
    return this.navigationMode === NavigationMode.BASIC_TREE_NAV;
  }

  processChanges(change: GroupChange) {
    switch (change.state) {
      case 'DELETED':
        if (change.artifact) {
          let node = this.currentItems.find(it => it.payload.id === change.artifact.id);
          if (node) {
            this.currentItems.splice(this.currentItems.indexOf(node), 1);
            this.manager.removeNodeByKey(node.payload.id);
          } else {
            node = this.manager.getNodeByKey(change.artifact.id);
            if (node) {
              // if this is a tree-view, try to find it in the children and hide it to avoid a full refresh
              node.parent.removeChild(node);
            } else {
              this.refresh();
            }
          }
        } else {
          this.refresh();
        }
        this.processFilter();
        return;
      case 'UPDATED':
        if (change.oldArtifact && change.artifact) {
          let oldId = change.oldArtifact.id;
          let changedNode = this.manager.getNodeByKey(oldId);
          if (!changedNode) {
            oldId = this.getRelevantID(change.oldArtifact);
            changedNode = this.manager.getNodeByKey(oldId);
          }
          if (!changedNode) {
            oldId = this.getRelevantID(change.artifact);
            changedNode = this.manager.getNodeByKey(oldId);
          }
          if (changedNode) {
            this.manager.replaceNodePayload(changedNode, change.artifact);
          } else {
            this.refresh();
          }
        } else if (change.artifact) {
          let id = change.artifact.id;
          let changedNode = this.manager.getNodeByKey(id);
          if (!changedNode) {
            id = this.getRelevantID(change.artifact);
            changedNode = this.manager.getNodeByKey(id);
          }
          if (changedNode) {
            this.manager.replaceNodePayload(changedNode, change.artifact);
          } else {
            this.refresh();
          }
        } else {
          this.refresh();
        }
        this.processFilter();
        return;
      case 'ADDED':
        if (change.artifact) {
          const addedNode = this.getNodeForArtifact(change.artifact, true);
          // only insert if nav is flat list?
          if (addedNode && this.navigationMode === NavigationMode.LIST_NAV) {
            this.insertCurrentItem(addedNode);
          }
        } else {
          this.refresh();
        }
        this.processFilter();
        return;
    }
  }

  refreshAll() {
    this.clear();
    this._contentService.refresh().then(() => this.refresh());
    // this.refresh();
  }

  refresh() {
    if (environment.enableBEUI) {
      this.setNavigationMode(this.setNavigationStyle());
    }
    this.clear();
    this._contentService.groups.forEach(grp => grp.clear());
    this._contentService.populateGroups().then(
      () => this.populateCurrentItems());
    //    projects
    //      .forEach(p => {
    //        let found = this.roots.find(r => r.id === p.projectId);
    //        if (!found) {
    //          let root = this.projectToRoot(p);
    //          this.roots.push(root);
    //        }
    //      });
  }

  clear() {
    this.visibleItems = [];
    this.currentItems = [];
    this.displayCount = 30;
    this.manager.clear();
  }

  toggleAccessMenu(e?: Event) {
    if (e) {
      e.stopPropagation();
    }
    this.displayAccessMenu = this.displayAccessMenu === 'true' ? 'false' : 'true'; // !this.displayAccessMenu;
  }
  searchChanged(filter: string) {
    if (filter) {
      filter = filter.toUpperCase();
      if (!filter.startsWith('*')) {
        filter = '*' + filter;
      }
      filter = filter.replace(/\*/g, '.*');
      filter = filter.replace(/\?/g, '.?');
      try {
        this.contentFilter.setRegExpFilter(new RegExp(filter));
      } catch (Exception) {
        this.alert.flash(this.i18n('Invalid filter expression'), 'warning');
        return;
      }

      this.processFilter();
      if (this.currentItems.length === 1) { // FIXME: need to check filtered length
        // just open the only result -- TODO: make this optional behavior
        const artItem = this.currentItems[0];
        if (artItem.type === NodeType.LEAF) {
          this.activateTab(artItem);
        }
      }
    } else {
      this.clearFilter();
      this.addVisibleItems(0);
    }
    this.displayAccessMenu = 'false';
  }
  clearFilter() {
    this.contentFilter.setRegExpFilter(null);
    if (this.navigationMode === NavigationMode.BASIC_TREE_NAV) {
      const nodes = this._projectExplorerService.roots;
      const flattenedArray = new Array<TreeNode>();
      nodes.forEach(item => this.collectChildren(item, flattenedArray));
      nodes.forEach(node => this.contentFilter.process(node, flattenedArray));
    } else {
      this.contentFilter.processNodes(this.currentItems);
    }
  }

  openRecent(entry: VisitEntry) {
    const artifact: Artifact = this._artifactService.syncWithCache(entry.artifact);
    this._editorService.activateTab(artifact);
  }
  setNavigationMode(mode: NavigationMode) {
    if (mode === this.navigationMode) {
      return;
    }

    if (this.navigationMode) {
      this.navigationMode.active = false;
    }
    this.navigationMode = mode;
    mode.active = true;
    this.popAll().then(() => {
      this.displayCount = 30;
      this.visibleItems = [];
      this.breadcrumbs.length = 0;
      this.breadcrumbs.push(this.getInitialBreadcrumb());
      this.populateCurrentItems();
    });
  }
  getInitialBreadcrumb(): string {
    let initBreadcrumb = this.navigationMode.initialBreadcrumb as string;
    if (this._contentService.projectsGroup && this._contentService.projectsGroup.active) {
      // prepend 'All' to the initial breadcrumb to indicate that the projects group is selected
      initBreadcrumb = 'All ' + initBreadcrumb;
    }
    if (initBreadcrumb.search('All') !== -1) {
      initBreadcrumb = this.i18n('All Artifacts');
    } else if (initBreadcrumb.search('Artifacts') !== -1) {
      initBreadcrumb = this.i18n('Artifacts');
    }
    return initBreadcrumb;
  }

  activate(artifact: Artifact): Promise<any> {
    const id = artifact ? artifact.id : null;
    return this.manager.activateByKey(id);
  }
  activateTab(artItem: TreeNode) {
    setTimeout(() => {
      this.openEditor(artItem);
    }, 200);
  }

  onScrollDown() {
    this.addVisibleItems(30);
  }

  addVisibleItems(added: number) {
    if (this.currentItems.length === 0) {
      this.visibleItems = [];
      return;
    }
    const visible = [];
    const start = 0;
    this.displayCount += added;
    if (this.displayCount < 30) {
      this.displayCount = 30;
    }
    if (this.displayCount > this.currentItems.length) {
      this.displayCount = this.currentItems.length;
    }
    for (let i = start; i < this.currentItems.length; ++i) {
      if (this.currentItems[i].state === 'unfiltered') {
        visible.push(this.currentItems[i]);
        if (visible.length === this.displayCount) {
          break;
        }
      }
    }
    this.visibleItems = visible;
  }

  processFilter() {
    if (this.contentFilter) {
      if (this.navigationMode === NavigationMode.BASIC_TREE_NAV) {
        const nodes = this._projectExplorerService.roots;
        const flattenedArray = new Array<TreeNode>();
        nodes.forEach(item => this.collectChildren(item, flattenedArray));
        nodes.forEach(node => this.contentFilter.process(node, flattenedArray));
      } else {
        this.contentFilter.processNodes(this.currentItems);
        this.addVisibleItems(0);
      }
      //      this.currentItems.forEach(item => item.state = item.type === NodeType.LEAF
      //        && !this.contentFilter.test(item.payload.name) ? 'filtered' : 'unfiltered');
      //      if (this.navigationMode === NavigationMode.TREE_NAV) {
      //        this.filterChildren(this.currentItems);
      //      }
    } else {
      this.clearFilter();
      this.addVisibleItems(0);
    }
  }
  toggleSidebar() {
    this.sidebarState = this.sidebarState === 'visible' ? 'hidden' : 'visible';
    //    console.log('now set to  ' + this.sidebarState);
  }
  toggleMove() {
    //    console.log('Clicked');
  }

  addArtifactToGroup(cg: ContentGroup, e: any) {
    // Get the dropped data here
    if (environment.enableBEUI) {
      // Check if the payload is instance of Artifact.
      const artifact = e.dragData.payload;
      if (artifact instanceof Artifact
        && ((cg.fileType == null || cg.fileType.length === 0)
          || (cg.fileType.length > 0 && cg.fileType === e.dragData.payload.type.defaultExtension)
        )) {
        this._contentService.updateGroup(cg.groupId, cg.groupName, cg.fileType, e.dragData.payload, false).then(
          data => {
            if (data.response) {
              //        console.log(data.response);
            }
          }
        );
      } else {
        this.alert.flash(this.i18n('This artifact/project/folder cannot be added to {{groupname}} group.'), 'error');
      }
    } else {
      this._contentService.updateGroup(cg.groupId, cg.groupName, cg.fileType, e.dragData.payload, false).then(
        data => {
          if (data.response) {
            //        console.log(data.response);
          }
        }
      );
    }
  }
  ngAfterViewInit() {
    this.artifactCtxMenu = new ContextMenuService(this._log, this.i18n);
    this.groupCtxMenu = new ContextMenuService(this._log, this.i18n);
    this.artifactCtxMenu.initWithComponent(this, this._editCtxMenu.getMenuBuilder());
    this.groupCtxMenu.initWithComponent(this, this.groupsMenuBuilder(), '#group-context-menu');
  }

  handleTreeEvent(event: TreeViewEvent) {
    const node = event.node;

    if (event.type === EventType.RIGHT_CLICK) {
      const e: MouseEvent = event.event;
      const ctx: Context = {
        data: node,
        event: e,
        manager: this.manager
      };
      this.artifactCtxMenu.showMenu(ctx);
    } else if (event.type === EventType.CLICK) {
      if (node instanceof TreeNode) {
        this.toggleState(node);
      }
    }
  }

  showGroupsContextMenu(group: ContentGroup, event: MouseEvent) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
      const e: MouseEvent = event;
      const ctx: Context = {
        data: group,
        event: e,
        manager: this.manager
      };
      this.groupCtxMenu.showMenu(ctx);
    }
  }

  public groupsMenuBuilder() {
    return (ctx: Context): any => {
      const node = ctx.data as ContentGroup;
      // build the menu based on which kind of node was clicked
      const items: ItemMap = {};
      if (!environment.enableBEUI) {
        items['edit'] = {
          name: this.i18n('Edit Group...'),
          accesskey: 'edit',
          icon: 'fa-edit',
          disabled: node.system,
          callback: this.editGroup(node)
        };
        items['share'] = {
          name: node.shared ? this.i18n('Stop Sharing Group') : this.i18n('Share Group'),
          accesskey: 'share',
          icon: 'fa-user-plus',
          disabled: node.system,
          callback: this.shareGroup(node)
        };
      }
      items['delete'] = {
        name: this.i18n('Delete Group'),
        accesskey: 'delete',
        icon: 'fa-trash',
        disabled: node.system,
        callback: this.deleteGroup(node)
      };
      return items;
    };
  }

  editGroup(group: ContentGroup) {
    return (key, opt) => {
      this.modal.open(EditGroupModal, new EditGroupModalContext('Proj', group, this._contentService))
        .then(() => { },
          err => {
            if (err) {
              throw err;
            }
          }).catch(() => {
            this._log.err(this.i18n('Error while editing artifact group'));
          });
    };
  }

  deleteGroup(group: ContentGroup) {
    return (key, opt) => {
      this._contentService.deleteGroup(group).then(() => {
        this._contentService.refreshGroups();
        const idx = this._contentService.groups.findIndex(grp => {
          return grp.groupId === group.groupId;
        });
        if (idx >= 0) {
          this._contentService.groups.splice(idx, 1);
        }
        this.refreshAll();
      });
    };
  }

  shareGroup(group: ContentGroup) {
    return (key, opt) => {
      this._contentService.shareGroup(group.groupId, !group.shared).then(() => {
        group.shared = !group.shared;
      });
    };
  }

  removeArtifact(element: TreeNode) {
    if (element.type === NodeType.LEAF) {
      // need to check whether this artifact exists in any other selected group
      let removeItem = true;
      this._contentService.groups.
        filter(grp => grp.active).
        forEach(grp => {
          if (removeItem && grp.resources.includes(element.payload)) {
            removeItem = false;
          }
        });
      if (removeItem) {
        // need to remove at the proper level for nested/breadcrumb navigation
        let idx: number = this.currentItems.indexOf(element);
        if (idx !== -1) {
          this.currentItems.splice(idx, 1);
        } else {
          const parent = element.parent;
          if (parent.isFolder) {
            parent.removeChild(element);
          }
        }
        idx = this.currentItems.indexOf(element);
        if (idx !== -1) {
          this.currentItems.splice(idx, 1);
        }
      }
    }
  }
  addItem(item: Artifact) {
    const node = this.getNodeForArtifact(item, true);
    if (node && node.type === NodeType.LEAF) {
      node.state = 'unfiltered';
    }
  }

  getInsertionIndex(item: TreeNode) {
    if (this.currentItems.length === 0) {
      return 0;
    }
    return this.currentItems.findIndex(arrItem => {
      if (item.isFolder && !arrItem.isFolder) {
        return true;
      }
      if (!item.isFolder && arrItem.isFolder) {
        return false;
      }
      return item.text.toUpperCase() < arrItem.text.toUpperCase();
    });
  }
  toggleBreadcrumb(crumb: string) {
    const idx = this.breadcrumbs.lastIndexOf(crumb);
    this.breadcrumbs.splice(idx + 1);
    this.popAll().then(() => {
      if (idx === 0) {
        setTimeout(() => this.populateCurrentItems(), this.delay + 100);
      } else if (idx > 0) {
        const item = this.breadcrumbMap.get(crumb);
        if (item.isFolder) {
          setTimeout(() => this.addContent(item), this.delay + 100);
        }
      }
    });
  }
  insertCurrentItem(item: TreeNode) {
    //    if (this.currentItems.find(it => it.id === item.payload.id) === null) {
    if (!this.currentItems.includes(item)) {
      item.expanded = false; // while switching nav modes, change expansion level to false
      const idx = this.getInsertionIndex(item);
      if (idx < 0) {
        this.currentItems.push(item);
      } else {
        this.currentItems.splice(idx, 0, item);
      }
    }
  }

  //  filterAll() {
  //    let iter = this._contentService.projectsGroup.getAllArtifacts();
  //    let item = iter.next();
  //    while (!item.done) {
  //      item.value.state = 'filtered';
  //      item = iter.next();
  //    }
  //  }

  popAll(): Promise<any> {
    return new Promise((resolve, reject) => {
      if (this.visibleItems.length > 15) {
        // don't recursively pop the items that aren't visible anyway
        this.visibleItems.length = 15; // arbitrary length to trim
      }
      setTimeout(() => {
        this.popRecursive().then(() => {
          if (this.visibleItems.length === 0) {
            this.currentItems = [];
            // this.filterAll();
            resolve();
          }
        });
      }, this.delay);
    });
  }
  popRecursive(): Promise<any> {
    return new Promise((resolve, reject) => {
      if (this.visibleItems.length > 0) {
        this.visibleItems.pop();
        setTimeout(() => {
          this.popRecursive().then(() => {
            if (this.visibleItems.length === 0) {
              resolve();
            }
          });
        }, this.delay);
      } else {
        resolve();
      }
    });
  }
  //  private printItems(items: TreeNode[]) {
  //    items.forEach(it => {
  //      if (it.type === NodeType.LEAF) {
  //        console.log('Artifact: ' + it.text + ' level ' + it.level);
  //      }
  //      if (it.isFolder) {
  //        console.log('Container: ' + it.text + ' level ' + it.level);
  //      }
  //    });
  //  }

  toggleCgExpansion() {
    this.cgExpanded = !this.cgExpanded;
  }

  toggleGroup(cg: ContentGroup) {
    if (cg) {
      cg.active = !cg.active;
      if (cg !== this._contentService.projectsGroup && this._contentService.projectsGroup.active) {
        // don't return here.  It doesn't allow other content groups to get populated, so if the
        // project group is deselected, nothing will remain
        //        return;
      }
      if (!cg.active) {
        if (this._contentService.groups.
          filter(grp => grp.active).length === 0) {
          // no more groups are selected -- remove all artifacts and return
          this.currentItems = [];
          this.visibleItems = [];
          return;
        }
        this.processFilter();
      } else {
        if (cg instanceof ProjectsGroup) {
          cg.clear();
          this._contentService.populateGroups().then(() => {
            this.insertProjectGroupArtifacts(cg);
          });
        } else {
          this.insertGroupArtifacts(cg);
        }
      }
      // update initial breadcrumb based on groups selection
      if (this.breadcrumbs.length > 0) {
        const bc = this.getInitialBreadcrumb();
        this.breadcrumbs.splice(0, 1, bc);
      }
    }
  }
  insertProjectGroupArtifacts(cg: ProjectsGroup) {
    cg.resources.forEach(node => this.addItem(node));
    if (this.navigationMode !== NavigationMode.LIST_NAV && this.breadcrumbs.length > 1) {
      this.insertBreadcrumbItems(cg);
      this.setFilteredItems(this.currentItems);
    } else {
      this._contentService.getProjects()
        .then(metas => {
          metas.forEach(meta => {
            let root = this.manager.getNodeByKey(meta.projectId);
            if (!root) {
              // this can happen if the project contains no artifacts.  we still want to show it in tree mode
              root = this.projectToRoot(meta);
            }
            this.insertCurrentItem(root);
          });
        })
        .then(() => {
          this.setFilteredItems(this.currentItems);
        });
    }
  }

  insertGroupArtifacts(cg: ContentGroup) {
    this._contentService.getGroupArtifacts(cg).then(
      response => {
        if (!response.ok()) {
          this._log.warn(
            this.i18n('Error retrieving group artifacts for group {{groupname}}. Try refreshing all artifacts', { groupname: cg.groupName }));
          return;
        }
        const currSize = this.currentItems.length;
        cg.resources.length = 0;
        if (environment.enableBEUI) {
          response.record.forEach(rec => {
            const artifactPath: string = rec.artifactPath;
            const projectName: string = artifactPath.substring(0, artifactPath.indexOf('/'));
            const type: string = artifactPath.substring(artifactPath.indexOf('.') + 1);
            const path: string = artifactPath.substring(projectName.length, artifactPath.indexOf('.'));
            const artifactId = this._contentService.getUserName() + '@' + projectName + '@' + path + '@' + type;
            const artItem = this._contentService.projectsGroup.getArtifactItemById(artifactId);
            if (artItem) {
              artItem.locked = rec.locked;
              cg.addItem(this._artifactService.syncWithCache(artItem));
            }
          });
        } else {
          // get artifact from ID and create ArtifactItem
          if (response.record[0] && response.record[0].artifacts) {
            response.record[0].artifacts.forEach(artifact => {
              const artifactId = (artifact.path) ? artifact.path : artifact;
              const artItem = this._contentService.projectsGroup.getArtifactItemById(artifactId);
              if (artItem) {
                cg.addItem(this._artifactService.syncWithCache(artItem));
              }
            }
            );
          }
        }
        if (cg.fileType) {
          this._contentService.processFilter(cg, cg.fileType);
        }
        cg.resources.forEach(node => this.addItem(node));
        if (this.navigationMode !== NavigationMode.LIST_NAV) {
          if (this.breadcrumbs.length > 1) {
            this.insertBreadcrumbItems(cg);
          } else {
            const roots = cg.resources.map(res => this.manager.getNodeByKey(res.projectId));
            roots.forEach(node => {
              this.insertCurrentItem(node);
            });
          }
        } else {
          cg.resources.forEach(node => {
            const newNode = this.getNodeForArtifact(node, true);
            if (newNode) {
              this.insertCurrentItem(newNode);
            }
            //            this.getNodeForArtifact(node, true).then(newNode => this.insertCurrentItem(newNode));
          });
        }
        if (currSize !== this.currentItems.length) {
          this.setFilteredItems(this.currentItems);
        }
      }).catch(err => {
        alert(err);
        this._log.err(err);
      });
  }

  getRelevantID(artifact: Artifact) {
    if (!artifact) {
      return null;
    }

    if (artifact.parentId) {
      return artifact.parentId;
    }
    return artifact.id;
  }

  getNodeForArtifact(art: Artifact, createIfAbsent?: boolean): TreeNode {
    const node = this.manager.getNodeByKey(art.id);
    const updatedArt = this._artifactService.syncWithCache(art);
    if (node) {
      if (updatedArt !== art) {
        node.payload = updatedArt;
      }

      return node;
    } else if (createIfAbsent) {
      return this.createNodeForArtifact(updatedArt);
    }
  }

  addArtifactNode(artifact: Artifact) {
    let root = this.manager.getNodeByKey(artifact.projectId);
    if (!root) {
      root = this.projectToRoot(this._projectService.getProjectMeta(artifact.projectId));
    }
    try {
      return this.addArtifactToRoot(root, artifact);
    } catch (e) {
      const notification: Notification = <Notification>{
        id: 'errorid',
        read: false,
        content: e.message,
        timestamp: Date.now().toString(),
        details: e.stack,
        severity: 'danger',
        icon: 'fa fa-exclamation-circle'
      };
      this.alert.flashDetail(notification, 'error', true, -1, this.i18n('Duplicate artifact error'));
    }
    return null;
  }

  createNodeForArtifact(art: Artifact): TreeNode {
    return this.addArtifactNode(art);
  }

  insertBreadcrumbItems(cg: ContentGroup) {
    // need to check whether the container matches the current level
    const currLevel = this.breadcrumbs[this.breadcrumbs.length - 1];
    const current = this.breadcrumbMap.get(currLevel);
    cg.resources.
      map(res =>
        this.manager.
          getNodeByKey(res.id)). // TODO : this will fail if node has not been created already
      filter(res => res.root.id === current.root.id).
      filter(res => res.level > current.level).
      map(res => {
        let bcNode = res;
        while (bcNode && (bcNode.level > current.level + 1)) {
          bcNode = bcNode.parent;
        }
        return bcNode.parent.id === current.id ? bcNode : null;
      }).
      forEach(node => {
        if (node) {
          this.insertCurrentItem(node);
        }
      });
  }

  toggleState(item: TreeNode) {
    const delay = this.delay + 100; // add the animation duration
    if (item.isFolder) {
      if (this.navigationMode === NavigationMode.BREADCRUMB_NAV) {
        this.breadcrumbs.push(item.text);
        this.breadcrumbMap.set(item.text, item);
        this.contentFilter.processNodes(item.children);
        this.popAll().then(() => setTimeout(() => this.addContent(item), delay));
      } else {
        item.expanded = !item.expanded;
      }
    } else {
      this.openEditor(item);
    }
  }
  openEditor(artItem: TreeNode) {
    const artifact: Artifact = this._artifactService.syncWithCache(artItem.payload);
    if (this.isOpenAllowed(artifact)) {
      this._editorService.activateTab(artifact)
        .then(() => this._workspaceService.activateAll(artifact));
      this._historyService.activate(artifact);
      if (environment.enableBEUI) {
        const historyList = this._historyService.history;
        const artifactId = artifact.id;
        let found = false;
        for (let i = 0; i < historyList.length; i++) {
          if (historyList[i].key === artifactId) {
            found = true;
            break;
          }
        }
        if (!found) {
          const projectName = artifactId.split('@')[1];
          const artifactPath = artifact.path + '.' + artifact.type.defaultExtension;
          this.rest.put(`recentlyOpened/add.json?projectName=`
            + projectName
            + `&artifactPath=`
            + artifactPath, undefined, undefined)
            .toPromise().then((response) => {
              if (response.ok()) {

              }
            });
        }

      }
    } else {
      this.alert.flash(this.i18n('This file type cannot be edited'), 'warning');
    }
  }

  getHistoryItems() {
    if (environment.enableBEUI) {
      const allowedOpenLimit = (<BESettings>this._settingsService.latestSettings).recentlyOpenedArtifactLimit;
      const entryArray: Array<VisitEntry> = new Array<VisitEntry>();
      let count = 0;
      for (const entry of this._historyService.history) {
        entryArray.push(entry);
        count = count + 1;
        if (count === allowedOpenLimit) {
          break;
        }
      }
      return entryArray;
    } else {
      return this._historyService.history;
    }
  }

  isOpenAllowed(artifact: Artifact): boolean {
    if (!environment.enableBEUI) {
      return true;
    } else {
      if (artifact.type === ArtifactType.DOMAIN_MODEL
        || artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE
        || artifact.type === ArtifactType.BE_DECISION_TABLE) {
        return true;
      } else {
        return false;
      }
    }
  }

  onCreateNewArtifact() {
    if (this.currentItems.length === 0) {
      return;
    }
    const artifactType = ArtifactType.SB_DECISION_TABLE;
    const project: ProjectMeta = this._projectService.getProjectMeta(this.currentItems[0].payload.projectId);
    const projectId = project.projectId;
    const projectName = project.projectName;

    let baseDir: string; // = '';//node.path;
    this._projectService.getAllThenMerge(projectId)
      .then(artifacts => artifacts.map(a => a.path))
      .then(paths => this.modal.open(ArtifactImporterModal,
        new ArtifactImporterModalContext(projectId, projectName, baseDir, paths, artifactType)))
      .then((real: Artifact) => {
        if (real) {
          // the artifact will be marked as added in the project service 'addToCheckout'
          //          this._artifactService.markAsAdded(real);
          //          this.addArtifactToRoot(root, real);
          this._workspaceService.activateAll(real);
        }
      }, () => {/*rejected*/ });
  }

  addArtifactToRoot(root: TreeNode, artifact: Artifact) {
    const parts = artifact.baseDir.split('/').filter(p => p !== '');
    const folder: TreeNode = parts.reduce((parent, part) => {
      const key = parent.id + '/' + part;
      let found = parent.children.find(c => c.id === key);
      if (!found) {
        found = this.manager.internal(key);
        found.text = part;
        parent.addChild(found);
      }
      return found;
    }, root);
    const leaf = this.artifactToLeaf(artifact);
    folder.addChild(leaf);
    return leaf;
  }
  onImportProject() {
    this._projectService.getAllProjects()
      .then(metas => metas.map(meta => meta.projectName))
      .then(names => this.modal.open(ProjectImporterModal, new ProjectImporterModalContext(names)))
      .then(() => this._workspaceService.refresh().then(() => this.refresh()), err => {
        if (err) {
          throw err;
        }
      });
  }

  onCheckoutArtifacts() {
    this.modal.open(ProjectCheckoutModal, new ProjectCheckoutModalContext())
      .then(() => { }, err => { if (err) { throw err; } });
  }

  onImportBEProject() {
    this.modal.open(BEImportProjectComponent, new BEImportProjectContext())
      .then(() => { }, err => {
        if (err) {
          throw err;
        }
      });
  }

  addContent(cg: TreeNode) {
    if (!cg.isFolder) {
      this.openEditor(cg);
      return;
    }
    if (cg.isFolder) {
      cg.children.forEach(res => {
        this.insertCurrentItem(res);
      });
      this.setFilteredItems(this.currentItems);
    }
  }
  addNewGroup(e?: any) {
    if (e && e.stopPropagation) {
      e.stopPropagation();
    }

    this.modal.open(CreateGroupModal, new CreateGroupModalContext('Proj',
      e && e.dragData ? e.dragData.payload : null, this._contentService))
      .then(() => { },
        err => {
          if (err) {
            throw err;
          }
        }).catch(() => {
          this._log.err(this.i18n('Error while creating artifact group'));
        });
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

  private buildActiveTree(insert: boolean) {
    this._contentService.groups.
      filter(grp => grp.active).
      forEach(grp => {
        if (grp.groupId === 'Business$Rules') {
          grp.groupName = this.i18n('Business Rules');
        } else if (grp.groupId === 'Decision$Tables') {
          grp.groupName = this.i18n('Decision Tables');
        } else if (grp.groupName === 'All Projects') {
          grp.groupName = this.i18n('All Projects');
        }
        if (grp instanceof ProjectsGroup) {
          this.insertProjectGroupArtifacts(grp);
        } else {
          this.insertGroupArtifacts(grp);
        }
      });
  }

  private populateCurrentItems() {
    if (!this.navigationMode) {
      this.navigationMode = NavigationMode.TREE_NAV;
    }

    const projActive = (this._contentService.projectsGroup && this._contentService.projectsGroup.active);
    this.buildActiveTree(!projActive);
    if (projActive) {
      // projects group is selected, no need to insert all other active groups
      this._contentService.projectsGroup.resources.map(res => {
        const node = this.getNodeForArtifact(res, true);
        return node.root;
      }).forEach(node => {
        this.insertCurrentItem(node);
      });
    }
    this.setFilteredItems(this.currentItems);
  }
  private setFilteredItems(items: Array<TreeNode>) {
    if (this.navigationMode === NavigationMode.LIST_NAV) {
      const flattenedArray = new Array<TreeNode>();
      items.forEach(item => this.collectChildren(item, flattenedArray));
      this.sortItems(flattenedArray);
      this.contentFilter.processNodes(flattenedArray);
      this.currentItems = flattenedArray;
      this.addVisibleItems(0);
      return;
    }
    this.contentFilter.processNodes(items);
    this.sortItems(items);
    this.currentItems = items;
    this.visibleItems = this.currentItems;
  }
  private sortItems(arr: Array<TreeNode>) {
    arr.sort((a: TreeNode, b: TreeNode) => {
      if (a.isFolder && !b.isFolder) {
        return -1;
      }
      if (!a.isFolder && b.isFolder) {
        return 1;
      }
      const item1 = a.text.toUpperCase();
      const item2 = b.text.toUpperCase();
      if (item1 < item2) {
        return -1;
      } else if (item1 > item2) {
        return 1;
      } else {
        return 0;
      }
    });
  }
  private collectChildren(item: TreeNode, arr: Array<TreeNode>) {
    if (item.isFolder) {
      item.children.forEach(child => this.collectChildren(child as TreeNode, arr));
    } else {
      if (arr.indexOf(item) === -1) {
        arr.push(item);
      } else {
        //        console.log('skipping item '+item.path);
      }
    }
  }

  private projectToRoot(project: ProjectMeta): TreeNode {
    const root = this.manager.root(project.projectId);
    root.payload = project;
    root.text = project.projectName;
    return root;
  }

  private artifactToLeaf(artifact: Artifact): TreeNode {
    const leaf = this.manager.leaf(artifact.id);
    leaf.text = artifact.displayName();
    leaf.payload = artifact;
    return leaf;
  }

  private setNavigationStyle(): NavigationMode {
    const modeSettings = (<BESettings>this._settingsService.latestSettings).itemView;
    let newSettingNavigationMode: NavigationMode;

    NavigationMode.BREADCRUMB_NAV.displayString = this.i18n('Show breadcrumbs');
    NavigationMode.TREE_NAV.displayString = this.i18n('Show resources as a tree');
    NavigationMode.LIST_NAV.displayString = this.i18n('Show resources as a flattened list');
    NavigationMode.BASIC_TREE_NAV.displayString = this.i18n('Basic tree navigation (no groups)');

    switch (modeSettings) {
      case 'Breadcrumbs': newSettingNavigationMode = NavigationMode.BREADCRUMB_NAV; break;
      case 'Tree': newSettingNavigationMode = NavigationMode.TREE_NAV; break;
      case 'List': newSettingNavigationMode = NavigationMode.LIST_NAV; break;
      default: newSettingNavigationMode = NavigationMode.BASIC_TREE_NAV;
    }
    if (_.isEqual(newSettingNavigationMode.displayString, this.currentSettignsNavigationMode.displayString)) {
      let navigationMode = null;
      for (let i = 0; i < this.navigationModes.length; i++) {
        if (this.navigationModes[i].active) {
          navigationMode = this.navigationModes[i];
          break;
        }
      }
      if (navigationMode) {
        for (let i = 0; i < this.navigationModes.length; i++) {
          if (!_.isEqual(this.navigationModes[i].displayString, navigationMode.displayString)) {
            this.navigationModes[i].active = false;
          }
        }
        return navigationMode;
      } else {
        return newSettingNavigationMode;
      }
    } else {
      this.currentSettignsNavigationMode = newSettingNavigationMode;
      return newSettingNavigationMode;
    }
  }

}
