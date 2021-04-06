import './Navbar.css';
import './App.css';
import React, {useState, useEffect} from "react";
import {getMonday} from "./WeekliHelpers";
import { Link } from "react-router-dom";
import Dropdown from "bootstrap/js/src/dropdown";
import DropdownButton from 'react-bootstrap/DropdownButton';

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
        let lastMonday = new Date(props.getDisplayMonday());
        lastMonday.setDate(lastMonday.getDate() - 7);
        props.setMonday(lastMonday)
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
        props.setMonday(thisMonday);
        setMonth(monthList[thisMonday.getMonth()]);
        setDay(thisMonday.getDate());

        let thisSunday = new Date(thisMonday);
        thisSunday.setDate(thisSunday.getDate() + 6);
        setMonth2(monthList[thisSunday.getMonth()]);
        setDay2(thisSunday.getDate());
        setYear(thisSunday.getFullYear());
    }

    const nextWeek = () => {
        let nextMonday = new Date(props.getDisplayMonday());
        nextMonday.setDate(nextMonday.getDate() + 7);
        props.setMonday(nextMonday);
        setMonth(monthList[nextMonday.getMonth()]);
        setDay(nextMonday.getDate());

        let nextSunday = new Date(nextMonday);
        nextSunday.setDate(nextSunday.getDate() + 6);
        setMonth2(monthList[nextSunday.getMonth()]);
        setDay2(nextSunday.getDate());
        setYear(nextSunday.getFullYear());
    }

    const createTask = () => {
        //MAKE A POPUP
    }




    return (
        <div className="Navbar">
            <div className="navbar-body">
                <div className="logo-area">
                    <img src="WeekliLogo.png" className="logo"/>
                    <div className="logo-text">
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
                                {"<"}
                            </button>

                            <button className="button next-week" onClick={nextWeek}>
                                {">"}
                            </button>

                            <button className="button this-week" onClick={thisWeek}>
                                This Week
                            </button>
                        </div>

                        <div className="flexbox-section">

                            <button className="button create">
                                <img src="plus.png" className="plus"/> <div className="create-text">Create</div>

                            </button>

                            <Link to="/progress">
                                <button className="button progress">
                                    <img src="bars.png" className="bars"/> <div className="progress-text">Progress</div>
                                </button>
                            </Link>

                            <DropdownButton
                                menuAlign="right"
                                title={<img src="Hamburger_icon.png" className="hamburger"/>}
                                id="dropdown-menu-align-right"
                            >
                                <Dropdown.Item eventKey="1">Action</Dropdown.Item>
                                <Dropdown.Item eventKey="2">Another action</Dropdown.Item>
                                <Dropdown.Item eventKey="3">Something else here</Dropdown.Item>
                                <Dropdown.Divider />
                                <Dropdown.Item eventKey="4">Separated link</Dropdown.Item>
                            </DropdownButton>

                            {/*<button className="menu">*/}
                            {/*</button>*/}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Navbar;