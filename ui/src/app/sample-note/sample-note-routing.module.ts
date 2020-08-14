import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SampleNotePage } from './sample-note.page';

const routes: Routes = [
  {
    path: '',
    component: SampleNotePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SampleNotePageRoutingModule {}
