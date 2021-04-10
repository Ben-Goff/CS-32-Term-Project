import './EventDialog.css';
import '../App.css';
import React, {useState, useEffect} from "react";

function EventDialog(props) {
    let [adjustingProgress, setAdjustingProgress] = useState(false);
    let [progress, setProgress] = useState(0);

    const adjustingProgressSlider = (
        <div className="flexbox-section">
            <input type="range" min="0" max="100" value={progress} onChange={(e) => setProgress(e.target.value)}/>
            <div className="flexbox-section">
                Progress: {progress}%
                <button onClick={() => setAdjustingProgress(false)}>Submit</button>
            </div>
        </div>
    )

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
                        <button onClick={() => setAdjustingProgress(true)}>Adjust Progress</button>
                        {adjustingProgress ? adjustingProgressSlider : <div/>}
                        <button style={{"color": "red"}}>Mark As Finished</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EventDialog;