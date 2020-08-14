import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { NoticeMasterCreatePage } from './notice-master-create.page';

describe('NoticeMasterCreatePage', () => {
  let component: NoticeMasterCreatePage;
  let fixture: ComponentFixture<NoticeMasterCreatePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoticeMasterCreatePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(NoticeMasterCreatePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
