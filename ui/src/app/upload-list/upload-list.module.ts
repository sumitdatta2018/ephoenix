import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UploadListPageRoutingModule } from './upload-list-routing.module';

import { UploadListPage } from './upload-list.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UploadListPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [UploadListPage]
})
export class UploadListPageModule {}
