import { Component } from '@angular/core';

import { ColDef, GridOptions } from 'ag-grid-community/main';

import { ArtifactTestDataService } from './artifact-testdata.service';

import { CellClassRules } from '../editors/decision-table/decorators/decisiontable-decorator';
import { TestDataCoverage } from '../models-be/decision-table-be/testdata-coverage';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';

@Component({
  selector: 'artifact-testdata',
  templateUrl: './artifact-testdata.component.html',
  styleUrls: ['./artifact-testdata.component.css']
})

export class ArtifactTestDataComponent {
  public columnDefs: Array<ColDef> = new Array<ColDef>();
  public rowData: Array<any> = new Array<any>();

  private gridOptions: GridOptions;

  constructor(private service: ArtifactTestDataService,
    private multiTabService: MultitabEditorService) {
    this.gridOptions = <GridOptions>{};
    this.createRowData();
    this.createColumnDefs();
    this.service.testData.subscribe((testData: TestDataCoverage) => {
      this.gridOptions = <GridOptions>{};
      this.rowData = new Array<any>();
      this.columnDefs = new Array<ColDef>();
      this.createRowData();
      this.createColumnDefs();
    });

    this.multiTabService.getActiveObservable().subscribe((currentTab) => {
      if (currentTab) {
        this.gridOptions = <GridOptions>{};
        this.rowData = new Array<any>();
        this.columnDefs = new Array<ColDef>();
      }
    });
  }

  getValueGetter(column: string) {
    return (params: any) => {
      const index: number = this.service.getTestDataColumns().indexOf(params.colDef.headerName);
      return params.data.value[index];
    };
  }

  getCellClassRules() {
    return <CellClassRules>{
      'diff-new': (params) => {
        if (params.data.coveredRuleId.length > 0) {
          return true;
        } else {
          return false;
        }
      }
    };
  }

  isTestDataPresent() {
    return this.service.testDataPresent();
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

  private createRowData() {
    if (this.service.testDataPresent()) {
      for (const record of this.service.getTestDataRecords()) {
        this.rowData.push(record);
      }
    }
  }

  private createColumnDefs() {
    if (this.service.testDataPresent()) {
      for (const column of this.service.getTestDataColumns()) {
        const colDef: ColDef = {
          headerName: column,
          valueGetter: this.getValueGetter(column),
          cellClassRules: this.getCellClassRules()
        };
        this.columnDefs.push(colDef);
      }
    }
  }

}
