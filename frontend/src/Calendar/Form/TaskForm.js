import './Form.css';
import '../../App.css';
import React, {useState} from "react";
import axios from "axios";

function TaskForm(props) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [dueDate, setDueDate] = useState("");
    const [dueTime, setDueTime] = useState("");
    const [estimatedEffort, setEstimatedEffort] = useState(0);
    const [sessionLength, setSessionLength] = useState(0);
    const [color, setColor] = useState("#EF8E96");

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }

    const createTask = (e) => {
        e.preventDefault();
        let startMillis = new Date().getTime();
        let endMillis = Date.parse(dueDate + " " + dueTime);
        let startMillisStr = startMillis.toString();
        let endMillisStr = endMillis.toString();
        let estimatedEffortMillisStr = (estimatedEffort * 60 * 60 * 1000).toString();
        let sessionLengthMillisStr = (sessionLength * 60 * 60 * 1000).toString();
        console.log(name);
        console.log(description);
        console.log(startMillisStr);
        console.log(endMillisStr);
        console.log(estimatedEffortMillisStr);
        console.log(sessionLengthMillisStr);
        console.log(color);

        const toSend = {
            name: name,
            description: description,
            startTime: startMillisStr,
            endTime: endMillisStr,
            estTime: estimatedEffortMillisStr,
            sessionTime: sessionLengthMillisStr,
            color: color
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/createtask",
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

    return (
        <div className="Form TaskForm">
            <form onSubmit={createTask}>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title" onChange={(e) => handleChange(e, setName)}/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description" onChange={(e) => handleChange(e, setDescription)}/><br/>
                <label htmlFor="due-date">Due Date</label><br/>
                <input type="date" id="due-date" name="due-date" placeholder={"mm-dd-yyyy"} onChange={(e) => handleChange(e, setDueDate)}/>
                <label htmlFor="due-time" style={{"display": "none"}}>Due Time</label>
                <input type="time" id="due-time" name="due-time" placeholder={"__:__ AM/PM"} onChange={(e) => handleChange(e, setDueTime)}/><br/>
                <label htmlFor="estimated-effort">Estimated Effort (hrs)</label><br/>
                <input type="number" step="0.1" min="0" id="estimated-effort" name="estimated-effort" onChange={(e) => handleChange(e, setEstimatedEffort)}/><br/>
                <label htmlFor="session-length">Max Session Length (hrs)</label><br/>
                <input type="number" step="0.1" min="0" id="session-length" name="session-length" onChange={(e) => handleChange(e, setSessionLength)}/><br/>
                <label htmlFor="color">Color</label><br/>
                <div className="color-input">
                    <div style={{"width": "20px", "height": "20px", "backgroundColor": color}}/>
                    <select id="color" name="color" onChange={(e) => handleChange(e, setColor)}>
                        <option value="#EF8E96">red</option>
                        <option value="#F9BA89">orange</option>
                        <option value="#FED48F">yellow</option>
                        <option value="#7FC0A0">green</option>
                        <option value="#7EAFDB">blue</option>
                        <option value="#A288BA">purple</option>
                    </select>
                </div>

                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default TaskForm;