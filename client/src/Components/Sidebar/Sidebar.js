import {NavLink} from 'react-router-dom';
import MockDao from '../../MockDao/MockDao'
import './Sidebar.css';

/**
 * 
 * @param {*} props 
 * @returns 
 */
export default function Sidebar(props) {
    let id = MockDao.getCurrentUserId()

    return (
        <div className="Sidebar">
            <ul>            
            <li key={0}><NavLink to={"/RequestViewByEmployee/" + id} className="navlink  activeClassName='navlinkActive'">Home</NavLink></li>
            <li key={1}><NavLink to="/RequestCreate" className="navlink" activeClassName='navlinkActive'>Request</NavLink></li>            
            <li key={2}><hr/></li>
            <li key={3}><NavLink to={"/ProfileView"} className="navlink"  activeClassName='navlinkActive'>Profile</NavLink></li>
            <li key={4}><NavLink to="/PasswordReset" className="navlink"  activeClassName='navlinkActive'>Password Reset</NavLink></li>
            <li key={5}><hr/></li>
            <li key={6}><NavLink to="/Logout" className="navlink"  activeClassName='navlinkActive'>Logout</NavLink></li>
            </ul>
        </div>
    );
}