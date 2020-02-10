import { AfterContentChecked, Component, Input, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ArtifactEditorComponent } from 'app/artifact-editor/artifact-editor.component';
import { BEDecisionTable } from 'app/editables/decision-table/be-decision-table';

import { BEDecisionTableEditorComponent } from './decision-table/be-decisiontable-editor.component';
import { ReviewPropertiesService } from './review-properties.service';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder/rule-template-instance-builder.component';

import { Properties } from '../artifact-editor/properties';
import { RuleTemplateInstance, RuleTemplateInstanceImpl } from '../editables/rule-template-instance/rule-template-instance';
import { Artifact } from '../models/artifact';
import { Result } from '../models/dto';
import { MultitabEditorService, Tab } from '../workspace/multitab-editor/multitab-editor.service';
@Component({
  selector: 'review-properties',
  templateUrl: './review-properties.component.html',
  styleUrls: ['./review-properties.component.css']
})
export class ReviewPropertiesComponent implements AfterContentChecked, OnInit {
  @Input()
  public artifact: any;
  // BE Decision Table properties
  selected = 'G';
  lastmodifiedG: string;
  versionG: string;
  implementsG: string;
  ruleID: string;
  rulePriority: string;
  ruleComments: string;
  cellEnabled = false;
  cellComments: string;
  isDT = false;

  // Rule Template Instance properties
  rtiName: string;
  rtiPriority: number;
  rtiDescription: string;
  isRTI = false;
  priorityValues = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  constructor(private service: ReviewPropertiesService,
    private multiTabService: MultitabEditorService,
    public i18n: I18n) {
    this.service.properties1.subscribe((props: Properties) => {
      this.initProps(props);
    });
  }

  ngOnInit() {
    this.initArtifact();
  }

  ngAfterContentChecked() {

  }

  initProps(props: Properties) {
    if (props) {
      if (props.isDT) {
        if (props.selected) {
          this.selected = props.selected;
        }
        this.lastmodifiedG = props.lastmodifiedG;
        this.versionG = props.versionG;
        this.implementsG = props.implementsG;
        this.ruleID = props.ruleID;
        this.rulePriority = props.rulePriority;
        this.ruleComments = props.ruleComments;
        this.cellEnabled = props.cellEnabled;
        this.cellComments = props.cellComments;
        this.isDT = props.isDT;
      } else if (props.isRTI) {
        this.rtiName = props.rtiName;
        this.rtiPriority = props.rtiPriority;
        this.rtiDescription = props.rtiDescription;
        this.isRTI = props.isRTI;
      }
    }
  }

  initArtifact() {
    if (this.artifact instanceof RuleTemplateInstanceImpl) {
      this.isRTI = true;
      this.rtiPriority = this.artifact.getRulepriortiy();
      this.rtiDescription = this.artifact.getDescription();
    } else if (this.artifact instanceof BEDecisionTable) {
      this.lastmodifiedG = '';
      this.versionG = '';
      this.implementsG = this.artifact.implementsPath;
      this.ruleID = '';
      this.rulePriority = '';
      this.ruleComments = '';
      this.cellEnabled = false;
      this.cellComments = '';
      this.isDT = true;
    }
  }

  getEnabledText(cellEnabled: boolean) {
    if (cellEnabled) {
      return this.i18n('ON');
    } else {
      return this.i18n('OFF');
    }
  }

  icon(entry: Result) {
    if (entry.error) {
      return 'fa fa-times-circle error';
    } else {
      return 'fa fa-exclamation-triangle warning';
    }
  }

  selectTab(tab: string) {
    if (this.selected) {
      if (tab === 'general') {
        this.selected = 'G';
      } else if (tab === 'cell') {
        this.selected = 'C';
      } else {
        this.selected = 'R';
      }
    } else {
      this.selected = 'G';
    }
  }

  getStyle(tab: string) {
    if (this.selected === tab) {
      return '#d3d3d3';
    }
  }

  public focusOutRTIProperties() {
    const currentArtifact: Artifact = this.multiTabService.activeTab.payload;
    if (JSON.parse(currentArtifact.content).view) {
      this.service.updateRTIViewProperties(this.rtiName, this.rtiPriority, this.rtiDescription);
    } else {
      this.service.updateRTIBuilderProperties(this.rtiName, this.rtiPriority, this.rtiDescription);
    }
  }

  public updateRTIPriority(event) {
    this.rtiPriority = event.target.value;
    this.focusOutRTIProperties();
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
