import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardMemoComponent } from './memo-set/card-memo/one-card-memo/card-memo.component';
import { ListMemoComponent } from './memo-set/list-memo/list-memo.component';
import { MemoSetComponent } from './memo-set/memo-set.component';
import { AllCardMemosComponent } from './memo-set/card-memo/all-card-memos/all-card-memos.component';

const memoRoutes: Routes = [
  { path: '', component: MemoSetComponent },
  { path: 'memoCarte/Mes mémos cartes', component: AllCardMemosComponent },
  {
    path: 'memoCarte/Mes mémos cartes/:memoId',
    component: CardMemoComponent,
  },
  { path: 'Mes mémos listes', component: ListMemoComponent },
];

@NgModule({
  imports: [RouterModule.forChild(memoRoutes)],
})
export class MemoRoutingModule {}
