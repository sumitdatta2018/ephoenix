import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SpokenEnglishRegistrationPage } from './spoken-english-registration.page';

describe('SpokenEnglishRegistrationPage', () => {
  let component: SpokenEnglishRegistrationPage;
  let fixture: ComponentFixture<SpokenEnglishRegistrationPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpokenEnglishRegistrationPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SpokenEnglishRegistrationPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
