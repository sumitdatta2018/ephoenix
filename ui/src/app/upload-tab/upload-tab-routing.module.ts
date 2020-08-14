import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UploadTabPage } from './upload-tab.page';

const routes: Routes = [
  {
    path: '',
    component: UploadTabPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UploadTabPageRoutingModule {}
