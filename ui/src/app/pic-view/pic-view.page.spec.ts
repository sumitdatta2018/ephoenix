import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { PicViewPage } from './pic-view.page';

describe('PicViewPage', () => {
  let component: PicViewPage;
  let fixture: ComponentFixture<PicViewPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PicViewPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(PicViewPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
