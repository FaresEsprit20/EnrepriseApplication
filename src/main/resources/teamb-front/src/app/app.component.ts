import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CoreModule } from "./core/core.module";
import { SharedModule } from './shared/modules/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './shared/services/auth.service';
import { AddressService } from './shared/services/address.service';
import { DepartmentService } from './shared/services/department.service';
import { EmployeeService } from './shared/services/employee.service';
import { EnterpriseService } from './shared/services/enterprise.service';
import { PublicationService } from './shared/services/publication.service';
import { RatingService } from './shared/services/rating.service';
import { ResponsibleService } from './shared/services/responsible.service';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    imports: [
        RouterOutlet,
        SharedModule,
        CoreModule,
        HttpClientModule
    ],
    providers: [
      AuthService,
      AddressService,
      DepartmentService,
      EmployeeService,
      EnterpriseService,
      PublicationService,
      RatingService,
      ResponsibleService
    ]
})
export class AppComponent {
  title = 'teamb-front';
}
