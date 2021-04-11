import './Form.css';
import '../../App.css';
import React, {useState, useEffect} from "react";

function CommitmentForm() {
    const [repeats, setRepeats] = useState(false);
    const repeatsInputs = (
        <div>
            <label htmlFor="repeat-count">Repeat every</label>
            <input type="number" id="repeat-count" name="repeat-count"/>
            <label htmlFor="repeat-type" style={{"display": "none"}}>Repeat type</label>
            <select id="repeat-type" name="repeat-type">
                <option value="day">days</option>
                <option value="week">weeks</option>
                <option value="month">months</option>
            </select>
        </div>)

    return (
        <div className="Form CommitmentForm">
            <form>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title"/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description"/><br/>
                <label htmlFor="start-date">Start Date</label><br/>
                <input type="date" id="start-date" name="start-date" placeholder={"mm-dd-yyyy"}/>
                <label htmlFor="start-time" style={{"display": "none"}}>Start Time</label>
                <input type="time" id="start-time" name="start-time" placeholder={"__:__ AM/PM"}/><br/>
                <label htmlFor="duration">Duration (hrs)</label><br/>
                <input type="number" id="duration" name="duration"/><br/>
                <label htmlFor="repeats">Repeats?</label>
                <input type="checkbox" id="repeats" name="repeats" value="checked" onChange={(e) => setRepeats(e.target.checked)}/><br/>
                {repeats ? repeatsInputs : <div/>}
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default CommitmentForm;