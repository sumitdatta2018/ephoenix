import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of, Observable, Subject } from 'rxjs';
import { catchError } from 'rxjs/operators'
import { HttpHeaders } from '@angular/common/http';
import { Storage } from '@ionic/storage';
import { LoadingController, AlertController, ToastController } from '@ionic/angular';
import { AppConstant } from '../app.constant';

@Injectable({
  providedIn: 'root'
})
export class CommonDataService {
  public loading: any;
  public isLoading = false;
  public alert: any;
  public menu = new Subject<any>();
  public menuSubscription = this.menu.asObservable();
  public user = new Subject<any>();
  public userSubscription = this.user.asObservable();
  public httpOptions: any;
  public loaderCount = 0;
  constructor(private http: HttpClient,
    private loadingController: LoadingController,
    public toastController: ToastController,
    private localStorage: Storage,
    private alertController: AlertController) {

  }

  async presentToast(message) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }

  async presentLoading() {
    this.isLoading = true;
    if (this.loaderCount === 0) {
      return await this.loadingController.create({
        cssClass: 'my-custom-class',
        message: 'Please wait...',
        spinner: 'circles',
        duration: 15000
      }).then(a => {
        a.present().then(() => {
          console.log('presented');
          if (!this.isLoading) {
            a.dismiss().then(() => console.log('abort presenting'));
          }
        });
      });
    }
    this.loaderCount = this.loaderCount + 1;
    // return await this.loadingController.create({
    //   cssClass: 'my-custom-class',
    //   message: 'Please wait...',
    //   spinner: 'circles'
    // }).then(a => {
    //   a.present().then(() => {
    //     console.log('presented');
    //     if (!this.isLoading) {
    //       a.dismiss().then(() => console.log('abort presenting'));
    //     }
    //   });
    // });
    
  }

  async dismissLoading() {
    if (this.loaderCount > 0) {
      this.loaderCount = this.loaderCount - 1;
    }
    if (this.loaderCount === 0) {
      this.isLoading = false;
      // return await this.loadingController.dismiss().then(() => console.log('dismissed'));
      return await this.loadingController.getTop().then(a => {
        if ( a )
         a.dismiss().then(() => console.log('loading dismissed'));
     });
    }
    
  }

  async presentAlert(message, header?, subHeader?) {
    const alert = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: header ? header : "The Phoenix",
      subHeader: subHeader,
      backdropDismiss: true,
      message: message,
      buttons: ['OK']
    });

    await alert.present();
  }

  public loadMenus() {
    this.menu.next();
  }

  public loadUser() {
    this.user.next();
  }



  public commonGetService(url: string, param?: any): Observable<any> {
    return this.http.get(url).pipe(
      catchError(error => {
        return of(null);
      })
    );
  }

  public secureGetService(url: string, param?: any): Observable<any> {
    if (AppConstant.access_token) {
      this.httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'bearer ' + AppConstant.access_token
        })
      }
    }
    return this.http.get(url, this.httpOptions).pipe(
      catchError(error => {
        return of(null);
      })
    );
  }

  public commonPostService(url: string, reqBody: any, param?: any): Observable<any> {
    return this.http.post(url, reqBody).pipe(
      catchError(error => {
        return of(null);
      })
    );
  }

  public securePostService(url: string, reqBody: any, param?: any): Observable<any> {
    if (AppConstant.access_token) {
      this.httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'bearer ' + AppConstant.access_token
        })
      }
    }
    return this.http.post(url, reqBody, this.httpOptions).pipe(
      catchError(error => {
        return of(null);
      })
    );
  }

  public secureUploadService(url: string, reqBody: any, param?: any): Observable<any> {
    if (AppConstant.access_token) {
      this.httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'multipart/form-data',
          'Accept': 'application/json',
          'Authorization': 'bearer ' + AppConstant.access_token
        })
      }
    }
    return this.http.post(url, reqBody, this.httpOptions).pipe(
      catchError(error => {
        return of(null);
      })
    );
  }

  public loginService(url: string, reqBody: any, access_token?: any): Observable<any> {
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'bearer ' + access_token,
      })
    }
    if (this.httpOptions) {
      return this.http.post(url, reqBody, this.httpOptions).pipe(
        catchError(error => {
          return of(null);
        })
      );
    } else {
      return this.http.post(url, reqBody).pipe(
        catchError(error => {
          return of(null);
        })
      );
    }
  }
}
