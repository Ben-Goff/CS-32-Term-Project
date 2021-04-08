import './App.css';
import { Link } from "react-router-dom";

function Progress() {

    return (
        <Link to="/">
            <button className="button">
                To Calendar
            </button>
        </Link>
    );
}

export default Progress;