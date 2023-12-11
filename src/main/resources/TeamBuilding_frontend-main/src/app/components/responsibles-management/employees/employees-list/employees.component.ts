import { Component } from '@angular/core';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { PublicationService } from 'src/app/shared/services/publication.service';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css'],
})
export class EmployeesComponent {
  employees: any;
  constructor(
    private blogService : PublicationService,
    private employeeService:EmployeeService) {}

  ngOnInit() {
    //this.blogService.findEmployeeByPublicationId.subscribe((res) => (this.employees = res));
  }
      
    deleteEmployee(id: number) {
      this.employeeService.deleteEmployeeById(id).subscribe(
        () => {
        
          this.employeeService.findAllEmployees();
        },
      );
    }
    updateEmployee(employee: any) {
      const employeeData = {
        firstName: employee.firstname,
        lastName :employee.lastName,
        email:employee.email,
        poste:employee.poste,
        department :employee.department 
      };
  
      this.employeeService.updateEmployee(employeeData).subscribe(
        (data) => {
          console.log('Mise à jour réussie :', data);
          this.employeeService.findAllEmployees(); 
        },
  )}
      }