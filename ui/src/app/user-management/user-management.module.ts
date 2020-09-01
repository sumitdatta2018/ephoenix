import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UserManagementPageRoutingModule } from './user-management-routing.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { UserManagementPage } from './user-management.page';
import { ComponentsModule } from '../component/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    UserManagementPageRoutingModule,
    NgxPaginationModule
  ],
  declarations: [UserManagementPage]
})
export class UserManagementPageModule {}
