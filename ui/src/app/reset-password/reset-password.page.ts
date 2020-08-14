import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NavController } from '@ionic/angular';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.page.html',
  styleUrls: ['./reset-password.page.scss'],
})
export class ResetPasswordPage implements OnInit {

  public resetForm: FormGroup;
  constructor(
    public navCtrl: NavController,
    private commonDataService: CommonDataService
  ) {
    this.resetForm = new FormGroup({
      cfmpassword: new FormControl('', Validators.required),
      password: new FormControl('',Validators.required)
    });
  }

  ngOnInit() {
  }

  reset() {
    if (this.resetForm.controls.password.value.trim() === this.resetForm.controls.cfmpassword.value.trim()) {
      this.commonDataService.presentLoading();
      const url = AppConstant.appURL + AppConstant.resetPassword;
      const reqObj = {
        "username": AppConstant.usernameforReset,
        "trackId": AppConstant.trackId,
        "newPassword": this.resetForm.controls.cfmpassword.value
      }
      this.commonDataService.commonPostService(url,reqObj).subscribe(data => {
        if (data && data.success) {
          this.commonDataService.dismissLoading();
          this.commonDataService.presentAlert(data.payload);
          this.navCtrl.navigateRoot('login');
        } else {
          if (data.error) {
            this.commonDataService.presentAlert(data.error.message)
          }
        }
      });
    } else {
      this.commonDataService.presentAlert('Password and Confirm Password does not match');
    }
  }

}
