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
                <input type="text" id="start-date" name="start-date"/><br/>
                <label htmlFor="start-date">End Date</label><br/>
                <input type="text" id="end-date" name="end-date"/><br/>
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default CommitmentForm;