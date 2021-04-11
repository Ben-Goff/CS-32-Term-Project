import './Popup.css';
import '../App.css';
import React, {useState, useEffect} from "react";
import CommitmentForm from "./Form/CommitmentForm";
import ProjectForm from "./Form/ProjectForm";
import TaskForm from "./Form/TaskForm";

function Popup(props) {
    const commitmentBody = (
        <div>
            <div>Commitment - An event where task/project work is to be made off-limits.</div>
            <CommitmentForm setShowPopup={props.setShowPopup}/>
        </div>
    );
    const taskBody = (
        <div>
            <div>Task - An objective to complete during spare time by a certain due date.</div>
            <TaskForm setShowPopup={props.setShowPopup}/>
        </div>
    );
    const projectBody = (
        <div>
            <div>Project - A more ambitious Task with multiple checkpoints, each with their own due date.</div>
            <ProjectForm setShowPopup={props.setShowPopup}/>
        </div>
    );

    const [commitmentSelected, setCommitmentSelected] = useState(true);
    const [taskSelected, setTaskSelected] = useState(false);
    const [projectSelected, setProjectSelected] = useState(false);
    const [popupBody, setPopupBody] = useState(commitmentBody);

    const selectCommitment = () => {
        setPopupBody(commitmentBody);
        setCommitmentSelected(true);
        setTaskSelected(false);
        setProjectSelected(false);
    }

    const selectTask = () => {
        setPopupBody(taskBody);
        setCommitmentSelected(false);
        setTaskSelected(true);
        setProjectSelected(false);
    }

    const selectProject = () => {
        setPopupBody(projectBody);
        setCommitmentSelected(false);
        setTaskSelected(false);
        setProjectSelected(true);
    }

    if (!props.showPopup) {
        return <div/>;
    }
    return (
        <div className="Popup">
            <div className="Popup-header">
                <div className="Popup-title">New Event</div>
                <button className="close" onClick={() => props.setShowPopup(false)}/>
            </div>
            <div className="Popup-content">
                <div className="Popup-buttons">
                    <button className={commitmentSelected ? "selected" : null} onClick={selectCommitment}>Commitment</button>
                    <button className={taskSelected ? "selected" : null} onClick={selectTask}>Task</button>
                    <button className={projectSelected ? "selected" : null} onClick={selectProject}>Project</button>
                </div>
                <div className="Popup-body">
                    {popupBody}
                </div>
            </div>
        </div>
    );
}

export default Popup;