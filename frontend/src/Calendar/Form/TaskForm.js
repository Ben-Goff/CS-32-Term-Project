import './Form.css';
import '../../App.css';
import React from "react";

function TaskForm() {
    return (
        <div className="Form TaskForm">
            <form>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title"/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description"/><br/>
                <label htmlFor="due-date">Due Date</label><br/>
                <input type="date" id="due-date" name="due-date" placeholder={"mm-dd-yyyy"}/>
                <label htmlFor="due-time" style={{"display": "none"}}>Due Time</label>
                <input type="time" id="due-time" name="due-time" placeholder={"__:__ AM/PM"}/><br/>
                <label htmlFor="estimated-effort">Estimated Effort (hrs)</label><br/>
                <input type="number" id="estimated-effort" name="estimated-effort"/><br/>
                <label htmlFor="session-length">Max Session Length (hrs)</label><br/>
                <input type="number" id="session-length" name="session-length"/><br/>
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default TaskForm;