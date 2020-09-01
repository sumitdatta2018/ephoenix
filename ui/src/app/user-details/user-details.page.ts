import { Component, OnInit, Input } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AppConstant } from '../app.constant';
import { CommonDataService } from '../providers/common-data.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.page.html',
  styleUrls: ['./user-details.page.scss'],
})
export class UserDetailsPage implements OnInit {

  @Input() user: any;
  caste: string;
  religion: string;
  constructor(private commonDataService: CommonDataService, public modalCtrl: ModalController) {
    // this.getCasteAndReligionList();
  }

  ngOnInit() {
  }

  dismiss() {
    this.modalCtrl.dismiss({
      'dismissed': true
    });
  }

  // getCasteAndReligionList() {
  //   this.commonDataService.presentLoading();
  //   const url = AppConstant.appURL + AppConstant.casteReligionList;
  //   this.commonDataService.commonGetService(url).subscribe(data => {
  //     this.commonDataService.dismissLoading();
  //     if (data.success) {
  //       this.caste = data.payload.CASTE.find(caste => caste.casteId === this.user.casteId).casteName;
  //       this.religion = data.payload.RELIGION.find(religion => religion.religionId === this.user.religionId).religionName;
  //     }
  //   });
  // }

}
