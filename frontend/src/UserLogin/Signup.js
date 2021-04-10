import '../App.css';
import './UserLogin.css';
import { Link, useHistory } from "react-router-dom";
import {useState} from "react";

function Signup() {

    let username = ""
    let password = ""
    let confirm = ""

    const [showError, setShowError] = useState(false)
    let history = useHistory();

    function SubmitSignIn() {
        console.log("user: " + username + " pass: " + password + " confirm: " + confirm)
        
        if (password === confirm) {
            //POST REQUEST HERE

            //Sends the user to the main page.
            history.push('/');
            setShowError(false)
        } else {
            setShowError(true)
        }
    }

    function changeUsername(event) {
        username = event.target.value;
    }

    function changePassword(event) {
        password = event.target.value;
    }

    function changeConfirm(event) {
        confirm = event.target.value;
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
                    Error, password does not match confirm password.
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