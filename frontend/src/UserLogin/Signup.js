import '../App.css';
import './UserLogin.css';
import { Link } from "react-router-dom";

function Signup() {

    return (
        <Link to="/login">
            <a className="textLink">
                Login Here
            </a>
        </Link>
    );
}

export default Signup;