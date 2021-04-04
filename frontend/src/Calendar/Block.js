import './Block.css';
import '../App.css';

function Block(props) {

    return (
        <div className="Block" style={
            {"height": props.durationMinutes + 'px',
                "top": (props.startMinutes - 2) + 'px',
                "background": props.color}
        }>
            <div className="contents">
                {props.text}
            </div>
        </div>
    );
}

export default Block;