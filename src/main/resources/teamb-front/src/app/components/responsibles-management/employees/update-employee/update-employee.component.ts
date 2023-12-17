import { Component, OnDestroy, OnInit } from '@angular/core';
import { EmployeeService } from '../../../../shared/services/employee.service';


@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit, OnDestroy {

  constructor(private employeeService:EmployeeService) {

  }

  ngOnInit(): void {
  
  }

  ngOnDestroy(): void {
    this.employeeService.ngOnDestroy()
  }


}
