import {useState, useRef} from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import Login from './Components/Login/Login';
import ManagerHome from './Components/ManagerHome';
import EmployeeHome from './Components/EmployeeHome';
import MockDao from "./MockDao/MockDao"
import './App.css';

const useConstructor = (callback = () => {}) => {
  const called = useRef(false)
  if(called) return
  callback()
  called.current = true
}

/**
 * 
 * @returns 
 */
const App = () => {
  const [user, setUser] = useState(null)
  useConstructor(() => {
    console.log('once before render')
  })
    
  MockDao.setLogOutCallback(setUser)

  const getHome = () => {
    
    if(user === null)
      return <Login callback={setUser}/>    

    return user.type === "manager" ? 
      <ManagerHome user={user.id} logoutCb={setUser}/> 
    : <EmployeeHome user={user.id} logoutCb={setUser}/>
  }

  return (
    <div className="App">
      <Router>
        <Route path='/'>
        { getHome() }
        </Route>
      </Router>
    </div>
  )
}

export default App;