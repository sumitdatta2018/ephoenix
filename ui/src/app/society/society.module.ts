import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SocietyPageRoutingModule } from './society-routing.module';

import { SocietyPage } from './society.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SocietyPageRoutingModule,
    ComponentsModule
  ],
  declarations: [SocietyPage]
})
export class SocietyPageModule {}
