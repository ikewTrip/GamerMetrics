import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../core/services/user/user.service";
import {ProfileResponse} from "../../../../core/models/responses/profile-response";
import {NgForm} from "@angular/forms";
import {EditPersonalInfoRequest} from "../../../../core/models/requests/edit-personal-info-request";
import {Router} from "@angular/router";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    constructor(
        private userService: UserService,
        private router: Router
    ) {
    }

    editMode: boolean = false;
    deleteMode: boolean = false;
    invalidLogin: boolean | undefined;
    errorMessage: string = "";

    editPersonalInfoRequest: EditPersonalInfoRequest = {} as EditPersonalInfoRequest;

    profile: ProfileResponse = {} as ProfileResponse;

    ngOnInit(): void {
        this.userService.getProfile().subscribe({
            next: value => {
                this.profile = value;
                this.editPersonalInfoRequest = {
                    nickName: value.nickName,
                    firstName: value.firstName,
                    lastName: value.lastName,
                    steamId: value.steamId
                }
            },
            error: err => {
                console.log(err);
            }
        })
    }

    changeEditMode() {
        this.editMode = !this.editMode;
    }

    savePersonalInfo(loginForm: NgForm) {
        if (loginForm.valid) {
            this.userService.editPersonalInfo(this.editPersonalInfoRequest).subscribe({
                next: value => {
                    this.profile = value;
                    this.editPersonalInfoRequest = {
                        nickName: value.nickName,
                        firstName: value.firstName,
                        lastName: value.lastName,
                        steamId: value.steamId
                    }
                    this.changeEditMode();
                },
                error: err => {
                    console.log(err);
                    this.invalidLogin = true;
                    if (err.status === 400) {
                        this.errorMessage = err.error.message;
                        return;
                    }
                }
            })
        }
    }

    changeDeleteMode() {
        this.deleteMode = !this.deleteMode;
    }

    deleteAccount() {
        this.userService.deleteAccount().subscribe();
        localStorage.removeItem("jwt");
        this.router.navigate(['/auth/login']);
    }
}
