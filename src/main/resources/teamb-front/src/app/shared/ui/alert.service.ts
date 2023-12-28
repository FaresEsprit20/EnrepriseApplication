// toast.service.ts
import { Injectable } from '@angular/core';
import { Message } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  
    messages: Message[] | undefined;

    message: Message | undefined;
 
     constructor() {
        this.messages = [{ severity: 'info', summary: 'Info', detail: 'Message Content' }];
     }

    public setInfoMessages(message:string) {
        return this.messages = [{ severity: 'info', summary: 'Info', detail: message }];
    }

    public setWarnMessages(message:string) {
        return this.messages = [{ severity: 'warn', summary: 'Warning', detail: message }];
    }

    public setSuccessMessages(message:string) {
        return this.messages = [{ severity: 'success', summary: 'Success', detail: message }];
    }

    public setErrorMessages(message:string) {
        return this.messages = [{ severity: 'error', summary: 'Error', detail: message }];
    }


    public setInfoToast(message:string, summary:string) {
        return this.message = {  key: 'bc', severity: 'info', summary: summary, detail: message };
    }

    public setWarnToast(message:string, summary:string) {
        return this.message = {  key: 'bc', severity: 'warn', summary: summary, detail: message };
    }

    public setSuccessToast(message:string, summary:string) {
        return this.message = { key: 'bc', severity: 'success', summary: summary, detail: message };
    }

    public setErrorToast(message:string, summary:string) {
        return this.message = { key: 'bc', severity: 'error', summary: summary, detail: message };
    }


}
