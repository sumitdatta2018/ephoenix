import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UploadMasterPageRoutingModule } from './upload-master-routing.module';

import { UploadMasterPage } from './upload-master.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UploadMasterPageRoutingModule,
    ComponentsModule,
    ReactiveFormsModule
  ],
  declarations: [UploadMasterPage]
})
export class UploadMasterPageModule {}
