import '../App.css';
import './UserLogin.css';
import { Link, useHistory } from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";

function Signup() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");

    const [showError, setShowError] = useState(false)
    const [message, setMessage] = useState("Signup failed")

    let history = useHistory();

    function SubmitSignIn() {
        console.log("user: " + username + " pass: " + password + " confirm: " + confirm)

        if (password === confirm) {
            requestSignup()

        } else {
            setMessage("Password does not match confirm password")
            setShowError(true)
        }
    }

    useEffect(() => {
        if (message === "sign up successful") {
            //Sends the user to the main page.
            history.push('/help');
            setShowError(false)
        } else if (message === "user ID already exists") {
            setShowError(true)
        }
    }, [message, history]);

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }

    /**
     * Makes an axios request to sign up.
     */
    const requestSignup = () => {
        const toSend = {
            username: username,
            password: password,
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/signup",
            toSend,
            config
        )
            .then(response => {
                console.log(response.data);
                setMessage(response.data["message"])
            })

            .catch(function (error) {
                console.log(error.response);
            });

    }

    return (
        <div className="login-grid">


            <div>
                <img alt="" src="WeekliLogo.png" className="logo"/>
                <div className="logo-text">
                    Weekli
                </div>
            </div>

            <div className="signup-form">

                <input
                    type="text"
                    id="username"
                    onChange={(e) => handleChange(e, setUsername)}
                    placeholder="Username"
                /><br/>

                <input
                    type="text"
                    id="password"
                    placeholder="Password"
                    onChange={(e) => handleChange(e, setPassword)}
                /><br/>

                <input
                    type="text"
                    id="confirm"
                    placeholder="Confirm Password"
                    onChange={(e) => handleChange(e, setConfirm)}
                /><br/>

                <button className="signup-button" onClick={SubmitSignIn}>Sign Up</button>

                {showError &&
                <div className="error-text">
                    Error: {message}
                </div>
                }

            </div>

            <br/>
            <div>
                Returning User?&nbsp;
                <Link to="/">
                    <a className="textLink">
                        Login Here
                    </a>
                </Link>
            </div>

        </div>

    );
}

export default Signup;