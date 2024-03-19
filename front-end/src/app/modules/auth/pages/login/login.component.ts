import { Component } from '@angular/core';
import {LoginRequest} from "../../../../core/models/requests/login-request";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {AuthenticatedResponse} from "../../../../core/models/responses/authenticated-response";
import {AuthService} from "../../../../core/services/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  invalidLogin: boolean | undefined;
  credentials: LoginRequest = {email:'', password:''};
  errorMessage: string = "";

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }

  login = ( form: NgForm) => {
    if (form.valid) {
      this.authService.login(this.credentials)
        .subscribe({
          next: (response: AuthenticatedResponse) => {
            console.log(response)
            const token = response.token;
            localStorage.setItem("jwt", token);
            this.invalidLogin = false;
            this.router.navigate(["/profile"]);
          },
          error: (err: HttpErrorResponse) => {
            this.invalidLogin = true;
            console.log(err)
            if (err.status === 401) {
              this.errorMessage = err.error.message;
              return;
            }
            err.error.errors.forEach((e: string) => {
              this.errorMessage += e + '\n';
            })
          }
        })
    }
  }

}
