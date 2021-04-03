import './Block.css';
import '../App.css';

function Block(props) {

    return (
        <div className="Block" style={
            {"height": props.durationMinutes + 'px',
                "top": props.startMinutes + 'px',
                "background": props.color}
        }>{props.text}</div>
    );
}

export default Block;