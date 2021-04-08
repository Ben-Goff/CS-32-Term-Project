import '../App.css';
import './UserLogin.css';
import { Link } from "react-router-dom";

function Login() {

    return (
        <Link to="/signup">
            <a className="textLink">
                Signup Here
            </a>
        </Link>
    );
}

export default Login;