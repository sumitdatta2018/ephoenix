import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UploadListPage } from './upload-list.page';

const routes: Routes = [
  {
    path: '',
    component: UploadListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UploadListPageRoutingModule {}
