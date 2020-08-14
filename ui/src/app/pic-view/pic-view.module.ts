import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { PicViewPageRoutingModule } from './pic-view-routing.module';

import { PicViewPage } from './pic-view.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    PicViewPageRoutingModule
  ],
  declarations: [PicViewPage]
})
export class PicViewPageModule {}
