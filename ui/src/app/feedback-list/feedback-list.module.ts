import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { FeedbackListPageRoutingModule } from './feedback-list-routing.module';

import { FeedbackListPage } from './feedback-list.page';
import { ComponentsModule } from '../component/components.module';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    FeedbackListPageRoutingModule,
    NgxPaginationModule
  ],
  declarations: [FeedbackListPage]
})
export class FeedbackListPageModule {}
