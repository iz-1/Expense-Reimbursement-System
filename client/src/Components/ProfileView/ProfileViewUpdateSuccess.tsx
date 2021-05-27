import { useParams } from "react-router-dom"

/**
 * 
 * @returns 
 */
export default function ProfileViewUpdateSuccess() {
    //@ts-ignore
    let { status } = useParams()

    return (
        <div className='ProfileViewUpdateSuccess'>
            <h1>Successully Updated {status}</h1>
        </div>
    )
}