import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { RateMasterPage } from './rate-master.page';

describe('RateMasterPage', () => {
  let component: RateMasterPage;
  let fixture: ComponentFixture<RateMasterPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateMasterPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(RateMasterPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
