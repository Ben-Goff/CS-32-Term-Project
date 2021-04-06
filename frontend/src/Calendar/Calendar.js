import './Calendar.css';
import '../App.css';
import Block from "./Block";
import Day from "./Day";

function Calendar(props) {

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


    let displayWeek = [];

    for (let i = 0; i < 7; i++) {
        let newDate = new Date(props.getDisplayMonday());
        newDate.setDate(newDate.getDate() + i);
        displayWeek.push(newDate)
    }

    const block1 = <Block start={new Date(2021, 3, 5, 9, 30, 0, 0)}
                          end={new Date(2021, 3, 4, 10, 30, 0, 0)}
                          color={"red"} text={"Breakfast"}/>;

    const block11 = <Block start={new Date(2021, 3, 5, 14, 0, 0, 0)}
                           end={new Date(2021, 3, 4, 16, 0, 0, 0)}
                           color={"orange"} text={"Math Lecture"}/>;

    const block2 = <Block start={new Date(2021, 3, 5, 9, 0, 0, 0)}
                          end={new Date(2021, 3, 4, 9, 30, 0, 0)}
                          color={"red"} text={"Shower"}/>;

    let blocks = [
        { year: 2021, month: 3, date: 5, blockComponent: block1},
        { year: 2021, month: 3, date: 5, blockComponent: block11},
        { year: 2021, month: 3, date: 6, blockComponent: block2}
    ]


    // const block21 = <Block start={60 * 4} end={60 * 10} color={"orange"} text={"Midterm Cramming"}/>;
    // const block3 = <Block start={60 * 3} end={60 * 9.5} color={"blue"} text={"Therapy Session"}/>;
    // const block31 = <Block start={60} end={60 * 13} color={"red"} text={"Lunch"}/>;
    // const block4 = <Block start={60 * 2} end={60 * 15} color={"orange"} text={"Term Project Group Meeting"}/>;
    // const block5 = <Block start={60 * 3} end={60 * 8.5} color={"blue"} text={"More Therapy"}/>;
    // const block6 = <Block start={60 * 2} end={60 * 10} color={"orange"} text={"CS32" +
    // " Term Project"}/>;
    // const block7 = <Block start={60 * 1.5} end={60 * 17} color={"red"} text={"Dinner"}/>;

    let days = [];

    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);
    days.push(<Day blocks={[]}/>);


    //Goes through each day of the display week and adds appropriate blocks
    for (let i = 0; i < 7; i++) {

        let month = displayWeek[i].getMonth()
        let date = displayWeek[i].getDate()
        let year = displayWeek[i].getFullYear()

        for (let block of blocks) {
            if (block.month === month && block.date === date && block.year === year) {
                days[i].props.blocks.push(block.blockComponent)
            }
        }
    }


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