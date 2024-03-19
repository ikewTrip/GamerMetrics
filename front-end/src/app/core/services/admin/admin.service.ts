import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AllUsersResponse} from "../../models/responses/all-users-response";
import {BackupResponse} from "../../models/responses/backup-response";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8080/api/v1/';
  constructor(
    private http: HttpClient
  ) { }

  getAllUsers() {
    return this.http.get<AllUsersResponse>(this.baseUrl + 'users');
  }

  blockUser(nickName: string) {
    return this.http.post(this.baseUrl + 'users/block/' + nickName, {});
  }

  unblockUser(nickName: string) {
    return this.http.post(this.baseUrl + 'users/unblock/' + nickName, {});
  }

  deleteUser(nickName: string) {
    return this.http.delete(this.baseUrl + 'users/' + nickName);
  }

  getBackups() {
    return this.http.get<BackupResponse[]>(this.baseUrl + 'backups');
  }

  createBackup() {
    return this.http.post(this.baseUrl + 'backups', {});
  }
}
