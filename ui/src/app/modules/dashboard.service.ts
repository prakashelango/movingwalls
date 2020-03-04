import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CampaignRequest} from "../models";
import {finalize, map} from "rxjs/operators";
import {BehaviorSubject} from "rxjs";
import {CampaignRecords} from "./dashboard/dashboard.component";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private currentCampaignData: BehaviorSubject<CampaignRecords[]>;

  private loadingSubject = new BehaviorSubject<boolean>(false);

  private loading = this.loadingSubject.asObservable();

  constructor(private http: HttpClient) {
    this.currentCampaignData = new BehaviorSubject<CampaignRecords[]>([]);
  }

  /*fetchCampaignData(username: string, password: string) {
      console.log('user' + username);
      return this.http.get<any>(`${environment.serviceApiUrl}/campaign/name/asc`);
  }*/

  fetchCampaignData(campaign: CampaignRequest) {
    this.loadingSubject.next(true);
    return this.http.post<any>(`${environment.serviceApiUrl}/campaign/search`, {
      campaignDate: campaign.campaignDate,
      campaignSearchKeyWord: campaign.campaignSearchKeyWord,
      campaignStatus: campaign.campaignStatus,
      campaignlocation: campaign.campaignlocation,
      sortFieldType: campaign.sortFieldType,
      sortby: campaign.sortby,
      sortbyOrder: campaign.sortbyOrder,
      startPage: campaign.startPage,
      totalPages: campaign.totalPages,
      tokenPayloadInfo: null
    })
      .pipe(
        finalize(() => this.loadingSubject.next(false)),
        map(campaignres => {
          this.currentCampaignData.next(campaignres);
          return campaignres;
        }));
  }

}
