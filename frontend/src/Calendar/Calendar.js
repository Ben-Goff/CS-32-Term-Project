import './Calendar.css';
import '../App.css';
import Block from "./Block";
import Day from "./Day";

function Calendar() {

    let hourLabels = [];
    for (let i = 0; i < 24; i++) {
        if (i === 0) {
            hourLabels.push("12am");
        } else if (i <= 12) {
            hourLabels.push(i + "am");
        } else if (i === 12) {
            hourLabels.push("12pm");
        } else if (i >= 12) {
            hourLabels.push(i - 12 + "pm");
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

    const block1 = <Block durationMinutes={120} startMinutes={120} color={"red"} text={"SAMPLE"}/>;
    const block11 = <Block durationMinutes={60} startMinutes={360} color={"blue"} text={"SAMPLE"}/>;
    const block2 = <Block durationMinutes={60} startMinutes={120} color={"blue"} text={"SAMPLE"}/>;
    const block21 = <Block durationMinutes={120} startMinutes={240} color={"orange"} text={"SAMPLE"}/>;
    const block3 = <Block durationMinutes={240} startMinutes={240} color={"red"} text={"SAMPLE"}/>;
    const block4 = <Block durationMinutes={120} startMinutes={360} color={"orange"} text={"SAMPLE"}/>;
    const block5 = <Block durationMinutes={120} startMinutes={480} color={"orange"} text={"SAMPLE"}/>;
    const block6 = <Block durationMinutes={60} startMinutes={600} color={"blue"} text={"SAMPLE"}/>;
    const block7 = <Block durationMinutes={240} startMinutes={720} color={"red"} text={"SAMPLE"}/>;

    days[0].props.blocks.push(block1);
    days[0].props.blocks.push(block11);
    days[1].props.blocks.push(block2);
    days[1].props.blocks.push(block21);
    days[2].props.blocks.push(block3);
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