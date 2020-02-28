import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home';
import {LoginComponent} from './login';
import {AuthGuard} from './_helpers';

const routes: Routes = [
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: '', component: LoginComponent},

  // otherwise redirect to home
  {path: '**', redirectTo: ''}
];

export const appRoutingModule = RouterModule.forRoot(routes);
