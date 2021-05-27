import {Switch, Route} from "react-router-dom";
import SidebarManager from './SidebarManager/SidebarManager';
import RequestReview from './RequestReview/RequestReview';
import RequestsViewAll from './RequestsViewAll/RequestsViewAll';
import EmployeesView from './EmployeesView/EmployeesView';
import EmployeeCreate from './EmployeeCreate/EmployeeCreate';

import ProfileView from './ProfileView/ProfileView';
import PasswordReset from './PasswordReset/PasswordReset';
import RequestCreate from './RequestCreate/RequestCreate';
import Success from './Success';
import EmployeeViewUpdateSuccess from './ProfileView/ProfileViewUpdateSuccess';
import Logout from "./Logout/Logout";
import RequestViewByEmployee from "./RequestViewByEmployee/RequestViewByEmployee";
import NoRequests from "./NoRequests";
import './Home.css';
import {Fragment} from 'react';

/**
 * 
 * @param props 
 * @returns 
 */
export default function ManagerHome(props:any) {   
    return (
        <Fragment>
        <SidebarManager user={props.user}/>
        <div className="ManagerHome">
            <Switch>

            <Route path='/RequestViewByEmployee/:id' component={()=><RequestViewByEmployee id={props.user}/>}/>
            <Route path='/RequestsViewAll/:id' component={RequestsViewAll}/>
            <Route path='/RequestReview/:id' component={RequestReview}/>
            <Route path='/EmployeesView' component={EmployeesView}/>
            
            <Route path='/EmployeeCreate' component={EmployeeCreate}/>
            
            <Route path='/ProfileView' component={()=> <ProfileView/>}/>
            <Route path='/PasswordReset' component={PasswordReset}/>
            <Route path='/RequestCreate' component={() => <RequestCreate/>}/>
            <Route path='/Success' component={Success}/>
            <Route path='/EmployeeViewUpdateSuccess/:status' component={EmployeeViewUpdateSuccess}/>
            <Route path='/Logout' component={()=> <Logout logoutCb={props.logoutCb}/>} />
            <Route path='/NoRequests' component={NoRequests}/>
            </Switch>
        </div>
        </Fragment>
    )
}