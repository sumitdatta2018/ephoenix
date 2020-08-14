import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-custom-header',
  templateUrl: './custom-header.component.html',
  styleUrls: ['./custom-header.component.scss'],
})
export class CustomHeaderComponent implements OnInit {

  constructor(private navCtrl: NavController) { 
    console.log('header loaded');
    
  }

  ngOnInit() {}

  goToHome() {
    this.navCtrl.navigateRoot('home');
  }

}
