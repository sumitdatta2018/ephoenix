import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ImportantLinkPage } from './important-link.page';

describe('ImportantLinkPage', () => {
  let component: ImportantLinkPage;
  let fixture: ComponentFixture<ImportantLinkPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImportantLinkPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ImportantLinkPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
