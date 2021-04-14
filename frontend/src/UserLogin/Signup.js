import '../App.css';
import './UserLogin.css';
import { Link, useHistory } from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function Signup() {

    let username = ""
    let password = ""
    let confirm = ""

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
    }, [message]);

    function changeUsername(event) {
        username = event.target.value;
    }

    function changePassword(event) {
        password = event.target.value;
    }

    function changeConfirm(event) {
        confirm = event.target.value;
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
                console.log(error);
            });

    }

    return (
        <div className="top-bar">
            <img src="WeekliLogo.png" className="logo"/>
            <h1>Welcome to Weekli</h1>

            <div>

                <label htmlFor="username">Username</label><br/>
                <input type="text" id="username" name="username" onChange={changeUsername}/><br/>
                <label htmlFor="password">Password</label><br/>
                <input type="text" id="password" name="password" onChange={changePassword}/><br/>
                <label htmlFor="confirm">Confirm Password</label><br/>
                <input type="text" id="confirm" name="confirm" onChange={changeConfirm}/><br/>

                <button onClick={SubmitSignIn}>Sign Up</button>

                {showError &&
                <h2>
                    Error: {message}
                </h2>
                }


            </div>

            Returning User?
            <Link to="/login">
                <a className="textLink">
                    Login Here
                </a>
            </Link>
        </div>

    );
}

export default Signup;