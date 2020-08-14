import { Component, OnInit } from '@angular/core';
import { AppAvailability } from '@ionic-native/app-availability/ngx';
import { InAppBrowser, InAppBrowserObject } from '@ionic-native/in-app-browser/ngx';
import { Platform, ModalController } from '@ionic/angular';
import { FeedbackPage } from 'src/app/feedback/feedback.page';

@Component({
  selector: 'app-custom-footer',
  templateUrl: './custom-footer.component.html',
  styleUrls: ['./custom-footer.component.scss'],
})
export class CustomFooterComponent implements OnInit {

  constructor(public platform: Platform, 
    private appAvailability: AppAvailability,
    private inAppBrowser: InAppBrowser,
    public modalController: ModalController) { }

  ngOnInit() {}

  
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

  async feedback() {
    const modal = await this.modalController.create({
      component: FeedbackPage,
    });
    return await modal.present();
  }

}
