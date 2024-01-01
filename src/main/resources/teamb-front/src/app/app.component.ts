import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CoreModule } from "./core/core.module";
import { SharedModule } from './shared/modules/shared.module';
import { EmployeesManagementModule } from './components/employees-management/employees-management.module';
import { ResponsiblesManagementModule } from './components/responsibles-management/responsibles-management.module';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    imports: [
        RouterOutlet,
        SharedModule,
        CoreModule,
        EmployeesManagementModule,
        ResponsiblesManagementModule,
        HttpClientModule
    ]
})
export class AppComponent implements OnInit{
  title = 'teamb-front';

  constructor() {

  }

  ngOnInit() {
    
}



}
