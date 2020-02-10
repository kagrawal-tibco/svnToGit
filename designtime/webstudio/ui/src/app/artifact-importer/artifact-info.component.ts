import {
  AfterViewInit,
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

import { environment } from '../../environments/environment';
import { ProjectService } from '../core/project.service';
import { DecisionTable } from '../editables/decision-table/decision-table';
import { EditorInterface } from '../editors/editor-interface';
import { EditorParams } from '../editors/editor-params';
import { Artifact, ArtifactType } from '../models/artifact';

@Component({
  selector: 'artifact-info',
  templateUrl: './artifact-info.component.html',
  styleUrls: ['./artifact-info.component.css']
})
export class ArtifactInfoComponent implements OnInit, AfterViewInit {
  @Input()
  input: Artifact;

  @Input()
  projectName: string;

  @Input()
  projectEditable?: boolean;

  @Input()
  baseDir: string;

  @Input()
  edit: boolean;

  @Input()
  state?: 'IMPORT' | 'CREATE';

  @Input()
  checkPathUnique: string[];

  @Output()
  checkPathUniqueSuccess = new EventEmitter<boolean>();

  @Output()
  checkDirty = new EventEmitter<boolean>();

  @ViewChild('pathInput', { static: false })
  pathInputBox: ElementRef;

  @ViewChild('artifactForm', { static: true })
  form: NgForm;

  metadataEditorParams: EditorParams<string>;

  constructor(
    public _projService: ProjectService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    if (!this.checkPathUnique) {
      this.checkPathUnique = [];
    }
    if (!this.projectName && this.baseDir) {
      this.projectName = this.baseDir;
    }
    this.baseDir = this.baseDir || '/';
    if (!this.input.baseDir) {
      this.input.path = this.baseDir;
    }
    this.form.statusChanges.subscribe(status => {
      this.checkDirty.emit(this.form.dirty);
      const hasPath = this.inputPath && this.inputPath.length > 0;
      this.checkPathUniqueSuccess.
        emit(this.form.dirty ? status === 'VALID' : this.form.pristine && hasPath);
    }
    );

    const buffer = EditorInterface.METADATA.makeEditBuffer(this.input, true);
    this.metadataEditorParams = {
      editBuffer: buffer,
      editorInterface: EditorInterface.METADATA,
      editorMode: this.edit ? 'edit' : 'display',
      showChangeSet: 'none'
    };
  }

  ngAfterViewInit() {
    this.pathInputBox.nativeElement.focus();
  }

  onMetadataEdit() {
    this.input.metadata = this.metadataEditorParams.editBuffer.serialize();
  }

  checkEditable(type: ArtifactType): boolean {
    return !type.isBinary;
  }

  get artifactExtension(): string {
    return this.input.type.defaultExtension;
  }

  set artifactExtension(val: string) {
    const artifactType = ArtifactType.fromExtension(val);
    if (artifactType !== this.input.type) {
      if (artifactType.isBinary !== this.input.type.isBinary) {
        if (artifactType.isBinary) {
          this.input.content = btoa(this.input.content);
          this.input.encoding = 'BASE64';
        } else {
          this.input.content = atob(this.input.content);
          this.input.encoding = 'NONE';
        }
      }
      this.input.type = artifactType;
    }
  }

  get pathValidator() {
    if (environment.enableBEUI) {
      return 'FULL_PATH_ALLOW_EXTENSION_BE';
    } else {
      return 'FULL_PATH_ALLOW_EXTENSION';
    }
  }

  get inputPath() {
    return this.input.name;
  }

  set inputPath(path) {
    this.artifactTypes.forEach((type) => {
      if (type.extensions.find((ext) => path.endsWith('.' + ext))) {
        this.artifactExtension = type.defaultExtension;
      }
    });
    if (!path.startsWith(this.baseDir) && !this.input.baseDir.startsWith(this.baseDir)) {
      path = this.baseDir + path;
    }
    this.input.path = this.input.baseDir + path;
  }
  get pathPrefix() {
    return '/' + this.safe(this.projectName) + this.safe(this.input.baseDir);
  }

  get artifactTypes() {
    let filteredArtifactTypes: ArtifactType[] = [];

    let toAdd: boolean;
    (this.input.imported ? ArtifactType.AVAILABLE_TYPES : ArtifactType.AVAILABLE_TYPES.filter(this.checkEditable)).forEach(at => {
      toAdd = false;
      if (environment.enableBEUI) {
        if (at === ArtifactType.BE_DECISION_TABLE || at === ArtifactType.RULE_TEMPLATE_INSTANCE || at === ArtifactType.DOMAIN_MODEL) {
          toAdd = true;
        }
      } else {
        toAdd = !at.isBEType;
      }
      if (toAdd) {
        filteredArtifactTypes.push(at);
      }
    });

    if (environment.enableBEUI) {
      filteredArtifactTypes = [];
      filteredArtifactTypes.push(this.input.type);
    }

    return filteredArtifactTypes;
  }

  get existingPaths() {
    if (environment.enableBEUI) {
      return this.checkPathUnique
        .filter(p => p.startsWith(this.input.baseDir))
        .map(p => p.slice(this.input.baseDir.length));
    } else {
      return this.checkPathUnique
        .filter(p => {
          // safety check in the case where the artifact path omits the leading '/'
          if (this.baseDir.startsWith('/') && !p.startsWith('/')) {
            p = '/' + p;
          }
          return p.startsWith(this.baseDir);
        })
        .map(p => {
          if (this.baseDir.startsWith('/') && !p.startsWith('/')) {
            p = '/' + p;
          }
          return p.slice(this.baseDir.length);
        });
    }
  }

  fillArtifactContent(content: DecisionTable) {
    this.input.content = content.toXml();
  }

  baseDT(art: Artifact) {
    return DecisionTable.fromXml(art.content);
  }

  get showSBSchemaImporter(): boolean {
    return this.input.type === ArtifactType.SB_DECISION_TABLE;
  }

  showMetadata() {
    return !environment.enableBEUI && this.input.type.supportMetadata;
  }

  getProjectNames(): string[] {
    const projNames = new Array<string>();
    if (environment.enableBEUI) {
      projNames.push(this.projectName);
    } else {
      this._projService.projectMetaCache.forEach(meta => {
        projNames.push(meta.projectName);
      });
    }
    return projNames;
  }

  setProjectId() {
    if (this.projectName) {
      this._projService.projectMetaCache.forEach(meta => {
        if (meta.projectName === this.projectName) {
          this.input.projectId = meta.projectId;
        }
      });
      // update valid paths and revalidate
      if (this.input.projectId) {
        this.baseDir = '/'; // reset the base dir, any supplied folder might not exist
        this.input.path = this.baseDir;
        this._projService.getAllThenMerge(this.input.projectId)
          .then(artifacts => artifacts.map(a => a.path))
          .then(paths => this.checkPathUnique = paths);
      }
    }
  }

  private safe(str: string) {
    return str ? str : '';
  }
}
