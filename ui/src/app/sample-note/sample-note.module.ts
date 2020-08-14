import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SampleNotePageRoutingModule } from './sample-note-routing.module';

import { SampleNotePage } from './sample-note.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SampleNotePageRoutingModule,
    ComponentsModule
  ],
  declarations: [SampleNotePage]
})
export class SampleNotePageModule {}
