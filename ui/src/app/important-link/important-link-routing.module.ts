import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ImportantLinkPage } from './important-link.page';

const routes: Routes = [
  {
    path: '',
    component: ImportantLinkPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ImportantLinkPageRoutingModule {}
