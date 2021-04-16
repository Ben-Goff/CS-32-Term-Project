import './MainContainer.css';
import './App.css';

import Navbar from "./Navbar";
import Taskbar from "./Taskbar";
import CalendarContainer from "./Calendar/CalendarContainer";
import React, {useState} from "react";
import {getMonday} from "./WeekliHelpers";
import Popup from "./Calendar/Popup";
import EventDialog from "./Calendar/EventDialog";

function MainContainer(props) {
    //A date on the display week's Monday
    const [displayMonday, setDisplayMonday] = useState(getMonday(new Date()));
    const [showPopup, setShowPopup] = useState(false);
    const [clickedBlock, setClickedBlock] = useState(null);
    const [clickedX, setClickedX] = useState(0);
    const [clickedY, setClickedY] = useState(0);
    const [blocks, setBlocks] = useState([]);
    const [updateFlag, setUpdateFlag] = useState(false);

    return (
        <div className="MainContainer">
            <Popup showPopup={showPopup} setShowPopup={setShowPopup}/>
            <EventDialog clickedBlock={clickedBlock} setClickedBlock={setClickedBlock}
                         clickedX={clickedX} clickedY={clickedY}
                         updateFlag={updateFlag} setUpdateFlag={setUpdateFlag}/>
            <div className="main-grid">
                <Navbar displayMonday={displayMonday} setDisplayMonday={setDisplayMonday}
                        setShowPopup={setShowPopup} updateFlag={updateFlag} setUpdateFlag={setUpdateFlag}/>
                <Taskbar taskBlocks={blocks} setTaskBlocks={setBlocks}/>
                <CalendarContainer displayMonday={displayMonday} setDisplayMonday={setDisplayMonday}
                                   setTaskBlocks={setBlocks} setClickedBLock={setClickedBlock}
                                   setClickedX={setClickedX} setClickedY={setClickedY}
                                   showPopup={showPopup} updateFlag={updateFlag} setUpdateFlag={setUpdateFlag}/>
            </div>
        </div>
    );
}

export default MainContainer;