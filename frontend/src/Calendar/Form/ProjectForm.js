import './Form.css';
import '../../App.css';
import React, {useState, useEffect} from "react";

function ProjectForm() {
    const [numCheckpoints, setNumCheckpoints] = useState(0);
    const [checkpointForms, setCheckpointForms] = useState(null);

    useEffect(() => {
        let formsList = [];
        for (let i = 0; i < numCheckpoints; i++) {
            let checkpointID = "checkpoint-" + (i + 1).toString();
            formsList.push(
                <div>
                    <label htmlFor={checkpointID + "-title"}>Checkpoint {i + 1} Title</label><br/>
                    <input type="text" id={checkpointID + "-title"} name={checkpointID + "-title"}/><br/>
                    <label htmlFor={checkpointID + "-description"}>Description</label><br/>
                    <input type="text" id={checkpointID + "-description"} name={checkpointID + "-description"}/><br/>
                    <label htmlFor={checkpointID + "-due-date"}>Due Date</label><br/>
                    <input type="date" id={checkpointID + "-due-date"} name={checkpointID + "-due-date"} placeholder={"mm-dd-yyyy"}/><br/>
                    <label htmlFor="due-time">Due Time</label><br/>
                    <input type="time" id="due-time" name="due-time" placeholder={"__:__ AM/PM"}/><br/>
                    <label htmlFor={checkpointID + "-estimated-effort"}>Estimated Effort (hrs)</label><br/>
                    <input type="number" id={checkpointID + "-estimated-effort"} name={checkpointID + "-estimated-effort"}/><br/>
                </div>
            );
        }
        setCheckpointForms(formsList);
    }, [numCheckpoints])

    return (
        <div className="Form ProjectForm">
            <form>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title"/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description"/><br/>
                <label htmlFor="numCheckpoints">Number of Checkpoints</label><br/>
                <input type="text" id="numCheckpoints" name="numCheckpoints" onChange={
                    (e) => setNumCheckpoints(e.target.value)}/><br/>
                {checkpointForms}
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default ProjectForm;