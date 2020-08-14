import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { NoticeMasterEditPage } from './notice-master-edit.page';

describe('NoticeMasterEditPage', () => {
  let component: NoticeMasterEditPage;
  let fixture: ComponentFixture<NoticeMasterEditPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoticeMasterEditPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(NoticeMasterEditPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
