import { Component, OnInit, Input } from '@angular/core';
import { ModalController, Platform } from '@ionic/angular';
import { saveAs } from 'file-saver';
import { CommonDataService } from '../providers/common-data.service';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx';
import { FileTransfer, FileTransferObject } from '@ionic-native/file-transfer/ngx';
import { File } from '@ionic-native/file/ngx';
import { Downloader, DownloadRequest, NotificationVisibility } from '@ionic-native/downloader/ngx';

@Component({
  selector: 'app-pic-view',
  templateUrl: './pic-view.page.html',
  styleUrls: ['./pic-view.page.scss'],
})
export class PicViewPage implements OnInit {


  @Input() url: string;
  @Input() fileName: string;
  fileTransfer: FileTransferObject;
  constructor(public modalCtrl: ModalController,
    private commonDataService: CommonDataService,
    private androidPermissions: AndroidPermissions,
    private transfer: FileTransfer,
    public platform: Platform,
    private downloader: Downloader,
    private file: File) {
    this.fileTransfer = this.transfer.create();
  }


  ngOnInit() {
  }

  dismiss() {
    this.modalCtrl.dismiss({
      'dismissed': true
    });
  }

  downloadFile() {
    if (this.platform.is('android')) {
      this.getPermission(this.url, this.fileName)
    } else {
      if (this.platform.is('android') && !this.platform.is('mobileweb')) {
        this.getPermission(this.url, this.fileName)
      } else {
        fetch(this.url).then(function (t) {
          return t.blob().then((b) => {
            var a = document.createElement("a");
            a.href = URL.createObjectURL(b);
            a.setAttribute("download", this.fileName);
            a.click();
          }
          );
        });
      }
    }
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
        console.log('File downloaded at:' + location);
        this.commonDataService.dismissLoading();
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
