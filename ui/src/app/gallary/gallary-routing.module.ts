import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GallaryPage } from './gallary.page';

const routes: Routes = [
  {
    path: '',
    component: GallaryPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GallaryPageRoutingModule {}
