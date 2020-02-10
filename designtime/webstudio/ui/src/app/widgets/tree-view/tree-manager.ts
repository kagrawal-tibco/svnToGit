import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { NodeType, TreeNode } from './tree-node';
import { TreeViewOption } from './tree-view-option';

@Injectable()
export class TreeManager {
  public option: TreeViewOption;
  private nodes: Map<string, TreeNode> = new Map<string, TreeNode>();
  private activatedId: string;
  constructor(public i18n: I18n
  ) {
    if (!this.option) {
      this.option = {
        icons: {
          file: 'fa fa-file',
          fileOpen: 'fa fa-file',
          checkbox: 'fa fa-square',
          checkboxSelected: 'fa fa-check-square',
          checkboxUnknown: 'fa fa-external-link',
          dragHelper: 'fa fa-play',
          dropMarker: 'fa fa-arrow-right',
          error: 'fa fa-exclamation-triangle',
          expanderClosed: 'fa fa-chevron-right',
          expanderLazy: 'fa fa-chevron-right',
          expanderOpen: 'fa fa-chevron-down',
          folder: 'fa fa-folder',
          folderOpen: 'fa fa-folder-open',
          loading: 'fa fa-refresh fa-spin'
        },
        lazyExpand: true,
      };
    }
  }

  root(id: string): TreeNode {
    const root = this.node(id);
    root.type = NodeType.ROOT;
    root.level = 0;
    return root;
  }

  internal(id: string): TreeNode {
    const internal = this.node(id);
    internal.type = NodeType.INTERNAL;
    return internal;
  }

  leaf(id: string): TreeNode {
    const leaf = this.node(id);
    leaf.type = NodeType.LEAF;
    return leaf;
  }

  getNodeByKey(id: string): TreeNode {
    return this.nodes.get(id);
  }

  removeNodeByKey(id: string) {
    this.nodes.delete(id);
  }

  replaceNodePayload(node: TreeNode, payload: any) {
    node.payload = payload;
    if (payload.id) {
      this.nodes.delete(node.id);
      this.nodes.set(payload.id, node);
      node.id = payload.id;
      node.text = payload.id;
    }
  }
  // only UI effect
  activateByKey(id: string): Promise<any> {
    const node = this.getNodeByKey(id);
    if (!node) {
      const activated = this.getActivatedNode();
      if (activated) {
        activated.selected = false;
      }
      this.activatedId = null;
      return Promise.resolve();
    } else {
      const activation = () => {
        if (this.activatedId !== undefined) {
          const activated = this.getActivatedNode();
          if (activated) {
            activated.selected = false;
          }
        }
        this.activatedId = id;
        this.getNodeByKey(id).selected = true;
      };
      if (id === this.activatedId) {
        // "Activated" node is the same node as the most recently activated node.
        this.getActivatedNode().selected = false;
        this.activatedId = null;
        return Promise.resolve();
      } else {
        if (node.parent && !node.parent.expanded) {
          return node.parent.expand().then(() => activation());
        } else {
          activation();
          return Promise.resolve();
        }
      }
    }
  }

  clear() {
    this.activatedId = null;
    this.nodes.clear();
  }

  getActivatedNode(): TreeNode {
    return this.getNodeByKey(this.activatedId);
  }

  private node(id: string): TreeNode {
    const node = new TreeNode(id, this.option, this, this.i18n);
    node.text = id;
    this.nodes.set(node.id, node);
    return node;
  }
}
