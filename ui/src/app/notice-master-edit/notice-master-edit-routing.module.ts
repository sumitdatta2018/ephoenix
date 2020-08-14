import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NoticeMasterEditPage } from './notice-master-edit.page';

const routes: Routes = [
  {
    path: '',
    component: NoticeMasterEditPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NoticeMasterEditPageRoutingModule {}
