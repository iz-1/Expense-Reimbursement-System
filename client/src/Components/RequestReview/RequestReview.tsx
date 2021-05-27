import { useEffect, useState, Fragment } from "react";
import { useHistory, useParams } from "react-router-dom";
import MockDao from "../../MockDao/MockDao";
import Spinner from "../Spinner";
import RequestCreateLine from '../RequestCreate/RequestCreateLine'
import './RequestReview.css'
import DateBadge from "../DateBadge/DateBadge";

/**
 * 
 * @returns 
 */
export default function RequestReview() {
    let { id } = useParams< {id: string} >()
    const [result, setResult] = useState< {result: string} >()
    let history = useHistory()

    useEffect(() => {

        MockDao.getDao(setResult, {req: id})

    }, [id])

    const handleClick = (evt: React.MouseEvent<HTMLButtonElement>) => {
        evt.preventDefault()
        MockDao.postDao(handleResult, { command: 'reviewRequest',
                                        id: id,
                                        status: evt.currentTarget.value,
                                        reviewerId: MockDao.getCurrentUserId()
        })   
    }

    function handleResult(results: object) {
        history.push({
            pathname: '/Success',
            state: { detail: 'Reviewed'}
        })
    }

    const getApprovalForm = (request: any) => {
        if(request.status === 'pending') {
            if(MockDao.getIsManager())
                return (
                    <div><form className='reviewForm'>
                    <button className='reviewbutton' value='approved' onClick={e => handleClick(e)}>Approve</button>
                    <button id='denied' className='reviewbutton' value='denied' onClick={e => handleClick(e)}>Deny</button>
                    </form></div>            
                )
        }
        else
            return (
                <div className='requestReviewer'>
                    <h4>Reviewer: {request.reviewerId}</h4>
                </div>
            )
    }

    // from RequestCreateForm
    const getItems = () => {
        let elements: any = []

        try{
            //@ts-ignore
            let request = result[0]
            let expenses = request.expenses

            expenses.forEach( (e: any, i: number) => {
                let formatDate = new Date(e.date).toLocaleDateString('en-US', {timeZone: 'UTC'})
                let value = parseInt(e.amount)
                let formatAmt = (value * 0.01).toFixed(2)
                elements.push(<RequestCreateLine id={i+1} date={formatDate} expense={e.expense} amount={formatAmt} />)
            })            
            
            //@ts-ignore
            let total = Number(request.total)
            let formatTotal = (total * 0.01).toFixed(2)
            return (
                <Fragment>
                
                <table><thead><tr><th>Requester</th><th>Status</th><th>Dates</th></tr></thead>
                <tbody>

                <tr key={0}><td>{request.requesterId}</td><td>{request.status}</td>
                <td className='dateRange'><DateBadge date={request.startDate}/><DateBadge date={request.endDate}/></td></tr>

                </tbody>
                </table>

                <hr/>

                <table><thead><tr key={0}><th>Date</th><th>Expense</th><th>Amount</th></tr></thead><tbody>
                {elements}
                <tr key={expenses.length+1}><td></td><td></td><td className='enMoney'>{formatTotal}</td></tr>
                </tbody></table>

                <hr/>
                {getApprovalForm(request)}
                </Fragment>
            )            
        }catch(error){
            console.log(error)
        }

        return (<div></div>)
    }

    return (    
        <div className='RequestReview'>
            {
                Object.keys(result === undefined || result).length === 0 ?
                <Spinner /> 
                : 
                getItems()
            }
        </div>
    );
}