import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { MissionVissionPage } from './mission-vission.page';

describe('MissionVissionPage', () => {
  let component: MissionVissionPage;
  let fixture: ComponentFixture<MissionVissionPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MissionVissionPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(MissionVissionPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
