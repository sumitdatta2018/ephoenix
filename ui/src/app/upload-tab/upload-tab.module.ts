import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UploadTabPageRoutingModule } from './upload-tab-routing.module';

import { UploadTabPage } from './upload-tab.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    UploadTabPageRoutingModule
  ],
  declarations: [UploadTabPage]
})
export class UploadTabPageModule {}
