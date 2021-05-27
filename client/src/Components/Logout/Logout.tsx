import Spinner from "../Spinner"

/**
 * 
 * @param props 
 * @returns 
 */
export default function Logout(props:any) {
    return (
        <div className="Logout">
            <Spinner title={'Logging Out...'} path={'/'} logoutCb={props.logoutCb}/>
        </div>
    )
}