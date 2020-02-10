/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:08:45+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:08:51+05:30
 */

import { BindingInfo } from './BindingInfo';

export class ViewInfo {

    private htmlText = '';
    private bindingInfo: Array<BindingInfo> = new Array<BindingInfo>();
    private script: string;

    constructor(html?: string) {
        if (html != null) {
            this.htmlText = html;
        }
    }

    public getBindingInfo(): Array<BindingInfo> {
        if (this.bindingInfo == null) {
            this.bindingInfo = new Array<BindingInfo>();
        }
        return this.bindingInfo;
    }

    public setBindingInfo(bindings: Array<BindingInfo>) {
        this.bindingInfo = bindings;
    }

    setHtmlText(html: string) {
        this.htmlText = html;
    }

    getHtmlText(): string {
        return this.htmlText;
    }

    setScript(script: string) {
        this.script = script;
    }

    getScript(): string {
        return this.script;
    }
}
