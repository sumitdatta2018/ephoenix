import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';
import { NavController, ModalController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { AppConstant } from '../app.constant';
import { UserDetailsPage } from '../user-details/user-details.page';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.page.html',
  styleUrls: ['./user-management.page.scss'],
})
export class UserManagementPage implements OnInit {

  userList = [];
  totalUsers:any;
  isAssociatedWithSpokenEnglish:boolean = false;
  pageSize = 10;
  curPage = 1;
  constructor(private commonDataService: CommonDataService, 
    public modalController: ModalController,
    private navCtrl: NavController, 
    private localStorage: Storage) {
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
    let url = '';
    if (filter) {
      url = AppConstant.appURL + AppConstant.userlist + '?isAssociatedWithSpokenEnglish=true';
    } else {
      url = AppConstant.appURL + AppConstant.userlist;
    }
    
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.userList = data.payload.content;
        this.totalUsers = data.payload.totalElements;
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

  filterUserList() {
    if (this.isAssociatedWithSpokenEnglish) {
      this.getUserList(true);
    } else {
      this.getUserList();
    }
  }

  getUserDetails(userId) {
    this.commonDataService.presentLoading();
    let url = AppConstant.appURL + AppConstant.getUserById + userId;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.openUserDetails(data.payload);
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.commonDataService.dismissLoading();
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
        }
      }
    });
  }

  async openUserDetails(data) {
    const modal = await this.modalController.create({
      component: UserDetailsPage,
      backdropDismiss: true,
      componentProps: {
        'user': data,
      }
    });
    return await modal.present();
  }

}
