import '../Navbar.css';
import '../App.css';
import React, {useState, useEffect} from "react";
import { Link } from "react-router-dom";

function ProgressNavbar(props) {


    return (
        <div className="Navbar">
            <div className="navbar-body">
                <div className="logo-area">
                    <img src="WeekliLogo.png" className="logo"/>
                    <div className="logo-text">
                        Weekli
                    </div>
                </div>

                <div className="navbar-main">
                    <div className="flexbox-section">
                        <div className="flexbox-section">

                            <Link to="/">
                                <button className="button">
                                    &lt; Back To Calendar
                                </button>
                            </Link>
                        </div>

                        <div className="flexbox-section">

                            {/*Hamburger menu css/html code TAKEN FROM: https://codepen.io/erikterwan/pen/EVzeRP*/}
                            <nav role="navigation">
                                <div id="menuToggle">

                                    <input type="checkbox" />

                                    <span></span>
                                    <span></span>
                                    <span></span>

                                    <ul id="menu">
                                        <Link to="/login"><li>Log Out</li></Link>
                                        <a href="#"><li>Manage Data</li></a>
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