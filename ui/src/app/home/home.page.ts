import { Component, OnInit, ViewChild } from '@angular/core';
import { IonSlides } from '@ionic/angular';
import { CommonDataService } from '../providers/common-data.service';
import { AppConstant } from '../app.constant';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  @ViewChild('bannerSlide') bannerSlide: IonSlides;
  slideOpts: any;
  noticeList = [];
  constructor(private commonDataService: CommonDataService) {
    this.slideOpts = {
      initialSlide: 0,
      autoplay: true,
      speed: 500,
      lazy: true,
      direction: 'horizontal',
      paginationClickable: true
    };
    this.commonDataService.loadMenus();
    this.commonDataService.loadUser();
    this.getNoticeList();
   }

  ngOnInit() {
  }

  loadAutoPlay() {
    this.bannerSlide.startAutoplay();
      if (this.bannerSlide.isEnd) {
        this.bannerSlide.slideTo(0,500);
      }
  }

  getNoticeList() {
    this.commonDataService.presentLoading();
    const url = AppConstant.appURL + AppConstant.getNoticeList;
    this.commonDataService.commonGetService(url).subscribe(data => {
      if (data && data.success) {
        this.noticeList = data.payload;
        this.commonDataService.dismissLoading();
        this.loadAutoPlay();
      } else {
        if (data && data.error) {
          this.commonDataService.presentAlert(data.error.message);
          this.commonDataService.dismissLoading();
        }
      }
    });
  }

}
