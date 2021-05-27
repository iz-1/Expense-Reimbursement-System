import { Fragment, useRef, useState } from 'react'
import { useHistory } from 'react-router-dom'
import './EmployeesViewListEntry.css'

/**
 * 
 * @param props 
 * @returns 
 */
export default function EmployeesViewListEntry(props: any) {

    let history = useHistory()   
    let entry = useRef()
    let showRow = useState(true || props?.hide)

    const handleClick = () => {
        //@ts-ignore
        let id = entry.current.innerHTML

        history.push({
            pathname: '/RequestViewByEmployee/' + id
        })
    }

    function formatPhoneNumber(phoneNumberString: string) {
        //@ts-ignore
        let cleaned = ('' + phoneNumberString).replace(/\D/g, '');
        let match = cleaned.match(/^(\d{3})(\d{3})(\d{4})$/);
        
        if (match) {
          return '(' + match[1] + ') ' + match[2] + '-' + match[3];
        }
        return phoneNumberString;
      }

    return(
        <Fragment>            
            {showRow && <tr key={props.id} onClick={handleClick}>
                    {/*//@ts-ignore */}
                    <td ref={entry} className='linkEmp'>{props.data.hexId}</td>
                    <td>{props.data.firstName}</td>
                    <td>{props.data.lastName}</td>                    
                    <td>{props.data.email}</td>
                    <td>{formatPhoneNumber(props.data.phoneNumber)}</td>
                    <td>{props.data.address}</td>
                    <td>{props.data.type}</td>
            </tr>}
        </Fragment>
    )
}