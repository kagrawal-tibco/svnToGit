
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { filter } from 'rxjs/operators';

import { ArtifactService } from '../../core/artifact.service';
import { Logger } from '../../core/logger.service';
import { ProjectService } from '../../core/project.service';
import { Artifact } from '../../models/artifact';
import { ProjectMeta } from '../../models/project-meta';
import { TreeManager } from '../../widgets/tree-view/tree-manager';
import { NodeType, TreeEventHandler, TreeNode } from '../../widgets/tree-view/tree-node';

// a bridge service that connect multitab-editor and project-explorer
@Injectable()
export class ProjectExplorerService {

  public roots: TreeNode[] = [];
  public manager: TreeManager = new TreeManager(this.i18n);
  // set by WorkspaceService
  public leafActivateHandler: TreeEventHandler;
  // set by WorkspaceService
  public rootExpandHandler: TreeEventHandler;

  constructor(
    private log: Logger,
    private project: ProjectService,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {
    this.artifact.stateChanges().pipe(
      filter(change => {
        return change.artifact !== null && (
          change.state === 'DELETED' ||
          change.state === 'DISPOSED' ||
          change.state === 'MODIFIED' ||
          change.state === 'CHECKED-OUT' ||
          change.state === 'ADDED'
        );
      }))
      .subscribe(change => {
        switch (change.state) {
          case 'DELETED':
            return this.removeArtifactFromExplorer(change.artifact);
          case 'DISPOSED':
            return this.artifact.getArtifactLatest(change.artifact.parentId)
              .then(latest => this.replaceExplorer(change.artifact, latest));
          case 'CHECKED-OUT':
            if (change.artifact && change.updated) {
              return this.replaceExplorer(change.artifact, change.updated);
            } else {
              return Promise.resolve();
            }
          case 'ADDED':
            this.addArtifactToExplorer(change.artifact);
            return Promise.resolve();
          case 'MODIFIED':
            return this.replaceExplorer(change.artifact, change.updated);
        }
      });
  }

  refresh(projects: ProjectMeta[]) {
    this.clear();
    projects
      .forEach(p => {
        const found = this.roots.find(r => r.id === p.projectId);
        if (!found) {
          const root = this.projectToRoot(p);
          this.roots.push(root);
        }
      });
  }

  clear() {
    this.roots = [];
    this.manager.clear();
  }

  activateExplorer(artifact: Artifact): Promise<any> {
    const id = artifact ? artifact.id : null;
    return this.manager.activateByKey(id);
  }

  addArtifactToExplorer(artifact: Artifact) {
    const root = this.getNodeByKey(artifact.projectId);
    return this.addArtifactToRoot(root, artifact);
  }

  replaceExplorer(lhs: Artifact, rhs: Artifact) {
    return this.removeArtifactFromExplorer(lhs)
      .then(() => this.addArtifactToExplorer(rhs));
  }

  removeArtifactFromExplorer(artifact: Artifact) {
    const root = this.getNodeByKey(artifact.projectId);
    return this.removeArtifactFromRoot(root, artifact);
  }

  removeProjectFromExplorer(meta: ProjectMeta) {
    const idx = this.roots.findIndex(root => root.id === meta.projectId);
    if (idx !== -1) {
      this.roots.splice(idx, 1);
    }
  }

  addArtifactToRoot(root: TreeNode, artifact: Artifact) {
    //    return Promise.resolve()
    //      .then(() => {
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
    //      });
  }

  get hasVisibleProjects() {
    return this.roots && this.roots.length > 0;
  }

  private getNodeByKey(id: string) {
    return this.manager.getNodeByKey(id);
  }

  private removeNodeByKey(id: string) {
    this.manager.removeNodeByKey(id);
  }

  private removeArtifactFromRoot(root: TreeNode, artifact: Artifact) {
    const activated = this.manager.getActivatedNode();
    let node = this.getNodeByKey(artifact.id);
    if (node) {
      while (node.parent && node.parent.type !== NodeType.ROOT && node.parent.children.length === 1) {
        // remove the node from manager's registry first
        this.removeNodeByKey(node.id);
        node = node.parent;
      }
      this.removeNodeByKey(node.id);
      if (node.parent) {
        node.parent.removeChild(node);
        node.parent = null;
      } else if (node.type === NodeType.ROOT) {
        this.log.warn('shall not reach the root node:', node.text);
      } else {
        this.log.warn('shall have parent:', node);
      }
    }
    if (activated && activated.id === artifact.id) {
      return this.activateExplorer(null);
    } else {
      return Promise.resolve();
    }
  }

  private projectToRoot(project: ProjectMeta): TreeNode {
    const root = this.manager.root(project.projectId);
    root.payload = project;
    root.text = project.projectName;
    root.setExpandHandler(this.rootExpandHandler);
    return root;
  }

  private artifactToLeaf(artifact: Artifact): TreeNode {
    const leaf = this.manager.leaf(artifact.id);
    leaf.text = artifact.displayName();
    leaf.payload = artifact;
    leaf.setActivateHandler(this.leafActivateHandler);
    return leaf;
  }

}
