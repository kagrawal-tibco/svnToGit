import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { ContentGroup } from '../content-group';
import { ContentModelService } from '../content-model.service';

export class EditGroupModalContext extends BSModalContext {
  constructor(
    public projectName: string,
    public group: ContentGroup,
    public service: ContentModelService
  ) {
    super();
  }

}

@Component({
  templateUrl: './edit-group.modal.html'
})
export class EditGroupModal implements ModalComponent<EditGroupModalContext> {
  context: EditGroupModalContext;
  constructor(
    groupService: ContentModelService,
    public dialog: DialogRef<EditGroupModalContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
  }

  onCancel() {
    this.dialog.dismiss();
  }

  onEditGroup(groupName: string, filter: string) {
    this.context.service.updateGroup(this.context.group.groupId, groupName, filter, null, false).then(
      data => {
        const idx = this.context.service.groups.indexOf(this.context.group);
        if (idx >= 0) {
          // remove the group so it will be recreated
          this.context.service.groups.splice(idx, 1);
        }
        this.context.service.refreshGroups();
      }
    );
    this.dialog.dismiss();
  }

  currName() {
    return this.context.group.groupName;
  }
  currFilter() {
    return this.context.group.fileType;
  }

  get title() {
    return this.i18n('Update an existing artifact group');
  }

}
