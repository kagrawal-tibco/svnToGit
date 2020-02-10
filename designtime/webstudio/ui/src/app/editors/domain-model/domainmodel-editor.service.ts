import { Injectable } from '@angular/core';

import { RangeDomainEntry, SingleDomainEntry } from './domain-entry';
import { DomainModelContent } from './domain-model';
import { DomainModelComponent } from './domainmodel-editor.component';

import { EditAction } from '../edit-action';

@Injectable()
export class DomainModelEditorService {

  rowCreateAction(entry: any): EditAction<DomainModelContent> {
    return {
      execute: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        if (dm) {
          dm.entries.push(entry);
          context.params.editBuffer.markForDirtyCheck();
          context.updateGrid(dm);
          // console.log(dm);

        }
      },
      revert: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        if (dm) {
          // console.log(entry);
          dm.entries.splice(dm.entries.indexOf(entry), 1);
          context.updateGrid(dm);
        }
        context.params.editBuffer.markForDirtyCheck();

      }
    };
  }

  rowDeleteAction(entriestoDelete: Array<SingleDomainEntry>): EditAction<DomainModelContent> {
    return {
      execute: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        if (dm) {
          for (const entry of entriestoDelete) {
            // console.log(entry);
            dm.entries.splice(dm.entries.indexOf(entry), 1);
            context.updateGrid(dm);
          }
          context.params.editBuffer.markForDirtyCheck();
        }
      },
      revert: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        if (dm) {
          for (const entry of entriestoDelete) {
            // console.log(entry);
            dm.entries.push(entry);
            context.updateGrid(dm);
          }
          dm.sortEntries();
          context.params.editBuffer.markForDirtyCheck();
        }
      }
    };
  }

  propertyEditAction(propName: string, oldVal: string, val: string): EditAction<DomainModelContent> {
    return {
      execute: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        if (dm) {
          if (propName === 'name') {
            dm.name = val;
          } else if (propName === 'folder') {
            dm.folder = val;
          } else if (propName === 'description') {
            dm.description = val;
          } else if (propName === 'ownerProjectName') {
            dm.ownerProjectName = val;
          } else if (propName === 'superDomainPath') {
            dm.superDomainPath = val;
          }
          context.params.editBuffer.markForDirtyCheck();
        }
      },
      revert: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        if (dm) {
          if (propName === 'name') {
            dm.name = oldVal;
          } else if (propName === 'folder') {
            dm.folder = oldVal;
          } else if (propName === 'description') {
            dm.description = oldVal;
          } else if (propName === 'ownerProjectName') {
            dm.ownerProjectName = oldVal;
          } else if (propName === 'superDomainPath') {
            dm.superDomainPath = oldVal;
          }
          context.params.editBuffer.markForDirtyCheck();
        }

      }
    };
  }

  entryEditAction(oldEntry: any, isRange: boolean, newVal: string, newLValue: string, newUValue: string, newDesc: string, newlInclude: boolean, newuInclude: boolean): EditAction<DomainModelContent> {
    return {
      execute: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        const e = dm.entries.filter(e => e.id === oldEntry.id)[0];
        if (oldEntry instanceof RangeDomainEntry) {
          if (isRange) {
            if (newLValue !== oldEntry.lower) {
              e.lower = newLValue;
            }
            if (newUValue !== oldEntry.upper) {
              e.upper = newUValue;
            }
            if (newDesc !== oldEntry.description) {
              e.description = newDesc;
            }
            if (newlInclude !== oldEntry.lowerInclusive) {
              e.lowerInclusive = newlInclude;
            }
            if (newuInclude !== oldEntry.upperInclusive) {
              e.upperInclusive = newuInclude;
            }
          } else {
            dm.entries.splice(dm.entries.indexOf(oldEntry), 1);
            dm.entries.push(new SingleDomainEntry(false, oldEntry.id, newVal, newDesc));
          }
        } else if (oldEntry instanceof SingleDomainEntry) {
          if (isRange) {
            dm.entries.splice(dm.entries.indexOf(oldEntry), 1);
            dm.entries.push(new RangeDomainEntry(false, oldEntry.id, newLValue, newUValue, newDesc, newlInclude, newuInclude));
          } else {
            if (newVal !== oldEntry.value) {
              e.value = newVal;
            }
            if (newDesc !== oldEntry.description) {
              e.description = newDesc;
            }
          }
        }
        context.params.editBuffer.markForDirtyCheck();
        context.updateGrid(dm);

      },
      revert: (context: DomainModelComponent) => {
        const dm = context.params.editBuffer.getBuffer();
        const e = dm.entries.filter(e => e.id === oldEntry.id)[0];
        if (oldEntry instanceof RangeDomainEntry) {
          if (isRange) {
            if (newLValue !== oldEntry.lower) {
              e.lower = oldEntry.lower;
            }
            if (newUValue !== oldEntry.upper) {
              e.upper = oldEntry.upper;
            }
            if (newDesc !== oldEntry.description) {
              e.description = oldEntry.description;
            }
          } else {
            dm.entries.splice(dm.entries.indexOf(e), 1);
            dm.entries.push(oldEntry);
          }
        } else if (oldEntry instanceof SingleDomainEntry) {
          if (isRange) {
            dm.entries.splice(dm.entries.indexOf(e), 1);
            dm.entries.push(oldEntry);
          } else {
            if (newVal !== oldEntry.value) {
              e.value = oldEntry.value;
            }
            if (newDesc !== oldEntry.description) {
              e.description = oldEntry.description;
            }
          }
        }
        context.params.editBuffer.markForDirtyCheck();
        context.updateGrid(dm);
      }
    };
  }
}
