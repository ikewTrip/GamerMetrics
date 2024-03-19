import {UserResponse} from "./user-response";
import {TeamResponse} from "./team-response";

export interface DetailedTeamResponse {
    teamInfo: TeamResponse;
    teamMembers: UserResponse[];
}
