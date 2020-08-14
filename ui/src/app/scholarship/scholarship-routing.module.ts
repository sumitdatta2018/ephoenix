import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ScholarshipPage } from './scholarship.page';

const routes: Routes = [
  {
    path: '',
    component: ScholarshipPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ScholarshipPageRoutingModule {}
