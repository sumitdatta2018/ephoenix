import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';

@Component({
  selector: 'app-others',
  templateUrl: './others.page.html',
  styleUrls: ['./others.page.scss'],
})
export class OthersPage implements OnInit {

  constructor(private commonDataService: CommonDataService) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
  }

  ngOnInit() {
  }

}
