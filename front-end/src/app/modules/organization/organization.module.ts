import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrganizationRoutingModule } from './organization-routing.module';
import { UserOrganizationComponent } from './pages/user-organization/user-organization.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { TeamComponent } from './pages/team/team.component';


@NgModule({
  declarations: [
    UserOrganizationComponent,
    TeamComponent
  ],
    imports: [
        CommonModule,
        OrganizationRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ]
})
export class OrganizationModule { }
