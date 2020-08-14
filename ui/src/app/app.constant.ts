import { environment } from 'src/environments/environment';

export const AppConstant = {
    'appURL': environment.appURL,
    'loginURL': 'login',
    'registrationURL': 'users',
    'getClassList': 'master-data?types=class',
    'getSubjectList': 'master-data?types=subject',
    'getDistrictAndStateTypeList': 'master-data?types=district,state',
    'menuURL': 'menus',
    'getDefaultStudyPlan': 'studyplans',
    'token': 'token',
    'studyplanTypelist': 'master-data?types=STUDY_PLAN_TYPE',
    'uploadType': 'master-data?types=UPLOAD_TYPE',
    'uploadList': 'uploads',
    'logout': 'logout',
    'getUserById': 'users/',
    'casteReligionList': 'master-data?types=CASTE,RELIGION',
    'createNotice': 'notice',
    'getNoticeList': 'notice',
    'generateOtp': 'otp/generate',
    'validateOtp': 'otp/validate',
    'resetPassword': 'reset',
    /********************************************* CONSTANT DATA ******************************************/
    'name': '',
    'email': '',
    'mobile': '',
    'menuResponse': '',
    'access_token': '',
    'usernameforReset': '',
    'trackId': ''
}