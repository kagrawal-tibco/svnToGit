/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:03:31+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:03:31+05:30
 */

import { I18n } from '@ngx-translate/i18n-polyfill';

import { OperatorTypes } from './OperatorTypes';

export class FilterOperator {

    private static equalOperator = new OperatorTypes('equals', 'primitive');
    private static notEqualOperator = new OperatorTypes('not equal', 'primitive');
    private static isNullOperator = new OperatorTypes('is null');
    private static isNotNullOperator = new OperatorTypes('is not null');
    private static matchesOtherFieldOperator = new OperatorTypes('matches other field', 'non primitive');
    private static differsFromFieldOperator = new OperatorTypes('differs from field', 'non primitive');
    private static containsOperator = new OperatorTypes('contains', 'primitive');
    private static startsWithOperator = new OperatorTypes('starts with', 'primitive');
    private static endsWithOperator = new OperatorTypes('ends with', 'primitive');
    private static lessThanOperator = new OperatorTypes('less than', 'primitive');
    private static greaterThanOperator = new OperatorTypes('greater than', 'primitive');
    private static lessThanFieldOperator = new OperatorTypes('less than field', 'non primitive');
    private static greaterThanFieldOperator = new OperatorTypes('greater than field', 'non primitive');
    private static lessThanOrEqualToOperator = new OperatorTypes('less than or equal to', 'primitive');
    private static greaterThanOrEqualToOperator = new OperatorTypes('greater than or equal to', 'primitive');
    private static lessThanOrEqualToFieldOperator = new OperatorTypes('less than or equal to field', 'non primitive');
    private static greaterThanOrEqualToFieldOperator = new OperatorTypes('greater than or equal to field', 'non primitive');
    private static isTrueOperator = new OperatorTypes('is true');
    private static isFalseOperator = new OperatorTypes('is false');
    private static operatorMap = new Map<string, OperatorTypes[]>();

    constructor(public i18n: I18n) { }

    public static intialize(): void {
        const stringOperator: OperatorTypes[] = new Array<OperatorTypes>();
        stringOperator.push(FilterOperator.equalOperator);
        stringOperator.push(FilterOperator.notEqualOperator);
        stringOperator.push(FilterOperator.containsOperator);
        stringOperator.push(FilterOperator.startsWithOperator);
        stringOperator.push(FilterOperator.endsWithOperator);
        stringOperator.push(FilterOperator.isNullOperator);
        stringOperator.push(FilterOperator.isNotNullOperator);
        stringOperator.push(FilterOperator.matchesOtherFieldOperator);
        stringOperator.push(FilterOperator.differsFromFieldOperator);

        const booleanOperator: OperatorTypes[] = new Array<OperatorTypes>();
        booleanOperator.push(FilterOperator.matchesOtherFieldOperator);
        booleanOperator.push(FilterOperator.differsFromFieldOperator);
        booleanOperator.push(FilterOperator.isTrueOperator);
        booleanOperator.push(FilterOperator.isFalseOperator);

        const numberOperator: OperatorTypes[] = new Array<OperatorTypes>();
        numberOperator.push(FilterOperator.equalOperator);
        numberOperator.push(FilterOperator.notEqualOperator);
        numberOperator.push(FilterOperator.lessThanOperator);
        numberOperator.push(FilterOperator.lessThanOrEqualToOperator);
        numberOperator.push(FilterOperator.lessThanFieldOperator);
        numberOperator.push(FilterOperator.lessThanOrEqualToFieldOperator);
        numberOperator.push(FilterOperator.greaterThanOperator);
        numberOperator.push(FilterOperator.greaterThanOrEqualToOperator);
        numberOperator.push(FilterOperator.greaterThanFieldOperator);
        numberOperator.push(FilterOperator.greaterThanOrEqualToFieldOperator);
        numberOperator.push(FilterOperator.matchesOtherFieldOperator);
        numberOperator.push(FilterOperator.differsFromFieldOperator);

        const conceptOperator: OperatorTypes[] = new Array<OperatorTypes>();
        conceptOperator.push(FilterOperator.equalOperator);
        conceptOperator.push(FilterOperator.notEqualOperator);
        conceptOperator.push(FilterOperator.isNullOperator);
        conceptOperator.push(FilterOperator.isNotNullOperator);
        conceptOperator.push(FilterOperator.matchesOtherFieldOperator);
        conceptOperator.push(FilterOperator.differsFromFieldOperator);

        const dateTimeOperator: OperatorTypes[] = new Array<OperatorTypes>();
        dateTimeOperator.push(FilterOperator.equalOperator);
        dateTimeOperator.push(FilterOperator.notEqualOperator);
        dateTimeOperator.push(FilterOperator.lessThanOperator);
        dateTimeOperator.push(FilterOperator.lessThanOrEqualToOperator);
        dateTimeOperator.push(FilterOperator.lessThanFieldOperator);
        dateTimeOperator.push(FilterOperator.lessThanOrEqualToFieldOperator);
        dateTimeOperator.push(FilterOperator.greaterThanOperator);
        dateTimeOperator.push(FilterOperator.greaterThanOrEqualToOperator);
        dateTimeOperator.push(FilterOperator.greaterThanFieldOperator);
        dateTimeOperator.push(FilterOperator.greaterThanOrEqualToFieldOperator);
        dateTimeOperator.push(FilterOperator.matchesOtherFieldOperator);
        dateTimeOperator.push(FilterOperator.differsFromFieldOperator);
        dateTimeOperator.push(FilterOperator.isNullOperator);
        dateTimeOperator.push(FilterOperator.isNotNullOperator);

        FilterOperator.operatorMap.set('number', numberOperator);
        FilterOperator.operatorMap.set('String', stringOperator);
        FilterOperator.operatorMap.set('boolean', booleanOperator);
        FilterOperator.operatorMap.set('concept', conceptOperator);
        FilterOperator.operatorMap.set('date', dateTimeOperator);
    }

    /**
     * This function provides all the operators related to given data type.
     * @param linkType
     * @return OperatorTypes[]
     */

    public static getOperators(linkType: string): OperatorTypes[] {
        let dataType: string;
        if (linkType === 'int' || linkType === 'long' || linkType === 'double' || linkType === 'float' || linkType === 'integer') {
            dataType = 'number';
        } else if (linkType === 'NA' || linkType.search('concept') !== -1 || linkType.search('event') !== -1 || linkType.search('Concept') !== -1) {
            dataType = 'concept';
        } else if (linkType === 'DateTime') {
            dataType = 'date';
        } else if (linkType === 'boolean') {
            dataType = 'boolean';
        } else {
            dataType = linkType;
        }
        return FilterOperator.operatorMap.get(dataType);
    }

    public static EQUAL_OPERATOR(): string {
        return FilterOperator.equalOperator.operator;
    }

    public static NOT_EQUAL_OPERATOR(): string {
        return FilterOperator.notEqualOperator.operator;
    }

    public static IS_NULL_OPERATOR(): string {
        return FilterOperator.isNullOperator.operator;
    }

    public static IS_NOT_NULL_OPERATOR(): string {
        return FilterOperator.isNotNullOperator.operator;
    }

    public static MATCHES_OTHER_FIELD_OPERATOR(): string {
        return FilterOperator.matchesOtherFieldOperator.operator;
    }

    public static DIFFERS_FROM_FIELD_OPERATOR(): string {
        return FilterOperator.differsFromFieldOperator.operator;
    }

    public static CONTAINS_OPERATOR(): string {
        return FilterOperator.containsOperator.operator;
    }

    public static STARTS_WITH_OPERATOR(): string {
        return FilterOperator.startsWithOperator.operator;
    }

    public static ENDS_WITH_OPERATOR(): string {
        return FilterOperator.endsWithOperator.operator;
    }

    public static LESS_THAN_OPERATOR(): string {
        return FilterOperator.lessThanOperator.operator;
    }

    public static LESS_THAN_FIELD_OPERATOR(): string {
        return FilterOperator.lessThanFieldOperator.operator;
    }

    public static LESS_THAN_OR_EQUAL_TO_OPERATOR(): string {
        return FilterOperator.lessThanOrEqualToOperator.operator;
    }

    public static LESS_THAN_OR_EQUAL_TO_FIELD_OPERATOR(): string {
        return FilterOperator.lessThanOrEqualToFieldOperator.operator;
    }

    public static GREATER_THAN_OPERATOR(): string {
        return FilterOperator.greaterThanOperator.operator;
    }

    public static GREATER_THAN_FIELD_OPERATOR(): string {
        return FilterOperator.greaterThanFieldOperator.operator;
    }

    public static GREATER_THAN_OR_EQUAL_TO_OPERATOR(): string {
        return FilterOperator.greaterThanOrEqualToOperator.operator;
    }

    public static GREATER_THAN_OR_EQUAL_TO_FIELD_OPERATOR(): string {
        return FilterOperator.greaterThanOrEqualToFieldOperator.operator;
    }

    public static IS_TRUE(): string {
        return FilterOperator.isTrueOperator.operator;
    }

    public static IS_FALSE(): string {
        return FilterOperator.isFalseOperator.operator;
    }

    /**
     * This api fetches operator instance from opertor name.
     * @param operatorName
     * @return OperatorTypes
     */

    public static getOperator(operator: string): OperatorTypes {
        switch (operator) {
            case FilterOperator.EQUAL_OPERATOR(): return FilterOperator.equalOperator;
            case FilterOperator.NOT_EQUAL_OPERATOR(): return FilterOperator.notEqualOperator;
            case FilterOperator.IS_NULL_OPERATOR(): return FilterOperator.isNullOperator;
            case FilterOperator.IS_NOT_NULL_OPERATOR(): return FilterOperator.isNotNullOperator;
            case FilterOperator.MATCHES_OTHER_FIELD_OPERATOR(): return FilterOperator.matchesOtherFieldOperator;
            case FilterOperator.DIFFERS_FROM_FIELD_OPERATOR(): return FilterOperator.differsFromFieldOperator;
            case FilterOperator.CONTAINS_OPERATOR(): return FilterOperator.containsOperator;
            case FilterOperator.STARTS_WITH_OPERATOR(): return FilterOperator.startsWithOperator;
            case FilterOperator.ENDS_WITH_OPERATOR(): return FilterOperator.endsWithOperator;
            case FilterOperator.LESS_THAN_OPERATOR(): return FilterOperator.lessThanOperator;
            case FilterOperator.GREATER_THAN_OPERATOR(): return FilterOperator.greaterThanOperator;
            case FilterOperator.LESS_THAN_FIELD_OPERATOR(): return FilterOperator.lessThanFieldOperator;
            case FilterOperator.GREATER_THAN_FIELD_OPERATOR(): return FilterOperator.greaterThanFieldOperator;
            case FilterOperator.LESS_THAN_OR_EQUAL_TO_OPERATOR(): return FilterOperator.lessThanOrEqualToOperator;
            case FilterOperator.GREATER_THAN_OR_EQUAL_TO_OPERATOR(): return FilterOperator.greaterThanOrEqualToOperator;
            case FilterOperator.LESS_THAN_OR_EQUAL_TO_FIELD_OPERATOR(): return FilterOperator.lessThanOrEqualToFieldOperator;
            case FilterOperator.GREATER_THAN_OR_EQUAL_TO_FIELD_OPERATOR(): return FilterOperator.greaterThanOrEqualToFieldOperator;
            case FilterOperator.IS_TRUE(): return FilterOperator.isTrueOperator;
            case FilterOperator.IS_FALSE(): return FilterOperator.isFalseOperator;
        }
    }
}
