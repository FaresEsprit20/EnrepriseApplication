import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, RouterOutlet } from '@angular/router';
import { CoreModule } from "./core/core.module";
import { SharedModule } from './shared/modules/shared.module';
import { EmployeesManagementModule } from './components/employees-management/employees-management.module';
import { ResponsiblesManagementModule } from './components/responsibles-management/responsibles-management.module';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthService } from './shared/services/auth.service';


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
export class AppComponent implements OnInit, OnDestroy{

  title = 'Team Builder';
  isConnected:boolean | undefined
  snapshot: ActivatedRouteSnapshot | undefined

  constructor(private authService:AuthService, private route: ActivatedRoute) {
   this.isConnected = false
  }

  ngOnInit(): void {
    this.isConnected = Boolean(this.authService.getAuthenticated())
    console.log("isConnected "+this.isConnected)

   this.snapshot  = this.route.snapshot;
    console.log('Current Route:',this.snapshot);
  }

ngOnDestroy(): void {
  console.log('')
}



}
