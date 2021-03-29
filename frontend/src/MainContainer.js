import './MainContainer.css';
import './App.css';

import Navbar from "./Navbar";
import Taskbar from "./Taskbar";
import Calendar from "./Calendar";

function MainContainer() {
    return (
        <div className="MainContainer">
            <div className="main-grid">
                <Navbar/>
                <Taskbar/>
                <Calendar/>
            </div>
        </div>
    );
}

export default MainContainer;