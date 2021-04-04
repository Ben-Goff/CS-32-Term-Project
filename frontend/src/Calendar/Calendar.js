import './Calendar.css';
import '../App.css';
import Block from "./Block";
import Day from "./Day";

function Calendar() {

    let hourLabels = [];
    for (let i = 0; i < 24; i++) {
        if (i === 0) {
            hourLabels.push("12 am");
        } else if (i < 12) {
            hourLabels.push(i + " am");
        } else if (i === 12) {
            hourLabels.push("12 pm");
        } else if (i > 12) {
            hourLabels.push(i - 12 + " pm");
        }
    }
    const hourLabelCol = (
        <div className="HourLabelCol">
            <br/><br/>
            <div>
            {hourLabels.map(row => {
                return <div className="HourLabel" key={row}>{row}</div>})
            }
            </div>
        </div>
    );

    let days = [];
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);

    const block1 = <Block durationMinutes={60} startMinutes={60 * 8.5} color={"red"} text={"Breakfast"}/>;
    const block11 = <Block durationMinutes={60 * 1.5} startMinutes={60 * 14} color={"orange"} text={"Math Lecture"}/>;
    const block2 = <Block durationMinutes={30} startMinutes={60 * 9} color={"red"} text={"Shower"}/>;
    const block21 = <Block durationMinutes={60 * 4} startMinutes={60 * 10} color={"orange"} text={"Midterm Cramming"}/>;
    const block3 = <Block durationMinutes={60 * 3} startMinutes={60 * 9.5} color={"blue"} text={"Therapy Session"}/>;
    const block31 = <Block durationMinutes={60} startMinutes={60 * 13} color={"red"} text={"Lunch"}/>;
    const block4 = <Block durationMinutes={60 * 2} startMinutes={60 * 15} color={"orange"} text={"Term Project Group Meeting"}/>;
    const block5 = <Block durationMinutes={60 * 3} startMinutes={60 * 8.5} color={"blue"} text={"More Therapy"}/>;
    const block6 = <Block durationMinutes={60 * 2} startMinutes={60 * 10} color={"orange"} text={"CS32" +
    " Term Project"}/>;
    const block7 = <Block durationMinutes={60 * 1.5} startMinutes={60 * 17} color={"red"} text={"Dinner"}/>;

    days[0].props.blocks.push(block1);
    days[0].props.blocks.push(block11);
    days[1].props.blocks.push(block2);
    days[1].props.blocks.push(block21);
    days[2].props.blocks.push(block3);
    days[2].props.blocks.push(block31);
    days[3].props.blocks.push(block4);
    days[4].props.blocks.push(block5);
    days[5].props.blocks.push(block6);
    days[6].props.blocks.push(block7);

    return (
        <div className="Calendar">
            <div className="CalendarContents">
                {hourLabelCol}
                {days}
            </div>
        </div>
    );
}

export default Calendar;