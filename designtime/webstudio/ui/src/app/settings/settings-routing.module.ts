import { AccountsComponent } from './accounts/accounts.component';
import { SettingsComponent } from './settings.component';
import { ProjectsComponent } from './projects/projects.component';
import { PreferenceComponent } from './preference/preference.component';
import { OperatorPreferenceComponent } from './projects/operator-preferences/operator-preferences.component';
import { NotificationsComponent } from './projects/notification/notification.component';
import { BEDeploymentPreferences } from './projects/be-deployment-preferences/be-deployment-preferences.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BEUserService } from '../core-be/be.user.service';
import { DownloadProjectsComponent } from './download-projects/download-projects.component';
@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: SettingsComponent,
        children: [
          { path: '', redirectTo: 'preferences', pathMatch: 'full' },
          { path: 'preferences', component: PreferenceComponent },
          { path: 'projects', component: ProjectsComponent },
          { path: 'accounts', component: AccountsComponent },
          { path: 'operator-preferences', component: OperatorPreferenceComponent, canActivate: [BEUserService] },
          { path: 'notification-preferences', component: NotificationsComponent, canActivate: [BEUserService] },
          { path: 'be-deployment-preferences', component: BEDeploymentPreferences, canActivate: [BEUserService] },
          { path: 'download-projects', component: DownloadProjectsComponent }
        ]
      },
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class SettingsRoutingModule {

}
