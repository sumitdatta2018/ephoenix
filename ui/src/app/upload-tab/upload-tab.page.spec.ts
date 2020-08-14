import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UploadTabPage } from './upload-tab.page';

describe('UploadTabPage', () => {
  let component: UploadTabPage;
  let fixture: ComponentFixture<UploadTabPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadTabPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UploadTabPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
