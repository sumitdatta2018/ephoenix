import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';

@Component({
  selector: 'app-scholarship',
  templateUrl: './scholarship.page.html',
  styleUrls: ['./scholarship.page.scss'],
})
export class ScholarshipPage implements OnInit {

  constructor(private commonDataService: CommonDataService) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
  }

  ngOnInit() {
  }

}
