import * as _ from 'lodash';
import { Observable, Subject } from 'rxjs';

import { BEDecisionTable } from './decision-table/be-decision-table';
import { DecisionTable } from './decision-table/decision-table';
import { RuleTemplateInstance, RuleTemplateInstanceImpl } from './rule-template-instance/rule-template-instance';

import { DomainModelContent } from '../editors/domain-model/domain-model';
import { EditorInterface } from '../editors/editor-interface';
import { ProjectSummary } from '../editors/project-summary/project-summary';

export class EditBuffer<T> {
  private dirty = false;
  private needDirtyCheck = false;
  private base: T;
  private buffer: T;
  private _refresh = new Subject<boolean>();

  constructor(
    private serializer: (T) => string,
    private deserializer: (string) => T,
    public editorInterface: EditorInterface
  ) { }

  static text() {
    return new EditBuffer<string>(raw => raw, raw => raw, EditorInterface.TEXT);
  }

  static metadata() {
    return new EditBuffer<string>(raw => raw, raw => raw ? raw : '{}', EditorInterface.METADATA);
  }

  static decisionTable() {
    return new EditBuffer<DecisionTable>(
      (dt) => dt.toXml(),
      raw => {
        if (raw) {
          return DecisionTable.fromXmlUnsafe(raw);
        } else {
          return DecisionTable.fromXml('');
        }
      },
      EditorInterface.SB_DECISION_TABLE);
  }

  static beDecisionTable() {
    return new EditBuffer<DecisionTable>(
      (dt) => dt.toXml(),
      raw => {
        if (raw) {
          return BEDecisionTable.fromXml(raw);
        } else {
          return BEDecisionTable.fromXml('');
        }
      },
      EditorInterface.BE_DECISION_TABLE);
  }

  static domainModel() {
    return new EditBuffer<DomainModelContent>(
      (dm) => dm.toXml(),
      raw => {
        if (raw) {
          return DomainModelContent.fromXml(raw);
        } else {
          return DomainModelContent.fromXml('');
        }
      },
      EditorInterface.DOMAIN_MODEL);
  }

  static projectSummary() {
    return new EditBuffer<ProjectSummary>(
      (ps) => ps.toXml(),
      raw => {
        if (raw) {
          return ProjectSummary.fromXml(raw);
        } else {
          return ProjectSummary.fromXml('');
        }
      },
      EditorInterface.PROJECT_SUMMARY);
  }

  static ruleTemplateInstanceBuilder() {
    return new EditBuffer<RuleTemplateInstanceImpl>(
      (rti) => RuleTemplateInstance.serialize(rti),
      raw => RuleTemplateInstance.deserialize(raw),
      EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER);
  }

  static ruleTemplateInstanceView() {
    return new EditBuffer<RuleTemplateInstanceImpl>(
      (rti) => RuleTemplateInstance.serialize(rti),
      raw => RuleTemplateInstance.deserialize(raw),
      EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW);
  }

  init(raw: string, editable: boolean): EditBuffer<any> {
    this.base = this.deserializer(raw);
    if (editable) {
      this.buffer = this.deserializer(raw);
    }
    return this;
  }

  replaceBuffer(buffer: T) {
    this.buffer = buffer;
  }

  getBase(): T {
    return this.base;
  }

  getBuffer(): T {
    return this.buffer;
  }

  getContent(): T {
    return this.buffer ? this.buffer : this.base;
  }

  serialize() {
    if (this.buffer) {
      return this.serializer(this.buffer);
    } else {
      return this.serializer(this.base);
    }
  }

  onSave() {
    if (this.buffer) {
      this.base = this.deserializer(this.serializer(this.buffer));
    } else {
      throw Error('Shall not invoke onSave as this editbuffer is not editable');
    }
    this.dirty = false;
    this.needDirtyCheck = false;
  }

  onDiscardChanges() {
    if (this.buffer) {
      this.buffer = this.deserializer(this.serializer(this.base));
    } else {
      throw Error('Shall not invoke onDiscardChanges as this editbuffer is not editable');
    }
    this.dirty = false;
    this.needDirtyCheck = false;
    this.markForRefresh();
  }

  markForDirtyCheck() {
    this.needDirtyCheck = true;
  }

  markForRefresh() {
    this._refresh.next(true);
  }

  onRefresh(): Observable<boolean> {
    return this._refresh;
  }

  isDirty() {
    if (this.buffer) {
      if (this.needDirtyCheck) {
        this.dirty = !_.isEqual(this.base, this.buffer);
        this.needDirtyCheck = false;
      }
      return this.dirty;
    } else {
      return false;
    }
  }

  isEditable() {
    return this.buffer != null;
  }
}
