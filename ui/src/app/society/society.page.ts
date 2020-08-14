import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';

@Component({
  selector: 'app-society',
  templateUrl: './society.page.html',
  styleUrls: ['./society.page.scss'],
})
export class SocietyPage implements OnInit {

  constructor(private commonDataService: CommonDataService) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
  }

  ngOnInit() {
  }

}
