import { Component } from '@angular/core';
import {NgForm} from "@angular/forms";
import {RegisterRequest} from "../../../../core/models/requests/register-request";
import {AuthService} from "../../../../core/services/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(
      private router: Router,
      private authService: AuthService
  ) { }

  invalidCredentials: boolean | undefined;
  errorMessage: string = "";

  credentials: RegisterRequest = {} as RegisterRequest;

  register(loginForm: NgForm) {
if (loginForm.valid) {
      this.authService.register(this.credentials)
          .subscribe({
            next: (response) => {
              console.log(response)
              const token = response.token;
              localStorage.setItem("jwt", token);
              this.invalidCredentials = false;
              this.router.navigate(["/profile"]);
            },
            error: (err) => {
              this.invalidCredentials = true;
              console.log(err)
              if (err.status === 400) {
                if (err.error.message != undefined) {
                    this.errorMessage = err.error.message;
                    return;
                }
              }
              err.error.errors.forEach((e: string) => {
                this.errorMessage += e + '\n';
              })
            }
          })
    }
  }

}
