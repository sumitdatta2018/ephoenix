import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomHeaderComponent } from './custom-header/custom-header.component';
import { CustomFooterComponent } from './custom-footer/custom-footer.component';



@NgModule({
  declarations: [
    CustomHeaderComponent,
    CustomFooterComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [CustomHeaderComponent,
    CustomFooterComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ComponentsModule { }
