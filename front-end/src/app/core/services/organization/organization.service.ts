import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {OrganizationRoleResponse} from "../../models/responses/organization-role-response";
import {OrganizationRequest} from "../../models/requests/organization-request";
import {OrganizationResponse} from "../../models/responses/organization-response";

@Injectable({
  providedIn: 'root'
})
export class OrganizationService {

  private URL = 'http://localhost:8080/api/v1/organizations';

  constructor(
      private http: HttpClient
  ) { }

  getOrganizationRole(){
    return this.http.get<OrganizationRoleResponse>(this.URL + '/organization-role');
  }

  createOrganization(organizationRequest: OrganizationRequest){
    return this.http.post<OrganizationResponse>(this.URL, organizationRequest,
        {headers: new HttpHeaders({ "Content-Type": "application/json"})}
    );
  }

  editOrganization(organizationRequest: OrganizationRequest){
    return this.http.put<OrganizationResponse>(this.URL, organizationRequest,
        {headers: new HttpHeaders({ "Content-Type": "application/json"})}
    );
  }

  getOrganizationInfo() {
    return this.http.get<OrganizationResponse>(this.URL + '/my');
  }

  deleteOrganization() {
    return this.http.delete(this.URL);
  }
}
