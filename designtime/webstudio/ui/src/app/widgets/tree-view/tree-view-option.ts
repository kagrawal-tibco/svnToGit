export class TreeViewOption {
  icons: IconConfig;
  lazyExpand: boolean;
}

export interface IconConfig {
  file: string;
  fileOpen: string;

  folder: string;
  folderOpen: string;

  expanderClosed: string;
  expanderLazy: string;
  expanderOpen: string;

  loading: string;

  checkbox?: string;
  checkboxSelected?: string;
  checkboxUnknown?: string;
  dragHelper?: string;
  dropMarker?: string;
  error?: string;
}
