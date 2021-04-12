import './Form.css';
import '../../App.css';
import React, {useState, useEffect} from "react";
import axios from "axios";

function CommitmentForm(props) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [startDate, setStartDate] = useState(0);
    const [startTime, setStartTime] = useState(0);
    const [duration, setDuration] = useState(0);
    const [repeats, setRepeats] = useState(false);
    const [repeatCount, setRepeatCount] = useState(0);
    const [repeatType, setRepeatType] = useState("day");

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }

    const repeatTypeDict = {};
    repeatTypeDict["day"] = 24;
    repeatTypeDict["week"] = 7 * 24;

    const createCommitment = (e) => {
        e.preventDefault();
        let startMillis = Date.parse(startDate + " " + startTime);
        let endMillis = startMillis + duration * 60 * 60 * 1000;
        let startMillisStr = startMillis.toString();
        let endMillisStr = endMillis.toString();
        let repeatTypeHours = repeatTypeDict[repeatType];
        let repeatPeriodMillisStr = repeats ? (repeatCount * repeatTypeHours * 60 * 60 * 1000).toString() : "";

        console.log("----Commitment----")
        console.log(name)
        console.log(description)
        console.log(startMillisStr)
        console.log(endMillisStr)
        console.log(repeatPeriodMillisStr)

        const toSend = {
            name: name,
            description: description,
            startTime: startMillisStr,
            endTime: endMillisStr,
            periodOfRepitition: repeatPeriodMillisStr
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/createcommitment",
            toSend,
            config
        )
            .then(response => {
                props.setShowPopup(false);
                console.log("SUCCESS");
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }

    const repeatsInputs = (
        <div>
            <label htmlFor="repeat-count">Repeat every</label>
            <input type="number" id="repeat-count" name="repeat-count" onChange={(e) => handleChange(e, setRepeatCount)}/>
            <label htmlFor="repeat-type" style={{"display": "none"}}>Repeat type</label>
            <select id="repeat-type" name="repeat-type" onChange={(e) => handleChange(e, setRepeatType)}>
                <option value="day">days</option>
                <option value="week">weeks</option>
            </select>
        </div>)

    return (
        <div className="Form CommitmentForm">
            <form onSubmit={createCommitment}>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title" onChange={(e) => handleChange(e, setName)}/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description" onChange={(e) => handleChange(e, setDescription)}/><br/>
                <label htmlFor="start-date">Start Date</label><br/>
                <input type="date" id="start-date" name="start-date" placeholder={"mm-dd-yyyy"} onChange={(e) => handleChange(e, setStartDate)}/>
                <label htmlFor="start-time" style={{"display": "none"}}>Start Time</label>
                <input type="time" id="start-time" name="start-time" placeholder={"__:__ AM/PM"} onChange={(e) => handleChange(e, setStartTime)}/><br/>
                <label htmlFor="duration">Duration (hrs)</label><br/>
                <input type="number" step="0.01" id="duration" name="duration" onChange={(e) => handleChange(e, setDuration)}/><br/>
                <label htmlFor="repeats">Repeats?</label>
                <input type="checkbox" id="repeats" name="repeats" value="checked" onChange={(e) => handleChange(e, setRepeats)}/><br/>
                {repeats ? repeatsInputs : <div/>}
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default CommitmentForm;