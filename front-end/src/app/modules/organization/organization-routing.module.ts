import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserOrganizationComponent} from "./pages/user-organization/user-organization.component";
import {TeamComponent} from "./pages/team/team.component";

const routes: Routes = [
    { path: '', component: UserOrganizationComponent},
    { path: 'team/:id', component: TeamComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrganizationRoutingModule { }
