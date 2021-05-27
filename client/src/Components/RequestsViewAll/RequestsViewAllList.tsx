import {Fragment, useEffect, useState} from "react"
import MockDao from "../../MockDao/MockDao"
import RequestsViewAllListEntry from "./RequestsViewAllListEntry"
import './RequestsViewAllListEntry.css'

/**
 * 
 * @param props 
 * @returns 
 */
export default function RequestsViewAllList(props: any) {
    const [owner, setIsOwner] = useState(false)

    useEffect(() => {
        let isOwner = props.empId === MockDao.getCurrentUserId()
        //@ts-ignore
        setIsOwner(isOwner)
    },[props.empId])

    const getTitle = () => {
        if(!owner && props.empId !== undefined)
            return (<h3>requester: {props.empId}</h3>)    
    }

    const requestData = () => {
        if(!owner && !props.viewEmpReqs)
            return(<th>Requester</th>)
    }

    const getItems = () => {
        const items:any = [];
        //@ts-ignore
        props.data.forEach( (element, index) => {
            //@ts-ignore
            items.push(<RequestsViewAllListEntry data={element} key={index+1} callback={handleClick} isOwner={owner} viewEmpReqs={props.viewEmpReqs}/>)
        }) 
        return(
            <Fragment>
            {getTitle()}
            <table><thead>
            <tr key={0}>
                <th>Request Id</th>                
                <th>Status</th>
                <th>Total</th>
                <th>Dates</th>
                {requestData()}
            </tr></thead><tbody>
            {items}
            </tbody></table>
            </Fragment>
        )
    }

    const handleClick = (formdata: any) => {
        console.log(formdata)
    }

    return (        
        <div className='RequestsViewAllList'>
            {getItems()}
        </div>
    );
}