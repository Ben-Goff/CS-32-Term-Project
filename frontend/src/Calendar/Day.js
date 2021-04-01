import './Day.css';
import '../App.css';

function Day() {

    let hours = [];
    for (let i = 0; i < 24; i++) {
        hours.push(<div className="Hour"></div>);
    }

    return (
        <div className="Day">
            {hours}
        </div>
    );

}

export default Day;