import '../App.css';
import './UserLogin.css';
import { Link, useHistory } from "react-router-dom";
import {useState, useEffect} from "react";
import axios from "axios";

function Login() {

    let username = ""
    let password = ""

    const [showError, setShowError] = useState(false)
    const [message, setMessage] = useState("nothing happened")

    let history = useHistory();

    function changeUsername(event) {
        username = event.target.value;
    }

    function changePassword(event) {
        password = event.target.value;
    }

    useEffect(() => {
        if (message === "login successful") {
            //Sends the user to the main page.
            history.push('/');
            setShowError(false)
        } else {
            setShowError(true)
        }
    }, [message]);

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
                console.log(error);
            });

    }

    return (
        <div>
            <img src="WeekliLogo.png" className="logo"/>
            <h1>Weekli</h1>

            <div>

                <label htmlFor="username">Username</label><br/>
                <input type="text" id="username" name="username" onChange={changeUsername}/><br/>
                <label htmlFor="password">Password</label><br/>
                <input type="text" id="password" name="password" onChange={changePassword}/><br/>


                <button onClick={requestLogin}>Login</button>

                {showError &&
                <p>LOGIN ERROR</p>
                }

            </div>


            New User?
            <Link to="/signup">
                <a className="textLink">
                    Signup Here
                </a>
            </Link>
        </div>
    );
}

export default Login;