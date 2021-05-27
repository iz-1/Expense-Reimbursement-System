/**
 * 
 */
export default class Employee {
    firstName: string;
    lastName: string;
    email: string;
    address: string;
    phoneNumber: number;
    password: string;
    type: string;
    requestIds: number[];
    id: string;

  constructor(
    firstName: string = '', 
    lastName: string  = '', 
    email: string = '', 
    address: string  = '', 
    phoneNumber: number = 0, 
    password: string = '', 
    type: string = 'Employee', 
    requestIds: number[] = [],
    id: string = ''
) {
    this.firstName = firstName
    this.lastName = lastName
    this.email = email
    this.address = address
    this.phoneNumber = phoneNumber
    this.password = password
    this.type = type
    this.requestIds = requestIds    
    this.id = id
  }
}