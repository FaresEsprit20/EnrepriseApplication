import { Component, OnDestroy, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
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
  styleUrl: './stats.component.css'
})
export class StatsComponent implements OnInit, OnDestroy {

  items: MenuItem[] | undefined;

  home: MenuItem | undefined;


  data: any;

  options: any;


  dataPie: any;

  optionsPie: any;

  constructor() {

  }

  ngOnInit(): void {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    this.items = [{ label: 'Management' }, { label: 'Responsibles' }, { label: 'Statistics' }];
    this.data = {
      labels: ['Total Responsibles', 'Total Employees'],
      datasets: [
          {
              data: [2, 6],
              backgroundColor: [documentStyle.getPropertyValue('--blue-500'), documentStyle.getPropertyValue('--yellow-500')],
              hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400')]
          }
      ]
  };
  this.options = {
    cutout: '60%',
    plugins: {
        legend: {
            labels: {
                color: textColor
            }
        }
    }
};
this.dataPie = {
  labels: ['Total LikedBlogs', 'Total Disliked Blogs'],
  datasets: [
      {
          data: [28, 2],
          backgroundColor: [documentStyle.getPropertyValue('--blue-500'), documentStyle.getPropertyValue('--yellow-500')],
          hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400')]
      }
  ]
};
this.optionsPie = {
cutout: '60%',
plugins: {
    legend: {
        labels: {
            color: textColor
        }
    }
}
};
}

  
  ngOnDestroy(): void {

  }

}
