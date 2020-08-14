import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { StudyMaterialPageRoutingModule } from './study-material-routing.module';

import { StudyMaterialPage } from './study-material.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    StudyMaterialPageRoutingModule
  ],
  declarations: [StudyMaterialPage]
})
export class StudyMaterialPageModule {}
