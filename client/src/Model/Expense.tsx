/**
 * 
 */
export default class Expense {
    description: string = '';
    cost: number = 0;
    date: string = '';

  constructor(description: string , cost: number , date: string ) {
    this.description = description
    this.cost = cost
    this.date = date
  }
}