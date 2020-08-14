import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NoticeMasterPage } from './notice-master.page';

const routes: Routes = [
  {
    path: '',
    component: NoticeMasterPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NoticeMasterPageRoutingModule {}
