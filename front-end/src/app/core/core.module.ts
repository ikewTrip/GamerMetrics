import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import {RouterLink} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import { NotFoundComponent } from './components/not-found/not-found.component';



@NgModule({
    declarations: [
        HeaderComponent,
        FooterComponent,
        NotFoundComponent
    ],
  exports: [
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    RouterLink,
    HttpClientModule
  ]
})
export class CoreModule { }
