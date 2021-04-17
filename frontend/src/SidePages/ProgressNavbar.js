import '../Navbar.css';
import '../App.css';
import { Link } from "react-router-dom";
import React from "react";

function ProgressNavbar() {
    return (
        <div className="Navbar">
            <div className="navbar-body" style={{"margin-top": "20px"}}>
                <div className="logo-area">
                    <img alt="" src="WeekliLogo.png" style={{"width": "50px", "height": "50px"}} className="logo"/>
                    <div className="logo-text" style={{"marginTop": "-10px"}}>
                        Weekli
                    </div>
                </div>

                <div className="navbar-main">
                    <div className="flexbox-section">
                        <div className="flexbox-section">
                        </div>
                        <div className="flexbox-section">
                            <Link to="/home">
                                <button className="button back-button">
                                    <span className="back-icon">&lt;</span> <span className="back-text">Back To Calendar</span>
                                </button>
                            </Link>
                            {/*Hamburger menu css/html code TAKEN FROM: https://codepen.io/erikterwan/pen/EVzeRP*/}
                            <nav role="navigation">
                                <div id="menuToggle">

                                    <input type="checkbox" />

                                    <span></span>
                                    <span></span>
                                    <span></span>

                                    <ul id="menu">
                                        <Link to="/"><li>Log Out</li></Link>
                                        <Link to="/help"><li>Help</li></Link>
                                        <Link to="/settings"><li>Settings</li></Link>
                                    </ul>
                                </div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProgressNavbar;