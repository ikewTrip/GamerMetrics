import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CoreModule} from "./core/core.module";
import {JwtModule} from "@auth0/angular-jwt";
import {AuthGuard} from "./core/guards/auth.guard";

@NgModule({
  declarations: [
    AppComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        CoreModule,
        JwtModule.forRoot({
            config: {
                tokenGetter: () => { return localStorage.getItem('jwt'); },
                allowedDomains: ['localhost:8080'],
                disallowedRoutes: ['http://localhost:8000/api/login']
            }
        })
    ],
  providers: [AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
