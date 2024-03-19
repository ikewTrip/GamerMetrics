import {MetricsResponse} from "./metrics-response";

export interface MetricsDetailedResponse {
    averageHeartRate: number;
    metrics: MetricsResponse[];
}
