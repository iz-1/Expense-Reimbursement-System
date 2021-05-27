import { useEffect, useState } from "react"
import { useHistory } from "react-router-dom"
import MockDao from "../../MockDao/MockDao"
import RequestCreateForm from "./RequestCreateForm"

/**
 * 
 * @param props 
 * @returns 
 */
export default function RequestCreate(props:any) {
    let history = useHistory()

    const [request, setResult] = useState()

    useEffect(() => {
        MockDao.getObject(setResult, MockDao.getNewRequest, removeAtributes)
    }, [])

    function removeAtributes(x: any) {
        delete x.requester
        delete x.status
        delete x.startDate
        delete x.endDate
        delete x.id
        delete x.total
    }

    function validate(formdata: object) {
        //post: create 
        let command = {command: 'create'}
        let cmdType = {cmdType: 'request'}
        formdata = {...formdata, ...command, ...cmdType}
        MockDao.postDao(handleResult, formdata)        
    }

    function handleResult(results: object) {
        console.log(results)
        history.push({
            pathname: '/Success',
            state: { detail: 'Request'}
        })
    }

    return (
        <div className="RequestCreate">
            <RequestCreateForm data={request} handleSubmit={validate} title={'Create Request'}/>
        </div>
    )
}