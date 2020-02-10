import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { BEDecisionTableEditorComponent } from './decision-table/be-decisiontable-editor.component';
import { DecisionTableEditorComponent } from './decision-table/decisiontable-editor.component';
import { DecisionTableEditorModule } from './decision-table/decisiontable-editor.module';
import { DomainCellEditorComponent } from './decision-table/decorators/decisiontable-domain-cell-editor';
import { DomainModelComponent } from './domain-model/domainmodel-editor.component';
import { DomainModelModule } from './domain-model/domainmodel-editor.module';
import { EditorLoaderComponent } from './editor-loader.component';
import { EditorLoaderModal } from './editor-loader.modal';
import { EditorComponent } from './editor.component';
import { MetadataEditorComponent } from './metadata/metadata-editor.component';
import { MetadataEditorModule } from './metadata/metadata-editor.module';
import { ProjectSummaryComponent } from './project-summary/project-summary.component';
import { ProjectSummaryModule } from './project-summary/project-summary.module';
import { ReviewPropertiesService } from './review-properties.service';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder/rule-template-instance-builder.component';
import { RuleTemplateInstanceBuilderModule } from './rule-template-instance-builder/ruletemplateinstancebuilder-editor.module';
import { RTIViewClient } from './rule-template-instance-view/RTIViewClient';
import { RuleTemplateInstanceViewModule } from './rule-template-instance-view/ruletemplateinstanceview-editor.module';
import { TextEditorComponent } from './text/text-editor.component';
import { TextEditorModule } from './text/text-editor.module';

import { ArtifactPropertiesService } from '../artifact-editor/artifact-properties.service';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    FormsModule,
    SharedModule,
    CommonModule,
    TextEditorModule,
    MetadataEditorModule,
    DecisionTableEditorModule,
    RuleTemplateInstanceBuilderModule,
    RuleTemplateInstanceViewModule,
    DomainModelModule,
    ProjectSummaryModule
  ],
  declarations: [EditorLoaderComponent, EditorLoaderModal, EditorComponent],
  exports: [EditorLoaderComponent, EditorLoaderModal],
  entryComponents: [DecisionTableEditorComponent,
    BEDecisionTableEditorComponent,
    MetadataEditorComponent,
    TextEditorComponent,
    RuleTemplateInstanceBuilder,
    RTIViewClient,
    DomainModelComponent,
    ProjectSummaryComponent,
    DomainCellEditorComponent],
  providers: [ArtifactPropertiesService, ReviewPropertiesService]
})
export class EditorsModule {

}
