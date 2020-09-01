import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';
import { NavController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { AppConstant } from '../app.constant';

@Component({
  selector: 'app-feedback-list',
  templateUrl: './feedback-list.page.html',
  styleUrls: ['./feedback-list.page.scss'],
})
export class FeedbackListPage implements OnInit {

  feedbackList = [];
  pageSize = 10;
  curPage = 1;
  constructor(private commonDataService: CommonDataService,
    private navCtrl: NavController, private localStorage: Storage) {
      this.commonDataService.loadMenus();
      this.commonDataService.loadUser();
      this.localStorage.get('access_token').then(token => {
        if (token) {
          AppConstant.access_token = token;
          this.getUserList();
        }
      });
    }

  ngOnInit() {
  }

  getUserList(filter?: boolean) {
    this.commonDataService.presentLoading();
    let url = AppConstant.appURL + AppConstant.userfeedback;    
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.feedbackList = data.payload;
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message)
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
        }
      }
    },
      error => {
        this.navCtrl.navigateRoot('login');
        this.localStorage.clear();
      });
  }

}
