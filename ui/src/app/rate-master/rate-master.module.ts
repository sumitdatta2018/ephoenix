import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RateMasterPageRoutingModule } from './rate-master-routing.module';

import { RateMasterPage } from './rate-master.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RateMasterPageRoutingModule,
    ComponentsModule,
    ReactiveFormsModule
  ],
  declarations: [RateMasterPage]
})
export class RateMasterPageModule {}
