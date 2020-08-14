import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  
  public loginForm: FormGroup;
  constructor(public navCtrl: NavController,
    private commonDataService: CommonDataService,
    private localStorage: Storage) {
    this.loginForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('',Validators.required)
    });
   }

  ngOnInit() {
  }

  goToRegistration() {
    this.navCtrl.navigateRoot('registration');
  }

  prelogin() {
    const reqBody = {
      "client_id": "ephoenix",
      "client_secret": "7b6b4f28-224e-4045-a6ed-d10014e7ac7f",
      "username": this.loginForm.controls.email.value,
	    "password": this.loginForm.controls.password.value
    }
    const url = AppConstant.appURL + AppConstant.token;
    this.commonDataService.presentLoading();
    this.commonDataService.commonPostService(url,reqBody).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data.access_token) {
        AppConstant.access_token = data.access_token;
        this.localStorage.set('access_token',data.access_token);
        this.login(data.access_token);
      } else {
        if (data.error) {
          this.commonDataService.presentAlert(data.error.message)
        }
      }
    });
  }

  login(access_token) {
    const reqBody = {
      "userLoginId": this.loginForm.controls.email.value,
	    "password": this.loginForm.controls.password.value
    }
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.loginURL;
    this.commonDataService.loginService(url,reqBody,access_token).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data.success) {
        AppConstant.name = data.payload.name;
        this.localStorage.set('id',data.payload.id);
        this.localStorage.set('userName',data.payload.name);
        this.localStorage.set('loginID', data.payload.userLoginId);
        AppConstant.email = data.payload.email;
        this.localStorage.set('email',data.payload.email);
        AppConstant.mobile = data.payload.mobile;
        this.localStorage.set('mobile',data.payload.mobile);
        AppConstant.menuResponse = data.payload.menus;
        this.localStorage.set('menuResponse',data.payload.menus);
        this.navCtrl.navigateRoot('home');
      } else {
        if (data.error) {
          this.commonDataService.presentAlert(data.error.message)
        }
        this.localStorage.clear();
      }
    },
    error => {
      this.localStorage.clear();
    });
    
  }

  forgotPassword() {
    this.navCtrl.navigateForward('forgot-password');
  }

}
