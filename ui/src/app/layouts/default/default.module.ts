import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DefaultComponent} from './default.component';
import {DashboardComponent} from 'src/app/modules/dashboard/dashboard.component';
import {RouterModule} from '@angular/router';
import {SharedModule} from 'src/app/shared/shared.module';
import {MatCardModule, MatDividerModule, MatPaginatorModule, MatSidenavModule, MatTableModule} from '@angular/material';
import {FlexLayoutModule} from '@angular/flex-layout';
import {DashboardService} from 'src/app/modules/dashboard.service';
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {SharedService} from "../../shared/shared.service";
import {LoginComponent} from "../../modules/login";
import {ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {MatSortModule} from "@angular/material/sort";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@NgModule({
    declarations: [
        DefaultComponent,
        DashboardComponent,
        LoginComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
        MatSidenavModule,
      MatDividerModule,
      FlexLayoutModule,
      MatCardModule,
      MatPaginatorModule,
      MatTableModule,
      MatButtonModule,
      MatFormFieldModule,
      MatSelectModule,
      ReactiveFormsModule,
      MatIconModule,
      MatSortModule,
      MatProgressSpinnerModule
    ],
    providers: [
        DashboardService,
        SharedService
    ]
})
export class DefaultModule {
}
