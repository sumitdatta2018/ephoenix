import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NoticeMasterPageRoutingModule } from './notice-master-routing.module';

import { NoticeMasterPage } from './notice-master.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    NoticeMasterPageRoutingModule
  ],
  declarations: [NoticeMasterPage]
})
export class NoticeMasterPageModule {}
