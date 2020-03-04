import {Component, OnInit, ViewChild} from '@angular/core';
import {DashboardService} from '../dashboard.service';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {SharedService} from "../../shared/shared.service";
import {MatSort} from "@angular/material/sort";
import {CampaignRequest} from "../../models";
import {startWith, tap} from "rxjs/operators";

export interface CampaignRecords {
  name: string;
  duration: string;
  status: string;
  report: string;
}

/*
const ELEMENT_DATA: CampaignRecords[] = [
  { name: '123 Campaign', duration: '1 APR - 2 JUN 2019', status: 'Processing', report:'In 23 Days' },
  { name: 'Star Campaign', duration: '1 APR - 2 JUN 2019', status: 'Waiting',  report: 'In 40 Days' },
];
*/


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  isLoggedin = false;
  displayedColumns: string[] = ['name', 'duration', 'status', 'report'];
  dataSource = new MatTableDataSource<CampaignRecords>();

  @ViewChild(MatPaginator, {static: true})
  paginator: MatPaginator;
  //@ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private dashboardService: DashboardService, private sharedService: SharedService) {
  }

  ngOnInit() {
    this.isLoggedin = this.sharedService.isLoggedin();

    this.getCampaignResult();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngAfterViewInit() {
    this.paginator.page.pipe(
      startWith(null),
      tap(() => {
        this.dashboardService.fetchCampaignData(new CampaignRequest('', '', 'ALL', '', 'String', 'name_sort', 'asc', this.paginator.pageIndex, this.paginator.pageSize))
      }))
      .subscribe()
  }

  pageChanged(event: Event) {
    this.paginator.page.pipe(
      startWith(null),
      tap(() => {
        this.dashboardService.fetchCampaignData(new CampaignRequest('', '', 'ALL', '', 'String', 'name_sort', 'asc', this.paginator.pageIndex, this.paginator.pageSize))
      }))
      .subscribe()
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  public getCampaignResult = () => {
    console.log("Page Size :: " + this.paginator.pageSize);
    this.dashboardService.fetchCampaignData(new CampaignRequest('', '', 'ALL', '', 'String', 'name_sort', 'asc', this.paginator.pageIndex, this.paginator.pageSize)).subscribe(res => {
      console.log(res);
      this.dataSource.data = res as CampaignRecords[];
    });
  }
}
