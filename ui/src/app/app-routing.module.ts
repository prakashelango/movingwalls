import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DefaultComponent} from './layouts/default/default.component';
import {DashboardComponent} from './modules/dashboard/dashboard.component';
import {LoginComponent} from "./modules/login";

const routes: Routes = [
    {path: '', component: LoginComponent},
    {
        path: 'campaign',
        component: DefaultComponent,
        children: [{
            path: '',
            component: DashboardComponent
        }],
    },
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
