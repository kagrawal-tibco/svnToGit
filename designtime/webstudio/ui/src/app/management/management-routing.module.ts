import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuditTrailComponent } from 'app/audit-trail/audit-trail.component';

import { BERolesComponent } from './be-roles/be-roles.component';
import { BEUsersComponent } from './be-users/be-users.component';
import { LockManagement } from './lock-management/be-lock-management.component';
import { ManagementComponent } from './management.component';
import { RolesComponent } from './roles/roles.component';
import { UsersComponent } from './users/users.component';

import { environment } from '../../environments/environment';
import { BEUserService } from '../core-be/be.user.service';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: ManagementComponent,
        children: [
          { path: '', redirectTo: environment.enableTCEUI ? 'audit-trail' : environment.enableBEUI ? 'be-users' : 'users', pathMatch: 'full' },
          { path: 'users', component: UsersComponent },
          { path: 'roles', component: RolesComponent },
          { path: 'be-users', component: BEUsersComponent, canActivate: [BEUserService] },
          { path: 'be-roles', component: BERolesComponent, canActivate: [BEUserService] },
          { path: 'be-lock-management', component: LockManagement, canActivate: [BEUserService] },
          { path: 'audit-trail', component: AuditTrailComponent }
        ]
      },
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class ManagementRoutingModule {

}
