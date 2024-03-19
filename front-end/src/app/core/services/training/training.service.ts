import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TrainingRequest} from "../../models/requests/training-request";
import {TrainingResponse} from "../../models/responses/training-response";

@Injectable({
  providedIn: 'root'
})
export class TrainingService {

  private URL = 'http://localhost:8080/api/v1/trainings';

  constructor(
      private http: HttpClient
  ) { }

  deleteTraining(id: number){
    return this.http.delete(this.URL + '/' + id);
  }

  editTraining(id: number, trainingRequest: TrainingRequest){
    return this.http.put<TrainingResponse>(this.URL + '/' + id, trainingRequest);
  }
}
