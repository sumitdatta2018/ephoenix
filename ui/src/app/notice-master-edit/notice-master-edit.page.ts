import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { CommonDataService } from '../providers/common-data.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AppConstant } from '../app.constant';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-notice-master-edit',
  templateUrl: './notice-master-edit.page.html',
  styleUrls: ['./notice-master-edit.page.scss'],
})
export class NoticeMasterEditPage implements AfterViewInit {

  @Input() notice: any;
  noticeMasterForm: FormGroup;
  constructor(public modalCtrl: ModalController,
    private navCtrl: NavController,
    private localStorage: Storage,
    private commonDataService: CommonDataService) {
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

  ngAfterViewInit() {
    let startDateArr = this.notice.startDate.split('/');
    let endDateArr = this.notice.endDate.split('/');
    this.noticeMasterForm.patchValue({
      title: this.notice.title,
      subTitle: this.notice.subTitle,
      body: this.notice.body,
      startDate: new Date(startDateArr[2],startDateArr[1] - 1,startDateArr[0]).toISOString(),
      endDate: new Date(endDateArr[2],endDateArr[1] - 1,endDateArr[0]).toISOString(),
      active: this.notice.isActive === 'Y' ? true : false
    })
  }

  dismiss() {
    this.modalCtrl.dismiss({
      'dismissed': false
    });
  }

  updateNotice() {
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
    const url = AppConstant.appURL + AppConstant.createNotice + '/' + this.notice.noticeId;
    this.commonDataService.securePostService(url,reqObj).subscribe(data => {
      if (data && data.success) {
        this.commonDataService.presentAlert("Notice Updated Successfully");
        this.noticeMasterForm.reset();
        this.commonDataService.dismissLoading();
        this.modalCtrl.dismiss({
          'success': true
        });
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
