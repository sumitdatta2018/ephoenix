import { Component, OnInit } from '@angular/core';
import { AppConstant } from '../app.constant';
import { Storage } from '@ionic/storage';
import { CommonDataService } from '../providers/common-data.service';
import { NavController, AlertController, Platform } from '@ionic/angular';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx';
import { FileTransfer } from '@ionic-native/file-transfer/ngx';
import { File } from '@ionic-native/file/ngx';
import { Downloader, DownloadRequest, NotificationVisibility } from '@ionic-native/downloader/ngx';

@Component({
  selector: 'app-study-material',
  templateUrl: './study-material.page.html',
  styleUrls: ['./study-material.page.scss'],
})
export class StudyMaterialPage implements OnInit {

  public studyMaterialList = [];
  public originalStudyMaterialList = [];
  public userId: any;
  public modifiedStudyMaterialList = [];
  constructor(private commonDataService: CommonDataService,
    private navCtrl: NavController,
    private androidPermissions: AndroidPermissions,
    private transfer: FileTransfer,
    public platform: Platform,
    private file: File,
    private downloader: Downloader,
    private localStorage: Storage,
    public alertController: AlertController) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.localStorage.get('access_token').then(token => {
      if (token) {
        AppConstant.access_token = token;
        this.localStorage.get('id').then(id => {
          this.userId = id;
          this.getStudyMaterialList(this.userId);
        });

      }
    });
  }

  ngOnInit() {
  }

  getStudyMaterialList(id) {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.uploadList + '?userId=' + id;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.studyMaterialList = data.payload;
        this.studyMaterialList = this.studyMaterialList.map(upload => {
          let file = upload.fileName.split('.');
          return {
            ...upload,
            fileExt: file.pop()
          }
        });
        this.originalStudyMaterialList = JSON.parse(JSON.stringify(this.studyMaterialList));
        this.getModifiedStudyMaterialList();
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

  searchMaterial(searchText: string) {
    this.studyMaterialList = JSON.parse(JSON.stringify(this.originalStudyMaterialList));
    if (searchText.length > 0) {
      this.studyMaterialList = this.studyMaterialList.filter(material => material.fileName.toLowerCase().includes(searchText));
    } else {
      this.studyMaterialList = JSON.parse(JSON.stringify(this.originalStudyMaterialList));
    }
    this.getModifiedStudyMaterialList();
  }

  getModifiedStudyMaterialList() {
    let classlist = [];
    this.modifiedStudyMaterialList = [];
    this.studyMaterialList.forEach(material => {
      if (classlist.indexOf(material.className) === -1) {
        classlist.push(material.className);
      }
    });
    classlist.forEach(className => {
      let filterMaterialListByClass = this.studyMaterialList.filter(material => material.className === className);
      let subjectList = [];
      filterMaterialListByClass.forEach(material => {
        if (subjectList.indexOf(material.subjectName) === -1) {
          subjectList.push(material.subjectName);
        }
      });
      let subjectArr = [];
      subjectList.forEach(subject => {
        let subjectObj = {
          subjectName: subject,
          files: this.studyMaterialList.filter(material => material.className === className && material.subjectName === subject)
        }
        subjectArr.push(subjectObj);
      });
      this.modifiedStudyMaterialList.push({
        className: className,
        subjects: subjectArr
      })
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
