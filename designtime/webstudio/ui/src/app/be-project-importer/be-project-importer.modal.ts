import {
    Component,
    OnInit,
} from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { FileUploader } from 'ng2-file-upload';
import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { AlertService } from '../core/alert.service';
import { RestService } from '../core/rest.service';
import { ResizableModal } from '../shared/resizablemodal';

export class BEImportProjectContext extends BSModalContext {
    dialogClass = 'modal-dialog modal-lg';
    constructor(

    ) {
        super();
    }
}

@Component({
    templateUrl: './be-project-importer.modal.html',
})

export class BEImportProjectComponent extends ResizableModal implements ModalComponent<BEImportProjectContext>, OnInit {

    resourceName: string;
    selectedFile: string;
    uploader: FileUploader;
    apiInProgress = false;
    importUrl: string;
    _createACL = true;
    isZip = false;

    constructor(
        public dialog: DialogRef<BEImportProjectContext>,
        public restService: RestService,
        public alert: AlertService,
        public i18n: I18n
    ) {
        super(dialog.context, dialog.context.dialogClass);
    }

    ngOnInit() {
        const importUrl = this.restService.url('projects/' + this.resourceName + '/import.json');

        this.uploader = new FileUploader({ url: importUrl, itemAlias: 'selectResource' });
        this.uploader.autoUpload = false;

        this.uploader.onWhenAddingFileFailed = (file) => {
            console.log(file);
        };
    }

    onClose() {
        this.dialog.close();
    }

    onConfirm() {

        const importUrl = this.restService.url('projects/'
            + this.resourceName
            + '/import.json?'
            + 'createACL='
            + this.createACL);
        this.uploader.setOptions({ url: importUrl, itemAlias: 'selectResource' });
        this.uploader.autoUpload = false;
        this.uploader.onSuccessItem = (item, response, status, header) => {
            const data = JSON.parse(response);
            this.dialog.close();
            this.alert.flash(data.response.responseMessage, 'success');
            console.log(data.response.responseMessage);
        };

        this.uploader.onErrorItem = (item, response, status, header) => {
            this.alert.flash('Failed importing project [' + this.resourceName + ']', 'success');
            const data = JSON.parse(response);
            console.log(data);
            this.dialog.close();
        };

        this.uploader.uploadItem(this.uploader.queue[0]);
        this.apiInProgress = true;
    }

    canConfirm(): boolean {
        // Fetch Extension of selected file

        return this.resourceName && !this.apiInProgress && this.isZip;
    }

    extractName(path: string) {
        const lastFolderIndex = path.lastIndexOf('\\');
        if (lastFolderIndex > 0) {
            path = path.substring(lastFolderIndex + 1, path.length);
        }

        if (!this.selectedFile || this.selectedFile !== path) {
            this.selectedFile = path;
            if (path.indexOf('.') > 0) { setTimeout(_ => this.resourceName = path.substring(0, path.indexOf('.'))); }
        }

        const lastIndex: number = this.selectedFile.lastIndexOf('.');
        const extension: string = this.selectedFile.substring(lastIndex + 1, this.selectedFile.length);
        this.isZip = extension === 'zip';
        if (!this.isZip && this.selectedFile !== '') {
            this.alert.flash(this.i18n('Only zip files can be imported. Add your project directory to a zip file and name it with the project name.'), 'error');
        }
        return path;
    }

    public get createACL(): boolean {
        return this._createACL;
    }

    public set createACL(value: boolean) {
        this._createACL = value;
    }

    getFileName(path: string) {
        return path.replace('C:\\fakepath\\', '');
    }
}
