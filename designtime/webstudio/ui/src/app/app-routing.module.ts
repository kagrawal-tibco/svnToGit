import { NgModule } from '@angular/core';
import { RouterModule, RouteReuseStrategy } from '@angular/router';
import { PreloadAllModules } from '@angular/router';

import { AppRouteReuseStrategy } from './app-route-reuse-strategy';
import { DeploymentHistoryComponent } from './artifact-deployment/deployment-history.component';
import { AuthGuard } from './core/auth-guard.service';
import { ManagementGuard } from './core/management-guard.service';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RedirectComponent } from './redirect/redirect.component';
import { WorkspaceComponent } from './workspace/workspace.component';

@NgModule({
  imports: [
    RouterModule.forRoot([
      {
        path: 'workspace',
        component: WorkspaceComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'settings',
        loadChildren: './settings/settings.module#SettingsModule',
        canActivate: [AuthGuard]
      },
      {
        path: 'management',
        loadChildren: './management/management.module#ManagementModule',
        canActivate: [ManagementGuard]
      },
      {
        path: 'deployment/history/:id',
        component: DeploymentHistoryComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'login',
        loadChildren: './auth/auth.module#AuthModule'
      },
      {
        path: 'redirect/:url',
        component: RedirectComponent
      },
      { path: '', redirectTo: 'redirect/workspace', pathMatch: 'full' },
      { path: '**', redirectTo: 'redirect/workspace' },
    ],
      {
        //        enableTracing: true, // <-- debugging purposes only
        //        preloadingStrategy: PreloadAllModules
      })
  ],
  exports: [
    RouterModule
  ],
  providers: [
    {
      provide: RouteReuseStrategy,
      useClass: AppRouteReuseStrategy
    }
  ]

})
export class AppRoutingModule {
}
