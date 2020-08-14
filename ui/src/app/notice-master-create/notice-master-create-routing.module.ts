import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NoticeMasterCreatePage } from './notice-master-create.page';

const routes: Routes = [
  {
    path: '',
    component: NoticeMasterCreatePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NoticeMasterCreatePageRoutingModule {}
