import { ModificationType } from './ModificationType';

import { RelatedFilterValueImpl } from '../../../editors/rule-template-instance-builder/RelatedFilterValueImpl';
import { SimpleFilterValueImpl } from '../../../editors/rule-template-instance-builder/SimpleFilterValueImpl';
import { SingleFilterImpl } from '../../../editors/rule-template-instance-builder/SingleFilterImpl';

export class ModificationEntry {
  private modificationType: ModificationType;
  private applied = false;
  private previousValue: string;
  private currentValue: string;
  private previousValObj: Object;
  private currentValObj: Object;

  constructor(modificationType: ModificationType,
    previousValue?: string,
    currentValue?: string,
    previousValObj?: Object,
    currentValObj?: Object) {
    this.modificationType = modificationType;
    if (previousValue) {
      this.previousValue = previousValue;
    }
    if (currentValue) {
      this.currentValue = currentValue;
    }
    if (previousValObj && currentValObj) {
      this.previousValObj = previousValObj;
      this.currentValObj = currentValObj;
      if (previousValObj instanceof SingleFilterImpl) {
        this.previousValue = ModificationEntry.getFilterAsString(<SingleFilterImpl>previousValObj);
      }
      if (currentValObj instanceof SingleFilterImpl) {
        this.currentValue = ModificationEntry.getFilterAsString(<SingleFilterImpl>currentValObj);
      }
    }
  }

  public static getFilterAsString(filter: SingleFilterImpl): string {
    if (filter == null) {
      return '';
    }
    let links = '';
    const relatedLinkImplList = filter.getLink();
    if (relatedLinkImplList) {
      for (let i = 0; i < relatedLinkImplList.length; i++) {
        links += (i === 0 ? ' ' : '\'s ') + relatedLinkImplList[i].getName();
      }
    }
    const operator: string = filter.getOperator();
    let filterValue = '';
    if (filter.getValue() instanceof SimpleFilterValueImpl) {
      const value: SimpleFilterValueImpl = <SimpleFilterValueImpl>filter.getValue();
      if (value) {
        filterValue = value.value;
      }
    } else if (filter.getValue() instanceof RelatedFilterValueImpl) {
      const value: RelatedFilterValueImpl = <RelatedFilterValueImpl>filter.getValue();
      const relatedLinkImplValueList = value.getLinks();
      for (let i = 0; i < relatedLinkImplValueList.length; i++) {
        filterValue += (i === 0 ? '' : '\'s ') + relatedLinkImplValueList[i].getName();
      }
    }
    return links + ' ' + operator + ' ' + filterValue;
  }

  public setModificationType(modificationType: ModificationType): void {
    this.modificationType = modificationType;
  }

  public getModificationType(): ModificationType {
    return this.modificationType;
  }

  public getPreviousValue(): string {
    return this.previousValue;
  }

  public setPreviousValue(previousValue: string): void {
    this.previousValue = previousValue;
  }

  public isApplied(): boolean {
    return this.applied;
  }

  public setApplied(applied: boolean): void {
    this.applied = applied;
  }

  public getCurrentValue(): string {
    return this.currentValue;
  }

  public setCurrentValue(currentValue: string): void {
    this.currentValue = currentValue;
  }

  public getPreviousValueObj(): Object {
    return this.previousValObj;
  }

  public getCurrentValueObj(): Object {
    return this.currentValObj;
  }
}
