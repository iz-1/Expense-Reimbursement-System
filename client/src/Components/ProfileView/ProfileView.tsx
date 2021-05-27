import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import MockDao from '../../MockDao/MockDao';
import ModelView from "../ModelView/ModelView";
import './ProfileView.css';

/**
 * 
 * @returns 
 */
export default function ProfileView() {
    let history = useHistory()
    
    const [result, setResult] = useState< {result: string} >()

    useEffect(() => {
        const isMgr = MockDao.getIsManager()
        const id = MockDao.getCurrentUserId() // props.id

        if(isMgr)
            MockDao.getDao(removeFields, {mgr: id})
        else
            MockDao.getDao(removeFields, {emp: id})
    }, [])

    function removeFields(profileData: any) {
        delete profileData.requestIds
        delete profileData.password
        delete profileData.id
        delete profileData.hexId
        setResult(profileData)
    }

    function validate(results: object): void {
        // check employee create request data
        // make requestIds not empty
        //@ts-ignore
        if(results.requestIds === undefined)
            //@ts-ignore
            results.requestIds = []

        //@ts-ignore
        results.phoneNumber = parseInt(results.phoneNumber)

        //------------------------------------------------------------------------
        //@todo add support for manager

        // append update tag
        let command = {command: 'update'}        
        let cmdType = {cmdType: MockDao.getIsManager() ? 'manager' : 'employee'}
        results = {...results, ...command, ...cmdType}

        MockDao.postDao(handleResult, results)
    }

    function handleResult(results: object) {
        history.push({
            pathname: '/Success',
            state: { detail: 'Updated Profile'}
        })
    }

    return (    
        //@ts-ignore
        <div className='ProfileView'>
            <ModelView data={result} handleSubmit={validate} title={'Profile'}/>
        </div>
    );
}