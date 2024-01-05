import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimations, provideNoopAnimations } from '@angular/platform-browser/animations';
import { HttpRequestInterceptor } from '../app/core/interceptors/httpRequest-Interceptor'
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { DateAgoPipe } from './pipes/date-ago.pipe';

export const appConfig: ApplicationConfig = {

  providers: [provideRouter(routes),
    DateAgoPipe,
    importProvidersFrom(HttpClientModule),
    provideHttpClient(withInterceptors([HttpRequestInterceptor])),
    provideAnimations()]

};
