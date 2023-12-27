import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { HttpRequestInterceptor } from '../app/core/interceptors/httpRequest-Interceptor'
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';

export const appConfig: ApplicationConfig = {

  providers: [provideRouter(routes),
    importProvidersFrom(HttpClientModule),
    provideHttpClient(withInterceptors([HttpRequestInterceptor])),
    provideNoopAnimations()]

};
