import {NavLink} from 'react-router-dom';
import './SidebarManager.css';
import Sidebar from '../Sidebar/Sidebar';

/**
 * 
 * @param {*} props 
 * @returns 
 */
export default function SidebarManager(props) {
    return (
        <div className="SidebarManager">
            <ul>            
            <li key={0}><NavLink to="/RequestsViewAll/all" className='navlink' activeClassName='navlinkActive'>Requests</NavLink></li>                  
            <li key={1}><NavLink to="/EmployeesView" className='navlink' activeClassName='navlinkActive'>Employees</NavLink></li>
            <li key={2}><NavLink to="/EmployeeCreate" className='navlink ' activeClassName='navlinkActive'>Create Employee</NavLink></li>
            </ul>
            <Sidebar user={props.user}/>
        </div>
    );
}