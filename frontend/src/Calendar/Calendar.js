import './Calendar.css';
import '../App.css';
import Day from "./Day";
import Block from "./Block";

function Calendar() {

    let days = [];
    for (let i = 0; i < 7; i++) {
        days.push(Day());
    }

    return (
        <div className="Calendar">
            <Block durationMinutes={120} day={0} startMinutes={0} color={"red"} text={"SAMPLE"}/>
            <Block durationMinutes={120} day={1} startMinutes={120} color={"blue"}/>
            <Block durationMinutes={120} day={2} startMinutes={240} color={"red"}/>
            <Block durationMinutes={120} day={3} startMinutes={360} color={"orange"}/>
            <Block durationMinutes={120} day={4} startMinutes={480} color={"orange"}/>
            <Block durationMinutes={120} day={5} startMinutes={600} color={"orange"}/>
            <Block durationMinutes={120} day={6} startMinutes={720} color={"orange"}/>
            {days}

        </div>
    );
}

export default Calendar;