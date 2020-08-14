import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { NoticeMasterListPage } from './notice-master-list.page';

describe('NoticeMasterListPage', () => {
  let component: NoticeMasterListPage;
  let fixture: ComponentFixture<NoticeMasterListPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoticeMasterListPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(NoticeMasterListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
