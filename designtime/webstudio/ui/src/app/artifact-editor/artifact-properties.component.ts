import { AfterContentChecked, Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ArtifactPropertiesService } from './artifact-properties.service';
import { Properties } from './properties';

import { BEDecisionTableEditorComponent } from '../editors/decision-table/be-decisiontable-editor.component';
import { Artifact } from '../models/artifact';
import { Result } from '../models/dto';
import { MultitabEditorService, Tab } from '../workspace/multitab-editor/multitab-editor.service';
import { MatSlideToggleChange } from '@angular/material';

@Component({
  selector: 'artifact-properties',
  templateUrl: './artifact-properties.component.html',
  styleUrls: ['./artifact-properties.component.css']
})
export class ArtifactPropertiesComponent implements AfterContentChecked {

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
  showCellRuleProps = false;

  constructor(private service: ArtifactPropertiesService,
    private multiTabService: MultitabEditorService,
    public i18n: I18n) {
    const currentTab: Tab = this.multiTabService.getActive();
    this.multiTabService.getActiveObservable().subscribe((currentTab) => {
      if (currentTab) {
        const currentArtifact: Artifact = currentTab.payload;
        this.initArtifact(currentArtifact);
        this.showCellRuleProps = false;
        this.service.properties.subscribe((props: Properties) => {
          this.showCellRuleProps = true;
          this.initProps(props);
        });

      }
    });
    if (currentTab) {
      const currentArtifact: Artifact = currentTab.payload;
      this.initArtifact(currentArtifact);
      this.showCellRuleProps = false;
      this.service.properties.subscribe((props: Properties) => {
        this.showCellRuleProps = true;
        this.initProps(props);
      });

    }
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
        this.isRTI = false;
      } else if (props.isRTI) {
        this.rtiName = props.rtiName;
        this.rtiPriority = props.rtiPriority;
        this.rtiDescription = props.rtiDescription;
        this.isRTI = props.isRTI;
        this.isDT = false;
      }
    }
  }

  initArtifact(artifact: Artifact) {
    if (artifact && artifact.content) {
      const jsonContent = JSON.parse(artifact.content);
      switch (artifact.type.defaultExtension) {
        case 'ruletemplateinstance':
          this.isRTI = true;
          this.isDT = false;
          this.rtiName = artifact.name;
          let priority: number = jsonContent.rulePriority;
          if (priority == null || priority < 1) {
            priority = 1;
          } else if (priority > 10) {
            priority = 10;
          }
          this.rtiPriority = priority;
          let description: string = jsonContent.description;
          if (description == null) {
            description = '';
          }
          this.rtiDescription = description;
          break;
        default:
          this.lastmodifiedG = this.service.getLastModifiedG;
          this.versionG = this.service.getVersionG;
          this.implementsG = jsonContent.implementsPath;
          this.ruleID = this.service.getRuleId;
          this.rulePriority = this.service.getRulePriority;
          this.ruleComments = this.service.getRuleComment;
          this.cellEnabled = this.service.getCellEnabled;
          this.cellComments = this.service.getCellComments;
          this.isDT = true;
          this.isRTI = false;
      }
    }
  }

  showCellRule(): boolean {
    if (this.isDT && this.showCellRuleProps) {
      return true;
    } else {
      this.selected = 'G';
      return false;
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

  public focusOutCellComment() {
    const currentArtifact: Artifact = this.multiTabService.activeTab.payload;
    this.service.updateCellComment(this.cellComments, currentArtifact.path);
  }

  public changeCellEnabled(event: MatSlideToggleChange) {
    // this.cellEnabled = event.checked;
    const currentArtifact: Artifact = this.multiTabService.activeTab.payload;
    this.service.updateCellEnabled(!this.cellEnabled, currentArtifact.path);
  }

  public focusOutRuleComment() {
    const currentArtifact: Artifact = this.multiTabService.activeTab.payload;
    this.service.updateRuleComment(this.ruleComments, currentArtifact.path);
  }

  public focusOutRulePriority() {
    const currentArtifact: Artifact = this.multiTabService.activeTab.payload;
    this.service.updateRuleComment(this.ruleComments, currentArtifact.path);
  }

  public focusOutRTIProperties() {
    const currentArtifact: Artifact = this.multiTabService.activeTab.payload;
    if (JSON.parse(currentArtifact.content).view) {
      this.service.updateRTIViewProperties(this.rtiName, this.rtiPriority, this.rtiDescription, currentArtifact.path);
    } else {
      this.service.updateRTIBuilderProperties(this.rtiName, this.rtiPriority, this.rtiDescription, currentArtifact.path);
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
