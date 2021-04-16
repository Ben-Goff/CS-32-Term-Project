import './App.css';
import MainContainer from "./MainContainer";
import Progress from "./SidePages/Progress";
import Login from "./UserLogin/Login";
import Signup from "./UserLogin/Signup";

import { Switch, Route } from 'react-router-dom';
import Help from "./SidePages/Help";
import Settings from "./SidePages/Settings";

/*Routing Help from https://stackoverflow.com/questions/41956465/how-to-create-multiple-page-app-using-react*/
const Router = () => {
    return (
        <Switch> {/* The Switch decides which component to show based on the current URL.*/}
            <Route exact path='/' render={() => (
                <Login isAuthed={true}/>
            )}/>
            <Route exact path='/home' render={() => (
                <MainContainer isAuthed={true}/>
            )}/>
            <Route exact path='/progress' component={Progress}></Route>
            <Route exact path='/signup' component={Signup}></Route>
            <Route exact path='/help' component={Help}></Route>
            <Route exact path='/settings' component={Settings}></Route>
        </Switch>
    );
}

function App() {

    /**
     * Task/Block information is kept here, as it's needed across multiple pages.
     */

    return (
        <div className="App">
            <div className="AppContent">
                {Router()}
            </div>
        </div>
    );
}

export default App;
