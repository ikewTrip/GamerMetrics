import { Injectable } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProfileResponse} from "../../models/responses/profile-response";
import {EditPersonalInfoRequest} from "../../models/requests/edit-personal-info-request";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080/api/v1/users';

  constructor(
      private http: HttpClient
  ) { }

  getProfile() : Observable<ProfileResponse>{
    return this.http.get<ProfileResponse>(this.url + '/profile');
  }

  editPersonalInfo(editPersonalInfoRequest: EditPersonalInfoRequest) {
    return this.http.put<ProfileResponse>(this.url, editPersonalInfoRequest);
  }

  deleteAccount() {
    return this.http.delete(this.url);
  }

}
