import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NoticeMasterCreatePageRoutingModule } from './notice-master-create-routing.module';

import { NoticeMasterCreatePage } from './notice-master-create.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    NoticeMasterCreatePageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [NoticeMasterCreatePage]
})
export class NoticeMasterCreatePageModule {}
