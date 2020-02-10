import { I18n } from '@ngx-translate/i18n-polyfill';

export class I18nImpl {

    public static i18n: I18n;

    constructor(public i18n: I18n) {
        I18nImpl.i18n = this.i18n;
    }

    public static getPriorityText(): string {
        if (I18nImpl.i18n) {
            return (I18nImpl.i18n('Priority'));
        } else {
            return ('Priority');
        }
    }

}
