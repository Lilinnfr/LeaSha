import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { MemoSetComponent } from './modules/memo-set/memo-set.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'utilisateur/inscription',
    component: LoginComponent,
  },
  { path: 'Tous mes mémos', component: MemoSetComponent },
  {
    path: 'Mes mémos',
    loadChildren: () =>
      import('./modules/memo-routing.module').then((m) => m.MemoRoutingModule),
  },
  { path: '**', redirectTo: 'landing', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
