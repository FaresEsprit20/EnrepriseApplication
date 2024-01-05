import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styles: [
    `:host {
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
  }`
  ],
  styleUrl: './employee-details.component.css'
})
export class EmployeeDetailsComponent implements OnInit, OnDestroy {

  constructor() {
  }
 
  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }

  
}
