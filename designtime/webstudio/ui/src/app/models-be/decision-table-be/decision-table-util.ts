export class BEDecisionTableUtil {

    static isSubstitutionField(columnName: string): boolean {

        const regExpCondPattern = new RegExp('(.*)[(!=)><(==)\\|\\|\\&\\&][(!=)><(==)\\|\\|\\&\\&]?(.*)\\{(.*)\\}(.*)', 'g');

        const result1 = regExpCondPattern.exec(columnName);

        const regExpCondPatternBefore = new RegExp('(.*)\\{(.*)\\}(.*)[(!=)><(==)\\|\\|\\&\\&][(!=)><(==)\\|\\|\\&\\&]?(.*)', 'g');

        const result2 = regExpCondPatternBefore.exec(columnName);

        const regExpActPattern = new RegExp('(.*)[^><]=(.*)\\{(.*)\\}(.*)', 'g');

        const result3 = regExpActPattern.exec(columnName);

        const regExpActPatternBefore = new RegExp('(.*)\\{(.*)\\}(.*)[^><]=(.*)', 'g');

        const result4 = regExpActPatternBefore.exec(columnName);

        if (result1 != null) {
            return true;
        } else if (result2 != null) {
            return true;
        } else if (result3 != null) {
            return true;
        } else if (result4 != null) {
            return true;
        } else {
            return false;
        }

    }

    static getFormattedString(columnName: string, variable: string): string {

        columnName = this.unEscapeColumnSubstitutionExpression(columnName);

        const variableStringOrig: string = columnName;

        const regExp = new RegExp('{([0-9])*}', 'g');

        let result = regExp.exec(columnName);

        const patterns: Array<number> = new Array<number>();

        while (result != null) {
            const count = result.length;
            if (count === 2) {
                const position = Number(result[1]);
                patterns.push(position);
            }
            result = regExp.exec(columnName);
        }

        const arguments1 = this.getArguments(columnName, variable, patterns);

        let sb = '';
        let matchesPattern = true;
        let formattedExpr = columnName;
        if (arguments1.length === patterns.length) {
            for (const position of patterns) {
                const escapedArgument = arguments1[position].replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, '\\$&');
                formattedExpr = formattedExpr.replace('{' + position + '}', escapedArgument);
            }
            sb = sb + formattedExpr;
        } else {
            matchesPattern = false;
        }
        if (!matchesPattern) {
            // If formats do not match no substitution to be done.
            sb = sb + variableStringOrig;
        }

        return sb;
    }

    static unEscapeColumnSubstitutionExpression(columnName: string): string {
        columnName = columnName.replace('&gt;', '>');
        columnName = columnName.replace('&lt;', '<');
        columnName = columnName.replace('&gte;', '>=');
        columnName = columnName.replace('&lte;', '<=');
        columnName = columnName.replace('&ne;', '<>');
        return columnName;
    }

    static getArguments(text: string, value: string, patterns: number[]): string[] {

        const textPartsList: Array<string> = new Array<string>();

        let textRemainingPart = text.trim();

        for (const position of patterns) {

            const textParts = textRemainingPart.split('{' + position + '}');

            if (textParts.length === 0) {
                textPartsList.push('{' + position + '}');
                textRemainingPart = '';
            } else if (textParts.length === 1) {
                if (textParts[0] != null && !(textParts[0].trim().length === 0)) {
                    textPartsList.push(textParts[0].trim());
                }
                textPartsList.push('{' + position + '}');
                textRemainingPart = '';
            } else if (textParts.length === 2) {
                if (textParts[0] != null && !(textParts[0].trim().length === 0)) {
                    textPartsList.push(textParts[0].trim());
                }
                textPartsList.push('{' + position + '}');
                if (textParts[1] != null && !(textParts[1].trim().length === 0)) {
                    textRemainingPart = textParts[1].trim();
                }
            }
        }

        if (!(textRemainingPart.length === 0)) {
            textPartsList.push(textRemainingPart);
        }

        const valuePartsList: Array<string> = new Array<string>();

        let valuePart = value.trim();

        for (const textPart of textPartsList) {

            if (!(textPart.charAt(0) !== '{')) {
                const valueParts = valuePart.split(textPart.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, '\\$&'), 2);
                if (valueParts.length === 0) {
                    valuePartsList.push(textPart);
                    valuePart = '';
                } else if (valueParts.length === 1) {
                    if (valueParts[0] != null && !(valueParts[0].trim().length === 0)) {
                        valuePartsList.push(valueParts[0].trim());
                    }
                    valuePartsList.push(textPart);
                    valuePart = '';
                } else if (valueParts.length === 2) {
                    if (valueParts[0] != null && !(valueParts[0].trim().length === 0)) {
                        valuePartsList.push(valueParts[0].trim());
                    }
                    valuePartsList.push(textPart);
                    if (valueParts[1] != null && !(valueParts[1].trim().length === 0)) {
                        valuePart = valueParts[1].trim();
                    } else {
                        valuePart = '';
                    }
                }
            }
        }

        if (!(valuePart.length === 0)) {
            valuePartsList.push(valuePart);
        }

        let alreadySubstituted = false;
        const existingArgs: Array<string> = new Array<string>();
        if (textPartsList.length === valuePartsList.length) {
            alreadySubstituted = true;
            let counter = 0;
            for (const textP of textPartsList) {
                if (textP.charAt(0) === '{') {
                    existingArgs.push(valuePartsList[counter]);
                } else if (!(textPartsList[counter] === valuePartsList[counter])) {
                    alreadySubstituted = false;
                    break;
                }

                counter = counter + 1;
            }
        }
        if (alreadySubstituted) {
            return existingArgs;
        } else {
            return value.split(';');
        }

    }
}
