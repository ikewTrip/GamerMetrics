import {Component, OnInit} from '@angular/core';
import {AdminService} from "../../../../core/services/admin/admin.service";
import {UserResponse} from "../../../../core/models/responses/user-response";
import {BackupResponse} from "../../../../core/models/responses/backup-response";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  constructor(
    private adminService: AdminService,
  ) { }

  users: UserResponse[] = [];
  backups: BackupResponse[] = [];

  ngOnInit(): void {
    this.getUsers();
    this.getBackups();
  }

  getUsers() {
    this.adminService.getAllUsers().subscribe(
      {
        next: data => {
          console.log(data)
          this.users = data.users;
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  blockUser(nickName: string) {
    this.adminService.blockUser(nickName).subscribe(
      {
        next: data => {
          this.ngOnInit();
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  unblockUser(nickName: string) {
    this.adminService.unblockUser(nickName).subscribe(
      {
        next: data => {
          this.ngOnInit();
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  deleteUser(nickName: string) {
    this.adminService.deleteUser(nickName).subscribe(
      {
        next: data => {
          this.ngOnInit();
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  getBackups() {
    this.adminService.getBackups().subscribe(
      {
        next: data => {
          this.backups = data;
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  createBackup() {
    this.adminService.createBackup().subscribe(
      {
        next: data => {
          this.ngOnInit();
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }
}
