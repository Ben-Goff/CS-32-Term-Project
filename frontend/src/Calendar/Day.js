import './Calendar.css';
import '../App.css';
import Hour from "./Hour";

function Day() {

    let hours = [];
    for (let i = 0; i < 24; i++) {
        hours.push(Hour());
    }

    return (
        <div className="Day">
            {hours}
        </div>
    );

}

export default Day;