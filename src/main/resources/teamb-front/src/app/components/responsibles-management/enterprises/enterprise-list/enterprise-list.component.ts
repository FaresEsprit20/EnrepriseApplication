import { Component, OnDestroy, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { SortPipe } from '../../../../pipes/sort.pipe';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { AlertService } from '../../../../shared/ui/alert.service';
import { EnterpriseService } from '../../../../shared/services/enterprise.service';

@Component({
  selector: 'app-enterprise-list',
  templateUrl: './enterprise-list.component.html',
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
  styleUrl: './enterprise-list.component.css'
})
export class EnterpriseListComponent  implements OnInit, OnDestroy {

  isLoading: boolean = true;
  enterprises: any;
  searchResult: any;
  sortedEntr: any[];
  private empSubscription: Subscription;

  constructor(
    private router: Router,
    private enterpriseService:EnterpriseService,
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

  navigateToAddEnterprise() {
    this.router.navigate(['/responsibles/enterprises/create']);
  }

  loademps(): void {
    this.isLoading = true;
    this.empSubscription = this.enterpriseService.getAllEnterprises().subscribe(
      (data: any[]) => {
        console.log(data)
        this.enterprises = data
        if(this.enterprises.length === 0) {
          this.alertService.setWarnMessages('No results found. Please try to create an Enterprise.')
        }
        this.isLoading = false;
      },
      (error) => {
        this.isLoading = false;
        console.error('Error loading enterprises:', error);
      }
    );
  }
 


  handleSearch(searchTerm: string) {
    this.searchResult = this.enterprises.filter((emp) => {
      return (
        emp.enterpriseName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        emp.enterpriseLocal.toLowerCase().includes(searchTerm.toLowerCase())
      );
    });
    if(this.searchResult?.length ==0 ){
      this.alertService.setWarnMessages('No results found. Please try a different search.')
    }
  }

 
  
  }
