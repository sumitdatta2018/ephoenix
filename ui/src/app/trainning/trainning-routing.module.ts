import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TrainningPage } from './trainning.page';

const routes: Routes = [
  {
    path: '',
    component: TrainningPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TrainningPageRoutingModule {}
