import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { CommonDataService } from './providers/common-data.service';
import { ComponentsModule } from './component/components.module';
import { IonicStorageModule } from '@ionic/storage';
import { AppAvailability } from '@ionic-native/app-availability/ngx';
import { InAppBrowser } from '@ionic-native/in-app-browser/ngx';
import { UploadTabPageModule } from './upload-tab/upload-tab.module';
import { PhotoViewer } from '@ionic-native/photo-viewer/ngx';
import { FileTransfer } from '@ionic-native/file-transfer/ngx';
import { File } from '@ionic-native/file/ngx';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx';
import { Downloader } from '@ionic-native/downloader/ngx';
import { NoticeMasterPageModule } from './notice-master/notice-master.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  entryComponents: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    ComponentsModule,
    IonicStorageModule.forRoot(),
    UploadTabPageModule,
    NoticeMasterPageModule,
  ],
  providers: [
    StatusBar,
    SplashScreen,
    CommonDataService,
    AppAvailability,
    InAppBrowser,
    PhotoViewer,
    File,
    FileTransfer,
    AndroidPermissions,
    Downloader,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
