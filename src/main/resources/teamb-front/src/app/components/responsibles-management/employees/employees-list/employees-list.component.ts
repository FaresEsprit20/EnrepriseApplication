import { Component, OnDestroy, OnInit } from '@angular/core';
import { EmployeeService } from '../../../../shared/services/employee.service';
import { SafeHtml } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { AlertService } from '../../../../shared/ui/alert.service';
import { SortPipe } from '../../../../pipes/sort.pipe';


@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  providers: [SortPipe,MessageService],
  styles: [ `
  :host {
      @keyframes slidedown-icon {
          0% {
              transform: translateY(0);
          }

          50% {
              transform: translateY(20px);
          }

          100% {
              transform: translateY(0);
          }
      }

      .slidedown-icon {
          animation: slidedown-icon;
          animation-duration: 3s;
          animation-iteration-count: infinite;
      }

      .box {
          background-image: radial-gradient(var(--primary-300), var(--primary-600));
          border-radius: 50% !important;
          color: var(--primary-color-text);
      }
  }
`],
  styleUrls: ['./employees-list.component.css'],
})
export class EmployeesListComponent implements OnInit, OnDestroy {

  isLoading: boolean = true;
  employees: any;
  searchResult: any;
  sortedEmps: any[];
  private empSubscription: Subscription;
  sanitizedContent: SafeHtml | undefined;

  constructor(
    private router: Router,
    private employeeService:EmployeeService,
    public alertService:AlertService
  ) {
   
  }


  ngOnInit() {
  
    //Skeleton timeout starts
    setTimeout(() => {
      this.loademps()
    }, 2000)
    //Skeleton timeout ends
  }

  ngOnDestroy(): void {
   this.empSubscription?.unsubscribe()
  }

  navigateToAddEmployee() {
    this.router.navigate(['/responsibles/management/employees/create']);
  }

  loademps(): void {
    this.isLoading = true;
    this.empSubscription = this.employeeService.findAllEmployees().subscribe(
      (data: any[]) => {
        console.log(data)
        this.employees = data
        if(this.employees.length === 0) {
          this.alertService.setWarnMessages('No results found. Please try to create an Employee.')
        }
        this.isLoading = false;
      },
      (error) => {
        this.isLoading = false;
        console.error('Error loading employees:', error);
      }
    );
  }
 


  handleSearch(searchTerm: string) {
    this.searchResult = this.employees.filter((emp) => {
      return (
        emp.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        emp.lastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        emp.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
        emp.occupation.toLowerCase().includes(searchTerm.toLowerCase()) ||
        emp.registrationNumber.toLowerCase().includes(searchTerm.toLowerCase())
      );
    });
    if(this.searchResult?.length ==0 ){
      this.alertService.setWarnMessages('No results found. Please try a different search.')
    }
  }

 /*  sanitizeContent(content: string): string {
    // Check if the content contains HTML tags
    const containsHtml = /<[a-z/][\s\S]*>/i.test(content);
    // Truncate the content if it's plain text
    if (!containsHtml && content.length > 150) {
      return content.substring(0, 145) + '....';
    }
    // If it contains HTML, sanitize and truncate it
    if (containsHtml) {
      // Create a div element
      const div = document.createElement('div');
      // Set the innerHTML of the div to your content
      div.innerHTML = content;
      // Get the text content from the div
      const textContent = div.textContent || '';
      // Truncate the text content
      const truncatedHtml = this.truncateText(textContent, 150);
      return truncatedHtml;
    }
    // If it's plain text, return truncated text
    return content.length > 150 ? content.substring(0, 145) + '....' : content;
  }
  
  truncateText(text: string, maxLength: number): string {
    return text.length > maxLength ? text.substring(0, maxLength) + '....' : text;
  } */
  

  
  }
