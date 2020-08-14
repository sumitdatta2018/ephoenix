import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { OthersPageRoutingModule } from './others-routing.module';

import { OthersPage } from './others.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    OthersPageRoutingModule,
    ComponentsModule
  ],
  declarations: [OthersPage]
})
export class OthersPageModule {}
