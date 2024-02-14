import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { MemoSetComponent } from './modules/memo-set/memo-set.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'Mes mémos', component: MemoSetComponent },
  { path: '**', redirectTo: 'landing', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
