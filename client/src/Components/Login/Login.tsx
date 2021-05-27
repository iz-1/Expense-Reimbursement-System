import { Fragment, useRef, useState } from "react"
import { useHistory } from "react-router-dom"
import MockDao from "../../MockDao/MockDao"
import './Login.css'

/**
 * 
 * @param props 
 * @returns 
 */
export default function Login(props:any) {
    const minLen = 8
    let loginRef = useRef<HTMLInputElement>(null)
    let passRef = useRef<HTMLInputElement>(null)
    let formRef = useRef<HTMLFormElement>(null)    
    let history = useHistory()

    const [button, setButton] = useState()

    const handleClick = (e: React.FormEvent) => {
        e.preventDefault()

        
        //const buttonId: string = (e.target as any).id

        //@ts-ignore
        const buttonId: string = button
        const login: string = loginRef.current!.value
        const password: string = passRef.current!.value
        MockDao.getDao(storeCredentials, {[buttonId]: login, password: password})
    }

    function storeCredentials(result: any){
        try {
            let user = result.map
            MockDao.setUser(user)
            props.callback(user)

            history.push({ 
                pathname: '/RequestViewByEmployee/' + user.id, 
            })            
        } catch(err) {
            console.log(err)
            alert('Login error')
            formRef?.current?.reset()
        }
    }

    function getLoginPassInputs() {
        return(
            <Fragment>
            <li><input ref={loginRef} type='text' placeholder='login...' minLength={minLen} autoComplete="username" required></input></li>
            <li><input ref={passRef} type='password' placeholder='password...' minLength={minLen} autoComplete="password" required></input></li>           
            </Fragment>
        )
    }

    // function getLoginPassInputs() {
    //     return(
    //         <Fragment>
    //         <li><input ref={loginRef} type='text' defaultValue='johnsmith@work.com'></input></li>
    //         <li><input ref={passRef} type='text' defaultValue='smithj'></input></li>     
    //         </Fragment>
    //     )
    // }

    // function getLoginPassInputs() {
    //     return(
    //         <Fragment>
    //         <li><input ref={loginRef} type='text' defaultValue='manager@work.com'></input></li>
    //         <li><input ref={passRef} type='text' defaultValue='manager'></input></li>     
    //         </Fragment>
    //     )
    // }

    function getInputFields() {
        return (
            <ul>
            {
            getLoginPassInputs()
            }
            </ul>
        )
    }

    function setButtonValue(e: React.MouseEvent){
        //@ts-ignore
        let id =  e.target?.id
        setButton(id)
    }

    return (
        <div className="Login">
            <h2>Reinbursement Portal</h2>
            <form ref={formRef} onSubmit={e=> handleClick(e)}>
                {getInputFields()}
                <div className="loginButtons">
                    <button type='submit' id='login' onClick={e => setButtonValue(e)}>Employee Login</button>
                    <button type='submit' id='mgrlogin'  onClick={e => setButtonValue(e)}>Manager Login</button>
                </div>
            </form>
        </div>
    )
}