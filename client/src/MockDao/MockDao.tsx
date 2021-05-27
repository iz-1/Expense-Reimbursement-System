import axios, { AxiosError } from 'axios';
import Employee from '../Model/Employee';
import Expense from '../Model/Expense';
import Manager from '../Model/Manager';
import Reimbursement from '../Model/Reimbursement';

/**
 * 
 */
export default class MockDao {
    static loadDelay: number = 200 //1200
    static url: string = 'http://localhost:8888'
    static appSetStateCb: (data: any) => void

    static getNewEmployee() {
        return new Employee()
    }

    static getNewRequest() {
        return new Reimbursement()
    }

    static getDummyEmployee() {
        return new Employee('first', 'last', 'abc@abc.com', '123 street', 123456890, 'password', 'Employee', [], '0')
    }

    static getDummyEmployees() {
        let employees = [
            MockDao.getDummyEmployee(),
            MockDao.getDummyEmployee(),
            MockDao.getDummyEmployee()
        ]
        return employees;
    }

    static getDummyManager() {
        return new Manager('boss', 'man', 'daboss@abc.com', '111 street', 123456890, 'password', 'Employee', [])
    }

    static getDummyRequest() {
        let expenses = [
            new Expense("drink", 200, "5-5-21"),
            new Expense("pizza", 200, "5-5-21"),
            new Expense("chips", 200, "5-5-21")
        ];
        return new Reimbursement(expenses, '100', 'pending', '5-5-21', '5-5-21', '0')
    }

    static getDummyRequests() {
        console.log('getdummyrequests')
        let requests = [
            MockDao.getDummyRequest(),
            MockDao.getDummyRequest(),
            MockDao.getDummyRequest()
        ]

        return requests
    }

    static getDummyEmployeeRequests(id?: string) {
        /*mock*/ 
        let result = MockDao.getDummyRequests()
        console.log(result)
        return result
    }

    static getObject(callbackSet: any, createObject: () => any, prepObject?: (obj: any) => void) {
        setTimeout(() => {
            let e = createObject()
            if(prepObject !== undefined)
                prepObject(e)
            //console.log('getobject:' + e)
            callbackSet(e)
        }, MockDao.loadDelay)
    }

    static retriveResult(data: any){
        return data;
    }

    static async login(callback: (data: any) => void, loginCredientials: {}) {
        axios({
            "method": "GET", //  "GET/POST"
            "url": MockDao.url,
            "params": loginCredientials
        })
        .then((response) => {
            console.log(response.status)
            if(response.status === 200)
            {
                //console.log(response.data)
                callback(response.data)
            }
        })
        .catch((error: AxiosError) => {
            console.log(error)
        })        
    }

    static setLogOutCallback(callback: (data: any) => void) {
        MockDao.appSetStateCb = callback;
    }

    static logout(){
        localStorage.removeItem('user')
        localStorage.removeItem('isManager')
        MockDao.appSetStateCb(null)
    }

    static setUser(result: any) {
        localStorage.setItem('user', JSON.stringify(result))
    }

    static getCurrentUserId(){
        //@ts-ignore
        const data = JSON.parse(localStorage.getItem('user'))
        return data.id
    }

    static getIsManager(): boolean {
        //@ts-ignore
        const data = JSON.parse(localStorage.getItem('user'))
        return (data.type === 'manager')
    }

    //breaks when param is camel case - only lower?
    static async getDao(callback: (data: any) => void, searchParams: object) {
        //console.log(`getDao: ${searchParams}`)
        // "params": { "emp": "abc@abc.com"}
        //@ts-ignore
        axios({
            "method": "GET", //  "GET/POST"
            "url": MockDao.url,
            "params": searchParams
        })
        .then((response) => {
            //console.log(response.status)
            if(response && response.status === 200)
            {
                //console.log(JSON.stringify(response.data))
                callback(response.data)
            }
        })
        .catch((error: AxiosError) => {
            console.error(error)
            callback(null)
        })        
    }

    static async postDao(callback: (data: any) => void, postParams: object) {
        //@todo loggin
        //console.log(`postDao: ${postParams}`)
        
        await axios.post(MockDao.url, postParams, {
            headers: { 
                'Content-Type': 'application/json'}
        })
        .then( res => {
            //console.log(res.status)
            if(res && res.status === 200) {
                //console.log(res.data.json)
                callback(res.data.json)
            }       
        }).catch((error: AxiosError) => {
            console.error(error)
            callback(null)
        })     
    }
}