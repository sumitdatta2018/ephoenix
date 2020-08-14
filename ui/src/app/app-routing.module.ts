import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from './authentication.guard';
import { UploadTabPage } from './upload-tab/upload-tab.page';
import { NoticeMasterPage } from './notice-master/notice-master.page';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'forgot-password',
    loadChildren: () => import('./forgot-password/forgot-password.module').then( m => m.ForgotPasswordPageModule)
  },
  {
    path: 'reset-password',
    loadChildren: () => import('./reset-password/reset-password.module').then( m => m.ResetPasswordPageModule)
  },
  {
    path: 'registration',
    loadChildren: () => import('./registration/registration.module').then( m => m.RegistrationPageModule),
  },
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then( m => m.HomePageModule),
  },
  {
    path: 'history',
    loadChildren: () => import('./history/history.module').then( m => m.HistoryPageModule),
  },
  {
    path: 'mission-vission',
    loadChildren: () => import('./mission-vission/mission-vission.module').then( m => m.MissionVissionPageModule),
  },
  {
    path: 'contact-us',
    loadChildren: () => import('./contact-us/contact-us.module').then( m => m.ContactUsPageModule),
  },
  {
    path: 'scholarship',
    loadChildren: () => import('./scholarship/scholarship.module').then( m => m.ScholarshipPageModule),
  },
  {
    path: 'society',
    loadChildren: () => import('./society/society.module').then( m => m.SocietyPageModule),
  },
  {
    path: 'important-link',
    loadChildren: () => import('./important-link/important-link.module').then( m => m.ImportantLinkPageModule),
  },
  {
    path: 'trainning',
    loadChildren: () => import('./trainning/trainning.module').then( m => m.TrainningPageModule),
  },
  {
    path: 'others',
    loadChildren: () => import('./others/others.module').then( m => m.OthersPageModule),
  },
  {
    path: 'spoken-english',
    loadChildren: () => import('./spoken-english/spoken-english.module').then( m => m.SpokenEnglishPageModule),
  },
  {
    path: 'gallary',
    loadChildren: () => import('./gallary/gallary.module').then( m => m.GallaryPageModule),
  },
  {
    path: 'feedback',
    loadChildren: () => import('./feedback/feedback.module').then( m => m.FeedbackPageModule),
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'study-plan',
    loadChildren: () => import('./study-plan/study-plan.module').then( m => m.StudyPlanPageModule),
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'rate-master',
    loadChildren: () => import('./rate-master/rate-master.module').then( m => m.RateMasterPageModule),
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'upload',
    component: UploadTabPage,
    children: [
      {
        path: 'create',
        children: [
          {
            path: '',
            loadChildren: () => import('./upload-master/upload-master.module').then( m => m.UploadMasterPageModule)
          }
        ]
      },
      {
        path: 'list',
        children: [
          {
            path: '',
            loadChildren: () => import('./upload-list/upload-list.module').then( m => m.UploadListPageModule)
          }
        ]
      },
      {
        path: '',
        redirectTo: '/upload/create',
        pathMatch: 'full'
      }
    ],
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'notice',
    component: NoticeMasterPage,
    children: [
      {
        path: 'create',
        children: [
          {
            path: '',
            loadChildren: () => import('./notice-master-create/notice-master-create.module').then( m => m.NoticeMasterCreatePageModule)
          }
        ]
      },
      {
        path: 'list',
        children: [
          {
            path: '',
            loadChildren: () => import('./notice-master-list/notice-master-list.module').then( m => m.NoticeMasterListPageModule)
          }
        ]
      },
      {
        path: '',
        redirectTo: '/notice/create',
        pathMatch: 'full'
      }
    ],
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'study-material',
    loadChildren: () => import('./study-material/study-material.module').then( m => m.StudyMaterialPageModule),
    canActivate: [AuthenticationGuard],
  },{
    path: 'syllabus',
    loadChildren: () => import('./syllabus/syllabus.module').then( m => m.SyllabusPageModule),
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'sample-note',
    loadChildren: () => import('./sample-note/sample-note.module').then( m => m.SampleNotePageModule),
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'spoken-english-registration',
    loadChildren: () => import('./spoken-english-registration/spoken-english-registration.module').then( m => m.SpokenEnglishRegistrationPageModule),
    canActivate: [AuthenticationGuard],
  },
  { path: '**', 
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'pic-view',
    loadChildren: () => import('./pic-view/pic-view.module').then( m => m.PicViewPageModule)
  },
  {
    path: 'notice-master-edit',
    loadChildren: () => import('./notice-master-edit/notice-master-edit.module').then( m => m.NoticeMasterEditPageModule)
  },
  ];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
