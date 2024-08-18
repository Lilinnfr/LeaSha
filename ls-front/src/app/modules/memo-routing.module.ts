import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardMemoComponent } from './memo-set/card-memo/one-card-memo/card-memo.component';
import { ListMemoComponent } from './memo-set/list-memo/all-list-memo/list-memo.component';
import { AllCardMemosComponent } from './memo-set/card-memo/all-card-memos/all-card-memos.component';
import { AuthGuard } from '../guards/auth.guard';
import { OneListComponent } from './memo-set/list-memo/one-list/one-list.component';

const memoRoutes: Routes = [
  {
    path: 'memoCarte/Mes mémos cartes',
    component: AllCardMemosComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'memoCarte/Mes mémos cartes/:memoId',
    component: CardMemoComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'memoListe/Mes mémos listes',
    component: ListMemoComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'memoListe/Mes mémos listes/:memoId',
    component: OneListComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memoRoutes)],
})
export class MemoRoutingModule {}
