import './MainContainer.css';
import './App.css';

import Navbar from "./Navbar";
import Taskbar from "./Taskbar";
import CalendarContainer from "./Calendar/CalendarContainer";
import React, {useState} from "react";
import {getMonday} from "./WeekliHelpers";

function MainContainer() {

    //A date on the display week's Monday
    const [displayMonday, setDisplayMonday] = useState(getMonday(new Date()));
    const [taskBlocks, setTaskBlocks] = useState([]);

    return (
        <div className="MainContainer">
            <div className="main-grid">
                <Navbar displayMonday={displayMonday} setDisplayMonday={setDisplayMonday}/>
                <Taskbar taskBlocks={taskBlocks} setTaskBlocks={setTaskBlocks}/>
                <CalendarContainer displayMonday={displayMonday} setDisplayMonday={setDisplayMonday}
                                   setTaskBlocks={setTaskBlocks}/>
            </div>
        </div>
    );
}

export default MainContainer;