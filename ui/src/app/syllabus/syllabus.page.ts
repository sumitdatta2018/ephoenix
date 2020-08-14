import { Component, OnInit } from '@angular/core';
import { Storage } from '@ionic/storage';
import { CommonDataService } from '../providers/common-data.service';
import { Platform, ModalController, NavController, AlertController } from '@ionic/angular';
import { AppConstant } from '../app.constant';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx';
import { FileTransfer, FileTransferObject } from '@ionic-native/file-transfer/ngx';
import { File } from '@ionic-native/file/ngx';
import { Downloader, DownloadRequest, NotificationVisibility } from '@ionic-native/downloader/ngx';

@Component({
  selector: 'app-syllabus',
  templateUrl: './syllabus.page.html',
  styleUrls: ['./syllabus.page.scss'],
})
export class SyllabusPage implements OnInit {
  syllabuslist = [];
  fileTransfer: FileTransferObject;
  constructor(private commonDataService: CommonDataService,
    public platform: Platform,
    public modalController: ModalController,
    public alertController: AlertController,
    private androidPermissions: AndroidPermissions,
    private transfer: FileTransfer,
    private file: File,
    private downloader: Downloader,
    private navCtrl: NavController,
    private localStorage: Storage) {
    this.fileTransfer = this.transfer.create();
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.localStorage.get('access_token').then(token => {
      if (token) {
        AppConstant.access_token = token;
        this.getSyllabusData();
      }
    });
  }

  ngOnInit() {
  }

  getSyllabusData() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.uploadList + '?uploadTypeIds=3';
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.syllabuslist = data.payload;
        this.syllabuslist = this.syllabuslist.map(upload => {
          let file = upload.fileName.split('.');
          return {
            ...upload,
            fileExt: file.pop()
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

  async downloadFile(file) {
    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: "The Phoenix",
      message: "Do you want to download",
      buttons: [
        {
          text: 'Yes',
          handler: (blah) => {
            if (this.platform.is('android') && !this.platform.is('mobileweb')) {
              this.getPermission(file.completeUploadPath, file.fileName)
            } else {
              this.commonDataService.presentLoading();
              fetch(file.completeUploadPath).then(function (t) {
                return t.blob().then((b) => {
                  var a = document.createElement("a");
                  a.href = URL.createObjectURL(b);
                  a.setAttribute("download", file.fileName);
                  a.click();
                  this.commonDataService.dismissLoading();
                }
                );
              });
            }
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

  async downloadFileMobile(path, fileName) {
    this.commonDataService.presentLoading();

    let request: DownloadRequest = {
      uri: encodeURI(path),
      title: fileName,
      description: '',
      mimeType: '',
      visibleInDownloadsUi: true,
      notificationVisibility: NotificationVisibility.VisibleNotifyCompleted,
      destinationUri: this.file.externalRootDirectory + '/Download/Phoenix/' + fileName,
    };
    this.downloader.download(request)
      .then((location: string) => {
        this.commonDataService.dismissLoading();
        this.commonDataService.presentAlert('File downloaded at:' + location);
      })
      .catch((error: any) => {
        console.error(error);
        this.commonDataService.dismissLoading();
      });
  }

  getPermission(path, fileName) {
    this.androidPermissions.checkPermission(this.androidPermissions.PERMISSION.WRITE_EXTERNAL_STORAGE)
      .then(status => {
        if (status.hasPermission) {
          this.downloadFileMobile(path, fileName);
        }
        else {
          this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.WRITE_EXTERNAL_STORAGE)
            .then(status => {
              if (status.hasPermission) {
                this.downloadFileMobile(path, fileName);
              }
            });
        }
      });
  }


}
