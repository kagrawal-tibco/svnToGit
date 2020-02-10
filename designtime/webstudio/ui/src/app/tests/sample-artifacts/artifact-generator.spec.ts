
// const generate = () => {
//   let rows = 5000;
//   let cols = 100;

//   let dt = DecisionTable.fromXml('');
//   for (let i = 0; i < cols; i++) {
//     dt.createAndAddColumn('col' + i, PropertyType.STRING, ColumnType.CONDITION);
//   }

//   for (let i = 0; i < rows; i++) {
//     let rule = dt.createRuleWithAutoId();
//     for (let j = 0; j < cols; j++) {
//       rule.setCell('' + j, `"a pretty long expression ${i}_${j}"`, 'cond');
//     }
//   }
//   fs.writeFileSync('big_artifact.sbdt', dt.seralize());
// };

// generate();
