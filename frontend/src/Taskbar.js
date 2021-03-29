import './Taskbar.css';
import './App.css';


function Taskbar() {
    return (
        <div className="Taskbar">
            <div className="taskbar-contents">
                <div className="task-box">
                    Wake Up
                </div>

                <div className="task-box">
                    Take Shower
                </div>

                <div className="task-box">
                    Brush Teeth
                </div>

                <div className="task-box">
                    Eat Breakfast
                </div>

                <div className="task-box">
                    Eat Lunch
                </div>

                <div className="task-box">
                    Eat Dinner
                </div>

                <div className="task-box">
                    Ponder the Meaninglessness of Life
                </div>

                <div className="task-box">
                    Sleep
                </div>

            </div>
        </div>
    );
}

export default Taskbar;