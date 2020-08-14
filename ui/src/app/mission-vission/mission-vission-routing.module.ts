import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MissionVissionPage } from './mission-vission.page';

const routes: Routes = [
  {
    path: '',
    component: MissionVissionPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MissionVissionPageRoutingModule {}
