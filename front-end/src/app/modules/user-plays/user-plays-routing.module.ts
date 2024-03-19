import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserPlaysComponent} from "./pages/user-plays/user-plays.component";

const routes: Routes = [
  { path: '', component: UserPlaysComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserPlaysRoutingModule { }
