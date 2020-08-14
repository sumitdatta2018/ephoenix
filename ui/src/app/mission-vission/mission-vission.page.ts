import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';

@Component({
  selector: 'app-mission-vission',
  templateUrl: './mission-vission.page.html',
  styleUrls: ['./mission-vission.page.scss'],
})
export class MissionVissionPage implements OnInit {

  constructor(private commonDataService: CommonDataService) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
  }

  ngOnInit() {
  }

}
