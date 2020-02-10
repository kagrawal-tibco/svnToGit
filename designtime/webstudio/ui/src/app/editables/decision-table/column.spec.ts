import { ColumnType } from './column';
describe('ColumnType', () => {
  it('action and custom action are of the same category', () => {
    expect(ColumnType.ACTION.isSameCategory(ColumnType.EXPR_ACTION)).toBe(true);
    expect(ColumnType.EXPR_ACTION.isSameCategory(ColumnType.ACTION)).toBe(true);
  });

  it('condition and custom condition of the same category', () => {
    expect(ColumnType.CONDITION.isSameCategory(ColumnType.EXPR_CONDITION)).toBe(true);
    expect(ColumnType.EXPR_CONDITION.isSameCategory(ColumnType.CONDITION)).toBe(true);
  });

  it('action and condition are of the different categories', () => {
    expect(ColumnType.ACTION.isSameCategory(ColumnType.CONDITION)).toBe(false);
    expect(ColumnType.CONDITION.isSameCategory(ColumnType.ACTION)).toBe(false);

    expect(ColumnType.EXPR_ACTION.isSameCategory(ColumnType.CONDITION)).toBe(false);
    expect(ColumnType.CONDITION.isSameCategory(ColumnType.EXPR_ACTION)).toBe(false);

    expect(ColumnType.EXPR_ACTION.isSameCategory(ColumnType.EXPR_CONDITION)).toBe(false);
    expect(ColumnType.EXPR_CONDITION.isSameCategory(ColumnType.EXPR_ACTION)).toBe(false);

    expect(ColumnType.ACTION.isSameCategory(ColumnType.EXPR_CONDITION)).toBe(false);
    expect(ColumnType.EXPR_CONDITION.isSameCategory(ColumnType.ACTION)).toBe(false);
  });
});
