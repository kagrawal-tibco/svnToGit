import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AuditTrailMode } from './audit-trail.component';

export class AuditTrailContext {
  projectName: string;
  artifactPath: string;
  artifactType: string;
  mode: AuditTrailMode;
}

@Component({
  templateUrl: './audit-trail.modal.html',
})
export class AuditTrailModal {

  artifactPath: string;
  artifactType: string;
  projectName: string;
  mode: AuditTrailMode;

  constructor(
    public dialogRef: MatDialogRef<AuditTrailModal>,
    @Inject(MAT_DIALOG_DATA) public data: AuditTrailContext,
    public i18n: I18n) {
    this.projectName = data.projectName;
    this.artifactPath = data.artifactPath;
    this.artifactType = data.artifactType;
    this.mode = data.mode;

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClose(): void {
    this.dialogRef.close();
  }
}
