import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PicViewPage } from './pic-view.page';

const routes: Routes = [
  {
    path: '',
    component: PicViewPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PicViewPageRoutingModule {}
