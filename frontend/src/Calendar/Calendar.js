import './Calendar.css';
import '../App.css';
import Day from "./Day";

function Calendar() {

    let days = [];
    for (let i = 0; i < 7; i++) {
        days.push(Day());
    }

    return (
        <div className="Calendar">
            {days}
        </div>
    );
}

export default Calendar;