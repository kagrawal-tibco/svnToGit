import { Component, OnInit } from '@angular/core';

import { DownloadProjectsService } from './download-projects.service';

@Component({
  selector: 'download-projects',
  templateUrl: './download-projects.component.html',
  styleUrls: ['./download-projects.component.css']
})
export class DownloadProjectsComponent implements OnInit {

  public privateProjectList: string[];
  public selectedProjectList: string[];

  constructor(private downloadProjectService: DownloadProjectsService) {
    this.selectedProjectList = new Array<string>();
  }

  ngOnInit() {
    this.init();
  }

  init() {
    this.privateProjectList = new Array<string>();
    this.downloadProjectService.fetchPrivateProjects()
      .then((response: string[]) => {
        this.privateProjectList = new Array<string>();
        for (let index = 0; index < response.length; index++) {
          this.privateProjectList.push(response[index]);
        }
      });
  }

  downloadProjects() {
    this.downloadProjectService.downloadProjects(this.selectedProjectList).subscribe((successs: boolean) => {
      if (successs) {
        this.init();
      }  
    });
  }

  get disableDownload(): boolean {
    return this.selectedProjectList.length === 0;
  }

  onProjectSelection(value: string[]) {
    this.selectedProjectList = value;
  }

}
