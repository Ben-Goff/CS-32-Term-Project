import '../Navbar.css';
import '../App.css';
import React, {useState, useEffect} from "react";
import { Link } from "react-router-dom";

function ProgressNavbar(props) {
    return (
        <div className="Navbar">
            <div className="navbar-body" style={{"margin-top": "20px"}}>
                <div className="logo-area">
                    <img src="WeekliLogo.png" className="logo"/>
                    <div className="logo-text">
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
                                        <Link to="/login"><li>Log Out</li></Link>
                                        <Link to="/help"><li>Help</li></Link>
                                        <Link to="/settings"><li>Settings</li></Link>
                                        <a href="#"><li style={{"color": "red"}}>Clear Schedule</li></a>
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