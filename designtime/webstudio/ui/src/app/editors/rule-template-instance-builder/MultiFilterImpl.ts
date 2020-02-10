/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:04:16+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:04:22+05:30
 */

import { BuilderSubClauseImpl } from './BuilderSubClauseImpl';
import { Filter } from './Filter';

export type MatchType = 'Match All' | 'Match Any' | 'Match None';

export class MultiFilterImpl extends Filter {

  private matchType: MatchType = undefined;
  private builderSubClause: BuilderSubClauseImpl = new BuilderSubClauseImpl(); // filter

  constructor(matchType?: MatchType) {
    super();
    this.setFilterId(this.getFilterId());
    this.matchType = matchType == null ? 'Match All' : matchType;
    this.builderSubClause = new BuilderSubClauseImpl();
  }

  getMatchType(): MatchType {
    return this.matchType;
  }

  setMatchType(matchType: MatchType) {
    this.matchType = matchType;
  }

  getBuilderSubClause(): BuilderSubClauseImpl {
    return this.builderSubClause;
  }

  setBuilderSubClause(value: BuilderSubClauseImpl) {
    this.builderSubClause = value;
  }
}
