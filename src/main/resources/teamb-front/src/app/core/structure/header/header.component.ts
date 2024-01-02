import { Component, OnDestroy, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy{
 
  items: MenuItem[] | undefined;


constructor() {

}



ngOnInit() {
  this.items = [

    {
        label: 'Edit Profile',
        icon: 'pi pi-fw pi-file-edit',
        items: [
            {
                label: 'Edit',
                icon: 'pi pi-fw pi-align-left',
                routerLink: ['responsibles/update']
            },
            
        ]
    },
    {
        label: 'Employees',
        icon: 'pi pi-fw pi-sitemap',
        items: [
            {
                label: 'New',
                icon: 'pi pi-fw pi-user-plus',
                routerLink: ['responsibles/management/employees/create']
            },
            {
                label: 'List',
                icon: 'pi pi-fw  pi-align-left',
                routerLink: ['responsibles/management/employees/list']
                
            },
          
         
        ]
    },
    {
        label: 'Responsibles',
        icon: 'pi pi-fw pi-sitemap',
        items: [
            {
                label: 'New',
                icon: 'pi pi-fw pi-user-plus',
                routerLink: ['responsibles/create']
            },
            {
                label: 'List',
                icon: 'pi pi-fw  pi-align-left',
                routerLink: ['responsibles/list']
            },
        ]
    },
    {
        label: 'Enterprises',
        icon: 'pi pi-fw pi-desktop',
        items: [
            {
                label: 'New',
                icon: 'pi pi-fw pi-user-plus',
                routerLink: ['responsibles/enterprises/create']
            },
            {
                label: 'List',
                icon: 'pi pi-fw  pi-align-left',
                routerLink: ['responsibles/enterprises/list']
            },
          
        ]
    },
    {
        label: 'Statistics',
        icon: 'pi pi-fw pi-chart-bar',
        routerLink: ['responsibles/stats']
    },
    {
        label: 'Logout',
        icon: 'pi pi-fw pi-power-off'
    }
];
}



  ngOnDestroy(): void {
    console.log('')
  }

  

}
