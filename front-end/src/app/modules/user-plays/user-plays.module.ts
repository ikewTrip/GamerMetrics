import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserPlaysRoutingModule } from './user-plays-routing.module';
import { UserPlaysComponent } from './pages/user-plays/user-plays.component';
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    UserPlaysComponent
  ],
  imports: [
    CommonModule,
    UserPlaysRoutingModule,
    FormsModule
  ]
})
export class UserPlaysModule { }
