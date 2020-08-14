import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TrainningPageRoutingModule } from './trainning-routing.module';

import { TrainningPage } from './trainning.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TrainningPageRoutingModule,
    ComponentsModule
  ],
  declarations: [TrainningPage]
})
export class TrainningPageModule {}
