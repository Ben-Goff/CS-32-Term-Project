import './Day.css';
import '../App.css';
import React, {useState} from "react";

function Day(props) {

    let hours = [];
    for (let i = 0; i < 24; i++) {
        hours.push(<div className="Hour"/>);
    }

    let date = new Date()

    const [top, setTop] = useState(date.getHours() * 60 + date.getMinutes()) //Pixels from the top of the calendar

    function setTime() {
        date = new Date()
        setTop(date.getHours() * 60 + date.getMinutes())
    }

    setInterval(setTime, 60000)

    let timeIndicator = <div className="timeIndicator" style={
        {"top": top + 'px',
        "height": props.height + 'px'}
    }/>

    return (
        <div className="Day">
            {timeIndicator}
            <div className="block-container">
                {props.blocks}
            </div>
            {hours}
        </div>
    );

}

export default Day;