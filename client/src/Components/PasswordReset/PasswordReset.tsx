import { useRef } from 'react'
import { useHistory } from 'react-router-dom'
import MockDao from '../../MockDao/MockDao'
import './PasswordReset.css'

/**
 * 
 * @returns 
 */
export default function PasswordReset() {
    const minLen = 8
    const pass = useRef<HTMLInputElement>(null)
    const confirm = useRef<HTMLInputElement>(null)
    const form = useRef<HTMLFormElement>(null)
    let history = useHistory()

    const handleClick = (evt: React.FormEvent) => {
        evt.preventDefault()

        let confirmPass = confirm?.current?.value
        let match: boolean | null = (pass?.current?.value === confirmPass)

        if(!match) {
            alert('passwords do not match')
            form?.current?.reset()
            return
        }

        let data:  {[k: string]: any} = {}
        data['command'] = 'updatePass'
        data['cmdType'] = MockDao.getIsManager() ? 'manager' : 'employee'
        data['password'] = confirmPass
        data['id'] = MockDao.getCurrentUserId()

        MockDao.postDao(displayResult, data)
    }

    const displayResult = (res: any) => {
            history.push({ 
                pathname: '/Success', 
                state: { detail: 'Updated Password'}
            })
    }

    return (
        <div className="PasswordReset">
            <form ref={form} onSubmit={e => handleClick(e)}>
                <div>
                    <label htmlFor="password">New Password<br/><small>(8 characters minimum)</small></label>
                    <input ref={pass} type="password" id="password" minLength={minLen} autoComplete="new-password" required/>
                </div>
                <div>
                    <label htmlFor="passwordconfirm">Confirm Password</label>
                    <input ref={confirm} type="password" id="passwordconfirm" minLength={minLen} autoComplete="new-password" required/>
                </div>
                <div>
                    <button type='submit'>Submit</button>
                </div>
            </form>
        </div>
    )
}