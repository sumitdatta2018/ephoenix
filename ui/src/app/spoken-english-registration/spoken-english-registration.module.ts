import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SpokenEnglishRegistrationPageRoutingModule } from './spoken-english-registration-routing.module';

import { SpokenEnglishRegistrationPage } from './spoken-english-registration.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    IonicModule,
    SpokenEnglishRegistrationPageRoutingModule,
    ComponentsModule
  ],
  declarations: [SpokenEnglishRegistrationPage]
})
export class SpokenEnglishRegistrationPageModule {}
