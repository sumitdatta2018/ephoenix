import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';
import { NavController, ModalController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { AppConstant } from '../app.constant';
import { NoticeMasterEditPage } from '../notice-master-edit/notice-master-edit.page';

@Component({
  selector: 'app-notice-master-list',
  templateUrl: './notice-master-list.page.html',
  styleUrls: ['./notice-master-list.page.scss'],
})
export class NoticeMasterListPage implements OnInit {

  public noticeList = [];
  constructor(private commonDataService: CommonDataService,
    public modalController: ModalController,
    private navCtrl: NavController,
    private localStorage: Storage) {
      this.commonDataService.loadMenus();
      this.commonDataService.loadUser();
      this.localStorage.get('access_token').then(token => {
        if (token) {
          AppConstant.access_token = token;
          this.getNoticeList();
        }
      });
    }

  ngOnInit() {
  }

  getNoticeList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getNoticeList;
    this.commonDataService.secureGetService(url).subscribe(data => {
      if (data && data.success) {
        this.noticeList = data.payload;
        this.commonDataService.dismissLoading();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.commonDataService.dismissLoading();
        }
      }
    },
      error => {
        this.navCtrl.navigateRoot('login');
        this.localStorage.clear();
        this.commonDataService.dismissLoading();
      });
  }

  async editNotice(notice) {
    const modal = await this.modalController.create({
      component: NoticeMasterEditPage,
      backdropDismiss: true,
      componentProps: {
        'notice': notice,
      }
    });
    await modal.present();
    await modal.onDidDismiss().then(data => {
      if (data.data.success) {
        this.getNoticeList();
      }
    })
  }

}
