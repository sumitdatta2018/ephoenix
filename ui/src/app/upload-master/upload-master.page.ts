import { Component, OnInit } from '@angular/core';
import { CommonDataService } from '../providers/common-data.service';
import { NavController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AppConstant } from '../app.constant';

@Component({
  selector: 'app-upload-master',
  templateUrl: './upload-master.page.html',
  styleUrls: ['./upload-master.page.scss'],
})
export class UploadMasterPage implements OnInit {

  public uploadMasterForm: FormGroup;
  public uploadTypeList = [];
  public classList = [];
  public subjectList = [];
  public filterSubjectList = [];
  public studyplanId: any;
  public fileName: any;
  public selectedTab = 'upload';
  constructor(private commonDataService: CommonDataService,
    private navCtrl: NavController,
    private localStorage: Storage) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.uploadMasterForm = new FormGroup({
      uploadType: new FormControl('', Validators.required),
      file: new FormControl('', Validators.required),
    });
    this.localStorage.get('access_token').then(token => {
      if (token) {
        AppConstant.access_token = token;
        this.getUploadTypeList();
      }
    });
  }

  ngOnInit() {
  }

  tabClick(tab) {
    this.selectedTab = tab;
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
    if (this.uploadMasterForm.controls.uploadType.value === "STUDY_MATERIAL") {
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
          if (!studyPlan){
            this.commonDataService.presentAlert("Studyplan for selected combination of class and subject is not present, please add rate from Rate Master");
            this.uploadMasterForm.controls.class.reset();
            this.uploadMasterForm.controls.subject.reset();
          } else {
            this.studyplanId = data.payload[0].studyPlanId;
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


  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.fileName = file.name;
      this.uploadMasterForm.get('file').setValue(file);
    }
  }

  upload() {
    this.commonDataService.presentLoading();
    let url = "";
    if (this.uploadMasterForm.controls.uploadType.value === "STUDY_MATERIAL") {
      url = "https://ephoenix.org:7004/camel/upload/" + this.uploadMasterForm.controls.uploadType.value + '?studyPlanId=' + this.studyplanId;
    } else {
      url = "https://ephoenix.org:7004/camel/upload/" + this.uploadMasterForm.controls.uploadType.value;
    }
    const formData = new FormData();
    formData.append('file', this.uploadMasterForm.get('file').value);
    this.commonDataService.secureUploadService(url,formData).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.completeUploadPath) {
        this.commonDataService.presentAlert("Upload Successful");
        this.navCtrl.navigateForward('upload/list');
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message)
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
        }
      }
      console.log(data);
      
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
    });

  }

}
