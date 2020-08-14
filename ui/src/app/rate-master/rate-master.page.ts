import { Component, OnInit } from '@angular/core';
import { AppConstant } from '../app.constant';
import { CommonDataService } from '../providers/common-data.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NavController } from '@ionic/angular';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-rate-master',
  templateUrl: './rate-master.page.html',
  styleUrls: ['./rate-master.page.scss'],
})
export class RateMasterPage implements OnInit {

  public classList = [];
  public subjectList = [];
  public studyplanList = [];
  public studyplanTypeList = [];
  public filterSubjectList = [];
  public rateMasterForm: FormGroup;
  public subjectRate: any;
  constructor(private commonDataService: CommonDataService,
    private navCtrl: NavController,
    private localStorage: Storage) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.rateMasterForm = new FormGroup({
      studyplanType: new FormControl('', Validators.required),
      price: new FormControl('',Validators.compose([Validators.required,Validators.pattern('^[0-9]*$')])),
      active: new FormControl(false, Validators.required),
    });
    this.localStorage.get('access_token').then(token => {
      if (token) {
        AppConstant.access_token = token;
        this.getStudyPlanTypeList();
      }
    });
   }

  ngOnInit() {
  }

  getStudyPlanTypeList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.studyplanTypelist;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.studyplanTypeList = data.payload.STUDY_PLAN_TYPE;
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

  getStudyplanList() {
    if (this.rateMasterForm.controls.studyplanType.value === 1) {
      this.rateMasterForm.addControl('category', new FormControl('', Validators.required));
      this.categoryList();
      if (this.rateMasterForm.controls.class) {
        this.rateMasterForm.removeControl('class');
      }
      if (this.rateMasterForm.controls.subject) {
        this.rateMasterForm.removeControl('subject');
      }
      this.rateMasterForm.controls.price.reset();
      this.rateMasterForm.controls.active.reset();
      this.subjectRate = null;
    } else if (this.rateMasterForm.controls.studyplanType.value === 2) {
      this.rateMasterForm.addControl('class', new FormControl('', Validators.required));
      this.rateMasterForm.addControl('subject', new FormControl('', Validators.required));
      this.getClassList();
      if (this.rateMasterForm.controls.category) {
        this.rateMasterForm.removeControl('category');
      }
      this.rateMasterForm.controls.price.reset();
      this.rateMasterForm.controls.active.reset();
      this.subjectRate = null;
    }
  }

  categoryList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan;
    this.commonDataService.secureGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data && data.success) {
        this.studyplanList = data.payload;
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

  getCategoryRate() {
    this.subjectRate = this.studyplanList.find(studyplan => studyplan.studyPlanId === this.rateMasterForm.controls.category.value);
    this.rateMasterForm.controls.price.setValue(this.subjectRate.priceRateYearly);
    this.rateMasterForm.controls.active.setValue(this.subjectRate.isActive === 'Y' ? true : false);
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
        this.filterSubjectList = this.subjectList.filter(subject => subject.classId === this.rateMasterForm.controls.class.value);
        this.rateMasterForm.controls.subject.reset();
        this.rateMasterForm.controls.price.reset();
        this.rateMasterForm.controls.active.reset();
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

  getYearlyRate() {
    if (this.rateMasterForm.controls.class.value && this.rateMasterForm.controls.subject.value) {
      this.commonDataService.presentLoading();
      const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan + '?clsId=' + this.rateMasterForm.controls.class.value + '&subIds=' + this.rateMasterForm.controls.subject.value;
      this.commonDataService.secureGetService(url).subscribe(data => {
        this.commonDataService.dismissLoading();
        if (data && data.success) {
          this.subjectRate = data.payload[0];
          if (this.subjectRate) {
            this.rateMasterForm.controls.price.setValue(this.subjectRate.priceRateYearly);
            this.rateMasterForm.controls.active.setValue(this.subjectRate.isActive === 'Y' ? true : false);
          } else {
            this.subjectRate = null;
            this.rateMasterForm.controls.price.setValue('');
            this.rateMasterForm.controls.active.setValue(false);
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

  updatePrice() {
    if (this.subjectRate) {
      let reqBody = {
        "studyPlanTypeId": this.rateMasterForm.controls.studyplanType.value,
        "priceRateYearly": this.rateMasterForm.controls.price.value,
        "isActive": this.rateMasterForm.controls.active.value ? 'Y' : 'N',
        "isDefault": this.rateMasterForm.controls.studyplanType.value === 1 ? "Y" : "N",
        "studyPlanName": this.subjectRate.studyPlanName
      }
      this.commonDataService.presentLoading();
      const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan + '/' + this.subjectRate.studyPlanId;
      this.commonDataService.securePostService(url,reqBody).subscribe(data => {
        this.commonDataService.dismissLoading();
        if (data && data.success) {
          this.commonDataService.presentAlert('Rate updated successfully');
          if (this.rateMasterForm.controls.class) {
            this.rateMasterForm.removeControl('class');
          }
          if (this.rateMasterForm.controls.subject) {
            this.rateMasterForm.removeControl('subject');
          }
          if (this.rateMasterForm.controls.category) {
            this.rateMasterForm.removeControl('category');
          }
          this.rateMasterForm.reset();
          this.subjectRate = null;
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

    } else {
      let reqBody = {
        "studyPlanTypeId": this.rateMasterForm.controls.studyplanType.value,
        "priceRateYearly": this.rateMasterForm.controls.price.value,
        "isActive": this.rateMasterForm.controls.active.value ? 'Y' : 'N',
        "isDefault": this.rateMasterForm.controls.studyplanType.value === 1 ? "Y" : "N",
        'classId': this.rateMasterForm.controls.class.value,
        'subjectId': this.rateMasterForm.controls.subject.value,
        "studyPlanName": this.classList.find(classObj => classObj.classId === this.rateMasterForm.controls.class.value).className + '_' + this.filterSubjectList.find(subject => subject.subjectId === this.rateMasterForm.controls.subject.value).subjectName
      }
      let requestList = [];
      requestList.push(reqBody);
      this.commonDataService.presentLoading();
      const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan;
      this.commonDataService.securePostService(url,requestList).subscribe(data => {
        this.commonDataService.dismissLoading();
        if (data && data.success) {
          this.commonDataService.presentAlert('Rate added successfully');
          if (this.rateMasterForm.controls.class) {
            this.rateMasterForm.removeControl('class');
          }
          if (this.rateMasterForm.controls.subject) {
            this.rateMasterForm.removeControl('subject');
          }
          if (this.rateMasterForm.controls.category) {
            this.rateMasterForm.removeControl('category');
          }
          this.rateMasterForm.reset();
          this.subjectRate = null;
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

}
