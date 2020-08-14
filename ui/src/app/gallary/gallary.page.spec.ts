import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { GallaryPage } from './gallary.page';

describe('GallaryPage', () => {
  let component: GallaryPage;
  let fixture: ComponentFixture<GallaryPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GallaryPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(GallaryPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
