import '../App.css';
import '../UserLogin/UserLogin.css';
import { Link } from "react-router-dom";
import React, {useState} from "react";
import axios from "axios";

function Settings() {

    const [breakTime, setBreakTime] = useState(0);

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }

    const changeBreakTime = (e) => {
        e.preventDefault()
        let breakTimeMS = breakTime * 60000 //Break time in milliseconds

        const toSend = {
            break: breakTimeMS,
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/changebreaktime",
            toSend,
            config
        )
            .then(response => {
                console.log("SUCCESS");
            })
            .catch(function (error) {
                console.log(error.response);
            });

        setBreakTime(0);
    }

    return (
        <div>
            <img src="WeekliLogo.png" className="logo"/>
            <h1>Weekli</h1>

            <h2>Settings</h2>

            <form onSubmit={changeBreakTime}>
                <label htmlFor="break-time">Preferred Breaktime (minutes)</label><br/>
                <input type="number" step="0.01" id="break-time" name="break-time" onChange={(e) => handleChange(e, setBreakTime)}/><br/>
                <input type="submit"/>
            </form>



            <Link to="/home">
                <button className="button forward-button">
                    <span className="forward-text">To The Calendar</span> <span className="forward-icon">&#62;</span>
                </button>
            </Link>
        </div>
    );
}

export default Settings;