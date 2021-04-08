import './Calendar.css';
import '../App.css';
import Block from "./Block";
import Day from "./Day";
import {getMonday} from "../WeekliHelpers";
import React, {useState, useEffect} from 'react'

function Calendar(props) {
    const [blocksGrid, setBlocksGrid] = useState([[], [], [], [], [], [], []])
    const [days, setDays] = useState([]);

    useEffect(() => {
        getBlocks();
    }, [props.displayMonday]);

    useEffect(() => {
        setDays(prepareBlocks());
    }, [blocksGrid]);

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
        let newDate = new Date(props.displayMonday);
        newDate.setDate(newDate.getDate() + i);
        displayWeek.push(newDate)
    }

    const prepareBlocks = () => {
        let days = [];

        let currentDate = new Date()
        let currentMonday = getMonday(currentDate)

        //If display week is current week, add height of 3 to respective day
        if (currentMonday.getFullYear() === props.displayMonday.getFullYear() &&
            currentMonday.getMonth() === props.displayMonday.getMonth() &&
            currentMonday.getDate() === props.displayMonday.getDate()) {
            for (let i = 0; i < 7; i++) {

                if (currentDate.getDay() === (i + 1) % 7) {
                    days[i] = <Day blocks={blocksGrid[i]} height={3}/>;
                } else {
                    days[i] = <Day blocks={blocksGrid[i]} height={0}/>;
                }
            }
        } else {
            for (let i = 0; i < 7; i++) {
                days.push(<Day blocks={blocksGrid[i]} height={0}/>);
            }
        }

    return days
    }


    const getBlocks = () => {
        //DUMMY EXAMPLE HARD CODED BLOCKS
        const block1 = <Block start={new Date(2021, 3, 5, 9, 30, 0, 0)}
                              end={new Date(2021, 3, 5, 10, 30, 0, 0)}
                              color={"red"} text={"Breakfast"}/>;

        const block11 = <Block start={new Date(2021, 3, 5, 14, 0, 0, 0)}
                               end={new Date(2021, 3, 5, 16, 0, 0, 0)}
                               color={"green"} text={"Math Lecture"}/>;

        const block2 = <Block start={new Date(2021, 3, 6, 9, 0, 0, 0)}
                              end={new Date(2021, 3, 6, 9, 30, 0, 0)}
                              color={"blue"} text={"Shower"}/>;

        const block3 = <Block start={new Date(2021, 3, 7, 10, 0, 0, 0)}
                              end={new Date(2021, 3, 7, 12, 30, 0, 0)}
                              color={"orange"} text={"Literally just vibe"}/>;

        const block31 = <Block start={new Date(2021, 3, 13, 9, 30, 0, 0)}
                               end={new Date(2021, 3, 13, 12, 0, 0, 0)}
                               color={"green"} text={"This is to make sure the calendar works" +
        " across multiple weeks"}/>;

        const block8 = <Block start={new Date(2021, 7, 20, 11, 0, 0, 0)}
                              end={new Date(2021, 7, 20, 12, 30, 0, 0)}
                              color={"purple"} text={"Easter egg: this is my birthday lol"}/>;

        let blocks = [
            { year: 2021, month: 3, date: 5, blockComponent: block1},
            { year: 2021, month: 3, date: 5, blockComponent: block11},
            { year: 2021, month: 3, date: 6, blockComponent: block2},
            { year: 2021, month: 3, date: 7, blockComponent: block3},
            { year: 2021, month: 3, date: 13, blockComponent: block31},
            { year: 2021, month: 7, date: 20, blockComponent: block8}
        ]

        let blockAcc = [];

        //Goes through each day of the display week and adds appropriate blocks

        let curBlocksGrid = [...blocksGrid];
        for (let i = 0; i < 7; i++) {

            let month = displayWeek[i].getMonth();
            let date = displayWeek[i].getDate();
            let year = displayWeek[i].getFullYear();
            //console.log(month);

            curBlocksGrid[i] = [];
            for (let block of blocks) {
                if (block.month === month && block.date === date && block.year === year) {
                    curBlocksGrid[i].push(block.blockComponent);
                    blockAcc.push(block.blockComponent);
                }
            }
        }
        props.setTaskBlocks(blockAcc);
        setBlocksGrid(curBlocksGrid);
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