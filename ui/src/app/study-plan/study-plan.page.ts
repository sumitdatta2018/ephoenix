import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';
import * as sha512 from 'js-sha512';
import { Storage } from '@ionic/storage';
import { NavController, Platform } from '@ionic/angular';
import { InAppBrowser, InAppBrowserObject, InAppBrowserOptions } from '@ionic-native/in-app-browser/ngx';
declare var bolt: any;
@Component({
  selector: 'app-study-plan',
  templateUrl: './study-plan.page.html',
  styleUrls: ['./study-plan.page.scss'],
})
export class StudyPlanPage implements OnInit {

  public studyPlanForm: FormGroup;
  public classList = [];
  public subjectList = [];
  public defaultSubjectList = [];
  public suggestedSubjectList = [];
  public activeSubjectList = [];
  public subscribeList = [];
  public filterSubjectList = [];
  public totalPrice = 0;
  public selectedSubjectList = [];
  public user: any;
  public email: any;
  public mobile: any;
  public userId: any;
  constructor(private commonDataService: CommonDataService, public platform: Platform, private inAppBrowser: InAppBrowser,
    private navCtrl: NavController, private localStorage: Storage) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.studyPlanForm = new FormGroup({
      class: new FormControl('', Validators.required),
      subject: new FormControl('', Validators.required)
    });
    this.localStorage.get('userName').then(val => {
      this.user = val;
    });
    this.localStorage.get('loginID').then(val => {
      this.userId = val;
    });
    this.localStorage.get('email').then(val => {
      this.email = val;
    });
    this.localStorage.get('mobile').then(val => {
      this.mobile = val;
    });
    
    this.localStorage.get('access_token').then(token => {
      if (token) {
        AppConstant.access_token = token;
        this.getClassList();
        this.getSubjectList();
      }
    });
   }

  ngOnInit() {
  }

  getClassList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getClassList;
    this.commonDataService.secureGetService(url).subscribe(data => {
      if (data && data.success) {
        this.classList = data.payload.CLASS;
        this.commonDataService.dismissLoading();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.commonDataService.dismissLoading();
        }
      }
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
      this.commonDataService.dismissLoading();
    });
  }

  getSubjectList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getSubjectList;
    this.commonDataService.secureGetService(url).subscribe(data => {
      if (data && data.success) {
        this.subjectList = data.payload.SUBJECT;
        this.localStorage.get('id').then(id => {
          this.getActiveStudyPlans(id);
        });
        this.commonDataService.dismissLoading();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.commonDataService.dismissLoading();
        }
      }
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
      this.commonDataService.dismissLoading();
    });
  }

  getSubjectListByClass() {
    this.filterSubjectList = this.subjectList.filter(subject => subject.classId === this.studyPlanForm.controls.class.value);
    this.filterSubjectList = this.filterSubjectList.map(subject => {
      return {
        ...subject,
        disabled: this.activeSubjectList.find(active => active.subjectId === subject.subjectId) ? true : false
      }
    })
  }

  getDefaultStudyPlan() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan;
    this.commonDataService.secureGetService(url).subscribe(data => {
      if (data && data.success) {
        this.defaultSubjectList = data.payload;
        this.defaultSubjectList.forEach(subject => {
          if (!this.activeSubjectList.find(subjectObj => subjectObj.studyPlanId === subject.studyPlanId)) {
            this.suggestedSubjectList.push(subject);
          }
        });
        this.commonDataService.dismissLoading();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.commonDataService.dismissLoading();
        }
      }
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
      this.commonDataService.dismissLoading();
    });
  }

  getActiveStudyPlans(id) {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan + '?userId=' + id;
    this.commonDataService.secureGetService(url).subscribe(data => {
      if (data && data.success) {
        this.activeSubjectList = data.payload;
        this.getDefaultStudyPlan();
        this.activeSubjectList.forEach(subject => {
          if (subject.classId) {
            let userClass = 
            this.subscribeList.push({className: this.classList.find(classObj => classObj.classId === subject.classId).className,
              subjectName: this.subjectList.find(subjectObj => subjectObj.subjectId === subject.subjectId).subjectName,
              priceRateYearly: subject.priceRateYearly});
          } else {
            this.subscribeList.push({className: subject.studyPlanType, subjectName: subject.studyPlanName, priceRateYearly: subject.priceRateYearly});
          }
        });
        this.commonDataService.dismissLoading();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.commonDataService.dismissLoading();
        }
      }
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
      this.commonDataService.dismissLoading();
    });
  }

  addDefaultSubject(index) {
    if (!this.selectedSubjectList.find(subject => subject.studyPlanId === this.suggestedSubjectList[index].studyPlanId)) {
      this.selectedSubjectList.push({studyPlanId: this.suggestedSubjectList[index].studyPlanId, className: this.suggestedSubjectList[index].studyPlanType, subjectName: this.suggestedSubjectList[index].studyPlanName, priceRateYearly: this.suggestedSubjectList[index].priceRateYearly});
      this.calculateTotal();
      this.suggestedSubjectList.splice(index,1);
    } else {
      this.commonDataService.presentAlert('Already Added');
    }
    
  }

  addToSubjectList() {
    const selectedClass = this.classList.find(item => item.classId === this.studyPlanForm.controls.class.value);
    const selectedSubjectIds = this.studyPlanForm.controls.subject.value.join(',');
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getDefaultStudyPlan + '?clsId=' + this.studyPlanForm.controls.class.value + '&subIds=' + selectedSubjectIds;
    this.commonDataService.secureGetService(url).subscribe(data => {
      if (data && data.success) {
        const subjectRateList = data.payload;
        subjectRateList.forEach(subjectRate => {
          const selectedSubject = this.subjectList.find(subject => subject.subjectId === subjectRate.subjectId);
          this.selectedSubjectList.push({studyPlanId: subjectRate.studyPlanId, className: selectedClass.className, subjectName: selectedSubject.subjectName, priceRateYearly: subjectRate.priceRateYearly});
        });
        this.studyPlanForm.reset();
        this.commonDataService.dismissLoading();
        this.calculateTotal();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
          this.commonDataService.dismissLoading();
        }
      }
    })
  }

  calculateTotal() {
    this.totalPrice = 0;
    if (this.selectedSubjectList.length > 0) {
      this.selectedSubjectList.forEach(subject => {
        this.totalPrice += subject.priceRateYearly;
      });
      console.log('selectedSubjectList',this.selectedSubjectList);
      
    } else {
      this.totalPrice = 0;
    }
    
  }

  remove(index) {
    let removedSubject = this.selectedSubjectList.splice(index,1);
    this.calculateTotal();
    if (removedSubject[0].className === 'OTHERS') {
      let subject =  {
        studyPlanId: removedSubject[0].studyPlanId,
        studyPlanType: removedSubject[0].className,
        studyPlanName: removedSubject[0].subjectName,
        priceRateYearly: removedSubject[0].priceRateYearly
      }
      this.suggestedSubjectList.push(subject);
    }
  }

  payAmount() {
    let superThis = this;
    let studyPlanIds = this.selectedSubjectList.map(({studyPlanId, ...rest}) => studyPlanId);
    let merchantID = '4961908';
    let key = 'AyghwXQW';
    let salt = 'vOPsrHAKBs';
    let txnid = 'PH' + new Date(Date.now()).getTime();
    let amount = this.totalPrice;
    let productinfo = 'Phoenix Subscription Fee';
    let udf1 = this.userId;
    let udf2 = studyPlanIds.join(',');
    let udf3 = '';
    let udf4 = '';
    let udf5 = '';
    let mystring = key + '|' + txnid + '|' + amount + '|' + productinfo + '|' + this.user + '|' + this.email + '|' + udf1 + '|' + udf2 + '|' + udf3 + '|' + udf4 + '|' + udf5 + '||||||' + salt;
    let hash = sha512.sha512(mystring);
    let data = {
      key: key,
      txnid: txnid,
      hash: hash,
      amount: this.totalPrice.toString(),
      firstname: this.user,
      email: this.email,
      phone: this.mobile,
      productinfo: productinfo,
      surl : 'https://ephoenix.org:7004/api/transactions',
      furl: 'https://ephoenix.org:7004/api/transactions',
      mode:'dropout',// non-mandatory for Customized Response Handling,
      udf1: this.userId,
      udf2: studyPlanIds.join(',')
    }
    if (this.platform.is('android') && !this.platform.is('mobileweb')) {
      let url = "https://ephoenix.org/payuBiz.html?amt=" + this.totalPrice.toString() + "&productinfo=" + productinfo + "&name=" + this.user + "&mobileNo=" + this.mobile + "&email=" + this.email + "&bookingId=" + txnid + "&salt=" + salt + "&key=" + key + "&hash=" + hash + "&udf1=" + udf1 + "&udf2=" + udf2 + "&udf3=" + udf3 + "&udf4=" + udf4 + "&udf5=" + udf5;
      let options: InAppBrowserOptions = {
        location: "no",
        clearcache: "yes",
        hardwareback: "no",
        zoom: "yes",
        closebuttoncaption: 'close',
        toolbar: "no"
      }
      const browser: InAppBrowserObject = this.inAppBrowser.create(url, '_blank', options);
      browser.on('loadstart').subscribe(event => {
        browser.executeScript({
          file: "https://ephoenix.org/payumoneyPaymentGateway.js"
        });
        if (event.url === 'https://ephoenix.org') {
          browser.close();
        }
      })
    } else {
      var Handler = {

        responseHandler: function(BOLT){
          console.log('success', BOLT);
          let res = JSON.parse(JSON.stringify(BOLT))
          if (res.response.txnStatus === "CANCEL") {
            superThis.commonDataService.presentToast('You have canceled the transaction');
          }
          if (res.response.txnStatus === "SUCCESS") {
            superThis.navCtrl.navigateRoot('home');
          }
          if (res.response.txnStatus === "FAILED") {
            superThis.navCtrl.navigateRoot('home');
            superThis.commonDataService.presentToast('Your transaction has been failed');
          }
          
          // your payment response Code goes here, BOLT is the response object
  
        },
        catchException: function(BOLT){
          console.log('failure', BOLT);
          // if (BOLT) {
          //   this.commonDataService.presentAlert(BOLT.message);
          // }
          // the code you use to handle the integration errors goes here
  
        }
      }
      console.log(data);
      
      bolt.launch( data , Handler );
    }
  }



}
