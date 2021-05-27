/**
 * 
 * @param evt 
 * @param sourcedata 
 * @param setResultCb 
 * @param isUser 
 * @returns 
 */
function handleSearch(evt: any, sourcedata: any, setResultCb: any, isUser: boolean){
        
    const { value } = typeof(evt.target) === 'number' ? evt.target.toString() : evt.target
    let filtered = sourcedata

    if(value.length > 0 && value.length < 3) {
        setResultCb(filtered)
        return;
    }

    let expr = new RegExp('^\\b' + value)
    //@ts-ignore
    filtered = sourcedata.filter( e => {
        
        // destructuring and property shorthand, get subset of properties we want to search
        const properties = getPropList(isUser, e)
        const results = Object.values(properties).filter(val => expr.test(val))
        return results.length > 0
    })

    setResultCb(filtered)
}

/**
 * 
 * @param user 
 * @param e 
 * @returns 
 */
function getPropList(user: boolean, e: any) {
    if(user === true)
        return (({id, firstName, lastName, email, phone, address} = e) => ({id, firstName, lastName, email, phone, address}))(e)
    return (({id, status, requester} = e) => ({id, status, requester}))(e)
}

export default handleSearch;