import { Injectable } from '@angular/core';
import { Category } from '../enums/category.enum';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  getCategories(): { id: number; libelle: Category }[] {
    return [
      { id: 1, libelle: Category.LANGUES },
      { id: 2, libelle: Category.FRANCAIS },
      { id: 3, libelle: Category.MATHEMATIQUES },
      { id: 4, libelle: Category.CUISINE },
      { id: 5, libelle: Category.AUTRES },
    ];
  }
}
