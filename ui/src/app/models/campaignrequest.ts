export class CampaignRequest {
  campaignDate: string;
  campaignSearchKeyWord: string;
  campaignStatus: string;
  campaignlocation: string;
  sortFieldType: string;
  sortby: string;
  sortbyOrder: string;
  startPage: number;
  totalPages: number;

  constructor(campaignDate: string, campaignSearchKeyWord: string, campaignStatus: string, campaignlocation: string, sortFieldType: string, sortby: string, sortbyOrder: string, startPage: number, totalPages: number) {
    this.campaignDate = campaignDate;
    this.campaignSearchKeyWord = campaignSearchKeyWord;
    this.campaignStatus = campaignStatus;
    this.campaignlocation = campaignlocation;
    this.sortFieldType = sortFieldType;
    this.sortby = sortby;
    this.sortFieldType = sortFieldType;
    this.sortbyOrder = sortbyOrder;
    this.startPage = startPage;
    this.totalPages = totalPages;
  }
}
