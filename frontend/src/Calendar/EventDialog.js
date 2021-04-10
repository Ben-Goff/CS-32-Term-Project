import './EventDialog.css';
import '../App.css';

function EventDialog(props) {
    let block = props.clickedBlock;
    if (block === null) {
        return <div/>;
    }

    return (
        <div className="EventDialog" style={
            {
                "left": Math.min(window.innerWidth - 575, props.clickedX) + "px",
                "top": Math.min(window.innerHeight - 360, props.clickedY) + "px",
                "height": "300px",
                "background-color": block.props.color
            }}>
            <button className="closeButton" onClick={() => props.setClickedBlock(null)}/>
            <div className="Title">
                {block.props.title}
            </div>
            <div className="ContentContainer">
                <div className="Content">
                    {block.props.desc}
                    <div className="DialogButtons">
                        <button>Adjust Progress</button><br/>
                        <button style={{"color": "red"}}>Mark As Finished</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EventDialog;