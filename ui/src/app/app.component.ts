import { Component, OnInit } from '@angular/core';

import { Platform, NavController, MenuController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { CommonDataService } from './providers/common-data.service';
import { AppConstant } from './app.constant';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent implements OnInit {
  public selectedIndex = 0;
  public menuResponse: any;
  public menus = [];
  public user: any;
  public email: any;
  public selectedMenu: any;
  public aboutUsActive = false;
  public adminActive = false;
  public studentActive = false;
  public downloadActive = false;
  public showLogout = false;
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private commonDataService: CommonDataService,
    public navCtrl: NavController,
    private localStorage: Storage,
    private menuCtrl: MenuController
  ) {
    this.initializeApp();

    this.commonDataService.menuSubscription.subscribe(data => {
      this.localStorage.get('access_token').then(token => {
        if (token) {
          AppConstant.access_token = token;
          this.showLogout = true;
          this.localStorage.get('menuResponse').then(data => {
            if (data) {
              this.menuResponse = data;
              this.populateMenu();
            } else {
              this.getMenuList();
              this.localStorage.clear();
            }
          });
        } else {
          this.getMenuList();
          this.localStorage.clear();
        }
      });
    });

    this.commonDataService.userSubscription.subscribe(data => {
      this.localStorage.get('userName').then(val => {
        this.user = val;
      });
      this.localStorage.get('email').then(val => {
        this.email = val;
      });
    });
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  logout() {
    const url = AppConstant.appURL + AppConstant.logout;
    this.commonDataService.securePostService(url, { token: AppConstant.access_token }).subscribe(data => {
      if (data.success) {
        this.commonDataService.presentAlert(data.payload);
      }
    })
    this.localStorage.clear();
    AppConstant.access_token = '';
    this.navCtrl.navigateRoot('home');
    this.menuCtrl.close();
    this.getMenuList();
    this.showLogout = false;
    this.user = null;
    this.email = null;

  }

  ngOnInit() { }

  populateMenu() {
    this.menus = [];
    this.menuResponse.forEach(menuElement => {
      if (menuElement.parentMenu === '0') {
        this.menus.push(menuElement)
      }
    });
    this.menuResponse.forEach(menuElement => {
      const currentMenu = menuElement;
      if (currentMenu.parentMenu !== '0') {
        const index = this.menus.findIndex(menu => menu.name === currentMenu.parentMenu);
        if (this.menus[index].submenu && Array.isArray(this.menus[index].submenu)) {
          this.menus[index].submenu.push(currentMenu);
        } else {
          this.menus[index].submenu = [currentMenu];
        }
      }
    });
    this.selectedMenu = 'HOME';
  }

  openPage(menu) {
    this.downloadActive = false;
    this.studentActive = false;
    this.aboutUsActive = false;
    this.adminActive = false;
    this.selectedMenu = menu.name;
    if (menu.name === 'HOME') {
      this.navCtrl.navigateRoot('home');
      this.menuCtrl.close();
    } if (menu.name === 'LOGIN') {
      this.navCtrl.navigateRoot('login');
      this.menuCtrl.close();
    } else if (menu.name === 'ABOUT US') {
      this.aboutUsActive = !this.aboutUsActive;
    } else if (menu.name === 'ADMIN') {
      this.adminActive = !this.adminActive;
    } else if (menu.name === 'STUDENT ZONE') {
      this.studentActive = !this.studentActive;
    } else if (menu.name === 'DOWNLOAD') {
      this.downloadActive = !this.downloadActive
    } else if (menu.name === 'GALLERY') {
      this.navCtrl.navigateForward('gallary');
      this.menuCtrl.close();
    } else if (menu.name === 'STUDY PLAN') {
      this.navCtrl.navigateForward('study-plan');
      this.menuCtrl.close();
    } else if (menu.name === 'SPOKEN ENGLISH') {
      this.navCtrl.navigateForward('spoken-english');
      this.menuCtrl.close();
    } else if (menu.name === 'ONLINE MOCKTEST') {
      this.commonDataService.presentAlert('Coming Soon, development is going on');
      this.menuCtrl.close();
    } else if (menu.name === 'SCHOLARSHIP') {
      this.navCtrl.navigateForward('scholarship');
      this.menuCtrl.close();
    } else if (menu.name === 'IMPORTANT LINKS') {
      this.navCtrl.navigateForward('important-link');
      this.menuCtrl.close();
    } else if (menu.name === 'CONTACT US') {
      this.navCtrl.navigateForward('contact-us');
      this.menuCtrl.close();
    } else if (menu.name === 'VISION & MISSION') {
      this.navCtrl.navigateForward('mission-vission');
      this.menuCtrl.close();
    } else if (menu.name === 'HISTORY') {
      this.navCtrl.navigateForward('history');
      this.menuCtrl.close();
    } else if (menu.name === 'SOCIETY') {
      this.navCtrl.navigateForward('society');
      this.menuCtrl.close();
    } else if (menu.name === 'TRAINING') {
      this.navCtrl.navigateForward('trainning');
      this.menuCtrl.close();
    } else if (menu.name === 'OTHERS') {
      this.navCtrl.navigateForward('others');
      this.menuCtrl.close();
    } else if (menu.name === 'RATE MASTER') {
      this.navCtrl.navigateForward('rate-master');
      this.menuCtrl.close();
    } else if (menu.name === 'UPLOAD MASTER') {
      this.navCtrl.navigateForward('upload');
      this.menuCtrl.close();
    } else if (menu.name === 'NOTICE MASTER') {
      this.navCtrl.navigateForward('notice');
      this.menuCtrl.close();
    } else if (menu.name === 'STUDY MATERIAL') {
      this.navCtrl.navigateForward('study-material');
      this.menuCtrl.close();
    } else if (menu.name === 'SAMPLE NOTE') {
      this.navCtrl.navigateForward('sample-note');
      this.menuCtrl.close();
    } else if (menu.name === 'SYLLABUS') {
      this.navCtrl.navigateForward('syllabus');
      this.menuCtrl.close();
    } else if (menu.name === 'RESULT') {
      this.commonDataService.presentAlert('Coming Soon, development is going on');
      this.menuCtrl.close();
    } else if (menu.name === 'USER LIST') {
      this.navCtrl.navigateForward('user-management');
      this.menuCtrl.close();
    } else if (menu.name === 'FEEDBACK LIST') {
      this.navCtrl.navigateForward('feedback-list');
      this.menuCtrl.close();
    }
  }

  getMenuList() {
    const url = AppConstant.appURL + AppConstant.menuURL;
    this.commonDataService.presentLoading();
    this.commonDataService.commonGetService(url).subscribe(data => {
      this.commonDataService.dismissLoading();
      if (data.success) {
        this.menuResponse = data.payload;
        this.populateMenu();
      }
    })
  }
}
