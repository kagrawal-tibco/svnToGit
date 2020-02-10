import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { SCMService } from '../core/scm.service';
import { ScmRepositoryRecord } from '../models/dto';
import { TreeManager } from '../widgets/tree-view/tree-manager';
import { TreeNode } from '../widgets/tree-view/tree-node';
@Component({
    selector: 'sandbox-artifact-importer',
    templateUrl: './sandbox-artifact-importer.component.html'
})
export class SandboxArtifactImporter implements OnInit, OnChanges {

    @Input()
    public repository: ScmRepositoryRecord;

    @Output()
    public root = new EventEmitter();

    public sandbox: TreeNode[];

    private manager;
    constructor(
        protected scm: SCMService,
        public i18n: I18n
    ) {
    }

    ngOnInit() {
        this.manager = new TreeManager(this.i18n);
        const root = this.manager.root('');
        root.text = this.repository.name;
        root.setActivateHandler((n) => this.activateDir(n));
        this.fillDirectory(root);
        root.expand().then(
            ok => this.sandbox = [root]
        );
    }

    ngOnChanges() {
        this.sandbox = [];
        this.ngOnInit();
    }

    private activateDir(selectedNode: TreeNode): Promise<boolean> {
        this.root.emit({
            repo: this.repository,
            root: selectedNode,
        });
        return Promise.resolve(true);
    }

    /**
     * This will populate a directory's children by calling the server.
     * This is done in a Breadth-first manner, as the user must traverse down the tree until they
     * can select the node that they wish to be the root of the AMS project.
     * @param node The selected node in the tree that needs to be filled with its children.
     */
    private fillDirectory(node: TreeNode): Promise<boolean> {
        return this.scm.enumerateRepository(this.repository.name, node.path, 1, 100).toPromise()
            .then(
                (response) => {
                    node.resetChildren(); // This line empties the directory of its children.
                    response.record.forEach(
                        (record: { path: string, isDirectory: boolean }) => {
                            const child = record.isDirectory ? this.manager.internal(record.path) : this.manager.leaf(record.path);
                            if (record.isDirectory) {
                                child.setExpandHandler(() => this.fillDirectory(child));
                                child.setActivateHandler((n) => this.activateDir(n));
                            } else {
                                child.disableSelect(true);
                                child.setActivateHandler((selectedNode: TreeNode) => {
                                    selectedNode.parent.activate();
                                    return Promise.resolve(true);
                                });
                            }
                            node.addChild(child);
                        });
                    return Promise.resolve(true);
                });
    }
}
