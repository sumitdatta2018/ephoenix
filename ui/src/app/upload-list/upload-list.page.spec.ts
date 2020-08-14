import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UploadListPage } from './upload-list.page';

describe('UploadListPage', () => {
  let component: UploadListPage;
  let fixture: ComponentFixture<UploadListPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadListPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UploadListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
