import { Fragment } from "react";
import './RequestCreateForm.css'

/**
 * 
 * @param props 
 * @returns 
 */
export default function RequestCreateLine(props:any) {
    return(
        <Fragment>
            <tr key={props.id}>
                <td>{props.date}</td>
                <td>{props.expense}</td>
                <td className='enMoney'>{props.amount}</td>
                
            </tr>
        </Fragment>
    )
}
