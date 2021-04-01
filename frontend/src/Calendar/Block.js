import './Block.css';
import '../App.css';

function Block(props) {

    return (
        <div className="Block" style={
            {height: props.durationMinutes + 'px',
                top: props.startMinutes + 'px',
                left: 2 + props.day * 127 + 'px',
                background: props.color}
        }>{props.text}</div>
    );
}

export default Block;