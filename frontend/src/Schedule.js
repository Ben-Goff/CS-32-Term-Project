import './App.css';
import './Navbar.css'
import './Schedule.css';
import {Link} from "react-router-dom";
import React, {useState, useEffect} from "react";

function Schedule() {
    let [numCards, setNumCards] = useState(4);
    let [titles, setTitles] = useState(["Breakfast", "Lunch", "Dinner", "Sleep"]);
    let [descs, setDescs] = useState(["Eat breakfast", "Eat lunch", "Eat dinner", "Hit" +
    " the Sack"]);
    let [startTimes, setStartTimes] = useState(["08:00:00", "12:00:00", "18:00:00", "23:30:00"]);
    let [endTimes, setEndTimes] = useState(["08:30:00", "12:30:00", "19:00:00", "07:30:00"]);
    let [repeatCounts, setRepeatCounts] = useState([1, 1, 1, 1]);
    let [repeatTypes, setRepeatTypes] = useState(["day", "day", "day", "day"]);
    let [repeatStarts, setRepeatStarts] = useState(["Monday", "Monday", "Monday", "Monday"]);
    let [cards, setCards] = useState([]);

    useEffect(() => {
        let cardsList = [];
        for (let i = 0; i < numCards; i++) {
            let card = (
                <div className="schedule-card">
                    <form>
                        <label htmlFor="title">Title</label><br/>
                        <input type="text" id="title" name="title" value={titles[i]}/><br/>
                        <label htmlFor="description">Description</label><br/>
                        <input type="text" id="description" name="description" value={descs[i]}/><br/>
                        <label htmlFor="start-time">Start Time</label><br/>
                        <input type="time" id="start-time" name="start-time" value={startTimes[i]}/><br/>
                        <label htmlFor="end-time">End Time</label><br/>
                        <input type="time" id="end-time" name="end-time" value={endTimes[i]}/><br/>
                        <label htmlFor="repeat-count">Repeat every</label>
                        <input type="number" id="repeat-count" name="repeat-count" value={repeatCounts[i]}/>
                        <label htmlFor="repeat-type" style={{"display": "none"}}>Repeat type</label>
                        <select id="repeat-type" name="repeat-type">
                            <option value="day">day</option>
                            <option value="week">week</option>
                        </select>
                        <label htmlFor="repeat-start">starting from</label><br/>
                        <select id="repeat-start" name="repeat-start">
                            <option value="Monday">Monday</option>
                            <option value="Tuesday">Tuesday</option>
                            <option value="Wednesday">Wednesday</option>
                            <option value="Thursday">Thursday</option>
                            <option value="Friday">Friday</option>
                            <option value="Saturday">Saturday</option>
                            <option value="Sunday">Sunday</option>
                        </select>
                    </form>
                </div>
            )
            cardsList.push(card);
        }
        setCards(cardsList);
    }, [])


    return (
        <div className="Schedule">
            <div className="flexbox-section">
                <div className="logo-area">
                    <img alt="" src="WeekliLogo.png" className="logo"/>
                    <div className="logo-text">
                        Weekli
                    </div>
                    <div className="title-text">
                        Edit Schedule
                    </div>
                </div>

                <div className="flexbox-section">
                    <div className="back-button-container">
                        <Link to="/" style={{ textDecoration: 'none' }}>
                            <button className="button">
                                <div className="back-button-contents"><span className="back-icon">{"<"}</span>
                                    <span className="back-text">Back to Calendar</span></div>
                            </button>
                        </Link>
                    </div>

                    <nav role="navigation" style={{"display": "block", "float": "right"}}>
                        <div id="menuToggle">

                            <input type="checkbox" />

                            <span></span>
                            <span></span>
                            <span></span>

                            <ul id="menu">
                                <Link to="/login"><li>Log Out</li></Link>
                                <Link to="/schedule"><li>Edit Schedule</li></Link>
                                <a href="#"><li>Manage Data</li></a>
                                <a href="#"><li style={{"color": "red"}}>Clear Schedule</li></a>
                            </ul>
                        </div>
                    </nav>
                </div>
            </div>

            <div className="schedule-cards">
                {cards}
            </div>
        </div>

    )
}

export default Schedule;