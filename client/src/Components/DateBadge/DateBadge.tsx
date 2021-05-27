import { Fragment } from "react"
import './DateBadge.css'

/**
 * 
 * @param props 
 * @returns 
 */
export default function DateBadge(props: any) {
    const getElement = () => {
        let date = new Date(props.date)
        try {
            return(
            <span className="entry-date">
                <span className="month">{ date.toLocaleString('default', { month: 'short' }) }</span>
                <span className="day">{date.getUTCDate()}</span>
                <span className="year">{date.getFullYear()}</span>
            </span>
            )
        }catch(err){
            console.error(err)
        }
    }

    return(
        <Fragment>
        {getElement()}
        </Fragment>
    )
}