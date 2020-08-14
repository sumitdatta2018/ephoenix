import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NoticeMasterEditPageRoutingModule } from './notice-master-edit-routing.module';

import { NoticeMasterEditPage } from './notice-master-edit.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    NoticeMasterEditPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [NoticeMasterEditPage]
})
export class NoticeMasterEditPageModule {}
