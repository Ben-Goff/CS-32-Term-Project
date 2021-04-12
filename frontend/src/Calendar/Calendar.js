import './Calendar.css';
import '../App.css';
import Block from "./Block";
import Day from "./Day";
import {getMonday, getSchedule} from "../WeekliHelpers";
import React, {useState, useEffect} from 'react'

function Calendar(props) {
    const [blocksGrid, setBlocksGrid] = useState([[], [], [], [], [], [], []])
    const [days, setDays] = useState([]);

    // TODO: make this an actual axios request
    const retrieveSchedule = () => {
        let schedule = getSchedule(props.displayMonday)
        console.log(schedule)
        //
        return schedule;
    }

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
                    return <div className="HourLabel" key={row}>{row}</div>
                })
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

    const blockRegisterClick = (event, block) => {
        props.setClickedBlock(block);
        props.setClickedX(event.clientX);
        props.setClickedY(event.clientY);
    }

    const getBlocks = () => {
        let schedule = retrieveSchedule();
        let blocks = [];

        for (let i = 0; i < schedule.length; i++) {
            let blockData = schedule[i];
            let startDate = new Date(parseInt(blockData[0]));
            let endDate = new Date(parseInt(blockData[1]));
            let identifier = blockData[2];
            let title = blockData[3];
            let description = blockData[4];
            let color = blockData[5];

            let block = <Block identifier={identifier} start={startDate} end={endDate} color={color} title={title} desc={description} onClick={(e) => blockRegisterClick(e, block)}/>
            blocks.push({year: startDate.getFullYear(), month: startDate.getMonth(), date: startDate.getDate(), blockComponent: block});
        }

        let blockAcc = [];

        //Goes through each day of the display week and adds appropriate blocks

        let curBlocksGrid = [...blocksGrid];
        for (let i = 0; i < 7; i++) {

            let month = displayWeek[i].getMonth();
            let date = displayWeek[i].getDate();
            let year = displayWeek[i].getFullYear();

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