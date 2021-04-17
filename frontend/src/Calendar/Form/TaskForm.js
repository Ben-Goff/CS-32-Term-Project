import './Form.css';
import '../../App.css';
import React, {useState} from "react";
import axios from "axios";

function TaskForm(props) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [dueDate, setDueDate] = useState("");
    const [dueTime, setDueTime] = useState("");
    const [estimatedEffortHours, setEstimatedEffortHours] = useState(0);
    const [estimatedEffortMinutes, setEstimatedEffortMinutes] = useState(0);
    const [sessionLengthHours, setSessionLengthHours] = useState(0);
    const [sessionLengthMinutes, setSessionLengthMinutes] = useState(0);
    const [color, setColor] = useState("#EF8E96");
    const [errorMessage, setErrorMessage] = useState("");

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }

    const createTask = (e) => {
        e.preventDefault();
        let startMillis = new Date().getTime();
        let endMillis = Date.parse(dueDate + " " + dueTime);
        let startMillisStr = startMillis.toString();
        let endMillisStr = endMillis.toString();
        let estimatedEffortMillisStr = (estimatedEffortHours * 60 * 60 * 1000 + estimatedEffortMinutes * 60 * 1000).toString();
        let sessionLengthMillisStr = (sessionLengthHours * 60 * 60 * 1000 + sessionLengthMinutes * 60 * 1000).toString();
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
                if (response.data["message"] !== "success") {
                    setErrorMessage("Error: no room for task in current schedule");
                } else {
                    setErrorMessage("");
                    props.setShowPopup(false);
                }
                console.log(response.data["message"]);
            })
            .catch(function (error) {
                setErrorMessage("Error: no room for task in current schedule");
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
                <label htmlFor="estimated-effort-hours">Estimated Effort</label><br/>
                <input type="number" step="1" min="0" id="estimated-effort-hours" name="estimated-effort-hours" onChange={(e) => handleChange(e, setEstimatedEffortHours)}/> hours{" "}
                <label htmlFor="estimated-effort-minutes" style={{"display": "none"}}>Estimated Effort</label>
                <input type="number" step="1" min="0" max="59" id="estimated-effort-minutes" name="estimated-effort-minutes" onChange={(e) => handleChange(e, setEstimatedEffortMinutes)}/> minutes<br/>
                <label htmlFor="session-length-hours">Max Session Length</label><br/>
                <input type="number" step="1" min="0" id="session-length-hours" name="session-length-hours" onChange={(e) => handleChange(e, setSessionLengthHours)}/> hours{" "}
                <label htmlFor="session-length-minutes" style={{"display": "none"}}>Estimated Effort</label>
                <input type="number" step="1" min="0" max="59" id="session-length-minutes" name="session-length-minutes" onChange={(e) => handleChange(e, setSessionLengthMinutes)}/> minutes<br/>
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
                <div className="error-message">{errorMessage}</div><br/>
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default TaskForm;