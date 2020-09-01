import { Component, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.page.html',
  styleUrls: ['./feedback.page.scss'],
})
export class FeedbackPage implements OnInit {

  userId: any;
  description: any;
  suggestion: any;
  rating = 1;
  constructor(private commonDataService: CommonDataService,
    public modalCtrl: ModalController,
    private navCtrl: NavController,
    private localStorage: Storage) {
      this.localStorage.get('access_token').then(token => {
        if (token) {
          AppConstant.access_token = token;
        }
      });
      this.localStorage.get('id').then(id => {
        if (id) {
          this.userId = id;
          this.getPreviousData(id);
        }
      });
    }

  ngOnInit() {
  }

  dismiss() {
    this.modalCtrl.dismiss({
      'dismissed': true
    });
  }

  getPreviousData(userId) {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.userfeedback + '/' + userId;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.description = data.payload.description;
        this.suggestion = data.payload.suggestion;
        this.rating = data.payload.rating;
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message)
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.dismiss();
        }
      }
    },
      error => {
        this.navCtrl.navigateRoot('login');
        this.localStorage.clear();
        this.dismiss();
      });
  }

  feedback() {
    if (this.description.trim() || this.suggestion.trim()) {
      const reqObj = {
        "description": this.description,
        "suggestion": this.suggestion,
        "rating": this.rating,
        "userId": this.userId
      }
      this.commonDataService.presentLoading();
      const url = AppConstant.appURL + AppConstant.userfeedback;
      this.commonDataService.securePostService(url,reqObj).subscribe(data => {
        this.commonDataService.dismissLoading();
        if (data && data.success) {
          this.commonDataService.presentAlert("Feedback saved successfully");
         this.dismiss();
        } else {
          if (data && data.error) {
            this.commonDataService.presentAlert(data.error.message);
          } else if (!data) {
            this.navCtrl.navigateRoot('login');
            this.localStorage.clear();
            this.dismiss();
          }
        }
      },
        error => {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.dismiss();
        });
    } else {
      this.commonDataService.presentAlert("Enter description or suggestion in proper format");
    }
  }

}
