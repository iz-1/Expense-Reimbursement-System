import {Switch, Route} from "react-router-dom";
import Sidebar from './Sidebar/Sidebar';

import ProfileView from './ProfileView/ProfileView';
import PasswordReset from './PasswordReset/PasswordReset';
import RequestCreate from './RequestCreate/RequestCreate';
import Success from './Success';
import EmployeeViewUpdateSuccess from './ProfileView/ProfileViewUpdateSuccess';
import Logout from "./Logout/Logout";

import RequestsViewAll from './RequestsViewAll/RequestsViewAll';
import RequestViewByEmployee from "./RequestViewByEmployee/RequestViewByEmployee";
import NoRequests from "./NoRequests";
import './Home.css';
import {Fragment} from 'react';
import RequestReview from "./RequestReview/RequestReview";

/**
 * 
 * @param props 
 * @returns 
 */
export default function EmployeeHome(props:any) {
    return (
        <Fragment>
        <Sidebar user={props.user}/>        
        <div className="EmployeeHome">
        
            <Switch>            
                <Route path='/RequestViewByEmployee/:id' component={()=><RequestViewByEmployee id={props.user}/>}/>
                <Route path='/RequestsViewAll/:id' component={RequestsViewAll}/>
                <Route path='/RequestReview/:id' component={RequestReview}/>
                <Route path='/ProfileView' component={()=> <ProfileView/>}/>

                <Route path='/PasswordReset' component={PasswordReset}/>
                <Route path='/RequestCreate' component={RequestCreate}/>
                <Route path='/Success' component={Success}/>
                <Route path='/EmployeeViewUpdateSuccess/:status' component={EmployeeViewUpdateSuccess}/>
                <Route path='/Logout' component={()=> <Logout logoutCb={props.logoutCb}/>} />
                <Route path='/NoRequests' component={NoRequests}/>
            </Switch>            
        </div>
        </Fragment>
    )
}