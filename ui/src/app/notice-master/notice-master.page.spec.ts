import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { NoticeMasterPage } from './notice-master.page';

describe('NoticeMasterPage', () => {
  let component: NoticeMasterPage;
  let fixture: ComponentFixture<NoticeMasterPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoticeMasterPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(NoticeMasterPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
