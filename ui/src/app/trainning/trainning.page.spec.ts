import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { TrainningPage } from './trainning.page';

describe('TrainningPage', () => {
  let component: TrainningPage;
  let fixture: ComponentFixture<TrainningPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainningPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(TrainningPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
