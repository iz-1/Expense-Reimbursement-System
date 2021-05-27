import { Fragment, useEffect, useState } from "react";
import MockDao from "../../MockDao/MockDao";
import RequestsViewAllList from "./RequestsViewAllList";
import Spinner from '../Spinner'
import { useParams } from "react-router-dom";
import handleSearch from '../../SearchEntries'

/**
 * 
 * @returns 
 */
export default function RequestsViewAll() {
    let { id } = useParams< {id: string} >()
    let { empFlag } = useParams< {empFlag: string} >()

    const [reinbursements, setResult] = useState([])
    const [reinbursementsDisplay, setResultDisplay] = useState([])

    useEffect(() => {
        if(empFlag !== undefined) {
            MockDao.getDao(setData, {empReq: id})
        }
        else
            MockDao.getDao(setData, {req: id})
    }, [id, empFlag])

    const setData = (data: any) =>{
        setResult(data)
        setResultDisplay(data)
    }

    return (
        <div className="RequestsViewAll">      
        { Object.keys(reinbursements === undefined || reinbursements).length === 0 ? <Spinner /> 
        : 
        <Fragment>
            <input type='text' placeholder={'Filter...'} onChange={ e => handleSearch(e, reinbursements, setResultDisplay, false)} ></input>
            <RequestsViewAllList data={reinbursementsDisplay} empId={empFlag ? id : undefined}/>
        </Fragment>
        }
        </div>
    );
}