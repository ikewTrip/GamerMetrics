import {Component, OnInit} from '@angular/core';
import {TeamService} from "../../../../core/services/team/team.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OrganizationService} from "../../../../core/services/organization/organization.service";
import {TeamResponse} from "../../../../core/models/responses/team-response";
import {UserResponse} from "../../../../core/models/responses/user-response";
import {logMessages} from "@angular-devkit/build-angular/src/tools/esbuild/utils";
import {DetailedTeamResponse} from "../../../../core/models/responses/detailed-team-response";
import {InviteUserRequest} from "../../../../core/models/requests/invite-user-request";
import {NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {TeamRequest} from "../../../../core/models/requests/team-request";
import {TrainingRequest} from "../../../../core/models/requests/training-request";
import {TrainingResponse} from "../../../../core/models/responses/training-response";
import {TrainingService} from "../../../../core/services/training/training.service";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit{

  constructor(
      private route: ActivatedRoute,
      private organizationService: OrganizationService,
      private teamService: TeamService,
      private trainingService: TrainingService,
      private router: Router
  ) { }

  organizationRole: string | null = null;
  organizationRoles = ["TRAINER", "PLAYER"];

  teamInfo: TeamResponse = {} as TeamResponse;
  participants: UserResponse[] = [];
  trainings: TrainingResponse[] = [];

  editTeamMode: boolean = false;
  deleteTeamMode: boolean = false;
  inviteUserMode: boolean = false;
  dismissParticipantMode: boolean = false;
  createTrainingMode: boolean = false;
  deleteTrainingMode: boolean = false;
  editTrainingMode: boolean = false;

  inviteUserRequest = {
    nickName: "",
    message: "",
    organizationRoleName: "",
  };
  inviteRequest: InviteUserRequest = {} as InviteUserRequest;

  editTeamRequest: TeamRequest= {} as TeamRequest
  createTrainingFormData = {
    name: "",
    description: "",
    startDate: "",
    startTime: "",
    endDate: "",
    endTime: ""
  }
  createTrainingRequest: TrainingRequest = {} as TrainingRequest

  editTrainingFormData = {
    name: "",
    description: "",
    startDate: "",
    startTime: "",
    endDate: "",
    endTime: ""
  };
  editTrainingRequest: TrainingRequest = {} as TrainingRequest;

  errorMessage: string = "";
  invalidCredentials: boolean = false;


  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.getOrganizationRole();
    this.getTeamInfo(Number(id));
    this.getTrainings(Number(id));
  }

  getTrainings(number: number) {
    this.teamService.getTrainings(number).subscribe({
      next: (response) => {
        console.log(response);
        this.trainings = response;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  getOrganizationRole() {
    this.organizationService.getOrganizationRole().subscribe(
        {
          next: (response) => {
            console.log(response)
            this.organizationRole = response.name;
          },
          error: (error) => {
            console.log(error);
            this.organizationRole = null;
          }
        }
    );
  }

  getTeamInfo(id: number){
      this.teamService.getTeamInfo(id).subscribe({
          next: (response) => {
              console.log(response);
              this.teamInfo = response.teamInfo;
              this.participants = response.teamMembers;
          },
          error: (error) => {
              console.log(error);
          }});
  }

  editTeamInfo(editTeamForm: NgForm) {
    this.invalidCredentials = false;
    this.errorMessage = "";

    if(editTeamForm.valid){
      const id = Number(this.route.snapshot.paramMap.get('id'));

      this.teamService.editTeam(id, this.editTeamRequest).subscribe({
        next: (response) => {
          console.log(response);
          this.changeEditTeamMode();
          this.getTeamInfo(id);
        },
        error: (error) => {
          this.handleErrors(error);
        }
      });
    }
  }

  deleteTeam() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.teamService.deleteTeam(id).subscribe({
      next: (response) => {
        console.log(response);
        this.changeDeleteTeamMode();
        this.getTeamInfo(id);
        this.router.navigate(['/organization']);
      },
      error: (error) => {
        this.handleErrors(error);
      }
    });
  }

  inviteUser(createTeamForm: NgForm) {
    this.invalidCredentials = false;
    this.errorMessage = "";
    if(createTeamForm.valid){
      const id = Number(this.route.snapshot.paramMap.get('id'));

      this.inviteRequest.message = this.inviteUserRequest.message;
      this.inviteRequest.organizationRoleName = this.inviteUserRequest.organizationRoleName;

      this.teamService.inviteUser(id, this.inviteUserRequest.nickName, this.inviteRequest).subscribe({
        next: (response) => {
          console.log(response);
          this.changeInviteUserMode();
          this.getTeamInfo(id);
        },
        error: (error) => {
          this.handleErrors(error);
        }
      });
    }
  }

  dismissParticipant(nickName: string) {
    this.invalidCredentials = false;
    this.errorMessage = "";

    this.teamService.dismissParticipant(nickName).subscribe({
      next: (response) => {
        console.log(response);
        this.changeDismissParticipantMode();
        this.getTeamInfo(Number(this.route.snapshot.paramMap.get('id')));
      },
      error: (error) => {
        this.handleErrors(error);
      }
    });
  }

  createTraining(createTrainingForm: NgForm) {
    this.invalidCredentials = false;
    this.errorMessage = "";
    if(createTrainingForm.valid){
      const id = Number(this.route.snapshot.paramMap.get('id'));

      this.createTrainingRequest.name = this.createTrainingFormData.name;
      this.createTrainingRequest.description = this.createTrainingFormData.description;
      this.createTrainingRequest.startTime = this.createTrainingFormData.startDate + "T" + this.createTrainingFormData.startTime;
      this.createTrainingRequest.endTime = this.createTrainingFormData.endDate + "T" + this.createTrainingFormData.endTime;

      this.teamService.createTraining(id, this.createTrainingRequest).subscribe({
        next: (response) => {
          console.log(response);
          this.changeCreateTrainingMode();
          this.ngOnInit();
        },
        error: (error) => {
          this.handleErrors(error);
        }
      });
    }
  }

  editTraining(id: number, editTrainingForm: NgForm) {
    this.invalidCredentials = false;
    this.errorMessage = "";
    if(editTrainingForm.valid){
      const teamId = Number(this.route.snapshot.paramMap.get('id'));

      this.editTrainingRequest.name = this.editTrainingFormData.name;
      this.editTrainingRequest.description = this.editTrainingFormData.description;
      this.editTrainingRequest.startTime = this.editTrainingFormData.startDate + "T" + this.editTrainingFormData.startTime;
      this.editTrainingRequest.endTime = this.editTrainingFormData.endDate + "T" + this.editTrainingFormData.endTime;

      this.trainingService.editTraining(id, this.editTrainingRequest).subscribe({
        next: (response) => {
          console.log(response);
          this.changeEditTrainingMode();
          this.ngOnInit();
        },
        error: (error) => {
          this.handleErrors(error);
        }
      });
    }
  }

  deleteTraining(id: number) {
    this.invalidCredentials = false;
    this.errorMessage = "";
    const teamId = Number(this.route.snapshot.paramMap.get('id'));
    this.trainingService.deleteTraining(id).subscribe({
      next: (response) => {
        console.log(response);
        this.changeDeleteTrainingMode();
        this.ngOnInit();
      },
      error: (error) => {
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

  changeEditTeamMode() {
    this.editTeamMode = !this.editTeamMode;
  }

  changeDeleteTeamMode() {
    this.deleteTeamMode = !this.deleteTeamMode;
  }

  changeInviteUserMode() {
    this.inviteUserMode = !this.inviteUserMode;
  }

  changeDismissParticipantMode() {
    this.dismissParticipantMode = !this.dismissParticipantMode;
  }

  changeCreateTrainingMode() {
    this.createTrainingMode = !this.createTrainingMode;
  }

  changeDeleteTrainingMode() {
    this.deleteTrainingMode = !this.deleteTrainingMode;
  }

  changeEditTrainingMode() {
    this.editTrainingMode = !this.editTrainingMode;
  }


}
