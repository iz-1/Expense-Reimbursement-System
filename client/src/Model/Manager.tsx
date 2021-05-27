import Employee from "./Employee";

/**
 * 
 */
export default class Manager extends Employee {
    constructor(
        firstName: string , 
        lastName: string  , 
        email: string , 
        address: string  , 
        phoneNumber: number  , 
        password: string , 
        type: string , 
        requestIds: number[] 
    ) {
        super(firstName, lastName, email, address, phoneNumber, password, "Manager", requestIds);
    }    
}