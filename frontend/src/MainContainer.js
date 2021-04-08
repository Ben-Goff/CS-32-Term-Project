import './MainContainer.css';
import './App.css';

import Navbar from "./Navbar";
import Taskbar from "./Taskbar";
import CalendarContainer from "./Calendar/CalendarContainer";
import React, {useState} from "react";
import {getMonday} from "./WeekliHelpers";
import Popup from "./Calendar/Popup";

function MainContainer() {

    //A date on the display week's Monday
    const [displayMonday, setDisplayMonday] = useState(getMonday(new Date()));
    const [taskBlocks, setTaskBlocks] = useState([]);
    const [showPopup, setShowPopup] = useState(false);

    return (
        <div className="MainContainer">
            <Popup showPopup={showPopup} setShowPopup={setShowPopup}/>
            <div className="main-grid">
                <Navbar displayMonday={displayMonday} setDisplayMonday={setDisplayMonday} setShowPopup={setShowPopup}/>
                <Taskbar taskBlocks={taskBlocks} setTaskBlocks={setTaskBlocks}/>
                <CalendarContainer displayMonday={displayMonday} setDisplayMonday={setDisplayMonday}
                                   setTaskBlocks={setTaskBlocks}/>
            </div>
        </div>
    );
}

export default MainContainer;