import './App.css';
import MainContainer from "./MainContainer";
import Progress from "./Progress/Progress";
import Login from "./UserLogin/Login";
import Signup from "./UserLogin/Signup";

import { Switch, Route } from 'react-router-dom';

/**
 * User and task information is kept here, as it's needed across multiple pages.
 */

{/*Routing Help from https://stackoverflow.com/questions/41956465/how-to-create-multiple-page-app-using-react*/}
const Router = () => {
    return (
        <Switch> {/* The Switch decides which component to show based on the current URL.*/}
            <Route exact path='/' component={MainContainer}></Route>
            <Route exact path='/progress' component={Progress}></Route>
            <Route exact path='/login' component={Login}></Route>
            <Route exact path='/signup' component={Signup}></Route>
        </Switch>
    );
}

function App() {
  return (
    <div className="App">
        <div className="AppContent">
            <Router/>
        </div>
    </div>
  );
}

export default App;
