import './Day.css';
import '../App.css';

function Day(props) {

    let hours = [];
    for (let i = 0; i < 24; i++) {
        hours.push(<div className="Hour"/>);
    }

    return (
        <div className="Day">
            <hr className="timeIndicator" style={
                {"height": 4 + 'px',
                    "top": 2 + 'px'}
            }/>
            <div className="block-container">
                {props.blocks}
            </div>
            {hours}
        </div>
    );

}

export default Day;