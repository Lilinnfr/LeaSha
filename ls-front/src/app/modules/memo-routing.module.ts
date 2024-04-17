import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardMemoComponent } from './memo-set/card-memo/card-memo.component';
import { ListMemoComponent } from './memo-set/list-memo/list-memo.component';
import { MemoSetComponent } from './memo-set/memo-set.component';

const memoRoutes: Routes = [
  { path: '', component: MemoSetComponent },
  { path: 'memoCarte/liste', component: CardMemoComponent },
  { path: 'Mes mémos listes', component: ListMemoComponent },
];

@NgModule({
  imports: [RouterModule.forChild(memoRoutes)],
})
export class MemoRoutingModule {}
