import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.page.html',
  styleUrls: ['./forgot-password.page.scss'],
})
export class ForgotPasswordPage implements OnInit {

  public email: any;
  public otp: any;
  public showOtpBox: boolean = false;
  public time: any;
  public enableResend: boolean = false;
  constructor(public navCtrl: NavController,
    private commonDataService: CommonDataService,) { }

  ngOnInit() {
  }

  generateOtp() {
    this.commonDataService.presentLoading();
    const reqBody = {
      "username": this.email
    }
    const url = AppConstant.appURL + AppConstant.generateOtp;
    
    this.commonDataService.commonPostService(url,reqBody).subscribe(data => {
      if (data && data.success) {
        this.commonDataService.dismissLoading();
        this.commonDataService.presentAlert(data.payload);
        this.showOtpBox = true;
        this.time = 60;
        this.enableResend = false;
        this.enableResendOtp();
      } else {
        if (data.error) {
          this.commonDataService.presentAlert(data.error.message)
        }
      }
    });
  }

  validateOtp() {
    this.commonDataService.presentLoading();
    const reqBody = {
      "username": this.email,
      "otp": this.otp
    }
    const url = AppConstant.appURL + AppConstant.validateOtp;
    
    this.commonDataService.commonPostService(url,reqBody).subscribe(data => {
      if (data && data.success) {
        this.commonDataService.dismissLoading();
        this.commonDataService.presentAlert('Your one time password has been validated successfully');
        AppConstant.trackId = data.payload.trackId;
        AppConstant.usernameforReset = this.email;
        this.navCtrl.navigateForward('reset-password');
      } else {
        if (data.error) {
          this.commonDataService.presentAlert(data.error.message)
        }
      }
    });
  }

  enableResendOtp() {
    let i  = setInterval(() => {
      if (this.time > 0) {
        this.time = this.time - 1;
      }
    }, 1000);
    setTimeout(() => {
      clearInterval(i);
      this.enableResend = true;
    }, 60000);
  }

}
