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

    /**
     * Getter for Monday
     */
    function getDisplayMonday() {
        return displayMonday;
    }

    return (
        <div className="MainContainer">
            <div className="main-grid">
                <Navbar getDisplayMonday={getDisplayMonday} setMonday={setDisplayMonday}/>
                <Taskbar/>
                <CalendarContainer getDisplayMonday={getDisplayMonday} setMonday={setDisplayMonday}/>
            </div>
        </div>
    );
}

export default MainContainer;