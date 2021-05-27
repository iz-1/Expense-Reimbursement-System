import { useEffect, useState } from "react"
import { useHistory } from "react-router-dom"
import MockDao from "../../MockDao/MockDao"
import Employee from "../../Model/Employee"
import Manager from "../../Model/Manager"
import EmployeeCreateForm from './EmployeeCreateForm'

/**
 * 
 * @param props 
 * @returns 
 */
export default function EmployeeCreate(props:any) {
    let history = useHistory()

    const [newUser, setResult] = useState<Employee | Manager>()

    useEffect(() => {
        let e = MockDao.getNewEmployee()
        removeAtributes(e)
        setResult(e)
    }, [])

    //@todo move to dao/service
    function removeAtributes(x: any) {
        delete x.password
        delete x.requestIds
        delete x.id
        delete x.type
    }

    function validate(formdata: object) {
        //parse phone
        //@ts-ignore
        formdata.phoneNumber = parseInt(formdata.phoneNumber)

        //post: either create or update
        let command = {command: 'create'}
        let cmdType = {cmdType: 'employee'}
        formdata = {...formdata, ...command, ...cmdType}

        MockDao.postDao(handleResult, formdata)
    }

    function handleResult(results: object) {
        //console.log(results)
        history.push({
            pathname: '/Success',
            state: { detail: 'Created Employee'}
        })
    }

    return (
        <div className="EmployeeCreate">
            <EmployeeCreateForm data={newUser} handleSubmit={validate} title={'Create Employee'} blank={true}/>
        </div>
    )
}