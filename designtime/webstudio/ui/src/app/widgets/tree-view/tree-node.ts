import { I18n } from '@ngx-translate/i18n-polyfill';

import { TreeManager } from './tree-manager';
import { TreeViewOption } from './tree-view-option';

import { environment } from '../../../environments/environment';
import { Artifact, ArtifactStatus, ArtifactType } from '../../models/artifact';

export enum NodeType {
  ROOT,
  INTERNAL,
  LEAF,
}

export type TreeEventHandler = (node: TreeNode, event?: Event) => Promise<boolean>;

export class TreeNode {

  get disabledSelect() {
    return this.disableSelection;
  }

  get isFolder() {
    return this.type !== NodeType.LEAF;
  }

  get hasChildren() {
    return this.children && this.children.length > 0;
  }

  get path(): string {
    const path = this._path;
    return this.type === NodeType.INTERNAL ? path + '/' : path;
  }

  // not include the root
  get _path(): string {
    if (this.parent && this.parent.type !== NodeType.ROOT) {
      const path = this.parent._path + '/' + this.text;
      return path;
    } else if (this.type !== NodeType.ROOT) {
      return '/' + this.text;
    } else {
      return '/';
    }
  }

  get root(): TreeNode {
    if (this.parent) {
      return this.parent.root;
    } else {
      return this;
    }
  }
  // structral
  text: string;
  parent: TreeNode;
  children: TreeNode[] = [];
  type: NodeType;
  level: number;

  // UI
  selected: boolean;
  expanded = false;
  loading: any;
  state = 'unfiltered';

  // data
  payload: any;
  public artStatus: string;

  private disableSelection: boolean;
  // state management
  private expandHandler: TreeEventHandler;
  private activateHandler: TreeEventHandler;

  private internalChildrenCnt = 0;

  constructor(
    public id: string,
    public opts: TreeViewOption,
    private manager: TreeManager,
    public i18n: I18n
  ) {
  }

  nodeIcon() {
    if (this.isFolder) {
      if (this.expanded) {
        return this.opts.icons.folderOpen;
      } else {
        return this.opts.icons.folder;
      }
    } else {
      return this.opts.icons.file;
    }
  }

  expanderIcon() {
    if (this.isFolder) {
      if (this.loading) {
        return this.opts.icons.loading;
      } else if (this.expanded) {
        return this.opts.icons.expanderOpen;
      } else {
        return this.opts.icons.expanderClosed;
      }
    } else {
      return 'tree-view-non-expandable';
    }
  }

  /**
   * Soft expand will not invoke the custom expandHandler
   */
  expand(soft?: boolean): Promise<boolean> {
    const expandAction = (): Promise<boolean> => {
      if (!soft && this.expandHandler) {
        this.loading = true;
        return this.expandHandler(this)
          .then(ok => ok && this.defaultExpand());
      } else {
        return Promise.resolve(this.defaultExpand());
      }
    };
    if (this.parent && !this.parent.expanded) {
      return this.parent.expand(soft).then(() => expandAction());
    } else {
      return expandAction();
    }
  }

  defaultExpand(): boolean {
    this.loading = false;
    this.expanded = true;
    return true;
  }

  activate() {
    if (this.activateHandler) {
      if (this.isOpenAllowed(this.payload)) {
        this.activateHandler(this).then(ok => ok && this.manager.activateByKey(this.id));
      }
    } else {
      this.manager.activateByKey(this.id);
    }
  }

  resetChildren() {
    this.children = [];
    this.internalChildrenCnt = 0;
  }

  addChild(child: TreeNode) {
    child.setLevel(this.level + 1);
    child.parent = this;
    const duplicate = this.children.find(n => n.text === child.text);
    if (duplicate) {
      // No need to throw error, just do nothing if duplicate
      // throw new Error(
      // this.i18n('Error adding \"{{0}}\". A duplicate node with the same name already exists in the folder \"{{1}}\". You might continue to encounter this error until the duplicate file has been removed/renamed', {0:child.text, 1:this.text}));
    } else {
      let partial: TreeNode[];
      if (child.type === NodeType.INTERNAL) {
        partial = this.children.slice(0, this.internalChildrenCnt);
      } else if (child.type === NodeType.LEAF) {
        partial = this.children.slice(this.internalChildrenCnt);
      } else {
        throw this.i18n('Should not add root to another node');
      }
      let idx = partial.findIndex(n => n.text.localeCompare(child.text) > 0);
      if (child.type === NodeType.INTERNAL) {
        if (idx === -1) {
          idx = this.internalChildrenCnt;
        }
        this.internalChildrenCnt++;
      } else {
        if (idx === -1) {
          idx = this.children.length;
        } else {
          idx = this.internalChildrenCnt + idx;
        }
      }
      this.children.splice(idx, 0, child);
    }
  }

  removeChild(child: TreeNode) {
    const idx = this.children.indexOf(child);
    if (idx !== -1) {
      this.children.splice(idx, 1);
      if (child.type === NodeType.INTERNAL) {
        this.internalChildrenCnt--;
      }
    }
  }

  disableSelect(disabled: boolean) {
    this.disableSelection = disabled;
  }

  setExpandHandler(handler: TreeEventHandler) {
    this.expandHandler = handler;
  }

  setActivateHandler(handler: TreeEventHandler) {
    this.activateHandler = handler;
  }

  setLevel(l: number) {
    this.level = l;
    this.children.forEach(c => c.setLevel(l + 1));
  }

  collapse() {
    this.expanded = false;
  }

  statusColor() {
    const art = this.payload;
    if (art instanceof Artifact) {
      switch (art.status) {
        case <ArtifactStatus>'ADDED': {
          this.artStatus = this.i18n('ADDED');
          return '#33cc33';
        }
        case <ArtifactStatus>'MODIFIED': {
          this.artStatus = this.i18n('MODIFIED');
          return '#ffff33';
        }
        case <ArtifactStatus>'DELETED': {
          this.artStatus = this.i18n('DELETED');
          return 'red';
        }
        case <ArtifactStatus>'CLEAN': {
          this.artStatus = this.i18n('CLEAN');
          return '#66ccff';
        }
      }
    }
    this.artStatus = art.status;
    return '#FFF';
  }
  info(): string {
    if (this.type === NodeType.LEAF) {
      const art = this.payload as Artifact;
      let path = art.path;
      if (!path.startsWith('/')) {
        path = '/' + path;
      }
      let info = this.i18n('Location: ') + this.root.text + path;
      //        ' IDs (id)' + this.payload.id + ':: (parentId)' + this.payload.parentId +
      if (art.isCheckedOutArtifact) {
        info += '<br>' + this.i18n('Artifact is checked out');
        info += '<br>' + this.i18n('Current Status') + ` <b><font color="${this.statusColor()}">${this.artStatus}</font></b>`;
        if (art.checkedOutFromRevision) {
          info += '<br>  <i>(at revision ' + art.checkedOutFromRevision + ')</i>';
        }
      } else {
        info += '<br>' + this.i18n('Artifact is not checked out');
      }
      if (art.locked) {
        info += '<br><font color="orange"><i>' + this.i18n('Artifact is locked') + '</i></font>';
      }
      if (art.needSync) {
        info += '<br><font color="#ffff33"><b>' + this.i18n('Artifact needs to be synchronized') + '</b>';
        info += `<br>  <i>(remote revision ${art.latestRevision})</i></font>`;
      }
      return info;
    }

    return this.path;
  }

  overlayIcon() {
    const art = this.payload;
    if (art instanceof Artifact) {
      switch (art.status) {
        case <ArtifactStatus>'ADDED':
          return 'overlay-info fa fa-plus';
        case <ArtifactStatus>'MODIFIED':
          return 'overlay-warning fa fa-asterisk';
        case <ArtifactStatus>'DELETED':
          return 'overlay-danger fa fa-minus';
        case <ArtifactStatus>'CLEAN':
          return 'overlay-clean fa fa-check-circle';
      }
    }
  }

  icon() {
    if (this.type === NodeType.ROOT) {
      return 'fa fa-briefcase';
    }

    if (this.isFolder) {
      if (this.expanded) {
        //        return 'fa fa-folder-open';//this.opts.icons.folderOpen;
        return 'fa fa-folder-open'; // this.opts.icons.folderOpen;
      } else {
        return 'fa fa-folder'; // this.opts.icons.folder;
      }
    } else {
      const art = this.payload as Artifact;
      return art.type.defaultIcon;
    }
  }

  prefix() {
    const art = this.payload;
    if (art instanceof Artifact) {
      switch (art.status) {
        case <ArtifactStatus>'ADDED':
          return '+';
        case <ArtifactStatus>'MODIFIED':
          return Artifact.DIRTY_PREFIX;
      }
    }
    return '';
  }

  label(): string {
    const label = this.payload.name + ' [' + this.payload.type.displayString + ']';
    return label; // + this.prefix();
  }

  isOpenAllowed(artifact: Artifact): boolean {
    if (!environment.enableBEUI) {
      return true;
    } else {
      if (artifact.type === ArtifactType.DOMAIN_MODEL || artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE || artifact.type === ArtifactType.BE_DECISION_TABLE) {
        return true;
      } else {
        return false;
      }
    }
  }

}
