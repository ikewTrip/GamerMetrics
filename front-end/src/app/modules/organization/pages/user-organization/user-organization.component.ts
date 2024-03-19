import {Component, OnInit} from '@angular/core';
import {OrganizationService} from "../../../../core/services/organization/organization.service";
import {OrganizationRequest} from "../../../../core/models/requests/organization-request";
import {NgForm} from "@angular/forms";
import {OrganizationResponse} from "../../../../core/models/responses/organization-response";
import {HttpErrorResponse} from "@angular/common/http";
import {TeamRequest} from "../../../../core/models/requests/team-request";
import {TeamService} from "../../../../core/services/team/team.service";
import {formatDate} from "@angular/common";
import {TeamResponse} from "../../../../core/models/responses/team-response";
import {UserResponse} from "../../../../core/models/responses/user-response";
import {InviteResponse} from "../../../../core/models/responses/invite-response";
import {InviteService} from "../../../../core/services/invite/invite.service";

@Component({
    selector: 'app-user-organization',
    templateUrl: './user-organization.component.html',
    styleUrls: ['./user-organization.component.css']
})
export class UserOrganizationComponent implements OnInit {

    constructor(
        private organizationService: OrganizationService,
        private teamService: TeamService,
        private inviteService: InviteService
    ) { }

    organizationRole: string | null = null;

    createOrganizationRequest: OrganizationRequest = {} as OrganizationRequest;
    editOrganizationRequest: OrganizationRequest = {} as OrganizationRequest;
    createTeamRequest: TeamRequest = {} as TeamRequest

    organizationInfo: OrganizationResponse = {} as OrganizationResponse;
    invites: InviteResponse[] = [] as InviteResponse[];

    createOrganizationMode: boolean = false;
    editOrganizationMode: boolean = false;
    deleteOrganizationMode: boolean = false;
    createTeamMode: boolean = false;

    viewInvitesMode: boolean = false;

    invalidCredentials: boolean = false;
    errorMessage: string = '';

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

    getOrganizationInfo() {
        this.organizationService.getOrganizationInfo().subscribe(
            {
                next: (response) => {
                    console.log(response);
                    this.organizationInfo = response;
                },
                error: (error) => {
                    console.log(error);
                    this.organizationInfo = {} as OrganizationResponse;
                }
            }
        );
    }

    ngOnInit(): void {
        this.getOrganizationRole();
        this.getOrganizationInfo();
        this.getInvites();
    }

    getInvites(){
        this.inviteService.getInvites().subscribe({
            next: (response) => {
                console.log(response);
                this.invites = response;
            },
            error: (error) => {
                console.log(error);
                this.invites = [] as InviteResponse[];
            }
        })
    }

    createOrganization(createOrganizationForm: NgForm) {
        this.organizationService.createOrganization(this.createOrganizationRequest).subscribe({
                next: (response) => {
                    console.log(response);
                    this.createOrganizationMode = false;
                    this.invalidCredentials = false;
                    this.ngOnInit();
                    this.errorMessage = '';
                    createOrganizationForm.reset();
                },
                error: (error) => {
                    this.handleErrors(error);
                }
            }
        );
    }

    editOrganizationInfo(createOrganizationForm: NgForm) {
        this.organizationService.editOrganization(this.editOrganizationRequest).subscribe({
                next: (response) => {
                    console.log(response);
                    this.editOrganizationMode = false;
                    this.invalidCredentials = false;
                    this.ngOnInit();
                    this.errorMessage = '';
                    createOrganizationForm.reset();
                },
                error: (error) => {
                    this.handleErrors(error);
                }
            }
        );
    }

    deleteOrganization() {
        this.organizationService.deleteOrganization().subscribe({
            next: (response) => {
                console.log(response);
                this.deleteOrganizationMode = false;
                this.invalidCredentials = false;
                this.ngOnInit();
                this.errorMessage = '';
            },
            error: (error) => {
                this.handleErrors(error);
            }
        })
    }

    createTeam(createTeamForm: NgForm) {
        this.teamService.createTeam(this.createTeamRequest).subscribe({
            next: (response) => {
                console.log(response);
                this.createTeamMode = false;
                this.invalidCredentials = false;
                this.ngOnInit();
                this.errorMessage = '';
                createTeamForm.reset();
            },
            error: (error) => {
                this.handleErrors(error);
            }
        })
    }
    acceptInvite(id: number) {
        this.inviteService.acceptInvite(id).subscribe({
            next: (response) => {
                console.log(response);
                this.invalidCredentials = false;
                this.ngOnInit();
                this.errorMessage = '';
            },
            error: (error) => {
                this.handleErrors(error);
            }
        })
    }
    changeCreateOrganizationMode() {
        this.createOrganizationMode = !this.createOrganizationMode;
    }

    changeCreateTeamMode() {
        this.createTeamMode = !this.createTeamMode;
    }

    changeEditOrganizationMode() {
        this.editOrganizationMode = !this.editOrganizationMode;
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

    changeDeleteOrganizationMode() {
        this.deleteOrganizationMode = !this.deleteOrganizationMode;
    }

    changeViewInvitesMode() {
    this.viewInvitesMode = !this.viewInvitesMode;
  }


}
