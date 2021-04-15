import '../App.css';
import '../UserLogin/UserLogin.css';
import { Link } from "react-router-dom";

function Help() {


    return (
        <div>
            <img src="WeekliLogo.png" className="logo"/>
            <h1>Weekli</h1>

            <h2>Help</h2>

            <p className="help-text">
                Weekli is a scheduling app that will help you manage your time wisely and avoid procrastination.
                You can get started by creating tasks (work that needs to get done). Fill in information about the
                task like how much you want to work on the task in one sitting, how many total hours you think
                the task will take, and when you want to finish the task by. Weekli will then schedule time for you
                to work on the task according to your preferences.
                <br/><br/>
                You can also create times where you don't want tasks to be scheduled as commitments. These can
                be repeating like sleep and lunch, or they can be one off commitments like meetings or appointments.
                <br/><br/>
                Make sure to check out the settings in order to change your preferences like how many minutes of break
                time you would like between tasks.
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