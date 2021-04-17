import './Form.css';
import '../../App.css';
import React, {useState, useEffect} from "react";
import axios from "axios";

function ProjectForm(props) {
    const [numCheckpoints, setNumCheckpoints] = useState(0);
    const [checkpointForms, setCheckpointForms] = useState(null);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [color, setColor] = useState("#EF8E96")

    const [nameDict, setNameDict] = useState({});
    const [descriptionDict, setDescriptionDict] = useState({});
    const [dueDateDict, setDueDateDict] = useState({});
    const [dueTimeDict, setDueTimeDict] = useState({});
    const [estimatedEffortDict, setEstimatedEffortDict] = useState({});
    const [sessionLengthDict, setSessionLengthDict] = useState({});
    const [errorMessage, setErrorMessage] = useState("");

    const handleChange = (e, setter) => {
        setter(e.target.value);
    }

    const handleCheckpointChange = (e, dict, i, setter) => {
        dict[i] = e.target.value;
        setter(dict);
    }

    useEffect(() => {
        let formsList = [];
        for (let i = 0; i < numCheckpoints; i++) {
            let checkpointID = "checkpoint-" + (i + 1).toString();
            formsList.push(
                <div>
                    <label htmlFor={checkpointID + "-title"}>Checkpoint {i + 1} Title</label><br/>
                    <input type="text" id={checkpointID + "-title"} name={checkpointID + "-title"} onChange={(e) => handleCheckpointChange(e, nameDict, i, setNameDict)}/><br/>
                    <label htmlFor={checkpointID + "-description"}>Description</label><br/>
                    <input type="text" id={checkpointID + "-description"} name={checkpointID + "-description"} onChange={(e) => handleCheckpointChange(e, descriptionDict, i, setDescriptionDict)}/><br/>
                    <label htmlFor={checkpointID + "-due-date"}>Due Date</label><br/>
                    <input type="date" id={checkpointID + "-due-date"} name={checkpointID + "-due-date"} placeholder={"mm-dd-yyyy"} onChange={(e) => handleCheckpointChange(e, dueDateDict, i, setDueDateDict)}/>
                    <label htmlFor={checkpointID + "-due-time"} style={{"display": "none"}}>Due" +
                        " Time</label>
                    <input type="time" id={checkpointID + "-due-time"} name={checkpointID + "-due-time"} placeholder={"__:__ AM/PM"} onChange={(e) => handleCheckpointChange(e, dueTimeDict, i, setDueTimeDict)}/><br/>
                    <label htmlFor={checkpointID + "-estimated-effort"}>Estimated Effort (hrs)</label><br/>
                    <input type="number" step="0.1" min="0" id={checkpointID + "-estimated-effort"} name={checkpointID + "-estimated-effort"} onChange={(e) => handleCheckpointChange(e, estimatedEffortDict, i, setEstimatedEffortDict)}/><br/>
                    <label htmlFor="session-length">Max Session Length (hrs)</label><br/>
                    <input type="number" step="0.1" min="0" id="session-length" name="session-length" onChange={(e) => handleCheckpointChange(e, sessionLengthDict, i, setSessionLengthDict)}/><br/>
                </div>
            );
        }
        setCheckpointForms(formsList);
    }, [numCheckpoints]);


    const createProject = (e) => {
        e.preventDefault();

        let curTime = new Date().getTime();
        let checkpoints = [];

        for (let i = 0; i < numCheckpoints; i++) {
            let startMillis = curTime;
            let endMillis = Date.parse(dueDateDict[i] + " " + dueTimeDict[i]);
            let startMillisStr = startMillis.toString();
            let endMillisStr = endMillis.toString();
            let estimatedEffortMillisStr = Math.round(estimatedEffortDict[i] * 60 * 60 * 1000).toString();
            let sessionLengthMillisStr = Math.round(sessionLengthDict[i] * 60 * 60 * 1000).toString();
            checkpoints.push({
                name: nameDict[i],
                description: descriptionDict[i],
                startTime: startMillisStr,
                endTime: endMillisStr,
                estTime: estimatedEffortMillisStr,
                sessionTime: sessionLengthMillisStr,
                color: color});
            curTime = endMillis;
        }

        console.log(name);
        console.log(description);
        console.log(checkpoints);

        const toSend = {
            name: name,
            description: description,
            checkpoints: checkpoints
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/createproject",
            toSend,
            config
        )
            .then(response => {
                if (response.data["message"] !== "success") {
                    setErrorMessage("Error: no room for project in current schedule");
                } else {
                    setErrorMessage("");
                    props.setShowPopup(false);
                }
                console.log(response.data["message"]);
            })
            .catch(function (error) {
                setErrorMessage("Error: no room for project in current schedule");
                console.log(error.response);
            });
    }


    return (
        <div className="Form ProjectForm" onSubmit={createProject}>
            <form>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title" onChange={(e) => handleChange(e, setName)}/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description" onChange={(e) => handleChange(e, setDescription)}/><br/>
                <label htmlFor="numCheckpoints">Number of Checkpoints</label><br/>
                <input type="text" id="numCheckpoints" name="numCheckpoints" onChange={
                    (e) => handleChange(e, setNumCheckpoints)}/><br/>
                {checkpointForms}
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
                </div><br/>
                <div className="error-message">{errorMessage}</div><br/>
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default ProjectForm;