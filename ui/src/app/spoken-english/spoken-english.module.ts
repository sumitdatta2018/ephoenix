import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SpokenEnglishPageRoutingModule } from './spoken-english-routing.module';

import { SpokenEnglishPage } from './spoken-english.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SpokenEnglishPageRoutingModule,
    ComponentsModule
  ],
  declarations: [SpokenEnglishPage]
})
export class SpokenEnglishPageModule {}
