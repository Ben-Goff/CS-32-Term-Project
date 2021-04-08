import '../App.css';
import './UserLogin.css';
import { Link } from "react-router-dom";
import {useState} from "react";

function Login() {

    //TODO: error handling when login is not valid.

    let username = ""
    let password = ""

    const [showError, setShowError] = useState(false)

    function changeUsername(event) {
        username = event.target.value;
    }

    function changePassword(event) {
        password = event.target.value;
    }

    function submitSignIn() {
        console.log("user: " + username + " pass: " + password)
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

                {/*Links to main page only if there is no error*/}
                {showError &&
                <div>
                    <button onClick={submitSignIn}>Login</button>
                    <h2>
                        ERROR
                    </h2>
                </div>
                }

                {!showError &&
                <div>
                    <Link to="/">
                        <button onClick={submitSignIn}>Login</button>
                    </Link>
                </div>
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