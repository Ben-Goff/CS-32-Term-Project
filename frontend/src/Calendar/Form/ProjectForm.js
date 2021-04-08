import './Form.css';
import '../../App.css';

function ProjectForm() {
    return (
        <div className="Form ProjectForm">
            <form>
                <label htmlFor="title">Title</label><br/>
                <input type="text" id="title" name="title"/><br/>
                <label htmlFor="description">Description</label><br/>
                <input type="text" id="description" name="description"/><br/>
                <button id="add-checkpoint" type="button" onClick={() => console.log("Pressed" +
                    " add checkpoint")}>+ Add Checkpoint</button>
                <input id="submit" type="submit" value="Create"/>
            </form>
        </div>
    );
}

export default ProjectForm;