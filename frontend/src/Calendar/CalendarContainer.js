import './CalendarContainer.css';
import '../App.css';
import Calendar from "./Calendar";

function CalendarContainer() {

    let hourLabels = [];
    for (let i = 0; i < 24; i++) {
        if (i === 0) {
            hourLabels.push("12am");
        } else if (i <= 12) {
            hourLabels.push(i + "am");
        } else if (i === 12) {
            hourLabels.push("12pm");
        } else if (i >= 12) {
            hourLabels.push(i - 12 + "pm");
        }
    }

    let daysOfWeek = ["Su", "M", "T", "W", "Th", "F", "Sa"]
    let d = new Date();
    let circlePosition = d.getDay();
    console.log(circlePosition)

    return (
        <div className="CalendarContainer">
            <div className="DayLabels">

                {daysOfWeek.map((item, index) => (
                    <div>
                        {index == circlePosition &&
                        <div className="TodayCircle"></div>}
                        {daysOfWeek[index]}
                    </div>
                    )
                )}
                <div>21</div>
                <div>22</div>
                <div>23</div>
                <div>24</div>
                <div>25</div>
                <div>26</div>
                <div>27</div>
            </div>
            <div className="HourLabels">
                <div></div>
                {hourLabels.map((item, index) => (
                    <div>
                        {hourLabels[index]}
                    </div>
                ))}
            </div>
            <Calendar/>
            <div className="BottomFiller"/>
        </div>
    );
}

export default CalendarContainer;