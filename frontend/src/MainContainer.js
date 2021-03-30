import './MainContainer.css';
import './App.css';

import Navbar from "./Navbar";
import Taskbar from "./Taskbar";
import CalendarContainer from "./Calendar/CalendarContainer";

function MainContainer() {
    return (
        <div className="MainContainer">
            <div className="main-grid">
                <Navbar/>
                <Taskbar/>
                <CalendarContainer/>
            </div>
        </div>
    );
}

export default MainContainer;