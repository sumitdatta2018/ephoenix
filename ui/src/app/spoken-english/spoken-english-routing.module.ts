import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SpokenEnglishPage } from './spoken-english.page';

const routes: Routes = [
  {
    path: '',
    component: SpokenEnglishPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SpokenEnglishPageRoutingModule {}
