/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T09:59:46+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-05-04T15:39:13+05:30
 */

import { ActionsImpl } from '../../editors/rule-template-instance-builder/ActionsImpl';
import { BuilderSubClauseImpl } from '../../editors/rule-template-instance-builder/BuilderSubClauseImpl';
import { CommandImpl } from '../../editors/rule-template-instance-builder/CommandImpl';
import { CommandOperator } from '../../editors/rule-template-instance-builder/CommandOperators';
import { EmptyFilterValueImpl } from '../../editors/rule-template-instance-builder/EmptyFilterValueImpl';
import { Filter } from '../../editors/rule-template-instance-builder/Filter';
import { FilterOperator } from '../../editors/rule-template-instance-builder/FilterOperators';
import { FilterValue } from '../../editors/rule-template-instance-builder/FilterValue';
import { MultiFilterImpl } from '../../editors/rule-template-instance-builder/MultiFilterImpl';
import { RangeFilterValueImpl } from '../../editors/rule-template-instance-builder/RangeFilterValueImpl';
import { RelatedFilterValueImpl } from '../../editors/rule-template-instance-builder/RelatedFilterValueImpl';
import { RelatedLinkImpl } from '../../editors/rule-template-instance-builder/RelatedLinkImpl';
import { SimpleFilterValueImpl } from '../../editors/rule-template-instance-builder/SimpleFilterValueImpl';
import { SingleFilterImpl } from '../../editors/rule-template-instance-builder/SingleFilterImpl';
import { SymbolInfo } from '../../editors/rule-template-instance-builder/SymbolInfo';
import { BindingInfo } from '../../editors/rule-template-instance-view/BindingInfo';
import { DomainInfo } from '../../editors/rule-template-instance-view/DomainInfo';
import { ViewInfo } from '../../editors/rule-template-instance-view/ViewInfo';

export class RuleTemplateInstance {

  static deserialize(jsonString: string): RuleTemplateInstanceImpl {
    let ruleTemplateInstanceImpl: RuleTemplateInstanceImpl = new RuleTemplateInstanceImpl();
    const jsonObject = JSON.parse(jsonString);
    try {
      ruleTemplateInstanceImpl = RuleTemplateInstance.fromJsonObject(jsonObject);
    } catch (error) {
      // console.log(error);
    }
    return ruleTemplateInstanceImpl;
  }

  static serialize(ruleTemplateInstanceImpl: RuleTemplateInstanceImpl) {
    return JSON.stringify(RuleTemplateInstance.toJSONObject(ruleTemplateInstanceImpl));
  }

  static fromJsonObject(jsonObject): RuleTemplateInstanceImpl {
    const ruleTemplateInstanceImpl: RuleTemplateInstanceImpl = new RuleTemplateInstanceImpl();
    ruleTemplateInstanceImpl.setImplementsPath(jsonObject.implementsPath);

    // add description
    if (jsonObject.description) {
      ruleTemplateInstanceImpl.setDescription(jsonObject.description);
    } else {
      ruleTemplateInstanceImpl.setDescription('');
    }

    // add priority
    if (jsonObject.rulePriority) {
      ruleTemplateInstanceImpl.setRulepriority(jsonObject.rulePriority);
    }
    if (jsonObject.view) {
      ruleTemplateInstanceImpl.setView(RuleTemplateInstance.getViewInfo(jsonObject.view));
    } else {
      if (jsonObject.symbols) {
        ruleTemplateInstanceImpl.setSymbols(RuleTemplateInstance.getSymbols(jsonObject.symbols));
      }
      if (jsonObject.conditions) {
        ruleTemplateInstanceImpl.setConditions(RuleTemplateInstance.getConditionFilter(jsonObject.conditions));
      }
      if (jsonObject.commands) {
        ruleTemplateInstanceImpl.getCommands().setActions(RuleTemplateInstance.getCommandInfo(jsonObject.commands, ruleTemplateInstanceImpl));
      }
    }
    return ruleTemplateInstanceImpl;
  }

  /********************************** Deserialize symbol info **********************************/
  /**
   * Retrieve Symbols
   *
   * @param jsonObject
   * @return
   */
  public static getSymbols(jsonObject): Array<SymbolInfo> {
    const symbols: Array<SymbolInfo> = new Array<SymbolInfo>();
    const symbolInfoList = jsonObject.symbolInfo;
    if (symbolInfoList
      && symbolInfoList.length > 0) {
      for (let i = 0; i < symbolInfoList.length; i++) {
        symbols.push(RuleTemplateInstance.parseSymbolInfo(symbolInfoList[i]));
      }
    }
    return symbols;
  }

  /**
   * Retrieve Symbol Info objects
   *
   * @param symbolInfo
   * @return
   */
  public static parseSymbolInfo(symbolInfo): SymbolInfo {
    const symbol: SymbolInfo = new SymbolInfo();
    for (const i in symbolInfo) {
      switch (i) {
        case 'symbolAlias': symbol.setSymbolAlias(symbolInfo[i]);
          break;
        case 'type': symbol.setType(symbolInfo[i]);
          break;
        case 'domainInfo': const domainInfo = RuleTemplateInstance.parseDomainInfo(symbolInfo[i]);
          symbol.setDomainInfo(domainInfo);
          break;
        case 'symbolInfo':
          if (symbol.getSymbolInfo() == null) {
            symbol.setSymbolInfo(new Array<SymbolInfo>());
          }
          for (let j = 0; j < symbolInfo[i].length; j++) {
            symbol.getSymbolInfo().push(RuleTemplateInstance.parseSymbolInfo(symbolInfo[i][j]));
          }

          break;
      }
    }
    return symbol;
  }

  /*****************************************************************************/
  /**
   * Retrieve data for single filter
   *
   * @param filter
   * @return
   */
  public static getSingleFilter(filter): SingleFilterImpl {
    const singleFilter: SingleFilterImpl = new SingleFilterImpl();
    for (const i in filter) {
      if (JSON.stringify(filter[i]).length > 0) {
        const nodeName: string = i;
        const nodeValue: string = nodeName === 'operator' || nodeName === 'filterId' ? filter[i] : '';

        if (nodeName === 'operator') { singleFilter.setOperator(nodeValue); } else if (nodeName === 'filterId') { singleFilter.setFilterId(nodeValue); } else if (nodeName === 'value') {
          singleFilter.setValue(RuleTemplateInstance.getFilterValue(filter[i]));
        } else if (nodeName === 'link') {
          const linkDetails = filter[i];
          let relatedLink: RelatedLinkImpl;
          for (let j = 0; j < linkDetails.length; j++) {
            relatedLink = new RelatedLinkImpl();
            for (const k in linkDetails[j]) {
              if (JSON.stringify(linkDetails[j][k]).length > 0) {
                if (k === 'name') {
                  relatedLink.setName(linkDetails[j][k]);
                } else {
                  relatedLink.setType(linkDetails[j][k]);
                }
              }
            }
            singleFilter.addRelatedLink(relatedLink);
          }
        }
      }
    }
    if (singleFilter.getOperator() != null) {
      if ((singleFilter.getOperator().trim() !== CommandOperator.SET_TO_FALSE()
        && singleFilter.getOperator().trim() !== CommandOperator.SET_TO_TRUE()
        && singleFilter.getOperator().trim() !== CommandOperator.SET_TO_NULL()
        && singleFilter.getOperator().trim() !== FilterOperator.IS_FALSE()
        && singleFilter.getOperator().trim() !== FilterOperator.IS_TRUE()
        && singleFilter.getOperator().trim() !== FilterOperator.IS_NULL_OPERATOR()
        && singleFilter.getOperator().trim() !== FilterOperator.IS_NOT_NULL_OPERATOR())
        && singleFilter.getValue() instanceof EmptyFilterValueImpl) {
        singleFilter.setValue(new SimpleFilterValueImpl());
      }
    }
    return singleFilter;
  }

 /**
	 * Retrieve data for filter value
	 *
	 * @param filterValue
	 * @return
	 */
  public static getFilterValue(filterValue): FilterValue {
    let newFilterValue: FilterValue = null;
    if (JSON.stringify(filterValue).length > 0) {
      for (const i in filterValue) {
        if (JSON.stringify(filterValue[i]).length > 0) {
          const nodeName: string = i;
          if (nodeName === 'minValue') {
            if (newFilterValue == null) {
              newFilterValue = new RangeFilterValueImpl();
            }
            (<RangeFilterValueImpl>newFilterValue).setMinValue(filterValue[i]);
          } else if (nodeName === 'maxValue') {
            (<RangeFilterValueImpl>newFilterValue).setMaxValue(filterValue[i]);
          } else if (nodeName === 'link') {
            if (newFilterValue == null) {
              newFilterValue = new RelatedFilterValueImpl();
            }
            const linkDetails = filterValue[i];
            let relatedLink: RelatedLinkImpl;
            for (const j in linkDetails) {
              if (JSON.stringify(linkDetails[j]).length > 0) {
                relatedLink = new RelatedLinkImpl();
                relatedLink.setName(linkDetails[j]['name']);
                relatedLink.setType(linkDetails[j]['type']);
                (<RelatedFilterValueImpl>newFilterValue).addLink(relatedLink);
              }
            }
          } else if (nodeName === 'simple') {
            let nodeValue = '';
            if (filterValue[i]) {
              nodeValue = filterValue[i];
            }
            newFilterValue = new SimpleFilterValueImpl();
            (<SimpleFilterValueImpl>newFilterValue).value = nodeValue;
          }
        }
      }
    }
    if (newFilterValue == null) {
      newFilterValue = new EmptyFilterValueImpl();
    }
    return newFilterValue;
  }

  /**
   * Retrieve the data for condition filter
   *
   * @param conditionElement
   * @return
   */
  public static getConditionFilter(conditionElement): MultiFilterImpl {
    let baseFilter = null;
    if (conditionElement) {
      for (const i in conditionElement) {
        if (conditionElement[i] && conditionElement[i].length > 0) {
          baseFilter = conditionElement[i];
          break;
        }
      }
    }
    return RuleTemplateInstance.getMultiFilter(baseFilter[0]);
  }

  /**
   * Retrieve data on commands
   *
   * @param docElement
   * @return
   */
  public static getCommandInfo(jsonObject, selectedRuleTemplateInstance: RuleTemplateInstanceImpl): Array<CommandImpl> {

    const commandList: Array<CommandImpl> = new Array<CommandImpl>();
    let bAddFilter = false;
    const command = jsonObject.commandInfo;
    for (const i in command) {
      const commandInfoDetails = command[i];
      bAddFilter = false;
      const cInfo: CommandImpl = new CommandImpl();
      for (const j in commandInfoDetails) {
        if (JSON.stringify(commandInfoDetails[j]).length > 0) {
          const nodeName: string = j;
          const nodeValue: string = (nodeName === 'filter' || nodeName === 'symbols') ? '' : commandInfoDetails[j];

          if (nodeName === 'commandAlias') { cInfo.setAlias(nodeValue); } else if (nodeName === 'type') { cInfo.setType(nodeValue); } else if (nodeName === 'actionType') { cInfo.setActionType(nodeValue); } else if (nodeName === 'filter') {
            if (cInfo.getFilter() == null) {
              cInfo.setFilter(new BuilderSubClauseImpl());
            }
            for (let index = 0; index < commandInfoDetails[j].length; index++) {
              cInfo.getFilter().addFilter(RuleTemplateInstance.getSingleFilter(commandInfoDetails[j][index]));
            }
            bAddFilter = true;
          } else if (nodeName === 'symbols') {
            let symbols: Array<SymbolInfo> = new Array<SymbolInfo>();
            let symbolInfoList = commandInfoDetails[j];
            for (const l in symbolInfoList) {
              if (JSON.stringify(symbolInfoList[l]).length > 0) {
                symbolInfoList = symbolInfoList[l];
                break;
              }
            }
            for (let k = 0; k < symbolInfoList.length; k++) {
              if (Object.keys(symbolInfoList[k]).length > 0 && symbolInfoList[k].symbolInfo.length > 0) {
                symbols = RuleTemplateInstance.getSymbols(symbolInfoList[k]);
              }
            }
            const commandInfoSymbols = new SymbolInfo();
            commandInfoSymbols.setType(cInfo.getType());
            commandInfoSymbols.setSymbolInfo(symbols);
            commandInfoSymbols.setSymbolAlias(cInfo.getAlias());
            cInfo.setSymbols(commandInfoSymbols);
          }
        }
      }
      if (cInfo.getType()) {
        commandList.push(cInfo);
      }
      // if (bAddFilter && selectedRuleTemplateInstance != null) selectedRuleTemplateInstance.getCommands().addCommand(cInfo);
    }
    return commandList;
  }

  /**************************************************RTI View Parsing***********************************************************/
  public static getViewInfo(jsonObject): ViewInfo {
    const viewInfo: ViewInfo = new ViewInfo();
    let styles: Map<string, string> = new Map<string, string>();
    let script: string;
    if (jsonObject.htmlText) {
      const html: string = jsonObject.htmlText;
      viewInfo.setHtmlText(html);
      styles = RuleTemplateInstance.fetchCSS(html);
      script = RuleTemplateInstance.fetchScript(html);
      if (script) {
        viewInfo.setScript(script);
      }
    }
    const bindingInfoList: Array<BindingInfo> = new Array<BindingInfo>();
    const bindingList = jsonObject.bindingInfo;
    for (let i = 0; i < bindingList.length; i++) {
      const bindingElements = bindingList[i];
      let bindingId: string = null;
      const bindingInfo: BindingInfo = new BindingInfo();
      for (const j in bindingElements) {
        if (JSON.stringify(bindingElements[j]).length > 0) {
          let nodeValue: string = null;
          const nodeName: string = j;
          if (nodeName !== 'domainInfo'
            && bindingElements[j] != null) {
            nodeValue = bindingElements[j];
          }
          if (nodeName === 'bindingId') {
            bindingInfo.setBindingId(nodeValue);
            bindingId = nodeValue;
          } else if (nodeName === 'type') {
            bindingInfo.setType(nodeValue);
          } else if (nodeName === 'value') {
            bindingInfo.setValue(nodeValue);
          } else if (nodeName === 'domainInfo'
            && JSON.stringify(bindingElements[j]).length > 0) {
            bindingInfo.setDomainInfo(RuleTemplateInstance.parseDomainInfo(bindingElements[j]));
          }
        }
      }
      if (styles && styles.has(bindingId) && styles.get(bindingId)) {
        bindingInfo.setStyle(styles.get(bindingId));
      }
      bindingInfoList.push(bindingInfo);
    }
    viewInfo.setBindingInfo(bindingInfoList);
    return viewInfo;
  }

  static toJSONObject(ruleTemplateInstanceImpl: RuleTemplateInstanceImpl): any {

    const JSONObject = JSON.parse('{}');
    JSONObject.implementsPath = ruleTemplateInstanceImpl.getImplementsPath();
    JSONObject.description = ruleTemplateInstanceImpl.getDescription();
    JSONObject.rulePriority = ruleTemplateInstanceImpl.getRulepriortiy();
    JSONObject.isSyncMerge = ruleTemplateInstanceImpl.getisSyncMerge();

    if (ruleTemplateInstanceImpl.getView()
      && ruleTemplateInstanceImpl.getView().getBindingInfo().length > 0) {
      JSONObject.view = ruleTemplateInstanceImpl.getView();
    } else {
      JSONObject['conditions'] = JSON.parse('{}');
      JSONObject.conditions = RuleTemplateInstance.createConditions(JSONObject.conditions, ruleTemplateInstanceImpl);

      JSONObject['symbols'] = JSON.parse('{}');
      JSONObject.symbols = RuleTemplateInstance.createSymbols(ruleTemplateInstanceImpl.getSymbols());

      JSONObject['commands'] = JSON.parse('{}');
      JSONObject.commands = RuleTemplateInstance.createCommands(ruleTemplateInstanceImpl);
    }
    return JSONObject;
  }

  private static checkIfBindingTypeIsDateTime(bindingInfoList: Array<BindingInfo>, bindingId: string): boolean {
    for (let i = 0; i < bindingInfoList.length; i++) {
      const bindingInfo: BindingInfo = bindingInfoList[i];
      if (bindingInfo.getBindingId() === bindingId) {
        return new RegExp('DateTime', 'i').test(bindingInfo.getType());
      }
    }
    return false;
  }

  private static getUniqueFilterId(): string {
    return String(new Date().getTime() + Math.random() * 1000 + Math.random() * 1000 + Math.random() * 1000);
  }

  private static formatDateValue(dateValue: string) {
    // TODO implement this function for modifying time zone.
    return dateValue;
  }

  private static systemPropertyReplacer(key: string, value: string): any {
    if (key === 'attributes') { return undefined; }
    return value;
  }

  private static parseDomainInfo(domainNodeList): DomainInfo {
    if (domainNodeList instanceof Array) {
      domainNodeList = domainNodeList[0];
    }
    const domainInfo: DomainInfo = new DomainInfo();
    for (const k in domainNodeList) {
      if (k === 'name') {
        domainInfo.setName(domainNodeList[k]);
      } else if (k === 'folder') {
        domainInfo.setFolder(domainNodeList[k]);
      } else if (k === 'ownerProjectName') {
        domainInfo.setOwnerProjectName(domainNodeList[k]);
      } else if (k === 'dataType') {
        domainInfo.setType(domainNodeList[k]);
      } else if (k === 'singleEntry') {
        for (let i = 0; i < domainNodeList[k].length; i++) {
          domainInfo.addValue(domainNodeList[k][i]['value'], domainNodeList[k][i]['description']);
        }
      }
    }
    return domainInfo;
  }

  /**
   * Retrieve data for Multi Filter
   *
   * @param filter
   * @return
   */
  private static getMultiFilter(filter): MultiFilterImpl {
    const multiFilter: MultiFilterImpl = new MultiFilterImpl();
    if (filter != null) {
      for (const i in filter) {
        if (JSON.stringify(filter[i]).length > 0) {
          if (i === 'filterId') {
            multiFilter.setFilterId(filter[i]);
          } else if (i === 'matchType') {
            multiFilter.setMatchType(filter[i]);
          } else {
            const builderSubClause: BuilderSubClauseImpl = multiFilter.getBuilderSubClause();
            let newFilter: Filter = null;
            for (let j = 0; j < filter[i].length; j++) {
              if (RuleTemplateInstance.isMultiFilter(filter[i][j])) {
                newFilter = RuleTemplateInstance.getMultiFilter(filter[i][j]);
              } else {
                newFilter = RuleTemplateInstance.getSingleFilter(filter[i][j]);
              }
              builderSubClause.addFilter(newFilter);
            }
          }
        }
      }
    }
    return multiFilter;
  }

  /**
   * Check filter type
   *
   * @param childNodes
   * @return
   */
  private static isMultiFilter(childNode): boolean {
    let isMulti = false;
    for (const i in childNode) {
      if (i === 'matchType') {
        isMulti = true;
        break;
      }
    }
    return isMulti;
  }

  /**
 	 * Fetch the CSS style from string containing binding tag.
 	 * It will return the key value pair of binding-id and respective CSS styles.
   * Assumption : Each binding should have at least 2 attributes i.e bindingId and
   * style and style is second. If there is less than 2, the style is set to null.
   * The attributes key-value pair is seperated by '=' (equal operator).
 	 * @param html
 	 * @return styles
 	 */
  private static fetchCSS(html: string): Map<string, string> {
    const styles: Map<string, string> = new Map<string, string>();
    const bindingStartSplit: Array<string> = html.split('<binding');
    for (let i = 1; i < bindingStartSplit.length; i++) {

      const bindingEndSplit: Array<string> = bindingStartSplit[i].split('></binding')[0].split(',');
      if (bindingEndSplit.length === 2) {
        styles.set(bindingEndSplit[0].split('=')[1].replace(/"/g, '').trim(),
          bindingEndSplit[1].split('=')[1].replace(/"/g, '').trim());
      } else {
        styles.set(bindingEndSplit[0].split('=')[1].replace(/"/g, '').trim(), null);
      }
    }
    return styles;
  }

  /**
   * Fetch java script from the html text.
   * @param html
   * @return scriptBody
   * indexOf gives the starting position of the substring.
   * The length of starting script tag is 8.
   * So the script body would start from 9 onwards till the index of
   * end scipt tag.
   */
  private static fetchScript(html: string): string {
    const startingIndex: number = html.indexOf('<script>');
    const endingIndex: number = html.indexOf('</script>');
    if (startingIndex >= 0 && endingIndex > startingIndex + 8) {
      return html.substring(startingIndex + 8, endingIndex);
    }
    return null;
  }

  private static createConditions(jsonObject, ruleTemplateInstanceImpl: RuleTemplateInstanceImpl): any {
    jsonObject['filter'] = new Array();
    jsonObject['filter'].push(RuleTemplateInstance
      .createMultiFilter(ruleTemplateInstanceImpl.getConditions()));
    return jsonObject;
  }

  private static createMultiFilter(multiFilter: MultiFilterImpl): any {

    const multiFilterJsonObject = JSON.parse('{}');
    multiFilterJsonObject['filter'] = new Array();
    if (multiFilter.getFilterId() == null) {
      multiFilter.setFilterId(RuleTemplateInstance.getUniqueFilterId());
    }
    multiFilterJsonObject['filterId'] = multiFilter.getFilterId();
    multiFilterJsonObject['matchType'] = multiFilter.getMatchType();
    for (const filter of <Array<Filter>>multiFilter.getBuilderSubClause().getFilters()) {
      if (filter instanceof MultiFilterImpl) {
        multiFilterJsonObject.filter.push(RuleTemplateInstance.createMultiFilter(<MultiFilterImpl>filter));
      } else if (filter instanceof SingleFilterImpl) {
        multiFilterJsonObject.filter.push(RuleTemplateInstance.createSingleFilter(<SingleFilterImpl>filter));
      }
    }
    return multiFilterJsonObject;
  }

  private static createSingleFilter(filter: SingleFilterImpl): any {

    const singleFilterJsonObject = JSON.parse('{}');
    singleFilterJsonObject['link'] = RuleTemplateInstance.createLinks(filter.getLink());
    if (filter.getFilterId() == null) {
      filter.setFilterId(RuleTemplateInstance.getUniqueFilterId());
    }
    singleFilterJsonObject['filterId'] = filter.getFilterId();
    singleFilterJsonObject['operator'] = filter.getOperator() == null ? '' : filter.getOperator();
    singleFilterJsonObject['value'] = RuleTemplateInstance.createFilterValue(filter.getValue(), RuleTemplateInstance.checkIfLinkTypeIsDateTime(filter.getLink()));
    return singleFilterJsonObject;
  }

  private static checkIfLinkTypeIsDateTime(links: Array<RelatedLinkImpl>): boolean {
    const lastLink: RelatedLinkImpl = links[links.length - 1];
    let linkType: string = lastLink.getType();
    if (linkType.indexOf('.') !== -1) {
      linkType = linkType.substring(0, linkType.indexOf('.'));
    }
    if (linkType != null
      && linkType
      && new RegExp('DateTime', 'i').test(linkType)) {
      return true;
    } else {
      return false;
    }
  }

  private static createFilterValue(filterValue: FilterValue, isLinkTypeDateTime: boolean): any {
    const filterValueJson = JSON.parse('{}');
    if (filterValue instanceof SimpleFilterValueImpl) {
      let filterData: string = ((<SimpleFilterValueImpl>filterValue).value == null) ? '' : (<SimpleFilterValueImpl>filterValue).value;
      if (filterData && isLinkTypeDateTime) {
        filterData = RuleTemplateInstance.formatDateValue(filterData);
      }
      filterValueJson['simple'] = filterData;
    } else if (filterValue instanceof RangeFilterValueImpl) {
      filterValueJson['minValue'] = (<RangeFilterValueImpl>filterValue).getMinValue();
      filterValueJson['maxValue'] = (<RangeFilterValueImpl>filterValue).getMaxValue();
    } else if (filterValue instanceof RelatedFilterValueImpl) {
      filterValueJson['link'] = RuleTemplateInstance.createLinks((<RelatedFilterValueImpl>filterValue).getLinks());
    } else if (filterValue instanceof EmptyFilterValueImpl) {
      filterValueJson['simple'] = null;
    }
    return filterValueJson;
  }

  private static createLinks(links: Array<RelatedLinkImpl>): any {
    const linksJSON = new Array();
    for (let i = 0; i < links.length; i++) {
      const link: RelatedLinkImpl = links[i];
      const linkJSON = JSON.parse('{}');
      linkJSON['name'] = link.getName();
      linkJSON['type'] = link.getType();
      linksJSON.push(linkJSON);
    }
    return linksJSON;
  }

  private static createSymbols(symbols: Array<SymbolInfo>) {
    const symbolsJson = JSON.parse('{}');
    symbolsJson['symbolInfo'] = new Array();
    RuleTemplateInstance.creteContainedSymbols(symbols, symbolsJson.symbolInfo);
    return symbolsJson;
  }

  private static creteContainedSymbols(containedSymbols: Array<SymbolInfo>, parentSymbolInfo): any {
    let symbolInfoJson;
    for (const baseSymbol of containedSymbols) {
      if (baseSymbol.getSymbolAlias()) {
        symbolInfoJson = JSON.parse('{}');
        symbolInfoJson['domainInfo'] = RuleTemplateInstance.createDomainInfo(baseSymbol.getDomainInfo());
        symbolInfoJson['visited'] = false;
        symbolInfoJson['type'] = baseSymbol.getType();
        symbolInfoJson['symbolAlias'] = baseSymbol.getSymbolAlias();
        if (baseSymbol.getSymbolInfo() != null
          && baseSymbol.getSymbolInfo().length > 0) {
          symbolInfoJson['symbolInfo'] = new Array();
          RuleTemplateInstance.creteContainedSymbols(baseSymbol.getSymbolInfo(),
            symbolInfoJson.symbolInfo);
        }
        parentSymbolInfo.push(symbolInfoJson);
      }
    }
    return symbolInfoJson;
  }

  private static createDomainInfo(domainInfo: DomainInfo): any {
    let domainInfoJson;
    if (domainInfo) {
      const singleEntryJsonArray = [];
      const singleEntryArray = domainInfo.getValues();
      for (let i = 0; i < singleEntryArray.length; i++) {
        const singleEntryJson = {
          value: singleEntryArray[i].getValue(),
          description: singleEntryArray[i].getDescription()
        };
        singleEntryJsonArray.push(singleEntryJson);
      }
      domainInfoJson = {
        description: domainInfo.getDescription(),
        folder: domainInfo.getFolder(),
        name: domainInfo.getName(),
        ownerProjectName: domainInfo.getOwnerProjectName(),
        singleEntry: singleEntryJsonArray,
        type: domainInfo.getType()
      };
    }
    return domainInfoJson;
  }

  private static createCommands(ruleTemplateInstanceImpl: RuleTemplateInstanceImpl): any {

    const addedCommands: Array<CommandImpl> = <Array<CommandImpl>>ruleTemplateInstanceImpl.getCommands().getActions();
    const commandJson = JSON.parse('{}');
    commandJson['commandInfo'] = new Array();

    for (const basecommand of ruleTemplateInstanceImpl.getCommands().getActions()) {
      const commandIndex: number = addedCommands.indexOf(basecommand);
      if (commandIndex !== -1) {
        const command: CommandImpl = addedCommands[commandIndex];
        if (command != null) {
          const commandInfoJson = JSON.parse('{}');
          commandInfoJson['commandAlias'] = command.getAlias();
          commandInfoJson['type'] = command.getType();
          commandInfoJson['actionType'] = command.getActionType();
          if (command.getSymbols() && command.getSymbols().getSymbolInfo()) {
            commandInfoJson['symbols'] = {
              symbolInfo: [RuleTemplateInstance.createSymbols(command.getSymbols().getSymbolInfo())]
            };
          }
          if (command.getFilter() != null) {
            commandInfoJson['filter'] = new Array();
            for (const filter of <Array<Filter>>command.getFilter().getFilters()) {
              commandInfoJson['filter'].push(RuleTemplateInstance.createSingleFilter(<SingleFilterImpl>filter));
            }
          }
          commandJson.commandInfo.push(commandInfoJson);
        }
      }
    }
    return commandJson;
  }
}

export class RuleTemplateInstanceImpl {

  private commands?: ActionsImpl = new ActionsImpl();
  private conditions?: MultiFilterImpl = new MultiFilterImpl();
  private description: string = undefined;
  private implementsPath: string = undefined;
  private rulePriority = 0;
  private symbols?: Array<SymbolInfo> = new Array<SymbolInfo>();
  private view?: ViewInfo = new ViewInfo();
  private isSyncMerge?: string;

  getImplementsPath(): string {
    return this.implementsPath;
  }

  setImplementsPath(value: string) {
    this.implementsPath = value;
  }

  getisSyncMerge(): string {
    return this.isSyncMerge;
  }

  setisSyncMerge(value: string) {
    this.isSyncMerge = value;
  }

  getDescription(): string {
    return this.description;
  }

  setDescription(value: string) {
    this.description = value;
  }

  getRulepriortiy(): number {
    return this.rulePriority;
  }

  setRulepriority(value: number) {
    this.rulePriority = value;
  }

  getView(): ViewInfo {
    return this.view;
  }

  setView(value: ViewInfo) {
    this.view = value;
  }

  getCommands(): ActionsImpl {
    if (this.commands == null) {
      this.commands = new ActionsImpl();
    }
    return this.commands;
  }

  setCommands(value: ActionsImpl) {
    this.commands = value;
  }

  getConditions(): MultiFilterImpl {
    return this.conditions;
  }

  setConditions(value: MultiFilterImpl) {
    this.conditions = value;
  }

  getSymbols(): Array<SymbolInfo> {
    return this.symbols;
  }

  setSymbols(value: Array<SymbolInfo>) {
    this.symbols = value;
  }
}
