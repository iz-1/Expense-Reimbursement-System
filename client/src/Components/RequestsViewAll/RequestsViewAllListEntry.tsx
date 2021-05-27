import './RequestsViewAllListEntry.css'
import { Fragment, useRef } from 'react'
import { useHistory } from 'react-router-dom'
import DateBadge from '../DateBadge/DateBadge'

/**
 * 
 * @param props 
 * @returns 
 */
export default function RequestsViewAllListEntry(props: any) {
    let history = useHistory()
    let request = useRef()

    const handleClick = (e: React.MouseEvent<HTMLElement>) => {
        const target = e.target as HTMLElement
        const id = target.innerHTML
        if (target.id === 'req') {
            history.push({
                pathname: '/RequestReview/' + id
            })
        }
        //pathname: '/RequestReview/' + id
        if (target.id === 'emp') {
            history.push({
                pathname: '/RequestViewByEmployee/' + id
            })
        }
    }

    const getTableLine = () => {
        if (props === undefined || (!props.isOwner&& !props.viewEmpReqs)) {
            return (
                <Fragment>
                    <tr key={props.id} onClick={handleClick}>
                        {/*//@ts-ignore */}
                        <td ref={request} id={'req'} className='linkRef'>{props.data.hexId}</td>
                        <td>{props.data.status}</td>
                        <td><p className="enMoney">{
                            (props.data.total / 100).toFixed(2)
                        }</p></td>
                        <td className='dateRange'>
                        <DateBadge date={props.data.startDate} />
                        <DateBadge date={props.data.endDate} />
                        </td>
                        {/*//@ts-ignore */}
                        <td ref={request} id={'emp'} className='linkEmp'>{props.data.requesterId}</td>
                    </tr>
                </Fragment>
            );
        } else {
            return (
                <Fragment>
                    <tr key={props.id} onClick={handleClick}>
                        {/*//@ts-ignore */}
                        <td ref={request} id={'req'} className='linkRef'>{props.data.hexId}</td>
                        <td>{props.data.status}</td>
                        <td><p className="enMoney">{
                            (props.data.total / 100).toFixed(2)
                        }</p></td>
                        <td className='dateRange'>
                        <DateBadge date={props.data.startDate} />
                        <DateBadge date={props.data.endDate} />
                        </td>
                    </tr>
                </Fragment>
            );
        }
    }

    return (
        <Fragment>
            {getTableLine()}
        </Fragment>
    )
}