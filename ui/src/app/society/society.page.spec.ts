import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SocietyPage } from './society.page';

describe('SocietyPage', () => {
  let component: SocietyPage;
  let fixture: ComponentFixture<SocietyPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SocietyPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SocietyPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
