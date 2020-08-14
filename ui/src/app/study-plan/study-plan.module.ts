import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { StudyPlanPageRoutingModule } from './study-plan-routing.module';

import { StudyPlanPage } from './study-plan.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    StudyPlanPageRoutingModule,
    ReactiveFormsModule,
    ComponentsModule
  ],
  declarations: [StudyPlanPage]
})
export class StudyPlanPageModule {}
