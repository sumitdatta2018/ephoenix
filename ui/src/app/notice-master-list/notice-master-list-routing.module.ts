import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NoticeMasterListPage } from './notice-master-list.page';

const routes: Routes = [
  {
    path: '',
    component: NoticeMasterListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NoticeMasterListPageRoutingModule {}
