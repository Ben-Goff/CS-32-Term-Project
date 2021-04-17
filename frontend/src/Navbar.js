import './Navbar.css';
import './App.css';
import React, {useState, useEffect} from "react";
import {getMonday} from "./WeekliHelpers";
import { Link } from "react-router-dom";
import axios from "axios";

function Navbar(props) {
    let monthList = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
        "Sep", "Oct", "Nov", "Dec"]

    const [month, setMonth] = useState(0); //The month to display
    const [day, setDay] = useState(0);
    const[month2, setMonth2] = useState(0);
    const [day2, setDay2] = useState(0);
    const [year, setYear] = useState(0);

    useEffect(() => {
        thisWeek();
    }, [])

    const prevWeek = () => {
        let lastMonday = new Date(props.displayMonday);
        lastMonday.setDate(lastMonday.getDate() - 7);
        props.setDisplayMonday(lastMonday)
        setMonth(monthList[lastMonday.getMonth()]);
        setDay(lastMonday.getDate());

        let lastSunday = new Date(lastMonday);
        lastSunday.setDate(lastSunday.getDate() + 6);
        setMonth2(monthList[lastSunday.getMonth()]);
        setDay2(lastSunday.getDate());
        setYear(lastSunday.getFullYear());
    }

    const thisWeek = () => {
        let thisMonday = new Date(getMonday(new Date()));
        props.setDisplayMonday(thisMonday);
        setMonth(monthList[thisMonday.getMonth()]);
        setDay(thisMonday.getDate());

        let thisSunday = new Date(thisMonday);
        thisSunday.setDate(thisSunday.getDate() + 6);
        setMonth2(monthList[thisSunday.getMonth()]);
        setDay2(thisSunday.getDate());
        setYear(thisSunday.getFullYear());
    }

    const nextWeek = () => {
        let nextMonday = new Date(props.displayMonday);
        nextMonday.setDate(nextMonday.getDate() + 7);
        props.setDisplayMonday(nextMonday);
        setMonth(monthList[nextMonday.getMonth()]);
        setDay(nextMonday.getDate());

        let nextSunday = new Date(nextMonday);
        nextSunday.setDate(nextSunday.getDate() + 6);
        setMonth2(monthList[nextSunday.getMonth()]);
        setDay2(nextSunday.getDate());
        setYear(nextSunday.getFullYear());
    }

    const createTask = () => {
        props.setShowPopup(true);
    }

    const clearSchedule = () => {
        axios.get("http://localhost:4567/deleteall")
            .then(response => {
                console.log("successfully deleted");
                props.setUpdateFlag(!props.updateFlag);
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }


    return (
        <div className="Navbar">
            <div className="navbar-body">
                <div className="logo-area">
                    <img alt="" src="WeekliLogo.png" className="logo" style={{"width": "50px", "height": "50px"}}/>
                    <div className="logo-text" style={{"marginTop": "-10px"}}>
                        Weekli
                    </div>
                </div>

                <div className="navbar-main">
                    <div className="flexbox-section">
                        <div className="flexbox-section">
                            <div className="date-label">
                                {month + " " + day + " - " + month2 + " " + day2 + " " + year}
                            </div>

                            <button className="button prev-week" onClick={prevWeek}>
                                <div className="week-icon">{"<"}</div>
                            </button>

                            <button className="button next-week" onClick={nextWeek}>
                                <div className="week-icon">{">"}</div>
                            </button>

                            <button className="button this-week" onClick={thisWeek}>
                                This Week
                            </button>
                        </div>

                        <div className="flexbox-section">
                            <button className="button create" onClick={createTask}>
                                <img alt="" src="plus.png" className="plus-icon"/> <div className="create-text">Create</div>
                            </button>

                            {/*<button className="button shuffle">*/}
                            {/*    <img alt="" src="shuffle.png" className="shuffle-icon"/> <div className="shuffle-text">Shuffle</div>*/}
                            {/*</button>*/}

                            <Link to="/progress" style={{ textDecoration: 'none' }}>
                                <button className="button progress">
                                    <img alt="" src="bars.png" className="bars-icon"/> <div className="progress-text">Progress</div>
                                </button>
                            </Link>

                            {/*Hamburger menu css/html code TAKEN FROM: https://codepen.io/erikterwan/pen/EVzeRP*/}
                            <nav role="navigation">
                                <div id="menuToggle">

                                    <input type="checkbox" />

                                    <span></span>
                                    <span></span>
                                    <span></span>

                                    <ul id="menu">
                                        <Link to="/"><li>Log Out</li></Link>
                                        <Link to="/help"><li>Help</li></Link>
                                        <Link to="/settings"><li>Settings</li></Link>
                                        <button className="clear-schedule-button" onClick={clearSchedule}><li style={{"color": "red"}}>Clear Schedule</li></button>
                                    </ul>
                                </div>
                            </nav>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Navbar;