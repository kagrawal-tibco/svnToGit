import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ArtifactService, SynchronizeStrategy } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { Artifact } from '../models/artifact';

@Component({
  selector: 'status-resolver',
  templateUrl: './synchronize-status-resolver.component.html',
})
export class SynchronizeStatusResolverComponent implements OnInit {
  @Input()
  lhs: Artifact;
  @Input()
  rhs: Artifact;
  @Output()
  done: EventEmitter<Artifact> = new EventEmitter<Artifact>();
  @Input()
  resolution: SynchronizeStrategy;
  @Output()
  resolutionChange = new EventEmitter<SynchronizeStrategy>();

  title: string;
  description: string;
  lhsDescription: string;
  rhsDescription: string;

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {

  }

  ngOnInit() {
    this.title = `Synchronize Artifact ${this.rhs.name}`;
    if (this.lhs.status === 'DELETED' && this.rhs.status === 'DELETED') {
      throw Error(this.i18n('No need to sync DELETED changes, it shall be resolved automatically'));
    } else if (this.lhs.status === 'DELETED') {
      this.description = this.i18n('Artifact{{name}} was deleted in the repository. \
      Do you want to discard your working copy or keep it as a newly added artifact?', { name: this.lhs.name });
      this.lhsDescription = this.i18n('Discard working copy');
      this.rhsDescription = this.i18n('Keep it as newly added');
    } else if (this.rhs.status === 'DELETED') {
      this.description = this.i18n('Artifact {{name}} was updated in the repository while it is deleted locally. \
      Do you want to revert your local deletion?', { name: this.rhs.name });
      this.lhsDescription = this.i18n('Revert the deletion');
      this.rhsDescription = this.i18n('Keep the deletion');
    } else {
      this.description = this.i18n('Artifact {{name}} was added in both the repository and \
      your working copy. Do you want to revert your addition?', { name: this.rhs.name });
      this.lhsDescription = this.i18n('Revert the addition');
      this.rhsDescription = this.i18n('Keep the artifact as a checkout from repository');
    }
  }

  onSelectedChange(idx: number) {
    if (idx === 0) {
      this.resolution = SynchronizeStrategy.LATEST;
    } else if (idx === 1) {
      this.resolution = SynchronizeStrategy.MERGE;
    } else {
      this.resolution = null;
    }
    this.resolutionChange.emit(this.resolution);
  }

  canConfirm(): boolean {
    return this.resolution != null;
  }

  onConfirm() {
    this.artifact.synchronize(this.lhs, this.rhs, this.resolution, null)
      .then(res => {
        if (res) {
          this.done.emit(res);
        }
      });
  }

  onCancel() {
    this.done.emit(null);
  }
}
