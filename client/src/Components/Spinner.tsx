import { useEffect } from "react"
import { useHistory } from "react-router-dom"
import MockDao from "../MockDao/MockDao"

/**
 * 
 * @param props 
 * @returns 
 */
export default function Spinner(props: any) {    
    let history = useHistory()
    useEffect(() =>{
        setTimeout(() =>{
            let path: string = props && props.path
            if(path !== undefined)
                history.push(path)

            if(props && props.logoutCb)
                MockDao.logout()
        }, 2000)
    },[props, history])

    let title: string = props && props.title
    
    return (        
        <div className="Spinner"><h2>{title}</h2>
            <div className="lds-spinner"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>
            </div>        
        </div>
    )
}