import './Navbar.css';
import './App.css';

function Navbar() {
    let d = new Date();
    let month = ["January", "February", "March", "April", "June", "July", "August", "September", "November", "December"][d.getMonth()];
    let day = d.getDate();

    return (
        <div className="Navbar">
            <div className="navbar-body">
                <div className="logo-area">
                    <img src="WeekliLogo.png" className="logo"/>
                </div>

                <div className="navbar-main">
                    <div className="flexbox-section">
                        <div className="flexbox-section">
                            <div className="temp-section">
                                {month + " " + day}
                            </div>

                            <div className="temp-section">
                                Prev Week
                            </div>

                            <div className="temp-section">
                                Next Week
                            </div>

                            <div className="temp-section">
                                This Week
                            </div>
                        </div>

                        <div className="flexbox-section">
                            <div className="temp-section">
                                Create
                            </div>

                            <div className="temp-section">
                                Progress
                            </div>

                            <div className="temp-section">
                                Menu
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Navbar;