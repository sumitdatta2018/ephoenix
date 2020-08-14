import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonDataService } from '../providers/common-data.service';
import { NavController } from '@ionic/angular';
import { AppConstant } from '../app.constant';
import { Storage } from '@ionic/storage';
@Component({
  selector: 'app-spoken-english-registration',
  templateUrl: './spoken-english-registration.page.html',
  styleUrls: ['./spoken-english-registration.page.scss'],
})
export class SpokenEnglishRegistrationPage implements OnInit {

  public registrationForm: FormGroup;
  public districtList = [];
  public stateList = [];
  public casteList = [];
  public religionList = [];
  public showDistrict = true;
  public userId: any;
  constructor(private commonDataService: CommonDataService, private navCtrl: NavController,private localStorage: Storage) {
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.registrationForm = new FormGroup({
      name: new FormControl('', Validators.required),
      gender: new FormControl('', Validators.required),
      fatherName: new FormControl('', Validators.required),
      motherName: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      state: new FormControl('', Validators.required),
      district: new FormControl('', Validators.required),
      pin: new FormControl('', Validators.compose([Validators.required,Validators.minLength(6),Validators.maxLength(6)])),
      email: new FormControl('', Validators.compose([Validators.required, Validators.pattern(/^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/)])),
      phone: new FormControl('', Validators.compose([Validators.required,Validators.minLength(10),Validators.maxLength(10)])),
      dob: new FormControl('', Validators.required),
      qualification: new FormControl('', Validators.required),
      religion: new FormControl('', Validators.required),
      caste: new FormControl('', Validators.required),
      aadhaar: new FormControl('', Validators.compose([Validators.required,Validators.minLength(12),Validators.maxLength(12)])),
    });
    this.getDistrictAndStateList();
    this.getCasteAndReligionList();
    this.localStorage.get('id').then(val => {
      this.userId = val;
      this.getUserDetails();
    });
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

  getCasteAndReligionList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.casteReligionList;
    this.commonDataService.commonGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data.success) {
        this.casteList = data.payload.CASTE;
        this.religionList = data.payload.RELIGION;
      }
    });
  }

  getUserDetails() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getUserById + this.userId;
    this.commonDataService.secureGetService(url).subscribe(data => {
      if (data && data.success) {
        let dobArr = null;
        if (data.payload.dob) {
          dobArr = data.payload.dob.split('/');
        } 
        this.registrationForm.patchValue({
          name: data.payload.name,
          address: data.payload.address,
          email: data.payload.email,
          phone: data.payload.mobile,
          pin: data.payload.pin,
          state: data.payload.stateId,
          district: data.payload.stateId === 1 ? data.payload.districtId : data.payload.district,
          aadhaar: data.payload.aadharNum,
          caste: data.payload.casteId,
          religion: data.payload.religionId,
          dob: dobArr ? new Date(dobArr[2],dobArr[1] - 1,dobArr[0]).toISOString() : null,
          qualification: data.payload.lastQualification,
          fatherName: data.payload.fatherName,
          gender: data.payload.gender,
          motherName: data.payload.mothername,
        });
        if (data.payload.stateId === 2) {
          this.showDistrict = false;
        }
        if (this.registrationForm.valid) {
          this.registrationForm.disable();
          this.commonDataService.presentAlert('You have already registered');
        }
        this.commonDataService.dismissLoading();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.commonDataService.dismissLoading();
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
        }
      }
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
      this.commonDataService.dismissLoading();
    });
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
    console.log(this.registrationForm.value);
    this.commonDataService.presentLoading();
    let dobstring = this.registrationForm.controls.dob.value.split('T')[0];
    let dateArr = dobstring.split('-');
    let date = dateArr[2] + '/' + dateArr[1] + '/' + dateArr[0];
    const reqObj = {
      "gender": this.registrationForm.controls.gender.value,
      "fatherName": this.registrationForm.controls.fatherName.value,
      "mothername": this.registrationForm.controls.motherName.value,
      "dob": date,
      "lastQualification": this.registrationForm.controls.qualification.value,
      "aadharNum": this.registrationForm.controls.aadhaar.value,
      "casteId": this.registrationForm.controls.caste.value,
      "religionId": this.registrationForm.controls.religion.value
    }
    const url = AppConstant.appURL + AppConstant.getUserById + this.userId;
    this.commonDataService.securePostService(url,reqObj).subscribe(data =>{
      if (data && data.success) {
        this.commonDataService.presentAlert("Spoken English Registration Done Successfully");
        this.commonDataService.dismissLoading();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        } else if (!data) {
          this.commonDataService.dismissLoading();
          this.navCtrl.navigateRoot('login');
          this.localStorage.clear();
        }
      }
    },
    error => {
      this.navCtrl.navigateRoot('login');
      this.localStorage.clear();
      this.commonDataService.dismissLoading();
    });
  }

}
