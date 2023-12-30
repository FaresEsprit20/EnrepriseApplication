import { Component, OnDestroy, OnInit } from '@angular/core';
import { EmployeeService } from '../../../../shared/services/employee.service';


@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.css'],
})
export class EmployeesListComponent implements OnInit, OnDestroy {

  constructor(private employeeService:EmployeeService) {}

  ngOnInit() {
    
  }

  ngOnDestroy(): void {

  }
      
   
  }
