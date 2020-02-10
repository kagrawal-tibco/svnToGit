
import {
  Component,
  DoCheck,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as JSZip from 'jszip';
import { FileUploader } from 'ng2-file-upload';
import { timer as observableTimer, Observable } from 'rxjs';

import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { Artifact, ArtifactType } from '../models/artifact';

@Component({
  selector: 'artifact-uploader',
  styleUrls: ['./artifact-uploader.component.css'],
  templateUrl: './artifact-uploader.component.html'
})
export class ArtifactUploaderComponent implements OnInit, DoCheck {
  @Input()
  multiple: boolean;

  @Input()
  result: Artifact[];

  @Output()
  resultChange = new EventEmitter<Artifact[]>();

  hasBaseDropZoneOver = false;
  uploader: FileUploader;
  readingFiles: boolean;
  processZip = false;

  @ViewChild('select', { static: false })
  private selectInput: ElementRef;

  constructor(
    private artifact: ArtifactService,
    private alert: AlertService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.uploader = new FileUploader({}); // null url because we are doing upload here
    if (!this.result) {
      this.result = [];
    }
  }

  ngDoCheck() {
    if (this.uploader.queue.length > 0) {
      this.readFile();
    }
  }

  fileOverBase(e: any) {
    this.hasBaseDropZoneOver = e;
  }

  readFile() {
    this.readingFiles = true;
    // keep a copy of the queue
    const queue = this.multiple ? [].concat(this.uploader.queue) : [this.uploader.queue[0]];

    // remove it from the uploader queue
    // avoid them being checked in next round of change detection
    this.uploader.clearQueue();
    observableTimer(0).toPromise().then(() => this.readingFiles = false);

    // parse each file to artifact
    queue.forEach(item => {
      const file = item._file;

      try {
        const reader = new FileReader();
        const processZipContents = this.processZip;
        reader.onloadend = () => {
          try {
            const result = reader.result as ArrayBuffer;

            // Begin the decoding process, fatal must be true so that if there is a decoding error it will base64 encode the content.
            const decoder = new TextDecoder('utf-8', { fatal: true });
            if (processZipContents &&
              (file.type === 'application/zip' || file.type === 'application/x-zip-compressed')) {
              const jszip = new JSZip();
              const comp = this;
              jszip.loadAsync(result).then(
                function (zip) {
                  zip.forEach(function (relativePath, zipEntry) {
                    if (zipEntry.dir) {
                      return;
                    }
                    const artifact = comp.artifact.createArtifactInfo();
                    artifact.type = ArtifactType.fromFileName(relativePath);
                    artifact.path = '/' + relativePath;
                    try {
                      // Attempts to decode the file. If it fails, it will stop decoding.
                      zipEntry.async('text').then(contents => {
                        artifact.content = contents;
                        artifact.encoding = 'NONE';
                        artifact.imported = true;
                        comp.result.push(artifact);
                      }).then(() => comp.emit());
                    } catch (decoderError) {
                      // If decoding failed, the artifact content will be base64 encoded.
                      zipEntry.async('base64').then(contents => {
                        artifact.content = contents;
                        artifact.encoding = 'BASE64';
                        artifact.imported = true;
                        comp.result.push(artifact);
                      }).then(() => comp.emit());
                    }
                  });
                }).then(() => {
                  comp.emit();
                });
            } else {
              const buffView = new DataView(result);
              const artifact = this.artifact.createArtifactInfo();
              artifact.type = ArtifactType.fromFileName(file.name);
              artifact.path = '/' + file.name;
              try {
                // Attempts to decode the file. If it fails, it will stop decoding.
                artifact.content = decoder.decode(buffView);
                artifact.encoding = 'NONE';
              } catch (decoderError) {
                // If decoding failed, the artifact content will be base64 encoded.
                artifact.content = btoa(new Uint8Array(result).reduce((p, c) => p + String.fromCharCode(c), ''));
                artifact.encoding = 'BASE64';
              }

              artifact.imported = true;
              this.result.push(artifact);
              this.emit();
            }

          } catch (e) {
            this.alert.flash(this.i18n('Unable to read {{name}} because: ', { name: file.name }) + e, 'warning');
          }
        };
        reader.readAsArrayBuffer(file);
        this.processZip = false; // reset flag
      } catch (e) {
        this.alert.flash(this.i18n('Unable to read {{name}} because: ', { name: file.name }) + e, 'warning');
      }
    });
  }

  onSelect(processZipContents: boolean) {
    this.processZip = processZipContents;
    this.selectInput.nativeElement.click();
  }

  remove(artifact: Artifact) {
    const idx = this.result.findIndex(a => a === artifact);
    if (idx !== -1) {
      this.result.splice(idx, 1);
      this.emit();
    }
  }

  size(artifact: Artifact) {
    if (artifact.content) {
      const bytesCnt = artifact.content.length;
      return bytesCnt / 1024.0 / 1024.0;
    } else {
      return 0;
    }
  }

  reset() {
    this.uploader = new FileUploader({}); // null url because we are doing upload here
    this.result = [];
  }

  emit() {
    this.resultChange.emit(this.result);
  }

  createPath(dir: string, name: string) {
    if (dir.endsWith('/')) {
      return dir + name;
    } else {
      return dir + '/' + name;
    }
  }
}
