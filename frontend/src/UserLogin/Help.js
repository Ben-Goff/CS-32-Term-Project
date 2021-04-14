import '../App.css';
import './UserLogin.css';
import { Link } from "react-router-dom";

function Help() {


    return (
        <div>
            <img src="WeekliLogo.png" className="logo"/>
            <h1>Weekli</h1>

            <h2>Help</h2>

            <p className="help-text">
                Weekli is a scheduling app that will help you manage your time wisely and avoid procrastination.
                You can get started by creating tasks (work that needs to get done occasionally). TODO
            </p>

            <Link to="/home">
                <button className="button forward-button">
                    <span className="forward-text">To The Calendar</span> <span className="forward-icon">&#62;</span>
                </button>
            </Link>
        </div>
    );
}

export default Help;