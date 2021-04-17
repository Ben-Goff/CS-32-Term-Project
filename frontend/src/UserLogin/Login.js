import '../App.css';
import './UserLogin.css';
import { Link, useHistory } from "react-router-dom";
import React, {useState, useEffect} from "react";
import axios from "axios";

function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [showError, setShowError] = useState(false)
    const [message, setMessage] = useState("login error")

    let history = useHistory();

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }

    useEffect(() => {
        if (message === "login successful") {
            //Sends the user to the main page.
            history.push('/home');
            setShowError(false)
        } else if (message === "login failed") {
            setShowError(true)
        }
    }, [message, history]);

    /**
     * Makes an axios request to login.
     */
    const requestLogin = () => {
        console.log("user: " + username + " pass: " + password)

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
            "http://localhost:4567/login",
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


            <div className="login-form">

                <input
                    type="text"
                    id="username"
                    placeholder="Username"
                    style={{width: 200 + "px"}}
                    onChange={(e) => handleChange(e, setUsername)}
                /><br/>

                <input
                    type="text"
                    id="password"
                    placeholder="Password"
                    style={{width: 200 + "px"}}
                    onChange={(e) => handleChange(e, setPassword)}
                /><br/>

                <button className="login-button" onClick={requestLogin}>Login</button>

                {<div className="error-text">
                    Error: {message}
                </div>}

            </div>
            <br/>
            <div>
                New User?&nbsp;
                <Link to="/signup">
                    <a className="textLink">
                        Signup Here
                    </a>
                </Link>
            </div>
        </div>
    );
}

export default Login;