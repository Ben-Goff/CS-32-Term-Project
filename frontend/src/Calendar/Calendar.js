import './Calendar.css';
import '../App.css';
import Block from "./Block";
import Day from "./Day";
import {getMonday} from "../WeekliHelpers";
import React, {useState, useEffect} from 'react'
import axios from "axios";

function Calendar(props) {
    const [blocksGrid, setBlocksGrid] = useState([[], [], [], [], [], [], []])
    const [days, setDays] = useState([]);
    const [schedule, setSchedule] = useState([]);
    const [progressMap, setProgressMap] = useState({});

    useEffect(() => {
        requestProgress();
    }, [])

    useEffect(() => {
        requestProgress();
    }, [schedule])

    useEffect(() => {
        const intervalId = setInterval(() => {  //assign interval to a variable to clear it.
            requestProgress();
        }, 5000)
        return () => clearInterval(intervalId); //This is important
    }, [])


    useEffect(() => {
        requestSchedule(props.displayMonday)
    }, [props.displayMonday, props.showPopup, props.updateFlag])

    useEffect(() => {
        getBlocks();
    }, [schedule, progressMap]);

    useEffect(() => {
        setDays(prepareBlocks());
    }, [blocksGrid]);

    const requestSchedule = (displayMonday) => {
        // console.log("requested schedule: " + displayMonday)
        const toSend = {
            start: displayMonday.getTime(),
            end: (displayMonday.getTime() + (86400000 * 7)) //Number of milliseconds in a week
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/schedule",
            toSend,
            config
        )
            .then(response => {
                // console.log(response.data);
                setSchedule(response.data["schedule"])
                // console.log(schedule)
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }

    const requestProgress = () => {
        const toSend = {
            id: "",
            progress: ""
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/update",
            toSend,
            config
        )
            .then(response => {
                let newProgressMap = {};
                let progressInfo = response.data["tasks"];
                // console.log(progressInfo[0])
                // console.log(progressInfo.length)
                for (let i = 0; i < progressInfo.length; i++) {
                    let blockInfo = progressInfo[i];
                    let blockID = blockInfo[2];
                    let blockProgress = blockInfo[3];
                    newProgressMap[blockID] = blockProgress;
                }
                // console.log(newProgressMap)
                setProgressMap(newProgressMap);
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }

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


    function compareBlocks(a, b) {
        if (a.props.start.getTime() < b.props.start.getTime()) {
            return -1;
        }
        if (a.props.start.getTime() > b.props.start.getTime()) {
            return 1;
        }
        return 0;
    }

    const getBlocks = () => {
        let blocks = [];
        // console.log("hello is anyone out there im so alone")
        // console.log(schedule)
        for (let i = 0; i < schedule.length; i++) {
            // console.log(schedule[i]);
            let blockData = schedule[i];
            let startDate = new Date(parseFloat(blockData[0]));
            // console.log("startDate: " + startDate);
            let endDate = new Date(parseFloat(blockData[1]));
            let identifier = blockData[2];
            let title = blockData[3];
            let description = blockData[4];
            let color = blockData[5];
            let isCommitment = !(identifier in progressMap)
            let progress = progressMap[identifier];

            let block = <Block identifier={identifier} isCommitment={isCommitment} start={startDate} end={endDate} color={color} title={title} desc={description} progress={progress} onClick={(e) => blockRegisterClick(e, block)}/>
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
        blockAcc.sort(compareBlocks);
        // console.log(blockAcc);
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