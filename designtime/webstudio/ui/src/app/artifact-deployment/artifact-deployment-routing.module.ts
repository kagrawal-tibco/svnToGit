import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeploymentHistoryComponent } from './deployment-history.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        // component: ManagementComponent,
        children: [
          { path: '', redirectTo: 'history', pathMatch: 'full' },
          { path: 'history/:id', component: DeploymentHistoryComponent },
        ]
      },
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class ArtifactDeploymentRoutingModule {
  // TODO: change deployment routing to use this module.
}
