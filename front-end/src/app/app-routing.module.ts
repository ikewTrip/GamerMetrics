import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from "./core/components/not-found/not-found.component";
import {AuthGuard} from "./core/guards/auth.guard";

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'profile',
    loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'organization',
    loadChildren: () => import('./modules/organization/organization.module').then(m => m.OrganizationModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'userplays',
    loadChildren: () => import('./modules/user-plays/user-plays.module').then(m => m.UserPlaysModule),
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
