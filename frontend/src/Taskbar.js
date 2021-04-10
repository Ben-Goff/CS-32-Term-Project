import './Taskbar.css';
import './App.css';


function Taskbar(props) {
    let taskBlocks = props.taskBlocks;
    let monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    return (
        <div className="Taskbar">
            <div className="space-filler"/>
            <div className="taskbar-contents">
                {taskBlocks.map(row => {
                    let attrs = row.props;
                    return <div className="task-box" style={{"background-color": attrs.color}}>
                        <div className="contents">
                            {monthNames[attrs.start.getMonth()] + " " + attrs.start.getDate() + " "}
                            {attrs.start.getHours() + ":" + attrs.start.getMinutes() + "-" + attrs.end.getHours() + ":" + attrs.end.getMinutes()}<br/>
                            {attrs.title}<br/><br/>
                            <div className="task-box-desc">{attrs.desc}</div>
                        </div>
                    </div>
                })}
            </div>
        </div>
    );
}

export default Taskbar;