import './Taskbar.css';
import './App.css';


function Taskbar() {
    return (
        <div className="Taskbar">
            <div className="space-filler"/>
            <div className="taskbar-contents">
                <div className="task-box">
                    <div className="contents">
                        Wake Up
                    </div>
                </div>

                <div className="task-box">
                    <div className="contents">
                        Take Shower
                    </div>
                </div>

                <div className="task-box">
                    <div className="contents">
                        Brush Teeth
                    </div>
                </div>

                <div className="task-box">
                    <div className="contents">
                    Eat Breakfast
                    </div>
                </div>

                <div className="task-box">
                    <div className="contents">
                        Eat Lunch
                    </div>
                </div>

                <div className="task-box">
                    <div className="contents">
                        Eat Dinner
                    </div>
                </div>

                <div className="task-box">
                    <div className="contents">
                        Ponder the Meaninglessness of Life
                    </div>
                </div>

                <div className="task-box">
                    <div className="contents">
                        Sleep
                    </div>
                </div>

            </div>
        </div>
    );
}

export default Taskbar;