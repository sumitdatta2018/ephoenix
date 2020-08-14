import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SpokenEnglishPage } from './spoken-english.page';

describe('SpokenEnglishPage', () => {
  let component: SpokenEnglishPage;
  let fixture: ComponentFixture<SpokenEnglishPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpokenEnglishPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SpokenEnglishPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
