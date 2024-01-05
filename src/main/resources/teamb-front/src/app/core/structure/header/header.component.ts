import { Component, OnDestroy, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthService } from '../../../shared/services/auth.service';


@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

    items: MenuItem[] | undefined;
    isConnected:boolean | undefined


    constructor(private authService: AuthService) {
        this.isConnected = false
     }



    ngOnInit() {
        this.isConnected = Boolean(this.authService.getAuthenticated())
        this.onCreateMenuItems(this.isConnected)
    }

    onCreateMenuItems(isEmployee: boolean) {
        if (isEmployee) {
            this.onCreateEmployeeMenu()
        } else {
            this.onCreateResponsibleMenu()
        }

    }


    ngOnDestroy(): void {
        console.log('')
    }

    onCreateResponsibleMenu() {
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

    onCreateEmployeeMenu() {
        this.items = [
            {
                label: 'Edit Profile',
                icon: 'pi pi-fw pi-file-edit',
                items: [
                    {
                        label: 'Edit',
                        icon: 'pi pi-fw pi-align-left',
                        routerLink: ['employees/update']
                    },
                ]
            },
            {
                label: 'Blogs',
                icon: 'pi pi-fw pi-comment',
                items: [
                    {
                        label: 'List',
                        icon: 'pi pi-fw  pi-align-left',
                        routerLink: ['employees/publications/list']
                    },
                ]
            },
            {
                label: 'Logout',
                icon: 'pi pi-fw pi-power-off'
            }
        ];
    }



}
