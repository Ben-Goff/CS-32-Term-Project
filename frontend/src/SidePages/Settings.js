import '../App.css';
import '../UserLogin/UserLogin.css';
import { Link } from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";

function Settings() {

    const [breakTime, setBreakTime] = useState(0);
    const [showMessage, setShowMessage] = useState(false);
    const [message, setMessage] = useState("");
    const [currentBreaktime, setCurrentBreaktime] = useState(0);

    useEffect(() => {
        setShowMessage(false)
        requestBreakTime()
    }, [])

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }


    const requestBreakTime = () => {
        const toSend = {

        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/getbreaktime",
            toSend,
            config
        )
            .then(response => {
                console.log(response);
                setCurrentBreaktime(response.data["breaktime"] / 60000);
            })
            .catch(function (error) {
                console.log(error.response);
            });
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
                setShowMessage(true);
                setMessage("Changes saved successfully")
                console.log("SUCCESS");
            })
            .catch(function (error) {
                setShowMessage(true);
                setMessage("Something went wrong")
                console.log(error.response);
            });
    }

    return (
        <div>

            <div className="settings-logo">
                <img alt="" src="WeekliLogo.png" className="logo"/>
                <div className="logo-text">
                    Weekli
                </div>
            </div>

            <h2>Settings</h2>
            <div className="settings-grid">

                <div>
                    <div className="settings-item">
                        <label htmlFor="break-time">Preferred Breaktime (minutes)</label>
                        <input type="number"
                               step="0.01"
                               id="break-time"
                               name="break-time"
                               placeholder={currentBreaktime}
                               style={{width: 50 + "px"}}
                               onChange={(e) => handleChange(e, setBreakTime)}
                        />
                    </div>


                    <button className="savechanges-button" onClick={changeBreakTime}>Save Changes</button>
                </div>

            </div>

            <br/>

            {showMessage &&
            <div >
                {message}
            </div>
            }

            <br/>
            <Link to="/home">
                <button className="button forward-button">
                    <span className="forward-text">To The Calendar</span> <span className="forward-icon">&#62;</span>
                </button>
            </Link>
        </div>
    );
}

export default Settings;