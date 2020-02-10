
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext, Modal } from 'ngx-modialog/plugins/bootstrap';
import { map } from 'rxjs/operators';

import { Logger } from '../../core/logger.service';
import { RestService } from '../../core/rest.service';
import { SettingsService } from '../../core/settings.service';
import { Column, ColumnType } from '../../editables/decision-table/column';
import { BESettings } from '../../models-be/settings-be';
import { SingleDomainEntry } from '../domain-model/domain-entry';

class ColumnForm {
    columnName: string;
    columnAlias: string;
    columnId: string;
    columnDText: string;
    includeExistingRules: boolean;
}

export class BEColumnSettingContext extends BSModalContext {
    constructor(
        public columnToSet: Column,
        public table: any
    ) {
        super();
    }

}

@Component({
    templateUrl: './be-column-fields-setting.modal.html',
    styleUrls: ['./be-column-field-setting.modal.css']
})
export class BEColumnSettingModal implements ModalComponent<BEColumnSettingContext>, OnInit, AfterViewInit {

    form: ColumnForm;
    columnToSet: Column;
    isDMAssociated: boolean;
    table: any;

    // DM Associated

    entries: Array<SingleDomainEntry> = new Array<SingleDomainEntry>();
    filteredEntries: Array<SingleDomainEntry> = new Array<SingleDomainEntry>();
    entriesSelected: Array<SingleDomainEntry> = new Array<SingleDomainEntry>();
    columnType = 'int';
    isConditionCol = true;
    valueFilter = '';
    descriptionFilter = '';
    customString = '';

    @ViewChild('columnNameInput', { static: false })
    private columnNameInput: ElementRef;

    constructor(
        private log: Logger,
        private modal: Modal,
        private artifact: RestService,
        private settings: SettingsService,
        public dialog: DialogRef<BEColumnSettingContext>,
        public i18n: I18n
    ) {
        this.columnToSet = dialog.context.columnToSet;
        this.table = dialog.context.table;
    }

    ngOnInit() {
        this.form = new ColumnForm();
        this.form.columnName = this.columnToSet.name;
        this.form.columnAlias = this.columnToSet.alias;
        this.form.columnId = this.columnToSet.id;
        this.form.columnDText = this.columnToSet.defaultCellText;
        this.form.includeExistingRules = false;
        if (this.columnToSet.associatedDM) {
            this.isDMAssociated = true;
            this.initProps();
        } else {
            this.isDMAssociated = false;
        }

    }

    initProps() {

        let artifactType;
        for (const key in this.table.argumentObj) {
            const arg = this.table.argumentObj[key];
            if (arg.argumentAlias === this.columnToSet.name.substr(0, this.columnToSet.name.indexOf('.'))) {
                artifactType = arg.resourceType;
            }
        }

        const pname = this.table.projectName;
        const artifactpath = this.columnToSet.property.substr(0, this.columnToSet.property.lastIndexOf('/'));
        const propertyname = this.columnToSet.property.substr(this.columnToSet.property.lastIndexOf('/') + 1);
        const artifacttype = artifactType;
        const isCondition = this.columnToSet.columnType === ColumnType.CONDITION ? true : false;
        const coltype = this.columnToSet.propertyType.name;

        this.columnType = coltype;
        this.isConditionCol = isCondition;
        this.artifact.get('domains.json?projectName=' + pname + '&argumentPath=' + artifactpath + '&argumentType=concept&artifactType=' + artifacttype + '&argumentPropertyName=' + propertyname + '&isConditionColumn=' + isCondition).pipe(
            map(res => {
                if (res.ok()) {
                    // console.log(res.record[0]);
                    this.creatDomainEntriesArray(res.record, this.columnToSet.defaultCellText);
                }
            }))
            .toPromise();
    }

    creatDomainEntriesArray(record: any, value: string) {
        this.customString = value;
        for (const entry of record) {
            if (value.indexOf(entry.value) !== -1) {
                const sEntry = new SingleDomainEntry(true, null, entry.value, entry.description !== '<NA>' ? entry.description : '');
                this.entries.push(sEntry);
                this.entriesSelected.push(sEntry);
            } else {
                const sEntry = new SingleDomainEntry(false, null, entry.value, entry.description !== '<NA>' ? entry.description : '');
                this.entries.push(sEntry);
            }
        }
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

    ngAfterViewInit() {
        this.columnNameInput.nativeElement.focus();
    }

    onCancel() {
        this.dialog.dismiss();
    }

    onSubmit() {
        this.dialog.close({
            columnName: this.form.columnName,
            columnAlias: this.form.columnAlias,
            columnId: this.form.columnId,
            columnDText: this.columnToSet.associatedDM ? this.customString : this.form.columnDText,
            includeExistingRules: this.form.includeExistingRules

        });
    }

    setIncludingRulesValue(value: boolean) {
        this.form.includeExistingRules = value;
    }
}
