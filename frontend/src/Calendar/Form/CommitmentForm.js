import './Form.css';
import '../../App.css';

function CommitmentForm() {
    return (
        <div className="Form CommitmentForm">
            <form>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title"/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description"/><br/>
                <label htmlFor="start-date">Start Date</label><br/>
                <input type="date" id="start-date" name="start-date" placeholder={"mm-dd-yyyy"}/><br/>
                <label htmlFor="start-time">Start Time</label><br/>
                <input type="time" id="start-time" name="start-time" placeholder={"__:__ AM/PM"}/><br/>
                <label htmlFor="end-date">End Date</label><br/>
                <input type="date" id="end-date" name="end-date" placeholder={"mm-dd-yyyy"}/><br/>
                <label htmlFor="end-time">End Time</label><br/>
                <input type="time" id="end-time" name="end-time" placeholder={"__:__ AM/PM"}/><br/>
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default CommitmentForm;