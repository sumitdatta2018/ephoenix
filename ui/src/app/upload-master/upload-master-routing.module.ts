import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UploadMasterPage } from './upload-master.page';

const routes: Routes = [
  {
    path: '',
    component: UploadMasterPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UploadMasterPageRoutingModule {}
