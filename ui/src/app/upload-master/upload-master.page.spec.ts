import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UploadMasterPage } from './upload-master.page';

describe('UploadMasterPage', () => {
  let component: UploadMasterPage;
  let fixture: ComponentFixture<UploadMasterPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadMasterPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UploadMasterPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
