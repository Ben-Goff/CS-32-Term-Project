import './Taskbar.css';
import './App.css';

function Taskbar(props) {
    let taskBlocks = props.taskBlocks;
    let monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];


    // CREDIT: https://stackoverflow.com/questions/8888491/how-do-you-display-javascript-datetime-in-12-hour-am-pm-format
    function formatAMPM(date) {
        let hours = date.getHours();
        let minutes = date.getMinutes();
        let ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12; // the hour '0' should be '12'
        minutes = minutes < 10 ? '0'+minutes : minutes;
        return hours + ':' + minutes + ' ' + ampm;
    }

    // console.log(taskBlocks)
    return (
        <div className="Taskbar">
            <div className="space-filler"/>
            <div className="taskbar-contents">
                {

                    taskBlocks.map(row => {
                    let attrs = row.props;
                    let thisYear = new Date().getFullYear();
                    let thisMonth = new Date().getMonth();
                    let thisDate = new Date().getDate();
                    let now = new Date().getTime();

                    if (attrs.start.getFullYear() === thisYear && attrs.start.getMonth() === thisMonth && attrs.start.getDate() === thisDate && attrs.end.getTime() >= now) {
                        let boxClassName = (attrs.start.getTime() <= now && attrs.end.getTime() >= now) ? "task-box highlighted" : "task-box"
                        return <div className={boxClassName} style={{"backgroundColor": attrs.color}} onClick={attrs.onClick}>
                            <div className="contents">
                                {monthNames[attrs.start.getMonth()] + " " + attrs.start.getDate() + " "}
                                {formatAMPM(attrs.start)}-{formatAMPM(attrs.end)}<br/>
                                {attrs.title}<br/><br/>
                                <div className="task-box-desc">{attrs.desc}</div>
                            </div>
                        </div>
                    } else {
                        return <div/>
                    }
                })}
            </div>
        </div>
    );
}

export default Taskbar;