import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';
import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Logger } from 'app/core/logger.service';
import { Artifact } from 'app/models/artifact';

import { MultitabEditorService, Tab } from '../multitab-editor/multitab-editor.service';
import { Context, ContextMenuService } from '../project-explorer/context-menu.service';
import { EditMenubuilderService } from '../project-explorer/edit-menubuilder-service';

@Component({
    selector: 'open-editors',
    templateUrl: './open-editors.component.html',
    styleUrls: ['./open-editors.component.css', '../content-group/content-group.component.css'],
    animations: [
        trigger('expansionChanged', [
            state('0', style({
                overflow: 'hidden',
                height: '0px',
                display: 'none',
                padding: '0px',
            })),
            state('1', style({
                overflow: 'auto',
                height: '*',
                display: 'block',
                padding: '12px'
            })),
            transition('1 => 0', animate('250ms ease')),
            transition('0 => 1', animate('250ms ease-in-out')),
        ])
    ]
})

export class OpenEditors implements OnInit, AfterViewInit {

    expanded = false;

    artifactList: Artifact[];
    artifactEditorMap: Map<string, Tab>;

    private optionsCtxMenu: ContextMenuService;

    constructor(private log: Logger,
        private multitabService: MultitabEditorService,
        private editMenubuilder: EditMenubuilderService,
        public i18n: I18n) {
        this.artifactEditorMap = new Map<string, Tab>();
        this.artifactList = [];
    }

    ngOnInit() {
        this.optionsCtxMenu = new ContextMenuService(this.log, this.i18n);
        this.optionsCtxMenu.initWithComponent(this, this.editMenubuilder.getMenuBuilder(), '#options-context-menu');
    }

    ngAfterViewInit() {
        this.multitabService.getActiveTabs().subscribe((tabs: Tab[]) => {
            this.artifactList = [];
            for (let index = 0; index < tabs.length; index++) {
                const tab: Tab = tabs[index];
                const artifactPath: string = tab.payload.path;
                tab.payload.name;
                tab.payload.type.defaultExtension;
                this.artifactList.push(tab.payload);
                this.artifactEditorMap.set(artifactPath, tab);
            }
        });
    }

    toggleExpanded() {
        this.expanded = !this.expanded;
    }

    getType(extension: string): string {
        if (extension === 'rulefunctionimpl') {
            return 'Decision Table';
        } else if (extension === 'ruletemplateinstance') {
            return 'Rule Template Instance';
        } else if (extension === 'domain') {
            return 'Domain Model';
        } else if (extension === 'project') {
            return 'Project Summary';
        }
    }

    onClick(artifact: Artifact) {
        this.multitabService.activateTab(artifact);
    }

    onTabDirectiveRemoved(artifact: Artifact) {
        const tab: Tab = this.artifactEditorMap.get(artifact.path);
        this.multitabService.setCloseTab(tab);
        this.artifactEditorMap.delete(artifact.path);
    }

    closeAll() {
        this.multitabService.closeAllSubject.next(true);
        this.multitabService.activateTab(null);
    }

    onEditOptions(artifact: Artifact, event?: Event) {
        this.optionsCtxMenu.initWithComponent(this, this.editMenubuilder.getMenuBuilder(), '#options-context-menu');
        if (event) {
            event.preventDefault();
            event.stopPropagation();
            const e: Event = event;
            const ctx: Context = {
                data: artifact,
                event: e,
            };
            this.optionsCtxMenu.showMenu(ctx);
        }
    }
}
