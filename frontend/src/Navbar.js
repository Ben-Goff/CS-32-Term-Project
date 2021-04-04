import './Navbar.css';
import './App.css';
import React, {useState} from "react";
import {getMonday} from "./WeekliHelpers";

function Navbar(props) {
    let monthList = ["January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"]

    let d = new Date();
    const [month, setMonth] = useState(monthList[d.getMonth()]); //The month to display
    const [day, setDay] = useState(d.getDate());

    const prevWeek = () => {
        let lastMonday = new Date(props.getDisplayMonday());
        lastMonday.setDate(lastMonday.getDate() - 7);
        props.setMonday(lastMonday)
        setMonth(monthList[lastMonday.getMonth()])
        setDay(lastMonday.getDate())
    }

    const thisWeek = () => {
        setMonth(monthList[d.getMonth()])
        setDay(d.getDate())
        props.setMonday(getMonday(new Date()));
    }

    const nextWeek = () => {
        let nextMonday = new Date(props.getDisplayMonday());
        nextMonday.setDate(nextMonday.getDate() + 7);
        props.setMonday(nextMonday)
        setMonth(monthList[nextMonday.getMonth()])
        setDay(nextMonday.getDate())
    }


    return (
        <div className="Navbar">
            <div className="navbar-body">
                <div className="logo-area">
                    <img src="WeekliLogo.png" className="logo"/>
                </div>

                <div className="navbar-main">
                    <div className="flexbox-section">
                        <div className="flexbox-section">
                            <div className="temp-section">
                                {month + " " + day}
                            </div>

                            <button className="button button1" onClick={prevWeek}>
                                Prev Week
                            </button>

                            <button className="button button1" onClick={thisWeek}>
                                This Week
                            </button>

                            <button className="button button1" onClick={nextWeek}>
                                Next Week
                            </button>
                        </div>

                        <div className="flexbox-section">
                            <div className="temp-section">
                                Create
                            </div>

                            <div className="temp-section">
                                Progress
                            </div>

                            <div className="temp-section">
                                Menu
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Navbar;