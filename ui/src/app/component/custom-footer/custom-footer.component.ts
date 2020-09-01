import { Component, OnInit } from '@angular/core';
import { AppAvailability } from '@ionic-native/app-availability/ngx';
import { InAppBrowser, InAppBrowserObject } from '@ionic-native/in-app-browser/ngx';
import { Platform, ModalController, NavController, AlertController } from '@ionic/angular';
import { FeedbackPage } from 'src/app/feedback/feedback.page';
import { Storage } from '@ionic/storage';
import { AppConstant } from 'src/app/app.constant';

@Component({
  selector: 'app-custom-footer',
  templateUrl: './custom-footer.component.html',
  styleUrls: ['./custom-footer.component.scss'],
})
export class CustomFooterComponent implements OnInit {

  showPlayIcon: boolean = false;
  constructor(public platform: Platform, 
    private appAvailability: AppAvailability,
    private inAppBrowser: InAppBrowser,
    private localStorage: Storage,
    private navCtrl: NavController,
    public alertController: AlertController,
    public modalController: ModalController) {
      if (!this.platform.is('android') || !this.platform.is('mobileweb')) {
        this.showPlayIcon = true;
      } else if (this.platform.is('mobileweb') && !AppConstant.mobilePopUpShown) {
        AppConstant.mobilePopUpShown = true;
        this.downloadFile();
      }

    }

  ngOnInit() {}

  async downloadFile() {
    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: "The Phoenix",
      message: `<p>Our App is available on Google Play, Do you want to download?</p>
      <img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/>`,
      buttons: [
        {
          text: 'Yes',
          handler: (blah) => {
            const browser: InAppBrowserObject = this.inAppBrowser.create('https://play.google.com/store/apps/details?id=com.thephoenix.app&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1', '_system');
          }
        }, {
          text: 'No',
          handler: () => {

          }
        }
      ]
    });

    await alert.present();
  }

  
  openFB(name) {
    let app;

    if (this.platform.is('ios')) {
      app = 'fb://';
    } else if (this.platform.is('android')) {
      app = 'com.facebook.katana';
    } else {
      const browser: InAppBrowserObject = this.inAppBrowser.create('https://www.facebook.com/' + name);
      return;
    }
    this.appAvailability.check(app)
      .then(
        (yes: boolean) => {
          console.log(app + ' is available')
          // Success
          // App exists
          const browser: InAppBrowserObject = this.inAppBrowser.create('https://www.facebook.com/ThePhoenixHelpline', '_system');
        },
        (no: boolean) => {
          // Error
          // App does not exist
          // Open Web URL
          const browser: InAppBrowserObject = this.inAppBrowser.create('https://www.facebook.com/' + name, '_system');
        }
      );
  }

  openYT(channel) {
    let app;

    if (this.platform.is('ios')) {
      app = 'youtube://';
    } else if (this.platform.is('android')) {
      app = 'com.google.android.youtube';
    } else {
      const browser: InAppBrowserObject = this.inAppBrowser.create('https://www.youtube.com/channel/' + channel, '_system');
      return;
    }

    this.appAvailability.check(app)
      .then(
        (yes: boolean) => {
          console.log(app + ' is available')
          // Success
          // App exists
          const browser: InAppBrowserObject = this.inAppBrowser.create('https://www.youtube.com/channel/' + channel, '_system');
        },
        (no: boolean) => {
          // Error
          // App does not exist
          // Open Web URL
          const browser: InAppBrowserObject = this.inAppBrowser.create('https://www.youtube.com/channel/' + channel, '_system');
        }
      );
  }

  openMap() {
    const browser: InAppBrowserObject = this.inAppBrowser.create('https://maps.google.com/?q=22.782812118530273,87.77485013008118', '_system');
      return;
  }

  feedback() {
    this.localStorage.get('access_token').then(token => {
      if (token) {
        AppConstant.access_token = token;
        this.openFeedback();
      } else {
        this.navCtrl.navigateRoot('login');
      }
    });
  }

  async openFeedback() {
    const modal = await this.modalController.create({
      component: FeedbackPage,
    });
    return await modal.present();
  }

}
