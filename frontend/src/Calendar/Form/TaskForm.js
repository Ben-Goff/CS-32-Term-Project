import './Form.css';
import '../../App.css';

function TaskForm() {
    return (
        <div className="Form TaskForm">
            <form>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title"/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description"/><br/>
                <label htmlFor="due-date">Due Date</label><br/>
                <input type="text" id="due-date" name="due-date"/><br/>
                <label htmlFor="estimated-effort">Estimated Effort</label><br/>
                <input type="text" id="estimated-effort" name="estimated-effort"/><br/>
                <label htmlFor="priority">Priority</label><br/>
                <select id="priority" name="priority">
                    <option value="" disabled selected>Select...</option>
                    <option value="high">High: concentrate effort early to get done as soon as possible</option>
                    <option value="medium">Medium: spread out effort for maximum balance</option>
                    <option value="low">Low: push towards end to make room for other things</option>
                </select><br/>
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default TaskForm;