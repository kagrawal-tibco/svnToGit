
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

import { BehaviorSubject, Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { DeploymentHistoryContext, DeploymentHistoryModal } from './deployment-history.modal';
import { DeploymentService } from './deployment.service';

import { environment } from '../../environments/environment';
import { ArtifactService } from '../core/artifact.service';
import { ModalService } from '../core/modal.service';
import { Artifact, ArtifactType } from '../models/artifact';

@Component({
    selector: 'deployment-history',
    template: ''
})
export class DeploymentHistoryComponent implements OnInit {

    private artifact: Observable<Artifact>;

    constructor(
        private serviceArtifact: ArtifactService,
        private serviceDeploy: DeploymentService,
        private route: ActivatedRoute,
        private router: Router,
        private serviceModal: ModalService
    ) {

    }

    ngOnInit() {
        this.route.paramMap.pipe(switchMap(
            (params: ParamMap) => {
                return this.serviceDeploy.getDeploymentHistoryById(params.get('id'));
            }
        )).subscribe(
            (deplHistRec) => {
                if (!deplHistRec) {
                    this.router.navigate(['/workspace']);
                } else {
                    this.serviceModal.open(DeploymentHistoryModal, new DeploymentHistoryContext(deplHistRec.artifactId, deplHistRec))
                        .then((nothing) => {/** * Not expecting anything from the modal, do nothing. */ },
                            (error?) => {
                                if (error) {
                                    throw error;
                                } else { /** Just closed */
                                    this.router.navigate(['/workspace']);
                                }
                            }
                        );
                }
            }
        );
    }
}
