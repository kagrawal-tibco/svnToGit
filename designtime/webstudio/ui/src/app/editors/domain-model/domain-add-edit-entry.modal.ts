import { AfterViewInit, Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ICellEditorAngularComp } from 'ag-grid-angular';
import * as moment from 'moment';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { RangeDomainEntry } from './domain-entry';

import { AlertService } from '../../core/alert.service';

class EntryForm {
    isRange: boolean;
    value: string;
    lValue: string;
    uValue: string;
    lIncluded: boolean;
    uIncluded: boolean;
    eDescription: string;
    valueD: Date;
    lValueD: Date;
    uValueD: Date;
}

export class AddDomainEntryContext extends BSModalContext {
    constructor(
        public filter: any,
        public addEditTitle: string,
        public dataType: string
    ) {
        super();
    }

}

@Component({
    selector: 'add-edit-domain-entry',
    templateUrl: './domain-add-edit-entry.modal.html',
    styleUrls: ['domain-add-edit-entry.modal.css']
})
export class AddDomainEntryModal implements ICellEditorAngularComp, AfterViewInit {

    form: EntryForm;
    filter: any;
    addEditTitle: string;
    dataType: string;
    isRange = false;
    params: any;

    constructor(
        private alert: AlertService,
        public i18n: I18n
    ) {
        // this.filter = dialog.context.filter;
        // this.addEditTitle = dialog.context.addEditTitle;
        // this.dataType = dialog.context.dataType;
    }

    agInit(params: any) {
        this.params = params;
        this.form = new EntryForm();
        this.filter = this.params.filter;
        this.addEditTitle = this.params.addEditTitle;
        this.dataType = this.params.dataType;
        if (this.filter instanceof RangeDomainEntry) {
            this.isRange = true;
            if (this.isDateTime()) {
                const m1 = moment(this.filter.lower, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
                this.form.lValueD = m1.isValid() ? m1.toDate() : null;
                const m2 = moment(this.filter.upper, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
                this.form.uValueD = m2.isValid() ? m2.toDate() : null;
                this.form.eDescription = this.filter.description;
                this.form.lIncluded = this.filter.lowerInclusive;
                this.form.uIncluded = this.filter.upperInclusive;
            } else {
                this.form.lValue = this.filter.lower;
                this.form.uValue = this.filter.upper;
                this.form.eDescription = this.filter.description;
                this.form.lIncluded = this.filter.lowerInclusive;
                this.form.uIncluded = this.filter.upperInclusive;
            }

        } else {
            this.isRange = false;
            if (this.isDateTime()) {
                const m1 = moment(this.filter.value, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
                this.form.valueD = m1.isValid() ? m1.toDate() : null;
                this.form.eDescription = this.filter.description;
            } else {
                this.form.value = this.filter.value;
                this.form.eDescription = this.filter.description;
            }
        }
    }

    getValue() {
        return {
            isRange: this.isRange,
            value: this.isDateTime() ? moment(this.form.valueD).format('YYYY-MM-DDTHH:mm:ss') : this.form.value,
            lValue: this.isDateTime() ? moment(this.form.lValueD).format('YYYY-MM-DDTHH:mm:ss') : this.form.lValue,
            uValue: this.isDateTime() ? moment(this.form.uValueD).format('YYYY-MM-DDTHH:mm:ss') : this.form.uValue,
            eDescription: this.form.eDescription,
            lIncluded: this.form.lIncluded,
            uIncluded: this.form.uIncluded,
            isValid: this.isRange ? this.validateRangeValue() : this.validateSingleValue
        };
    }

    ngAfterViewInit() {
        // this.columnNameInput.nativeElement.focus();
    }

    onCancel() {
        // this.dialog.dismiss();
    }

    onSubmit() {
        // this.dialog.close({
        //     isRange: this.isRange,
        //     value: this.isDateTime() ? moment(this.form.valueD).format('YYYY-MM-DDTHH:mm:ss') : this.form.value,
        //     lValue: this.isDateTime() ? moment(this.form.lValueD).format('YYYY-MM-DDTHH:mm:ss') : this.form.lValue,
        //     uValue: this.isDateTime() ? moment(this.form.uValueD).format('YYYY-MM-DDTHH:mm:ss') : this.form.uValue,
        //     eDescription: this.form.eDescription,
        //     lIncluded: this.form.lIncluded,
        //     uIncluded: this.form.uIncluded

        // });
    }

    isDateTime() {
        if (this.dataType === 'DateTime') {
            return true;
        } else {
            return false;
        }
    }

    isCancelAfterEnd(): boolean {
        if (this.isRangeFilter()) {
            return !this.validateRangeValue();
        } else {
            return !this.validateSingleValue();
        }
    }

    isPopup(): boolean {
        return true;
    }

    supportRangeFilter(): boolean {
        if (this.dataType === 'String' || this.dataType === 'boolean') {
            return false;
        } else {
            return true;
        }
    }

    isRangeFilter() {
        return this.isRange;
    }

    setRangeValue(value: boolean) {
        this.isRange = value;
    }

    canEdit(): boolean {
        return this.dataType === 'boolean' ? false : true;
    }

    validateSingleValue() {
        let isValid = true;
        if (this.dataType === 'String') {
            isValid = this.form.value ? true : false;
        } else if (this.dataType === 'int') {
            isValid = this.isNumeric(this.form.value);
        } else if (this.dataType === 'long') {
            isValid = this.isLong(this.form.value);
        } else if (this.dataType === 'double') {
            isValid = this.isDouble(this.form.value);
        } else if (this.dataType === 'boolean') {
            isValid = true;
        } else {
            isValid = true;
        }
        return isValid;
    }

    validateRangeValue() {
        let isValid = true;
        if (this.dataType === 'int') {
            isValid = (this.isNumeric(this.form.lValue) && this.isNumeric(this.form.uValue));
        } else if (this.dataType === 'long') {
            isValid = (this.isLong(this.form.lValue) && this.isLong(this.form.uValue));
        } else if (this.dataType === 'double') {
            isValid = (this.isDouble(this.form.lValue) && this.isDouble(this.form.uValue));
        }

        if (isValid) {
            if (Number(this.form.lValue) > Number(this.form.uValue)) {
                this.alert.flash(this.i18n('Lower value cannot be greater than Upper value.'), 'error');
                isValid = false;
            }
        }
        return isValid;
    }

    isNumeric(value: string): boolean {
        if ((isNaN(Number(value)) || value.indexOf('.') !== -1)) {
            this.alert.flash(this.i18n('Invalid integer type entry: ') + value, 'error');
            return false;
        } else if (!(Number(value) >= -2147483648 && Number(value) <= 2147483647)) {
            this.alert.flash(this.i18n('Invalid integer type entry: ') + value, 'error');
            return false;
        }
        return true;
    }

    isLong(value: string): boolean {
        if ((isNaN(Number(value)) || value.indexOf('.') !== -1)) {
            this.alert.flash(this.i18n('Invalid long type entry: ') + value, 'error');
            return false;
        } else if (!(Number(value) >= -9223372036854775808 && Number(value) <= 9223372036854775807)) {
            this.alert.flash(this.i18n('Invalid long type entry: ') + value, 'error');
            return false;
        }
        return true;
    }

    isDouble(value: string): boolean {
        if (isNaN(Number(value))) {
            this.alert.flash(this.i18n('Invalid double type entry: ') + value, 'error');
            return false;
        } else if (!(Number(value) >= Number.MIN_VALUE && Number(value) <= Number.MAX_VALUE)) {
            this.alert.flash(this.i18n('Invalid double type entry: ') + value, 'error');
            return false;
        }
        return true;
    }
}
