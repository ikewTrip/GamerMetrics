import {TeamResponse} from "./team-response";

export interface OrganizationResponse {
    id: number;
    name: string;
    description: string;
    creationDate: string;
    creatorName: string;
    teams: TeamResponse[];
}
