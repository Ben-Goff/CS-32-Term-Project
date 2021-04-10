import '../App.css';
import './UserLogin.css';
import { Link, useHistory } from "react-router-dom";
import {useState} from "react";

function Login() {

    let username = ""
    let password = ""

    const [showError, setShowError] = useState(false)
    let history = useHistory();

    function changeUsername(event) {
        username = event.target.value;
    }

    function changePassword(event) {
        password = event.target.value;
    }

    function SubmitLogin() {
        console.log("user: " + username + " pass: " + password)

        //POST REQUEST HERE
        if (true) {
             //Sends the user to the main page.
            history.push('/');
            setShowError(false)
        } else {
            setShowError(true)
        }
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


                <button onClick={SubmitLogin}>Login</button>

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