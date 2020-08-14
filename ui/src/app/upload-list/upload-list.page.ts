import { Component, OnInit } from '@angular/core';
import { Storage } from '@ionic/storage';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';
import { NavController, AlertController, Platform } from '@ionic/angular';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx';
import { FileTransfer, FileTransferObject } from '@ionic-native/file-transfer/ngx';
import { File } from '@ionic-native/file/ngx';
import { Downloader, DownloadRequest, NotificationVisibility } from '@ionic-native/downloader/ngx';

@Component({
  selector: 'app-upload-list',
  templateUrl: './upload-list.page.html',
  styleUrls: ['./upload-list.page.scss'],
})
export class UploadListPage implements OnInit {

  public uploadList = [];
  public filteredUploadList = [];
  public uploadTypeList = [];
  public classList = [];
  public subjectList = [];
  public filterSubjectList = [];
  public uploadMasterForm: FormGroup;
  fileTransfer: FileTransferObject;

  constructor(private commonDataService: CommonDataService,
    private navCtrl: NavController, private localStorage: Storage,
    private androidPermissions: AndroidPermissions,
    private transfer: FileTransfer,
    public platform: Platform,
    private file: File,
    private downloader: Downloader,
    public alertController: AlertController) {
    this.fileTransfer = this.transfer.create();
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.uploadMasterForm = new FormGroup({
      uploadType: new FormControl('', Validators.required),
    });
    this.localStorage.get('access_token').then(token => {
      if (token) {
        AppConstant.access_token = token;
        this.getUploadList();
        this.getUploadTypeList();
      }
    });
  }

  ngOnInit() {
  }

  getUploadTypeList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.uploadType;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.uploadTypeList = data.payload.UPLOAD_TYPE;
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

  getUploadType() {
    if (this.uploadMasterForm.controls.uploadType.value === 5) {
      this.getUploadListByType(this.uploadMasterForm.controls.uploadType.value);
      this.uploadMasterForm.addControl('class', new FormControl('', Validators.required));
      this.uploadMasterForm.addControl('subject', new FormControl('', Validators.required));
      this.getClassList();
    } else {
      if (this.uploadMasterForm.controls.class) {
        this.uploadMasterForm.removeControl('class');
      }
      if (this.uploadMasterForm.controls.subject) {
        this.uploadMasterForm.removeControl('subject');
      }
      this.getUploadListByType(this.uploadMasterForm.controls.uploadType.value);
    }
  }

  getClassList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getClassList;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.classList = data.payload.CLASS;
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

  getSubjectList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getSubjectList;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.subjectList = data.payload.SUBJECT;
        this.filterSubjectList = this.subjectList.filter(subject => subject.classId === this.uploadMasterForm.controls.class.value);
        this.uploadMasterForm.controls.subject.reset();
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

  getStudyPlanId() {
    if (this.uploadMasterForm.controls.class.value && this.uploadMasterForm.controls.subject.value) {
      this.commonDataService.presentLoading();
      const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan + '?clsId=' + this.uploadMasterForm.controls.class.value + '&subIds=' + this.uploadMasterForm.controls.subject.value;
      this.commonDataService.secureGetService(url).subscribe(data => {
        this.commonDataService.dismissLoading();
        if (data && data.success) {
          let studyPlan = data.payload[0];
          if (studyPlan) {
            this.filteredUploadList = this.uploadList.filter(file => file.studyPlanId === data.payload[0].studyPlanId);
            this.filteredUploadList = this.filteredUploadList.map(upload => {
              let file = upload.fileName.split('.');
              return {
                ...upload,
                fileExt: file.pop()
              }
            })
          } else {
            this.filteredUploadList = [];
          }
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
  }

  getUploadList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.uploadList;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.uploadList = data.payload;
        this.filteredUploadList = this.uploadList.map(upload => {
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

  getUploadListByType(id) {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.uploadList + '?uploadTypeIds=' + id;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.uploadList = data.payload;
        this.filteredUploadList = this.uploadList.map(upload => {
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

  async deleteFile(file) {
    console.log('delete clicked');
    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: "The Phoenix",
      message: "Do you want to delete this file?",
      buttons: [
        {
          text: 'Yes',
          handler: (blah) => {
            this.deleteFileConfirm(file);
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

  deleteFileConfirm(file) {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.uploadList + '/' + file.uploadId;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.commonDataService.presentAlert('File deleted successfully');
        this.getUploadList();
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

}
