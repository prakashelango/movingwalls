import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    constructor(private http: HttpClient) {
    }

    fetchCampaignData(username: string, password: string) {
        console.log('user' + username);
        return this.http.get<any>(`${environment.serviceApiUrl}/campaign/name/asc`);
    }

}
