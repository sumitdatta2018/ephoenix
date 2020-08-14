import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ScholarshipPage } from './scholarship.page';

describe('ScholarshipPage', () => {
  let component: ScholarshipPage;
  let fixture: ComponentFixture<ScholarshipPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScholarshipPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ScholarshipPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
