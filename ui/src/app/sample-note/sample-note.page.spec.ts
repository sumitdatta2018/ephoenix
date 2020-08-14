import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SampleNotePage } from './sample-note.page';

describe('SampleNotePage', () => {
  let component: SampleNotePage;
  let fixture: ComponentFixture<SampleNotePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SampleNotePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SampleNotePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
