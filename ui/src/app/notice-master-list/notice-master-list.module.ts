import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NoticeMasterListPageRoutingModule } from './notice-master-list-routing.module';

import { NoticeMasterListPage } from './notice-master-list.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    NoticeMasterListPageRoutingModule,
  ],
  declarations: [NoticeMasterListPage]
})
export class NoticeMasterListPageModule {}
