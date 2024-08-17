import { Injectable } from '@angular/core';
import { Category } from '../enums/category.enum';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  getCategories(): { id: number; libelle: Category }[] {
    return [
      { id: 1, libelle: Category.ANGLAIS },
      { id: 2, libelle: Category.REGLES_CONJUGAISON },
      { id: 3, libelle: Category.PROGRAMMATION_ORIENTEE_OBJET },
      { id: 4, libelle: Category.DEVELOPPEMENT_WEB_FRONT_END },
    ];
  }
}
