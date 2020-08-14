import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { StudyMaterialPage } from './study-material.page';

describe('StudyMaterialPage', () => {
  let component: StudyMaterialPage;
  let fixture: ComponentFixture<StudyMaterialPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudyMaterialPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(StudyMaterialPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
