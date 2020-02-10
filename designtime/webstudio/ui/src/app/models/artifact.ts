import { Result } from './dto';

import { environment } from '../../environments/environment';
import { BEDecisionTable } from '../editables/decision-table/be-decision-table';
import { DecisionTable } from '../editables/decision-table/decision-table';
import { RuleTemplateInstance } from '../editables/rule-template-instance/rule-template-instance';
import { DomainModelContent } from '../editors/domain-model/domain-model';
import { EditorInterface } from '../editors/editor-interface';
import { ProjectSummary } from '../editors/project-summary/project-summary';

export type ArtifactStatus = 'ADDED' | 'MODIFIED' | 'DELETED' | 'CLEAN';
export type ArtifactKind = 'UMBRELLA' | 'REVISION' | 'CHECKOUT';
export class Artifact {
  static CHECKOUT_SUFFIX = ' (working)';
  static DIRTY_PREFIX = '*';

  // metadata
  id: string;
  description: string;
  path: string;
  type: ArtifactType;
  projectId: string;                // which project the artifact belongs to
  parentId: string;
  checkedOutFromRevision: number;
  latestRevision: number;
  revisionNumber: number;           // the revision of the ArtifactRevision
  revisionId: string;               // the id of the ArtifactRevision
  status: ArtifactStatus;
  stale: boolean;
  locked: boolean;
  disposed: boolean;
  isCheckedOutArtifact: boolean; // whether the artifact is editable
  analyzeResult: Result[];
  baseArtifactPath?: string;  // parent artifact path for BEDT and RTI.

  // content
  content: string;
  encoding: string;
  metadata: string;

  // ui control
  phantom: boolean; // whether the artifact object really exists in the system. Mostly for UI.
  imported = false; // whether the artifact was imported. This is used to limit artifact types.
  // Setting to false here so that it has to be set to true.
  selectedProblem: Result; // The problem which was clicked, this will be used to store the locations.
  // In case, when the editor is not opened and problem is selected.

  // state management;
  cacheStale: boolean;

  private _displayName: string;

  displayName() {
    let name = this.name;
    if (this.isCheckedOutArtifact) {
      name += Artifact.CHECKOUT_SUFFIX;
    }
    if (name !== this._displayName) {
      this._displayName = name;
    }
    return this._displayName;
  }

  fullPath(): string {
    let base: string = this.baseDir;
    if (!base.endsWith('/')) {
      base += '/';
    }
    return base + this.name;
  }

  updateMeta(other: Artifact, includeContent?: boolean) {
    this.id = other.id;
    this.description = other.description;
    this.path = other.path;
    this.type = other.type;
    this.projectId = other.projectId;
    this.parentId = other.parentId;
    this.checkedOutFromRevision = other.checkedOutFromRevision;
    this.revisionNumber = other.revisionNumber;
    this.revisionId = other.revisionId;
    this.locked = other.locked;
    this.latestRevision = other.latestRevision;
    this.status = other.status;
    this.analyzeResult = other.analyzeResult;
    if (includeContent) {
      this.content = other.content;
    }
  }

  get isBinary() {
    switch (this.encoding) {
      case 'BASE64':
      case 'ZIP_BASE64':
        return true;
      default:
        return false;
    }
  }

  get contentAsBlob() {
    switch (this.encoding) {
      case 'BASE64':
      case 'ZIP_BASE64':
        // two steps:
        // 1. base64 to regular conversion
        // 2. binary string to binary array
        const str = atob(this.content);
        const buf = new ArrayBuffer(str.length);
        const bufView = new Uint8Array(buf);
        for (let i = 0; i < str.length; i++) {
          bufView[i] = str.charCodeAt(i);
        }
        return new Blob([buf]);
      case 'NONE':
      default:
        return new Blob([this.content]);
    }
  }

  get name() {
    const idx = this.path.lastIndexOf('/');
    return this.path.slice(idx + 1);
  }

  get baseDir() {
    const idx = this.path.lastIndexOf('/');
    return this.path.slice(0, idx + 1);
  }

  get needSync() {
    return this.isCheckedOutArtifact && this.checkedOutFromRevision !== this.latestRevision;
  }

  get canSyncExternal() {
    return this.isCheckedOutArtifact;
  }
}

export class ArtifactType {
  private static _available: ArtifactType[] = [];

  private static _other = new ArtifactType('Other', false, 'other');

  private static _compressed_spark_model = new ArtifactType('Compressed Spark Model', false, 'zip');
  private static _excel = new ArtifactType('Excel File', false, 'xlsx');
  private static _h2o_pojo = new ArtifactType('H2O Plain Old Java Object', false, 'pojo');
  private static _predictive_model = new ArtifactType('Predictive Model from PMML', false, 'pmml');
  private static _text = new ArtifactType('Text File', false, 'txt');
  private static _rdata = new ArtifactType('R Data File', false, 'rdata', 'rda');
  private static _rds = new ArtifactType('R Object File', false, 'rds');
  private static _rScript = new ArtifactType('R Script', false, 'r');
  private static _sbdt = new ArtifactType('StreamBase DecisionTable', false, 'sbdt');
  private static _scala = new ArtifactType('Scala File', false, 'scala');

  // BE Artifact Types
  private static _cpt = new ArtifactType('Concept', true, 'concept');
  private static _evt = new ArtifactType('Event', true, 'event');
  private static _dt = new ArtifactType('Decision Table', true, 'rulefunctionimpl');
  private static _rt = new ArtifactType('Rule Template', true, 'ruletemplate');
  private static _rti = new ArtifactType('Rule Template Instance', true, 'ruletemplateinstance');
  private static _dm = new ArtifactType('Domain Model', true, 'domain');
  private static _rf = new ArtifactType('Rule Function', true, 'rulefunction');
  private static _ru = new ArtifactType('Rule', true, 'rule');
  private static _ch = new ArtifactType('Channel', true, 'channel');
  private static _sc = new ArtifactType('Scorecard', true, 'scorecard');
  private static _rtv = new ArtifactType('Rule Template View', true, 'ruletemplateview');
  private static _tmevt = new ArtifactType('Time Event', true, 'time');
  private static _pr = new ArtifactType('Process', true, 'beprocess');
  private static _fldr = new ArtifactType('Folder', true, 'folder');
  private static _psummary = new ArtifactType('Project Summary', true, 'project');
  // BE shared resource
  private static _rvtransport = new ArtifactType('RVTRANSPORT', true, 'rvtransport');
  private static _sharedhttp = new ArtifactType('SHAREDHTTP', true, 'sharedhttp');
  private static _sharedjdbc = new ArtifactType('SHAREDJDBC', true, 'sharedjdbc');
  private static _sharedjndiconfig = new ArtifactType('SHAREDJNDICONFIG', true, 'sharedjndiconfig');
  private static _sharedjmscon = new ArtifactType('SHAREDJMSCON', true, 'sharedjmscon');
  private static _sharedascon = new ArtifactType('SHAREDASCON', true, 'sharedascon');
  private static _substvar = new ArtifactType('SUBSTVAR', true, 'substvar');
  private static _sharedSB = new ArtifactType('SHAREDSB', true, 'sharedsb');
  private static _cdd = new ArtifactType('CDD', true, 'cdd');
  private static _id = new ArtifactType('ID', true, 'id');
  private static _xsd = new ArtifactType('XSD', true, 'xsd');
  private static _wsdl = new ArtifactType('WSDL', true, 'wsdl');

  public extensions: string[];

  constructor(
    public displayString: string,
    public isBEType: boolean,
    ...extensions: string[]
  ) {
    this.extensions = extensions;
    ArtifactType._available.push(this);
  }

  static get AVAILABLE_TYPES() {
    return ArtifactType._available;
  }

  static get OTHER() {
    return ArtifactType._other;
  }

  static get SPARK() {
    return ArtifactType._compressed_spark_model;
  }

  static get EXCEL() {
    return ArtifactType._excel;
  }

  static get POJO() {
    return ArtifactType._h2o_pojo;
  }

  static get PMML() {
    return ArtifactType._predictive_model;
  }

  static get RDATA() {
    return ArtifactType._rdata;
  }

  static get RDS() {
    return ArtifactType._rds;
  }

  static get RSCRIPT() {
    return ArtifactType._rScript;
  }

  static get SB_DECISION_TABLE() {
    return ArtifactType._sbdt;
  }

  static get SCALA() {
    return ArtifactType._scala;
  }

  static get TEXT() {
    return ArtifactType._text;
  }

  // BE Types

  static get BE_DECISION_TABLE() {
    return ArtifactType._dt;
  }

  static get RULE_TEMPLATE() {
    return ArtifactType._rt;
  }

  static get RULE_TEMPLATE_INSTANCE() {
    return ArtifactType._rti;
  }

  static get DOMAIN_MODEL() {
    return ArtifactType._dm;
  }

  static get RULE_FUNCTION() {
    return ArtifactType._rf;
  }

  static get FOLDER() {
    return ArtifactType._fldr;
  }

  static get PROJECT_SUMMARY() {
    return ArtifactType._psummary;
  }

  static fromFileName(name: string): ArtifactType {
    const idx = name.lastIndexOf('.');
    if (idx !== -1) {
      const extension = name.slice(idx + 1);
      return ArtifactType.fromExtension(extension.toLowerCase());
    } else {
      return ArtifactType.OTHER;
    }
  }

  static fromExtension(extension: string): ArtifactType {
    const found = ArtifactType.AVAILABLE_TYPES.filter(t => t.extensions.includes(extension));
    if (found.length > 0) {
      return found[0];
    } else {
      return ArtifactType.OTHER;
    }
  }

  /**
   * Returns the default file extension.
   */
  get defaultExtension() {
    return this.extensions[0];
  }

  /**
   * All ArtifactTypes support metadata as of 10.3.0
   * In order to maintain the ability to disable support for a single type,
   * each ArtifactType's metadata support is explicitly true.
   */
  get supportMetadata() {
    switch (this) {
      // BE
      case ArtifactType.BE_DECISION_TABLE:
      case ArtifactType.RULE_TEMPLATE:
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
      case ArtifactType.DOMAIN_MODEL:
      case ArtifactType.RULE_FUNCTION:
      case ArtifactType.FOLDER:
        return false;
      case ArtifactType.OTHER:
      case ArtifactType.SPARK:
      case ArtifactType.EXCEL:
      case ArtifactType.POJO:
      case ArtifactType.PMML:
      case ArtifactType.RDATA:
      case ArtifactType.RDS:
      case ArtifactType.RSCRIPT:
      case ArtifactType.SB_DECISION_TABLE:
      case ArtifactType.SCALA:
      case ArtifactType.TEXT:
      default:
        return true;
    }
  }

  get supportVerify() {
    switch (this) {
      case ArtifactType.SB_DECISION_TABLE:
        return true;
      default:
        return false;
    }
  }

  get supportValidate() {
    switch (this) {
      case ArtifactType.SB_DECISION_TABLE:
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
      case ArtifactType.DOMAIN_MODEL:
      case ArtifactType.BE_DECISION_TABLE:
        return true;
      default:
        return false;
    }
  }

  get canEdit(): boolean {
    if (!environment.enableBEUI) {
      return true;
    } else {
      switch (this) {
        case ArtifactType.BE_DECISION_TABLE:
        case ArtifactType.DOMAIN_MODEL:
        case ArtifactType.RULE_TEMPLATE_INSTANCE:
          return true;
        default:
          return false;
      }
    }
  }

  get isBinary() {
    if (environment.enableBEUI) {
      switch (this) {
        case ArtifactType.BE_DECISION_TABLE:
        case ArtifactType.DOMAIN_MODEL:
        case ArtifactType.RULE_FUNCTION:
        case ArtifactType.RULE_TEMPLATE:
        case ArtifactType.RULE_TEMPLATE_INSTANCE:
          return false;
        default:
          return true;
      }
    } else {
      switch (this) {
        case ArtifactType.SB_DECISION_TABLE:
        case ArtifactType.PMML:
        case ArtifactType.POJO:
        case ArtifactType.TEXT:
        case ArtifactType.RSCRIPT:
        case ArtifactType.SCALA:
          return false;
        default:
          return true;
      }
    }

  }

  get defaultContent(): string {
    switch (this) {
      case ArtifactType.SB_DECISION_TABLE:
        return DecisionTable.fromXml('').seralize();
      case ArtifactType.BE_DECISION_TABLE:
        return BEDecisionTable.fromXml('').seralize();
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
        return RuleTemplateInstance.serialize(RuleTemplateInstance.deserialize(''));
      case ArtifactType.DOMAIN_MODEL:
        return DomainModelContent.fromXml('').seralize();
      case ArtifactType.PROJECT_SUMMARY:
        return ProjectSummary.fromXml('').seralize();
      default:
        return '';
    }
  }

  public get defaultIcon() {
    switch (this) {
      case ArtifactType.SB_DECISION_TABLE:
      case ArtifactType.BE_DECISION_TABLE:
        return 'fa fa-th-list';
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
        return 'fa fa-level-down';
      case ArtifactType.DOMAIN_MODEL:
        return 'fa fa-list-ol';
      case ArtifactType.RDS:
      case ArtifactType.RDATA:
        return 'fa fa-connectdevelop';
      case ArtifactType.SPARK:
        return 'fa fa-file-archive-o';
      case ArtifactType.PMML:
      case ArtifactType.RSCRIPT:
      case ArtifactType.POJO:
      case ArtifactType.SCALA:
        return 'fa fa-codepen';
      case ArtifactType.EXCEL:
        return 'fa fa-file-excel-o';
      case ArtifactType.TEXT:
        return 'fa fa-file-text-o';
      default:
        return 'fa fa-file-o';
    }
  }

  get defaultInterface(): EditorInterface {
    switch (this) {
      case ArtifactType.SB_DECISION_TABLE:
        return EditorInterface.SB_DECISION_TABLE;
      case ArtifactType.BE_DECISION_TABLE:
        return EditorInterface.BE_DECISION_TABLE;
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
        return EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW;
      case ArtifactType.DOMAIN_MODEL:
        return EditorInterface.DOMAIN_MODEL;
      case ArtifactType.PMML:
      case ArtifactType.RSCRIPT:
      case ArtifactType.POJO:
      case ArtifactType.TEXT:
      case ArtifactType.SCALA:
      case ArtifactType.EXCEL:
        return EditorInterface.TEXT;
      case ArtifactType.RDS:
      case ArtifactType.RDATA:
      case ArtifactType.SPARK:
      default:
        return EditorInterface.METADATA;
    }
  }

  /**
   * Added in SB-42331 as most artifact types do not support synchronize.
   */
  get supportSync(): boolean {
    switch (this) {
      case ArtifactType.BE_DECISION_TABLE:
      case ArtifactType.SB_DECISION_TABLE:
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
        return true;
      default:
        return false;
    }
  }
}
