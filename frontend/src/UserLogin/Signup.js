import '../App.css';
import './UserLogin.css';
import { Link } from "react-router-dom";
import {useState} from "react";

function Signup() {

    let username = ""
    let password = ""
    let confirm = ""

    const [showError, setShowError] = useState(false)

    function submitSignIn() {
        console.log("user: " + username + " pass: " + password + " confirm: " + confirm)
    }

    function changeUsername(event) {
        username = event.target.value;
    }

    function changePassword(event) {
        password = event.target.value;
        if (password !== confirm) {
            setShowError(true)
        } else {
            setShowError(false)
        }
    }

    function changeConfirm(event) {
        confirm = event.target.value;
        if (password !== confirm) {
            setShowError(true)
        } else {
            setShowError(false)
        }
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

                {/*Links to main page only if there is no error*/}
                {showError &&
                    <div>
                        <button onClick={submitSignIn}>Sign Up</button>
                        <h2>
                            Error, password does not match confirm password.
                        </h2>
                    </div>
                }

                {!showError &&
                <div>
                    <Link to="/">
                        <button onClick={submitSignIn}>Sign Up</button>
                    </Link>
                </div>
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