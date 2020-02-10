import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule, MatDividerModule, MatGridListModule, MatInputModule, MatListModule } from '@angular/material';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTooltipModule } from '@angular/material/tooltip';

import { SelectModule } from 'ng-select';

import { AuditTrailModule } from 'app/audit-trail/audit-trail.module';
import { DashboardModule } from 'app/dashboard/dashboard.module';

import { BEManagementService } from './be-management.service';
import { BERolesComponent } from './be-roles/be-roles.component';
import { AddBEUserComponent } from './be-users/add-be-user.component';
import { BEUsersComponent } from './be-users/be-users.component';
import { ChangePasswordBEUser } from './be-users/change-password-be-user.component';
import { LockManagement } from './lock-management/be-lock-management.component';
import { ManagementRoutingModule } from './management-routing.module';
import { ManagementComponent } from './management.component';
import { ManagementService } from './management.service';
import { PermissionInstanceComponent } from './roles/permission-instance.component';
import { RolesComponent } from './roles/roles.component';
import { UsersComponent } from './users/users.component';

import { BEArtifactService } from '../core-be/be.artifact.service';
import { BEProjectService } from '../core-be/be.project.service';
import { BEUserService } from '../core-be/be.user.service';
import { SettingsModule } from '../settings/settings.module';
import { SharedModule } from '../shared/shared.module';
import { UsernameValidator } from '../shared/username-validator.directive';

@NgModule({
  imports: [
    SharedModule,
    SelectModule,
    AuditTrailModule,
    ManagementRoutingModule,
    SettingsModule,
    MatGridListModule,
    MatCardModule,
    MatDividerModule,
    MatListModule,
    MatIconModule,
    MatTooltipModule,
    MatSidenavModule,
    MatExpansionModule,
    MatButtonModule,
    MatMenuModule,
    MatFormFieldModule,
    MatDialogModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    ManagementComponent,
    UsersComponent,
    PermissionInstanceComponent,
    RolesComponent,
    UsernameValidator,
    RolesComponent,
    BEUsersComponent,
    AddBEUserComponent,
    ChangePasswordBEUser,
    BERolesComponent,
    LockManagement
  ],
  providers: [
    ManagementService,
    BEManagementService,
    BEProjectService,
    BEUserService,
    BEArtifactService
  ],
  exports: [
    UsersComponent
  ],
  entryComponents: [
    AddBEUserComponent,
    ChangePasswordBEUser
  ]
})
export class ManagementModule {

}
