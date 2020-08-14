import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { StudyPlanPage } from './study-plan.page';

describe('StudyPlanPage', () => {
  let component: StudyPlanPage;
  let fixture: ComponentFixture<StudyPlanPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudyPlanPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(StudyPlanPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
