import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { MissionVissionPageRoutingModule } from './mission-vission-routing.module';

import { MissionVissionPage } from './mission-vission.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    MissionVissionPageRoutingModule,
    ComponentsModule
  ],
  declarations: [MissionVissionPage]
})
export class MissionVissionPageModule {}
