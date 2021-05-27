import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import MockDao from "../../MockDao/MockDao"
import RequestsViewAllList from "../RequestsViewAll/RequestsViewAllList"

/**
 * 
 * @param props 
 * @returns 
 */
export default function RequestViewByEmployee(props: any) {

    let { id } = useParams< {id: string} >()

    const [reinbursements, setResult] = useState()

    useEffect(() => {
        MockDao.getDao(setResult, {empReq: id})

    }, [id])

    return (
        <div className="RequestViewByEmployee">      
        { //@ts-ignore
        Object.keys(reinbursements === undefined || reinbursements).length === 0 ? <div>No Requests</div>
        //@ts-ignore
        : <RequestsViewAllList data={reinbursements} empId={id} loggedId={props.id} viewEmpReqs={true}/>
        }
        </div>
    );
}