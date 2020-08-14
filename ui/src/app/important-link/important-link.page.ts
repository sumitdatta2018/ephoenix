import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';

@Component({
  selector: 'app-important-link',
  templateUrl: './important-link.page.html',
  styleUrls: ['./important-link.page.scss'],
})
export class ImportantLinkPage implements OnInit {

  constructor(private commonDataService: CommonDataService) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
  }

  ngOnInit() {
  }

}
