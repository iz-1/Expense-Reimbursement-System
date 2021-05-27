import Expense from "./Expense";

/**
 * 
 */
export default class Reimbursement {
    expenses: Expense[];
    requester: string;
    status: string;
    startDate: string;
    endDate: string;
    id: string;
    total: number;

  constructor(
    expenses: Expense[] = [], 
    requester: string = '',
    status: string = '', 
    startDate: string = '', 
    endDate: string = '',
    id: string = '',
    total: number = 0
) {
    this.expenses = expenses
    this.requester = requester
    this.status = status
    this.startDate = startDate
    this.endDate = endDate
    this.id = id
    this.total = total

    //@ts-ignore
    expenses.forEach((e) => {
      this.total += e.cost;
    });
  }

}