import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-spoken-english',
  templateUrl: './spoken-english.page.html',
  styleUrls: ['./spoken-english.page.scss'],
})
export class SpokenEnglishPage implements OnInit {

  constructor(private commonDataService: CommonDataService,
    public navCtrl: NavController,) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
  }

  ngOnInit() {
  }

  register() {
    this.navCtrl.navigateForward('spoken-english-registration');
  }

}
