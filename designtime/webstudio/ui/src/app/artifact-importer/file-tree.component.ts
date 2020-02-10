import { EventEmitter } from '@angular/core';
import { Output } from '@angular/core';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { GridOptions, RowDataUpdatedEvent, RowNode } from 'ag-grid-community';
import 'ag-grid-enterprise';

import { AddRemoveButtonComponent } from './add-remove-button.component';

import { ArtifactService } from '../core/artifact.service';
import { ModalService } from '../core/modal.service';
import { Artifact, ArtifactType } from '../models/artifact';
import { ProjectImporterContext } from '../project-importer/project-importer-context';
import { ValidPathDirective } from '../shared/valid-path.directive';

@Component({
  selector: 'file-tree',
  templateUrl: './file-tree.component.html',
  styleUrls: ['./file-tree.component.css']
})
export class FileTreeComponent implements OnInit {

  @Input()
  projectName: string;

  @Input()
  input: Artifact[];

  @Input()
  edit: boolean;

  @Input()
  checkProjectNameUnique: string[];

  @Output()
  inputChange = new EventEmitter<Artifact[]>();

  public validator = new ValidPathDirective(this.i18n);
  public getDataPath;
  public autoGroupColumnDef;
  public gridApi;
  public gridColumnApi;
  public gridOptions: GridOptions = {
    defaultColDef: {
      resizable: true,
    },
    treeData: true,
    getDataPath: this.getDataPath,
    rowSelection: 'single',
    getRowClass: function (params) {
      const validator = new ValidPathDirective(this.i18n);
      validator.validPath = 'FULL_PATH_ALLOW_EXTENSION';
      if (params.data && params.data.data instanceof Artifact) {
        const art = params.data.data as Artifact;
        const path = art.name;
        const result = validator._validate(path);
        if (result) {
          return 'alert-danger';
        } else {
          return '';
        }
      } else {
        // group node, need to check children
        const children = params.node.allLeafChildren;
        const invalidArt = children.find(art => {
          const path = art.data.data.name;
          const result = validator._validate(path);
          if (result) {
            return true;
          }
          return false;
        });
        if (invalidArt) {
          return 'alert-danger';
        } else {
          return '';
        }
      }
    },
    autoGroupColumnDef: this.autoGroupColumnDef
  };

  public getContextMenuItems;
  public columnDefs;
  public rowData;
  public getNodeChildDetails;
  public icons;
  public components;
  public gridSize = 8;
  public rowHeight = this.gridSize * 6;
  public headerHeight = this.gridSize * 7;
  public rowSelected = false;

  constructor(private _artifactService: ArtifactService,
    private _modalService: ModalService, public i18n: I18n) {
    this.icons = {
      groupExpanded: '<i class="fa fa-angle-down"/><i style="padding-left: 3px" class="fa fa-folder-open"/>',
      groupContracted: '<i class="fa fa-angle-right"/><i style="padding-left: 3px" class="fa fa-folder"/>',
    };
    this.components = {
      fileCellRenderer: this.getFileCellRenderer()
    };
  }

  size(artifact: Artifact) {
    if (artifact.content) {
      const bytesCnt = artifact.content.length;
      return (bytesCnt / 1024.0).toFixed(2);
    } else {
      return 0;
    }
  }

  createRowData() {
    const roots = [];
    this.input.forEach(art => {
      roots.push({
        size: this.size(art),
        type: art.type,
        data: art
      });
      // create explicit parent nodes to allow renaming, empty dirs
      let segs = art.baseDir.split('/');
      //      if (art.path.startsWith('/')) {
      //        segs = segs.splice(1);
      //      }
      segs = segs.slice(0, segs.length - 1);
      let parents = '';
      segs.forEach(seg => {
        if (seg !== '') {
          parents = parents.concat('/').concat(seg);
          const parNode = {
            data: {
              path: parents
            }
          };
          if (!roots.find(root => root.data.path === parents)) {
            roots.push(parNode);
          }
        }
      });
    });

    return roots;
  }

  ngOnInit() {
    this.getNodeChildDetails = function getNodeChildDetails(rowItem) {
      if (rowItem.children) {
        return {
          group: true,
          expanded: !rowItem.parent,
          children: rowItem.children,
        };
      } else {
        return null;
      }
    };
    const _this = this;

    this.columnDefs = [
      //      {
      //        headerName: 'Artifact Name',
      //        field: 'filename',
      //        colKey: 'filename',
      //        newValueHandler: this.getNewValueHandler('filename'),
      //        width: 250,
      //        checkboxSelection: true,
      //        headerCheckboxSelection: true,
      //        editable: true,
      //      },
      {
        headerName: this.i18n('Type'),
        field: 'type.displayString',
        editable: true,
        width: 120,
        onCellValueChanged: function (params) {
          params.api.redrawRows();
          _this.emit();
        },
        cellEditor: 'agRichSelectCellEditor',
        newValueHandler: this.getNewValueHandler('type'),
        cellEditorParams: {
          values: this.availableTypes()
        }
      },
      {
        headerName: this.i18n('Size (KB)'),
        field: 'size',
        hide: true,
        editable: false,
        width: 100
      },
      {
        headerName: this.i18n('Metadata'),
        field: 'metadata',
        newValueHandler: this.getNewValueHandler('metadata'),
        editable: true,
        hide: true,
        onCellValueChanged: function (params) {
          params.api.redrawRows();
          _this.emit();
        },
        cellEditor: 'agLargeTextCellEditor'
      },
      {
        headerName: this.i18n('Actions'),
        field: 'action',
        cellRendererFramework: AddRemoveButtonComponent,
        colId: 'edit',
        width: 80
      }
    ];
    this.rowData = this.createRowData();
    this.getDataPath = function (data) {
      let path = data.data.path;
      if (path.startsWith('/')) {
        path = path.slice(1);
      }

      return path.split('/');
    };
    this.autoGroupColumnDef = {
      headerName: this.i18n('Artifacts'),
      width: 250,
      editable: true,
      newValueHandler: this.getNewValueHandler('path'),
      onCellValueChanged: function (params) {
        params.api.redrawRows();
        _this.emit();
      },
      cellRendererParams: {
        // checkbox: true,
        //        suppressCount: true,
        editable: true,
        innerRenderer: 'fileCellRenderer'
      }
    };
    const comp = this;
    this.getContextMenuItems = function getContextMenuItems(params) {
      const data = params && params.node ? params.node.data : undefined;
      const noSelection = data === undefined;
      const disabled = (noSelection || data.data instanceof Artifact);
      const result = [
        {
          name: this.i18n('Create artifact'),
          icon: '<i style="color: blue;" class="fa fa-plus-circle"></i>',
          action: function () {
            comp.createArtifact(params.node, true);
          },
        },
        {
          name: this.i18n('Create folder'),
          icon: '<i style="color: blue;" class="fa fa-folder-open"></i>',
          action: function () {
            comp.createFolder(params.node, true);
          },
        },
        {
          name: this.i18n('Create artifact inside of current selection'),
          disabled: disabled,
          icon: '<i style="color: blue;" class="fa fa-level-down"></i>',
          action: function () {
            comp.createArtifact(params.node);
          },
        },
        {
          name: this.i18n('Create folder inside of current selection'),
          disabled: disabled,
          icon: '<i style="color: blue;" class="fa fa-level-down"></i>',
          action: function () {
            comp.createFolder(params.node);
          },
        },
        {
          name: this.i18n('Rename'),
          disabled: noSelection,
          icon: '<i style="color: blue;" class="fa fa-edit"></i>',
          action: function () {
            comp.rename(params.node);
          },
        },
        {
          name: this.i18n('Delete'),
          disabled: noSelection,
          icon: '<i style="color: red;" class="fa fa-trash"></i>',
          action: function () {
            comp.deleteArtifact(params.node);
          },
        }];
      return result;
    };
  }

  rename(node: RowNode) {
    const data = node.data;
    if (data && data.data instanceof Artifact) {
      this.gridApi.startEditingCell({ colKey: 'ag-Grid-AutoColumn', rowIndex: node.rowIndex });
    } else {
      this.gridApi.startEditingCell({ colKey: 'ag-Grid-AutoColumn', rowIndex: node.rowIndex });
      return;
    }
    // just edit inline (above)
    //    if (node.group) {
    //      this._modalService.prompt()
    //        .size('sm')
    //        .isBlocking(true)
    //        .showClose(false)
    //        .keyboard(27)
    //        .body('Enter new folder name')
    //        .defaultValue(node.key)
    //        .open()
    //        .result
    //        .then(
    //        newVal => {
    //          // get new base path
    //          let par = node.parent;
    //          let basePath = newVal;
    //          let idx = 0;
    //          while (par !== null && par.id !== 'ROOT_NODE_ID') {
    //            basePath = par.key.concat('/').concat(basePath);
    //            par = par.parent;
    //            idx++;
    //          }
    //          node.setGroupValue('ag-Grid-AutoColumn', newVal);
    //          // update all child paths
    //          node.allLeafChildren.forEach(leaf => {
    //            let art = leaf.data.data as Artifact;
    //            let segs = art.path.split('/');
    //            let offset = art.path.startsWith('/') ? 1 : 0;
    //            segs[idx + offset] = newVal;
    //            art.path = segs.join('/');
    //          });
    //          this.gridApi.redrawRows();
    //        },
    //        () => {});
    //    }
  }

  deleteArtifact(node: RowNode) {
    const rows = this.getRowsToRemove(node);
    this.gridApi.updateRowData({ remove: rows });
  }

  getRowsToRemove(node) {
    let res = [];
    for (let i = 0; i < node.childrenAfterGroup.length; i++) {
      res = res.concat(this.getRowsToRemove(node.childrenAfterGroup[i]));
    }

    // ignore nodes that have no data, i.e. 'filler groups'
    return node.data ? res.concat([node.data]) : res;
  }

  revalidate(params) {
    params.api.redrawRows();
  }

  getNewValueHandler(field: string) {
    return (params: any) => {
      if (params.oldValue !== params.newValue) {
        if (params.data.data instanceof Artifact) {
          const art = params.data.data as Artifact;
          switch (field) {
            case 'metadata':
              art.metadata = params.newValue;
              break;
            case 'path':
              art.path = art.baseDir + params.newValue;
              params.node.setGroupValue(params.column, params.newValue);
              // reset the group key
              params.node.key = params.newValue;
              params.node.parent.childrenMapped[params.oldValue] = undefined;
              params.node.parent.childrenMapped[params.newValue] = params.node;
              break;
            case 'type':
              art.type = ArtifactType.AVAILABLE_TYPES.find(t => t.displayString === params.newValue);
              params.data['type'] = art.type;
              break;
          }
        } else if (field === 'path') {
          // renamed folder
          params.node.setGroupValue(params.column, params.newValue);
          // get new base path
          let par = params.node.parent;
          let basePath = params.newValue;
          let idx = 0;
          while (par !== null && par.id !== 'ROOT_NODE_ID') {
            basePath = par.key.concat('/').concat(basePath);
            par = par.parent;
            idx++;
          }
          // reset the group key
          params.node.key = params.newValue;
          params.node.parent.childrenMapped[params.oldValue] = undefined;
          params.node.parent.childrenMapped[params.newValue] = params.node;

          // update all child paths
          params.node.allLeafChildren.forEach(leaf => {
            const art = leaf.data.data as Artifact;
            const segs = art.path.split('/');
            const offset = art.path.startsWith('/') ? 1 : 0;
            segs[idx + offset] = params.newValue;
            art.path = segs.join('/');
          });
          //          this.gridApi.redrawRows();
        }
      }
      return params.newValue;
    };
  }

  availableTypes() {
    return ArtifactType.AVAILABLE_TYPES.map(artType => {
      return artType.displayString;
    });
  }

  onTextboxFilterChanged() {
    const value = document.getElementById('filter-textbox').textContent;
    this.gridApi.setQuickFilter(value);
  }

  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.gridApi.sizeColumnsToFit();

    params.api.sizeColumnsToFit();
    const comp = this;
    const dataUpdatedFunc = function (data) {
      const event = data as RowDataUpdatedEvent;
      const rowData = [];
      event.api.forEachNode(node => {
        if (node.data && node.data.data instanceof Artifact) {
          rowData.push(node.data.data);
        }
      });
      comp.input = rowData;
      comp.gridApi.redrawRows();
      comp.emit();
    };

    this.gridApi.addEventListener('rowDataUpdated', dataUpdatedFunc);

  }

  onSelectionChanged(event) {
    const rowCount = event.api.getSelectedNodes().length;
    this.rowSelected = rowCount > 0;
  }

  createFolder(parent: RowNode, sibling?: boolean) {
    //    support folders as leaf nodes
    let counter = 1;
    let basePath = '';
    if (parent) {
      if (sibling) {
        parent = parent.parent;
        //        basePath = basePath.concat(parent.key).concat('/');
      }
      let par = parent;
      while (par !== undefined && par.id !== 'ROOT_NODE_ID') {
        basePath = par.key.concat('/').concat(basePath);
        par = par.parent;
      }
    }

    const baseName = basePath.concat('NewFolder');
    let name = this.getUniqueName(baseName);
    while (name === undefined) {
      const suffix = counter++;
      name = this.getUniqueName(baseName.concat(suffix.toString()));
    }
    const parNode = {
      data: {
        path: name
      }
    };
    this.gridApi.updateRowData({ add: [parNode] });
  }

  createArtifact(parent: RowNode, sibling?: boolean) {
    const newArt = this._artifactService.createArtifactInfo(false);
    let counter = 1;
    let basePath = '';
    if (parent) {
      if (!sibling) {
        basePath = parent.key.concat('/');
      }
      let par = parent.parent;
      while (par !== null && par.id !== 'ROOT_NODE_ID') {
        basePath = par.key.concat('/').concat(basePath);
        par = par.parent;
      }
    }

    const baseName = basePath.concat('MyArtifact');
    let name = this.getUniqueName(baseName);
    while (name === undefined) {
      const suffix = counter++;
      name = this.getUniqueName(baseName.concat(suffix.toString()));
    }
    newArt.type = ArtifactType.fromFileName(name);
    newArt.path = name;
    const newNode = {
      size: 0,
      type: newArt.type,
      data: newArt
    };
    this.gridApi.updateRowData({ add: [newNode] });
  }

  getUniqueName(name: string): string {
    this.gridApi.forEachNode(node => {
      const data = node.data;
      if (data && data.data && data.data.path === name) {
        name = undefined;
        return;
      }
    });
    return name;
  }

  getFileCellRenderer() {
    function FileCellRenderer() { }
    FileCellRenderer.prototype.init = function (params) {
      const getFileIcon = function (filename) {
        return filename.endsWith('.mp3') || filename.endsWith('.wav')
          ? 'fa fa-file-audio-o' : filename.endsWith('.sbdt')
            ? 'fa fa-file-excel-o' : filename.lastIndexOf('.') !== -1
              ? 'fa fa fa-file-o' : filename.endsWith('.pdf')
                ? 'fa fa-file-pdf-o' : 'fa fa-folder-o';
      };
      const data = params.node.data;
      const value = (data && data.data instanceof Artifact) ? data.data.name : params.value;
      const tempDiv = document.createElement('div');
      const icon = (data && data.data instanceof Artifact) ? data.data.type.defaultIcon : getFileIcon(params.value);
      tempDiv.innerHTML = icon ? `<span><i class="${icon}"></i><span style="padding-left:5px">${value}</span></span>` : value;
      this.eGui = tempDiv.firstChild;
    };
    FileCellRenderer.prototype.getGui = function () {
      return this.eGui;
    };
    return FileCellRenderer;
  }

  emit() {
    this.inputChange.emit(this.input);
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') != -1 ? true : false);
  }
}
