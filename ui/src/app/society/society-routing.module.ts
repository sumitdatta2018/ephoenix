import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SocietyPage } from './society.page';

const routes: Routes = [
  {
    path: '',
    component: SocietyPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SocietyPageRoutingModule {}
