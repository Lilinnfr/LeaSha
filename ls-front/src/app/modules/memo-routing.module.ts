import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardMemoComponent } from './memo-set/card-memo/one-card-memo/card-memo.component';
import { ListMemoComponent } from './memo-set/list-memo/list-memo.component';
import { MemoSetComponent } from './memo-set/memo-set.component';
import { AllCardMemosComponent } from './memo-set/card-memo/all-card-memos/all-card-memos.component';
import { AuthGuard } from '../guards/auth.guard';

const memoRoutes: Routes = [
  { path: '', component: MemoSetComponent },
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
    path: 'Mes mémos listes',
    component: ListMemoComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memoRoutes)],
})
export class MemoRoutingModule {}
