import './Block.css';
import '../App.css';

function Block(props) {


    let startMinutes = props.start.getHours() * 60 + props.start.getMinutes();
    let endMinutes = props.end.getHours() * 60 + props.end.getMinutes();
    let durationMinutes = endMinutes - startMinutes;

    return (
        <div className="Block" style={
            {"height": (durationMinutes) + 'px',
                "top": (startMinutes) + 'px',
                "background": props.color}
        }>
            <div className="contents">
                {props.text}
            </div>
        </div>
    );
}

export default Block;