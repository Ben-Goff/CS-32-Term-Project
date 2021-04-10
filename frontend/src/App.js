import './App.css';
import MainContainer from "./MainContainer";
import Progress from "./Progress";
import Login from "./UserLogin/Login";
import Signup from "./UserLogin/Signup";
import Schedule from "./Schedule";

import { Switch, Route } from 'react-router-dom';

{/*Routing Help from https://stackoverflow.com/questions/41956465/how-to-create-multiple-page-app-using-react*/}
const Router = () => {
    return (
        <Switch> {/* The Switch decides which component to show based on the current URL.*/}
            <Route exact path='/' component={MainContainer}></Route>
            <Route exact path='/progress' component={Progress}></Route>
            <Route exact path='/login' component={Login}></Route>
            <Route exact path='/signup' component={Signup}></Route>
            <Route exact path='/schedule' component={Schedule}></Route>
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
