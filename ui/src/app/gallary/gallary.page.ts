import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';
import { NavController, Platform, ModalController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { PhotoViewer } from '@ionic-native/photo-viewer/ngx';
import { PicViewPage } from '../pic-view/pic-view.page';

@Component({
  selector: 'app-gallary',
  templateUrl: './gallary.page.html',
  styleUrls: ['./gallary.page.scss'],
})
export class GallaryPage implements OnInit {

  galarylist = [];
  constructor(private commonDataService: CommonDataService, public platform: Platform, public modalController: ModalController,
    private navCtrl: NavController, private localStorage: Storage,private photoViewer: PhotoViewer) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.getGallaryData();
  }

  ngOnInit() {
  }

  getGallaryData() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.uploadList + '?uploadTypeIds=6';
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.galarylist = data.payload;
        this.galarylist = this.galarylist.map(upload => {
          let file = upload.fileName.split('.');
          return {
            ...upload,
            fileExt: file.pop(),
            title: upload.fileName.substring(0,upload.fileName.length - 4),
          }
        })
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

  viewPhoto(photo) {
    if (this.platform.is('android')) {
      this.photoViewer.show(photo.completeUploadPath,'Phoenix Gallary',{share: true,closeButton: true});
    } else {
      this.webView(photo);
    }
  }

  async webView(photo) {
    const modal = await this.modalController.create({
      component: PicViewPage,
      backdropDismiss: true,
      cssClass: 'picModal',
      componentProps: {
        'url': photo.completeUploadPath,
        'fileName': photo.fileName,
      }
    });
    return await modal.present();
  }

}
