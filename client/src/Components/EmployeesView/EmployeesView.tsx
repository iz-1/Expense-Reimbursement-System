import { Fragment, useEffect, useState } from "react"
import MockDao from "../../MockDao/MockDao"
import EmployeesViewList from "./EmployeesViewList"
import Spinner from "../Spinner"
import handleSearch from '../../SearchEntries'

/**
 * 
 * @param props 
 * @returns 
 */
export default function EmployeesView(props: any) {

    const [employees, setResult] = useState([])
    const [employeesDisplay, setResultDisplay] = useState([])

    useEffect(() => {
        MockDao.getDao(setData, {emp: "all"})
    }, [])

    const setData = (loadedEmployees: any) => {
        setResult(loadedEmployees)
        setResultDisplay(loadedEmployees)
    }

    return (
        <div className="EmployeesView">      
            { Object.keys(employees === undefined || employees).length === 0 ? 
            <Spinner /> : 
            <Fragment>
                <input type='text' placeholder={'Filter...'} onChange={ e => handleSearch(e, employees, setResultDisplay, true)} ></input>
                <EmployeesViewList data={employeesDisplay}/> 
            </Fragment>
            }
        </div>
    )
}