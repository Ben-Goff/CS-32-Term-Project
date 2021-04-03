import './CalendarContainer.css';
import '../App.css';
import Calendar from "./Calendar";
import {getMonday} from "../WeekliHelpers";

function CalendarContainer(props) {


    let dayNames = ["M", "T", "W", "Th", "F", "S", "Su"];
    let dayNumbers = [];
    const monday = props.getDisplayMonday();

    let curDay = monday;
    for (let i = 0; i < 7; i++) {
        dayNumbers.push(curDay.getDate());
        let nextDay = new Date(curDay);
        nextDay.setDate(nextDay.getDate() + 1);
        curDay = nextDay;
    }

    let dayLabels = [0, 1, 2, 3, 4, 5, 6].map(idx => {
        return <div className="DayLabel">{dayNames[idx]}<br/>{dayNumbers[idx]}</div>
    })

    let d = new Date();

    if (getMonday(d).getDate() === props.getDisplayMonday().getDate()  &&
        getMonday(d).getMonth() === props.getDisplayMonday().getMonth()){
        let circlePosition = (d.getDay() - 1) % 6; // modulo is to convert to monday-start
        dayLabels[circlePosition] = <div className="circled">{dayLabels[circlePosition]}</div>
        //console.log(circlePosition)
    }

    return (
        <div className="CalendarContainer">
            <div className="DayLabels">
                {dayLabels}
            </div>
            <div className="scrollable">
                <Calendar/>
            </div>
        </div>
    );
}

export default CalendarContainer;