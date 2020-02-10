import { Injectable, ReflectiveInjector, ResolvedReflectiveProvider } from '@angular/core';

import { AlertService } from './alert.service';
import { ArtifactService } from './artifact.service';
import { LifecycleService } from './lifecycle.service';
import { Logger } from './logger.service';
import { ModalService } from './modal.service';
import { ProjectService } from './project.service';
import { RecordService } from './record.service';
import { RestService } from './rest.service';

import { Differ } from '../editables/decision-table/differ/differ';

@Injectable()
export class ProviderService {

  providers: ResolvedReflectiveProvider[];

  constructor(
    private artifact: ArtifactService,
    private project: ProjectService,
    private lifecycle: LifecycleService,
    private record: RecordService,
    private log: Logger,
    private rest: RestService,
    private modal: ModalService,
    private alert: AlertService,
    private differ: Differ
  ) {
    this.providers = ReflectiveInjector.resolve(
      [
        { provide: ArtifactService, useValue: this.artifact },
        { provide: ProjectService, useValue: this.project },
        { provide: LifecycleService, useValue: this.lifecycle },
        { provide: RecordService, useValue: this.record },
        { provide: Logger, useValue: this.log },
        { provide: RestService, useValue: this.rest },
        { provide: Differ, useValue: this.differ },
        { provide: ModalService, useValue: this.modal },
        { provide: AlertService, useValue: this.alert },
        { provide: ProviderService, useValue: this },
      ]
    );
    // avoid circular DI
    this.modal.setProvider(this);
  }
}
