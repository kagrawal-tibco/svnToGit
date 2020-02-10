
import { Component, DoCheck, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { FileUploader } from 'ng2-file-upload';
import { timer as observableTimer, Observable } from 'rxjs';
// import { parseString } from 'xml2js';

import { AlertService } from '../../core/alert.service';
import { PropertyType } from '../../editables/decision-table/column';

export class SBSchema {
  name?: string;
  fields: {
    name: string,
    type: PropertyType | SBSchema,
    id?: string,
  }[];
}

@Component({
  selector: 'sb-schema-importer',
  templateUrl: './sb-schema-importer.component.html'
})
export class SbSchemaImporter implements DoCheck {

  readingFiles: boolean;

  @Output()
  schemas = new EventEmitter<SBSchema[]>();

  uploader: FileUploader = new FileUploader({});

  hasBaseDropZoneOver = false;

  @ViewChild('select', { static: false })
  private selectInput: ElementRef;

  constructor(
    protected alert: AlertService,
    public i18n: I18n
  ) {

  }

  fileOverBase(e: any) {
    this.hasBaseDropZoneOver = e;
  }

  ngDoCheck() {
    if (this.uploader.queue.length > 0) {
      this.readFile();
    }
  }

  readFile() {
    this.readingFiles = true;
    // keep a copy of the queue
    const queue = [this.uploader.queue[0]];

    // remove it from the uploader queue
    // avoid them being checked in next round of change detection
    this.uploader.clearQueue();
    observableTimer(0).toPromise().then(() => this.readingFiles = false);

    // parse each file to artifact
    queue.forEach(item => {
      const file = item._file;
      try {
        const reader = new FileReader();
        reader.onloadend = () => {
          try {
            const result = reader.result as ArrayBuffer;

            // Begin the decoding process, fatal must be true so that if there is a decoding error it will base64 encode the content.
            const decoder = new TextDecoder('utf-8', { fatal: true });
            const buffView = new DataView(result);

            // try {
            //   // Attempts to decode the file. If it fails, it will stop decoding.
            //     parseString(decoder.decode(buffView),
            //         {
            //             explicitArray: false,
            //             attrkey: '$',
            //             mergeAttrs: true
            //         },
            //         (err, xml) => {
            //             this.schemas.emit(xml.modify.add['named-schemas'].schema as any as SBSchema[]);
            //     });
            // } catch (decoderError) {
            //     /* Do nothing */
            // }

          } catch (e) {
            this.alert.flash('Unable to read ' + file.name + ' because: ' + e, 'warning');
          }
        };
        reader.readAsArrayBuffer(file);
      } catch (e) {
        this.alert.flash('Unable to read ' + file.name + ' because: ' + e, 'warning');
      }
    });
  }

  onSelect() {
    this.selectInput.nativeElement.click();
  }
}
