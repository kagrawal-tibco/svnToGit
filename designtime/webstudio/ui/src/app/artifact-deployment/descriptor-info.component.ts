import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import { NgForm } from '@angular/forms';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DeploymentService, TargetType } from './deployment.service';

import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { DeploymentDescriptorItem, DeploymentDescriptorRecord } from '../models/dto';
@Component({
  selector: 'descriptor-info',
  templateUrl: './descriptor-info.component.html',
  styles: ['textarea { height: 100px; resize: none; }']
})
export class DescriptorInfoComponent implements OnInit {

  get availableRevisions() {
    return [-1].concat(this.revisions);
  }

  get availableTargetTypes() {
    return this.service.availableTargetTypes;
  }

  get targetType(): TargetType {
    return <TargetType>this.input.type;
  }

  @ViewChild('targetURIInput', { static: false })
  set targetURIInput(input: ElementRef) {
    if (input) {
      Promise.resolve().then(() => input.nativeElement.focus());
    }
  }

  @ViewChild('targetServiceNameInput', { static: false })
  set targetServiceNameInput(input: ElementRef) {
    if (input) {
      Promise.resolve().then(() => input.nativeElement.focus());
    }
  }

  set host(hostInput: string) {
    this._hostInput = hostInput;
    this.input.streamBaseTarget = this._hostInput;
  }

  get host() {
    return this._hostInput;
  }

  get selectedRevision(): number {
    if (this.input.amsDecisionTableVersion) {
      return this.input.amsDecisionTableVersion;
    } else {
      throw Error(this.i18n('Shall have some version specified'));
    }
  }

  set selectedRevision(revision: number) {
    this.input.amsDecisionTableVersion = revision;
  }
  @Input()
  input: DeploymentDescriptorRecord | DeploymentDescriptorItem;

  @Input()
  artifactId: string;

  @Input()
  revisions: number[];

  @Input()
  edit: boolean;

  @Input()
  unique: DeploymentDescriptorItem[];

  @Output()
  valid = new EventEmitter<boolean>();

  duplicate: DeploymentDescriptorItem;

  @ViewChild('descriptorInfo', { static: false })
  private form: NgForm;

  private _hostInput: string;

  constructor(
    private log: Logger,
    private artifactService: ArtifactService,
    private service: DeploymentService,
    private modal: ModalService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this._hostInput = this.input.streamBaseTarget;

    this.form.statusChanges.subscribe(status => {
      const valid = status === 'VALID';
      const samePrimaryKey = (a: DeploymentDescriptorItem, b: DeploymentDescriptorItem) => {
        return a.amsDecisionTablePath === b.amsDecisionTablePath
          && a.amsDecisionTableVersion === b.amsDecisionTableVersion
          && a.amsProjectName === b.amsProjectName
          && a.decisionTableOperatorName === b.decisionTableOperatorName
          && a.streamBaseTarget === b.streamBaseTarget
          && a.type === b.type;
      };
      if (valid) {
        if (this.unique) {
          this.duplicate = this.unique.find(item => samePrimaryKey(this.input, item));
        }
        this.valid.emit(this.duplicate == null && valid);
      } else {
        this.valid.emit(valid);
      }
    });
    if (!this.revisions && this.edit) {
      throw Error(this.i18n('Revisions shall be specified when in edit mode.'));
    }
  }

  getTargetTypeDisplay(type: TargetType) {
    return this.service.getTargetTypeDisplay(type);
  }

  isURI() {
    return this.input.type === 'STREAMBASE_URI';
  }

  isServiceName() {
    return this.input.type === 'STREAMBASE_SERVICE_NAME';
  }

  onViewContent() {

    // checkout artifact at necessary revision
    // will fail if artifact is deleted
    // then
    // display artifact revision in the editor modal as below.

    return this.artifactService.getArtifactRevisionWithContent(this.artifactId, this.input.amsDecisionTableVersion)
      .then(
        (success) => {
          this.modal.open(EditorLoaderModal, EditorLoaderModal.default(success, null, `Content: ${success.name}`));
        },
        (fail) => {
          return fail;
        }
      ).catch(
        (fail) => fail
      ).then(
        (never) => { },
        (error) => {
          if (error) { throw error; }
        }
      );
  }

  getRevisionString(rev: number) {
    return this.service.getRevisionString(rev);
  }

}
