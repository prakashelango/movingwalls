import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatPaginatorModule, MatTableModule} from '@angular/material'
import {MatIconModule} from '@angular/material/icon';
import {MatSortModule} from '@angular/material/sort';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
// used to create fake backend
import {BasicAuthInterceptor, ErrorInterceptor, fakeBackendProvider} from './_helpers';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';
import {appRoutingModule} from './app.routing';
import {HomeComponent} from './home';
import {LoginComponent} from './login';
import {CampaignComponent} from './campaign/campaign.component';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import {SidenavComponent} from './sidenav/sidenav.component'
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule} from '@angular/material/button'

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    appRoutingModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatSortModule,
    MatInputModule,
    MatListModule,
    BrowserModule.withServerTransition({appId: 'universal-app'}),
    MatSidenavModule,
    MatToolbarModule,
    LayoutModule,
    MatButtonModule],
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    CampaignComponent,
    SidenavComponent],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}},

    // provider used to create fake backend
    fakeBackendProvider
  ],
  exports: [
    SidenavComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
