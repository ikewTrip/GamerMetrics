import {Component, OnInit} from '@angular/core';
import {UserPlayService} from "../../../../core/services/user-play/user-play.service";
import {UserPlayRequest} from "../../../../core/models/requests/user-play-request";
import {NgForm} from "@angular/forms";
import {UserPlayResponse} from "../../../../core/models/responses/user-play-response";
import {HttpErrorResponse} from "@angular/common/http";
import {UserPlayResultResponse} from "../../../../core/models/responses/user-play-result-response";
import {MetricsDetailedResponse} from "../../../../core/models/responses/metrics-detailed-response";
import {UserPlayResultRequest} from "../../../../core/models/requests/user-play-result-request";

@Component({
  selector: 'app-user-plays',
  templateUrl: './user-plays.component.html',
  styleUrls: ['./user-plays.component.css']
})
export class UserPlaysComponent implements OnInit{

  maps = [
    "DE_OVERPASS",
    "DE_ANUBIS",
    "DE_INFERNO",
    "DE_MIRAGE",
    "DE_VERTIGO",
    "DE_NUKE",
    "DE_ANCIENT"
  ];

  constructor(
    private userPlayService: UserPlayService
  ) {
  }

  userPlays: UserPlayResponse[] = [];

  userPlayStartMode: boolean = false;
  editUserPlayMode: boolean = false
  editResultMode: boolean = false
  showMetricsMode: boolean = false;

  userPlayRequest: UserPlayRequest = {} as UserPlayRequest;
  editUserPlayRequest: UserPlayRequest = {} as UserPlayRequest;
  editPlayResultRequest: UserPlayResultRequest = {} as UserPlayResultRequest;

  selectedUserPlayId: number | undefined;
  selectedUserPlayResult: UserPlayResultResponse = {} as UserPlayResultResponse;
  selectedUserPlayMetrics: MetricsDetailedResponse = {} as MetricsDetailedResponse;

  isUserPlayStarted: boolean | undefined;

  invalidCredentials: boolean = false;
  errorMessage: string = ""



  ngOnInit(): void {
    this.getAllUserPlays();
    this.isAnyUserPlayStarted();
    if (this.selectedUserPlayId != undefined) {
      this.showPlayResultsAndMetrics(this.selectedUserPlayId);
    }
  }

  getAllUserPlays() {
    this.userPlayService.getAllUserPlays().subscribe({
      next: response => {
        this.userPlays = response;
      }
    });
  }

  startUserPlay(ngForm: NgForm) {
    this.invalidCredentials = false;
    this.errorMessage = "";
    if (ngForm.valid) {
      this.userPlayService.startUserPlay(this.userPlayRequest).subscribe({
        next: response => {
          this.ngOnInit();
          this.changeUserPlayStartMode();
        },
        error: error => {
          this.invalidCredentials = true;
          this.handleErrors(error);
        }
      });
    }
  }

  isAnyUserPlayStarted() {
    this.userPlayService.getIfUserPlayAlreadyStarted().subscribe({
      next: response => {
        console.log(response)
        this.isUserPlayStarted = response;
      }
    });
  }

  stopUserPlay() {
    this.userPlayService.stopUserPlay().subscribe({
      next: response => {
        this.ngOnInit();
      }
    });
  }

  showPlayResultsAndMetrics(id: number) {
    this.selectedUserPlayId = id;
    this.userPlayService.getUserPlayResult(id).subscribe({
      next: response => {
        this.selectedUserPlayResult = response;
      },
      error: error => {
        this.handleErrors(error);
      }
    });
    this.userPlayService.getMetricsOfUserPlay(id).subscribe({
      next: response => {
        this.selectedUserPlayMetrics = response;
      },
      error: error => {
        this.handleErrors(error);
      }
    });
  }

  editUserPlay(form: NgForm, id: number) {
    this.invalidCredentials = false;
    this.errorMessage = "";
    if (form.valid) {
      this.userPlayService.editUserPlay(id, this.editUserPlayRequest).subscribe({
        next: response => {
          this.ngOnInit();
          this.changeEditUserPlayMode();
        },
        error: error => {
          this.handleErrors(error);
        }
      });
    }
  }

  editResultPlay(editResultForm: NgForm, selectedUserPlayId: number | undefined) {
    this.invalidCredentials = false;
    this.errorMessage = "";
    if (editResultForm.valid) {
      this.userPlayService.editUserPlayResult(selectedUserPlayId!, this.editPlayResultRequest).subscribe({
        next: response => {
          this.ngOnInit();
          this.changeEditResultMode();
        },
        error: error => {
          this.handleErrors(error);
        }
      });
    }
  }

  deletePlay(id: number) {
    this.userPlayService.deleteUserPlay(id).subscribe({
      next: response => {
        this.ngOnInit();
      },
      error: error => {
        this.handleErrors(error);
      }
    });
  }

  handleErrors(error: HttpErrorResponse) {
    console.log(error);
    this.invalidCredentials = true;
    if (error.status === 400) {
      if (error.error.message != undefined) {
        this.errorMessage = error.error.message;
        return;
      }
    }
    this.errorMessage = '';
    error.error.errors.forEach((e: string) => {
      this.errorMessage += e + '\n';
    })
  }

  changeUserPlayStartMode() {
    this.userPlayStartMode = !this.userPlayStartMode;
  }

  changeEditUserPlayMode() {
    this.editUserPlayMode = !this.editUserPlayMode;
  }

  changeEditResultMode() {
    this.editResultMode = !this.editResultMode;
  }

  changeShowMetricsMode() {
    this.showMetricsMode = !this.showMetricsMode;
  }


}
