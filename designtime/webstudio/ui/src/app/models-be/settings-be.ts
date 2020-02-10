import { Settings } from '../models/settings';

export interface BESettings extends Settings {
  portalColumns: number;
  recentlyOpenedArtifactLimit: number;
  favoriteArtifactLimit: number;
  customURL: string;
  itemView: string;
  decisionTablePageSize: number;
  scsUserName: string;
  scsUserPassword: string;
  autoUnLockOnReview: boolean;
  groupRelatedArtifacts: boolean;
  allowCustomDomainValues: boolean;
  showColumnAliasIfPresent: boolean;
  isGroupingPropertyChanged: boolean;
  autoFitColumnsApproch: string;
  defaultRTIFilterType: string;
  rtiViewStringWidget: string;
}

export class ProjectViewType {
  static TYPES = [];
  static TREE = new ProjectViewType('Tree', 'Tree');
  static VIEW = new ProjectViewType('List', 'List');
  static BREADCRUMBS = new ProjectViewType('Breadcrumbs', 'Breadcrumbs');
  static BASICTREE = new ProjectViewType('Basic Tree (no groups)', 'Basic Tree (no groups)');

  constructor(
    public name: string,
    public value: string
  ) {
    ProjectViewType.TYPES.push(this);
  }
}

// No need of this preference/setting as ag-grid support this functionality.
// export class ColumnAutoFitType {
//  static TYPES = [];
//  static DEFAULT = new ColumnAutoFitType('Default', 'Default');
//  static BOTH = new ColumnAutoFitType('Both', 'Both');
//  static TITLE = new ColumnAutoFitType('Title', 'Title');
//  static VALUE = new ColumnAutoFitType('Value', 'Value');
//
//  constructor(
//    public name: string,
//    public value: string
//  ) {
//    ColumnAutoFitType.TYPES.push(this);
//  }
// }

export class RTIFilterType {
  static TYPES = [];
  static ANY = new RTIFilterType('Match Any', 'Match Any');
  static ALL = new RTIFilterType('Match All', 'Match All');
  static NONE = new RTIFilterType('Match None', 'Match None');

  constructor(
    public name: string,
    public value: string
  ) {
    RTIFilterType.TYPES.push(this);
  }
}

export class RTIStringWidget {
  static WIDGETS = [];
  static TEXTAREA = new RTIStringWidget('TextArea', 'TextArea');
  static TEXTBOX = new RTIStringWidget('TextBox', 'TextBox');

  constructor(
    public name: string,
    public value: string
  ) {
    RTIStringWidget.WIDGETS.push(this);
  }
}
