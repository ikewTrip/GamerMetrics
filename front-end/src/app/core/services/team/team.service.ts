import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TeamRequest} from "../../models/requests/team-request";
import {TeamResponse} from "../../models/responses/team-response";
import {DetailedTeamResponse} from "../../models/responses/detailed-team-response";
import {InviteUserRequest} from "../../models/requests/invite-user-request";
import {TrainingRequest} from "../../models/requests/training-request";
import {TrainingResponse} from "../../models/responses/training-response";

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private URL = 'http://localhost:8080/api/v1/teams';
  constructor(
      private http: HttpClient
  ) { }

  createTeam(teamRequest: TeamRequest){
    return this.http.post<TeamResponse>(this.URL, teamRequest);
  }

  editTeam(id: number, teamRequest: TeamRequest){
    return this.http.put<TeamResponse>(this.URL + '/' + id, teamRequest);
  }

  deleteTeam(id: number){
    return this.http.delete(this.URL + '/' + id);
  }

  getTeamInfo(id: number){
    return this.http.get<DetailedTeamResponse>(this.URL + '/' + id);
  }

  inviteUser(teamId: number, nickName: string, inviteUserRequest: InviteUserRequest){
    return this.http.post(this.URL + '/' + teamId + '/invite/' + nickName, inviteUserRequest);
  }

  dismissParticipant(nickName: string){
    return this.http.post(this.URL + '/remove/' + nickName, {});
  }

  createTraining(teamId: number, trainingRequest: TrainingRequest){
    return this.http.post<TrainingResponse>(this.URL + '/' + teamId + '/trainings', trainingRequest);
  }

  getTrainings(teamId: number){
    return this.http.get<TrainingResponse[]>(this.URL + '/' + teamId + '/trainings');
  }


}
