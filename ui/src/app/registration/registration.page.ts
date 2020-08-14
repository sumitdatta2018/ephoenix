import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.page.html',
  styleUrls: ['./registration.page.scss'],
})
export class RegistrationPage implements OnInit {

  public registrationForm: FormGroup;
  public districtList = [];
  public stateList = [];
  public showDistrict = true;
  constructor(private commonDataService: CommonDataService, private navCtrl: NavController) { 
    this.registrationForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.compose([Validators.required, Validators.pattern(/^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/)])),
      phone: new FormControl('', Validators.compose([Validators.required,Validators.minLength(10),Validators.maxLength(10)])),
      state: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      institute: new FormControl('', Validators.required),
      district: new FormControl('', Validators.required),
      pin: new FormControl('', Validators.compose([Validators.required,Validators.minLength(6),Validators.maxLength(6)])),
      password: new FormControl('', Validators.required),
      cnfPassword: new FormControl('', Validators.required),
    });
    this.getDistrictAndStateList();
  }

  ngOnInit() {
  }

  getDistrictAndStateList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getDistrictAndStateTypeList;
    this.commonDataService.commonGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data.success) {
        this.districtList = data.payload.DISTRICT;
        this.stateList = data.payload.STATE;
      }
    });
  }

  goToLogin() {
    this.navCtrl.navigateRoot('login');
  }

  selectState() {
    if (this.registrationForm.controls.state.value === 2) {
      this.showDistrict = false;
      this.registrationForm.controls.district.setValue('');
    } else {
      this.showDistrict = true;
      this.registrationForm.controls.district.setValue('');
    }
  }

  register() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.registrationURL;
    const requestBody = {
      "userLoginId": this.registrationForm.controls.email.value,
      "password": this.registrationForm.controls.cnfPassword.value,
      "name": this.registrationForm.controls.firstName.value + ' ' + this.registrationForm.controls.lastName.value, 
      "email": this.registrationForm.controls.email.value,
      "mobile": this.registrationForm.controls.phone.value,
      "address": this.registrationForm.controls.address.value,
      "district": this.registrationForm.controls.state.value !== 2 ? this.districtList.find(district => district.districtId === this.registrationForm.controls.district.value).districtName : this.registrationForm.controls.district.value,
      "pin": this.registrationForm.controls.pin.value,
      "districtId": this.registrationForm.controls.district.value,
      "stateId": this.registrationForm.controls.state.value,
      "instituteName": this.registrationForm.controls.institute.value,
    }
    this.commonDataService.commonPostService(url,requestBody).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data.success) {
        this.goToLogin();
      } else {
        if (data.error) {
          this.commonDataService.presentAlert(data.error.message)
        }
      }
    })
  }

}
