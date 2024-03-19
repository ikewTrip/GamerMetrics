import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {InviteResponse} from "../../models/responses/invite-response";

@Injectable({
  providedIn: 'root'
})
export class InviteService {

  private URL = 'http://localhost:8080/api/v1/';
  constructor(
      private http: HttpClient
  ) { }

  getInvites(){
    return this.http.get<InviteResponse[]>(this.URL + 'invites');
  }

  acceptInvite(id: number){
    return this.http.post(this.URL + 'users' + '/accept/' + id , {});
  }
}
