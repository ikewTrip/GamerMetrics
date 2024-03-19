import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserPlayResponse} from "../../models/responses/user-play-response";
import {UserPlayRequest} from "../../models/requests/user-play-request";
import {UserPlayResultResponse} from "../../models/responses/user-play-result-response";
import {MetricsDetailedResponse} from "../../models/responses/metrics-detailed-response";
import {UserPlayResultRequest} from "../../models/requests/user-play-result-request";

@Injectable({
  providedIn: 'root'
})
export class UserPlayService {

  private url = 'http://localhost:8080/api/v1/userplays';
  private url_result = 'http://localhost:8080/api/v1/results';
  private url_metrics = 'http://localhost:8080/api/v1/metrics';

  constructor(
    private http: HttpClient
  ) { }

  getAllUserPlays() {
    return this.http.get<UserPlayResponse[]>(this.url);
  }

  startUserPlay(userPlayRequest: UserPlayRequest) {
    return this.http.post<UserPlayResponse>(this.url, userPlayRequest);
  }

  stopUserPlay() {
    return this.http.post(this.url + '/stop', {});
  }

  getIfUserPlayAlreadyStarted() {
    return this.http.get<boolean>(this.url + '/state');
  }

  editUserPlay(id: number, userPlayRequest: UserPlayRequest) {
    return this.http.put<UserPlayResponse>(this.url + "/" + id, userPlayRequest);
  }

  deleteUserPlay(id: number) {
    return this.http.delete(this.url + "/" + id);
  }

  getUserPlayResult(id: number) {
    return this.http.get<UserPlayResultResponse>(this.url_result + "/" + id);
  }

  editUserPlayResult(id: number, userPlayRequest: UserPlayResultRequest) {
    return this.http.put<UserPlayResultResponse>(this.url_result + "/" + id, userPlayRequest);
  }

  getMetricsOfUserPlay(id: number) {
    return this.http.get<MetricsDetailedResponse>(this.url_metrics + "/" + id);
  }

}
