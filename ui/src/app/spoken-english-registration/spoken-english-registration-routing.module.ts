import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SpokenEnglishRegistrationPage } from './spoken-english-registration.page';

const routes: Routes = [
  {
    path: '',
    component: SpokenEnglishRegistrationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SpokenEnglishRegistrationPageRoutingModule {}
