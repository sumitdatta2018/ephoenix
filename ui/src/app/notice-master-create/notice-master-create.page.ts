import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonDataService } from '../providers/common-data.service';
import { NavController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { AppConstant } from '../app.constant';

@Component({
  selector: 'app-notice-master-create',
  templateUrl: './notice-master-create.page.html',
  styleUrls: ['./notice-master-create.page.scss'],
})
export class NoticeMasterCreatePage implements OnInit {

  noticeMasterForm: FormGroup;
  constructor(private commonDataService: CommonDataService,
    private navCtrl: NavController,
    private localStorage: Storage) {
      this.commonDataService.loadMenus();
      this.commonDataService.loadUser();
      this.noticeMasterForm = new FormGroup({
        title: new FormControl('', Validators.required),
        subTitle: new FormControl('', Validators.required),
        body: new FormControl('', Validators.required),
        startDate: new FormControl('', Validators.required),
        endDate: new FormControl('', Validators.required),
        active: new FormControl(true, Validators.required),
      });
  }

  ngOnInit() {
  }

  createNotice() {
    console.log(this.noticeMasterForm.value);
    this.commonDataService.presentLoading();
    let startDateString = this.noticeMasterForm.controls.startDate.value.split('T')[0];
    let endDateString = this.noticeMasterForm.controls.endDate.value.split('T')[0];
    let startDateArr = startDateString.split('-');
    let endDateArr = endDateString.split('-');
    let startDate = startDateArr[2] + '/' + startDateArr[1] + '/' + startDateArr[0];
    let endDate = endDateArr[2] + '/' + endDateArr[1] + '/' + endDateArr[0];
    let reqObj = {
      "title": this.noticeMasterForm.controls.title.value,
      "subTitle": this.noticeMasterForm.controls.subTitle.value,
      "body": this.noticeMasterForm.controls.body.value,
      "startDate": startDate,
      "endDate": endDate,
      "isActive": this.noticeMasterForm.controls.active.value ? "Y" : "N",
    }
    const url = AppConstant.appURL + AppConstant.createNotice;
    this.commonDataService.securePostService(url,reqObj).subscribe(data => {
      if (data && data.success) {
        this.commonDataService.presentAlert("Notice Created Successfully");
        this.noticeMasterForm.reset();
        this.commonDataService.dismissLoading();
        this.navCtrl.navigateForward('notice/list');
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
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
      this.commonDataService.dismissLoading();
    });
  }

}
