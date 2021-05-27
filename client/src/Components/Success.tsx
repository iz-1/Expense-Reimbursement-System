import { useLocation } from "react-router-dom"

interface stateType {
    detail: { pathname: string }
}

/**
 * 
 * @returns 
 */
export default function Success() {
    const location = useLocation<stateType>()
    return (
        <div className='Success'>
            <h1>Successully {location.state.detail}</h1>
        </div>
    )
}