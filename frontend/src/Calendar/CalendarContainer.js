import './CalendarContainer.css';
import '../App.css';
import Calendar from "./Calendar";

function CalendarContainer() {

    return (
        <div className="CalendarContainer">
            <div className="HourLabels"/>
            <Calendar/>
            <div className="BottomFiller"/>
        </div>
    );
}

export default CalendarContainer;