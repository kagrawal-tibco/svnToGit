
import { AfterViewInit, Component, ViewChild, ViewContainerRef } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ICellEditorAngularComp } from 'ag-grid-angular/main';
import { map } from 'rxjs/operators';

import { RestService } from '../../../core/rest.service';
import { SettingsService } from '../../../core/settings.service';
import { BESettings } from '../../../models-be/settings-be';
import { SingleDomainEntry } from '../../domain-model/domain-entry';

@Component({
    selector: 'editor-cell',
    templateUrl: './decisiontable-domain-cell-editor.html',
    styleUrls: ['./decisiontable-domain-cell-editor.css']
})
export class DomainCellEditorComponent implements ICellEditorAngularComp, AfterViewInit {
    entries: Array<SingleDomainEntry> = new Array<SingleDomainEntry>();
    filteredEntries: Array<SingleDomainEntry> = new Array<SingleDomainEntry>();
    entriesSelected: Array<SingleDomainEntry> = new Array<SingleDomainEntry>();
    columnType = 'int';
    isConditionCol = true;
    valueFilter = '';
    descriptionFilter = '';
    customString = '';

    @ViewChild('container', { static: true }) public container;
    private params: any;

    constructor(
        private artifact: RestService,
        private settings: SettingsService,
        public i18n: I18n) {
    }

    // dont use afterGuiAttached for post gui events - hook into ngAfterViewInit instead for this
    ngAfterViewInit() {
        // this.container.element.nativeElement.focus();
    }

    agInit(params: any): void {
        this.columnType = params.params.coltype;
        this.isConditionCol = params.params.isCondition;
        this.artifact.get('domains.json?projectName=' + params.params.pname + '&argumentPath=' + params.params.artifactpath + '&argumentType=concept&artifactType=' + params.params.artifacttype + '&argumentPropertyName=' + params.params.propertyname + '&isConditionColumn=' + params.params.isCondition).pipe(
            map(res => {
                if (res.ok()) {
                    // console.log(res.record[0]);
                    this.createDomainEntriesArray(res.record, params.value);
                }
            }))
            .toPromise();
    }

    createDomainEntriesArray(record: any, value: string) {
        this.customString = value;
        for (const entry of record) {
            const selected = this.domainValuesEqual(value, entry.value);
            const sEntry = new SingleDomainEntry(selected, null, entry.value, entry.description !== '<NA>' ? entry.description : '');
            this.entries.push(sEntry);
            if (selected) {
                this.entriesSelected.push(sEntry);
            }
        }
    }

    domainValuesEqual(val1: string, val2: string): boolean {
        if (val1 === val2) {
            return true;
        }
        if (val1.includes('||')) {
            const split = val1.split('||');
            for (let index = 0; index < split.length; index++) {
                const val = split[index];
                if (this.domainValuesEqual(val, val2)) {
                    return true;
                }
            }
        }
        if (val1.charAt(0) === '"') {
            if (val1.substr(1, val1.length - 2) === val2) {
                return true;
            }
        }
        if (val2.charAt(0) === '"') {
            if (val2.substr(1, val2.length - 2) === val1) {
                return true;
            }
        }
        return false;
    }

    getExpressionValue(): any {
        let cellExpression = '';
        if (this.entriesSelected.length >= 1 && !this.isStarPresent()) {
            let count = 1;
            for (const entry of this.entriesSelected) {
                if (this.entriesSelected.length !== count) {
                    if (this.columnType === 'string') {
                        cellExpression = cellExpression + '\"' + entry.value + '\"' + '||';
                    } else {
                        cellExpression = cellExpression + entry.value + '||';
                    }
                } else {
                    if (this.columnType === 'string') {
                        cellExpression = cellExpression + '\"' + entry.value + '\"';
                    } else {
                        cellExpression = cellExpression + entry.value;
                    }
                }
                count = count + 1;
            }

        } else if (this.entriesSelected.length >= 1) {
            cellExpression = '*';
        }
        this.customString = cellExpression;
        return cellExpression;

    }

    getValue() {
        return this.customString;
    }

    isStarPresent(): boolean {
        let present = false;
        for (const entry of this.entriesSelected) {
            if (entry.value === '*') {
                present = true;
            }
        }
        return present;
    }

    isPopup(): boolean {
        return true;
    }

    updateList(entry: any) {
        if (this.isConditionCol) {
            if (this.entriesSelected.indexOf(entry) !== -1) {
                entry.selected = false;
                this.entriesSelected.splice(this.entriesSelected.indexOf(entry), 1);
            } else {
                entry.selected = true;
                this.entriesSelected.push(entry);
            }
        } else {
            if (this.entriesSelected.indexOf(entry) !== -1) {
                entry.selected = false;
                this.entriesSelected.splice(0, this.entriesSelected.length);
            } else {
                entry.selected = true;
                this.entriesSelected.splice(0, this.entriesSelected.length);
                this.entriesSelected.push(entry);
            }
        }

        this.getExpressionValue();
    }

    getIsChecked(entry: any) {
        for (const e of this.entriesSelected) {
            if (entry.value === e.value) {
                return true;
            }
        }
    }

    getFilteredEntries() {
        if (this.valueFilter !== '' || this.descriptionFilter !== '') {
            if (this.valueFilter === '') {
                this.filteredEntries = this.entries.filter(entry => (entry.description.toLowerCase()).startsWith(this.descriptionFilter.toLowerCase()));
            } else if (this.descriptionFilter === '') {
                this.filteredEntries = this.entries.filter(entry => (entry.value.toLowerCase()).startsWith(this.valueFilter.toLowerCase()));
            } else {
                this.filteredEntries = this.entries.filter(entry => (entry.value.toLowerCase()).startsWith(this.valueFilter.toLowerCase()) && (entry.description.toLowerCase()).startsWith(this.descriptionFilter.toLowerCase()));
            }
            return this.filteredEntries;
        } else {
            return this.entries;
        }
    }

    isCustomAllow() {
        if ((<BESettings>this.settings.latestSettings).allowCustomDomainValues) {
            return false;
        } else {
            return true;
        }
    }
}
