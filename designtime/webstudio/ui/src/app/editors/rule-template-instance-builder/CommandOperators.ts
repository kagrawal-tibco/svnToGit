/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:03:09+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:03:11+05:30
 */

import { OperatorTypes } from './OperatorTypes';

export class CommandOperator {
  private static setToOperator = new OperatorTypes('set to', 'primitive');
  private static incrementByOperator = new OperatorTypes('increment by', 'primitive');
  private static decrementByOperator = new OperatorTypes('decrement by', 'primitive');
  private static setToFieldOperator = new OperatorTypes('set to field', 'non primitive');
  private static incrementByFieldOperator = new OperatorTypes('increment by field', 'non primitive');
  private static decrementByFieldOperator = new OperatorTypes('decrement by field', 'non primitive');
  private static setToNullOperator = new OperatorTypes('set to null');
  private static setToFalseOperator = new OperatorTypes('set to false');
  private static setToTrueOperator = new OperatorTypes('set to true');
  private static commandOperatorMap = new Map<string, OperatorTypes[]>();

  public static initialize(): void {
    const stringCommandOperator: OperatorTypes[] = new Array<OperatorTypes>();
    stringCommandOperator.push(CommandOperator.setToOperator);
    stringCommandOperator.push(CommandOperator.setToFieldOperator);
    stringCommandOperator.push(CommandOperator.setToNullOperator);

    const numberCommandOperator: OperatorTypes[] = new Array<OperatorTypes>();
    numberCommandOperator.push(CommandOperator.setToOperator);
    numberCommandOperator.push(CommandOperator.setToFieldOperator);
    numberCommandOperator.push(CommandOperator.incrementByOperator);
    numberCommandOperator.push(CommandOperator.incrementByFieldOperator);
    numberCommandOperator.push(CommandOperator.decrementByOperator);
    numberCommandOperator.push(CommandOperator.decrementByFieldOperator);

    const booleanCommandOperator: OperatorTypes[] = new Array<OperatorTypes>();
    booleanCommandOperator.push(CommandOperator.setToFieldOperator);
    booleanCommandOperator.push(CommandOperator.setToFalseOperator);
    booleanCommandOperator.push(CommandOperator.setToTrueOperator);

    const conceptCommandOperator: OperatorTypes[] = new Array<OperatorTypes>();
    conceptCommandOperator.push(CommandOperator.setToOperator);
    conceptCommandOperator.push(CommandOperator.setToFieldOperator);
    conceptCommandOperator.push(CommandOperator.setToNullOperator);

    const dateTimeCommandOperator: OperatorTypes[] = new Array<OperatorTypes>();
    dateTimeCommandOperator.push(CommandOperator.setToOperator);
    dateTimeCommandOperator.push(CommandOperator.setToFieldOperator);

    CommandOperator.commandOperatorMap.set('number', numberCommandOperator);
    CommandOperator.commandOperatorMap.set('String', stringCommandOperator);
    CommandOperator.commandOperatorMap.set('boolean', booleanCommandOperator);
    CommandOperator.commandOperatorMap.set('concept', conceptCommandOperator);
    CommandOperator.commandOperatorMap.set('DateTime', dateTimeCommandOperator);
  }

  public static getOperators(linkType: string): OperatorTypes[] {
    let dataType: string;
    if (linkType === 'int' || linkType === 'long' || linkType === 'double' || linkType === 'float' || linkType === 'integer') {
      dataType = 'number';
    } else if (linkType === 'NA' || linkType.search('concept') !== -1 || linkType.search('event') !== -1 || linkType.search('Concept') !== -1) {
      dataType = 'concept';
    } else if (linkType === 'boolean') {
      dataType = 'boolean';
    } else {
      dataType = linkType;
    }
    return CommandOperator.commandOperatorMap.get(dataType);
  }

  public static SET_TO(): string {
    return CommandOperator.setToOperator.operator;
  }

  public static SET_TO_FIELD(): string {
    return CommandOperator.setToFieldOperator.operator;
  }
  public static SET_TO_TRUE(): string {
    return CommandOperator.setToTrueOperator.operator;
  }

  public static SET_TO_FALSE(): string {
    return CommandOperator.setToFalseOperator.operator;
  }

  public static SET_TO_NULL(): string {
    return CommandOperator.setToNullOperator.operator;
  }

  public static INCREMENT_BY_OPERATOR(): string {
    return CommandOperator.incrementByOperator.operator;
  }

  public static INCREMENT_BY_FIELD_OPERATOR(): string {
    return CommandOperator.incrementByFieldOperator.operator;
  }

  public static DECREMENT_BY_OPERATOR(): string {
    return CommandOperator.decrementByOperator.operator;
  }
  public static DECREMENT_BY_FIELD_OPERATOR(): string {
    return CommandOperator.decrementByFieldOperator.operator;
  }

  public static getOperator(operator: string): OperatorTypes {
    switch (operator) {
      case CommandOperator.SET_TO(): return CommandOperator.setToOperator;
      case CommandOperator.SET_TO_NULL(): return CommandOperator.setToNullOperator;
      case CommandOperator.SET_TO_TRUE(): return CommandOperator.setToTrueOperator;
      case CommandOperator.SET_TO_FALSE(): return CommandOperator.setToFalseOperator;
      case CommandOperator.SET_TO_FIELD(): return CommandOperator.setToFieldOperator;
      case CommandOperator.INCREMENT_BY_OPERATOR(): return CommandOperator.incrementByOperator;
      case CommandOperator.INCREMENT_BY_FIELD_OPERATOR(): return CommandOperator.incrementByFieldOperator;
      case CommandOperator.DECREMENT_BY_OPERATOR(): return CommandOperator.decrementByOperator;
      case CommandOperator.DECREMENT_BY_FIELD_OPERATOR(): return CommandOperator.decrementByFieldOperator;
    }
  }

}
