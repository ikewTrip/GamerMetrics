import {Component} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(
      private authService: AuthService,
      private router: Router
  ) {
  }

  isUserAuthenticated = (): boolean => {
    return this.authService.isUserAuthenticated();
  }

  logOut() {
    this.authService.logout().subscribe();
    localStorage.removeItem("jwt");
    this.router.navigate(["/auth/login"]);
  }

  checkIsUserAdmin() {
    return this.authService.checkIsUserAdmin();
  }

}
