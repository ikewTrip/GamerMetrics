import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {LoginRequest} from "../../models/requests/login-request";
import {AuthenticatedResponse} from "../../models/responses/authenticated-response";
import {JwtHelperService} from "@auth0/angular-jwt";
import {RegisterRequest} from "../../models/requests/register-request";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private AUTH_URL = 'http://localhost:8080/api/v1/auth/';

  constructor(
    private http: HttpClient,
    private jwtHelper: JwtHelperService
  ) {}

  // method to login user to the application
  public login (credentials: LoginRequest): Observable<AuthenticatedResponse> {
    return this.http.post<AuthenticatedResponse>(
        this.AUTH_URL + 'authenticate',
        credentials,
        {headers: new HttpHeaders({ "Content-Type": "application/json"})}
    );
  }

  // method to register user in the application
  public register (credentials: RegisterRequest): Observable<AuthenticatedResponse> {
    return this.http.post<AuthenticatedResponse>(
        this.AUTH_URL + 'register',
        credentials,
        {headers: new HttpHeaders({ "Content-Type": "application/json"})}
    );
  }

  // method to logout user from the application
  public logout (): Observable<any> {
    return this.http.post(
        this.AUTH_URL + 'logout',
        {},
        {headers: {'Content-Type': 'application/json'}}
    );
  }

  // method to check if user is authenticated
  isUserAuthenticated = (): boolean => {
    const token = localStorage.getItem("jwt");
    if (token && !this.jwtHelper.isTokenExpired(token)){
      return true;
    }
    return false;
  }

  // method to check if user is admin
  checkIsUserAdmin() {
    const token = localStorage.getItem("jwt");
    if (token) {
      const decodedToken = this.jwtHelper.decodeToken(token);
      if (decodedToken.role == "ADMIN") {
        return true;
      }
    }
    return false;
  }
}
