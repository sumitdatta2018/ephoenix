import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ImportantLinkPageRoutingModule } from './important-link-routing.module';

import { ImportantLinkPage } from './important-link.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ImportantLinkPageRoutingModule,
    ComponentsModule
  ],
  declarations: [ImportantLinkPage]
})
export class ImportantLinkPageModule {}
