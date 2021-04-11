import './App.css';
import MainContainer from "./MainContainer";
import Progress from "./Progress/Progress";
import Login from "./UserLogin/Login";
import Signup from "./UserLogin/Signup";

import { Switch, Route } from 'react-router-dom';
import {useState} from "react";

{/*Routing Help from https://stackoverflow.com/questions/41956465/how-to-create-multiple-page-app-using-react*/}
const Router = (blocks, setBlocks) => {
    return (
        <Switch> {/* The Switch decides which component to show based on the current URL.*/}
            <Route exact path='/login' render={() => (
                <Login setBlocks={setBlocks} isAuthed={true}/>
            )}/>
            <Route exact path='/' render={() => (
                <MainContainer blocks={blocks} setBlocks={setBlocks} isAuthed={true}/>
            )}/>
            <Route exact path='/progress' component={Progress}></Route>
            <Route exact path='/signup' component={Signup}></Route>
        </Switch>
    );
}

function App() {

    /**
     * Task/Block information is kept here, as it's needed across multiple pages.
     */
    const [blocks, setBlocks] = useState([]);

    return (
        <div className="App">
            <div className="AppContent">
                {Router(blocks, setBlocks)}
            </div>
        </div>
    );
}

export default App;
