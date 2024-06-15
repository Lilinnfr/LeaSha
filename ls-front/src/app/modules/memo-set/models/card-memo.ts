import { Card } from './card';

export class CardMemo {
  id?: number;
  titre?: string;
  description?: string;
  dateCreation?: Date;
  dateModif?: Date;
  categorieId?: number;
  carte?: Card[];
}
