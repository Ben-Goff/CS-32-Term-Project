import './EventDialog.css';
import '../App.css';
import React, {useState, useEffect} from "react";
import axios from "axios";

function EventDialog(props) {
    let [adjustingProgress, setAdjustingProgress] = useState(false);
    let [progress, setProgress] = useState(0);

    useEffect(() => {
        if (props.clickedBlock == null) {
            return;
        }
        setProgress(props.clickedBlock.props.progress / (props.clickedBlock.props.end.getTime() - props.clickedBlock.props.start.getTime()) * 100)
    }, [adjustingProgress, props.clickedBlock])

    useEffect(() => {
        setAdjustingProgress(false);
    }, [props.clickedBlock])


    const adjustingProgressSlider = (
        <div className="flexbox-section">
            <input type="range" min="0" max="100" value={progress} onChange={(e) => setProgress(e.target.value)}/>
            <div className="flexbox-section">
                Progress: {progress}%
                <button onClick={() => setAdjustingProgress(false)}>Submit</button>
            </div>
        </div>
    )

    if (props.clickedBlock === null) {
        return <div/>;
    }

    const showProgressSlider = () => {
        setAdjustingProgress(true);
    }

    const deleteBlock = () => {
        console.log("deleting block")
        const toSend = {
            id: props.clickedBlock.props.identifier
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        let postURL = props.clickedBlock.props.isCommitment ? "http://localhost:4567/deletecommitment" : "http://localhost:4567/deletetask"

        axios.post(
            postURL,
            toSend,
            config
        )
            .then(response => {
                // console.log(response.data);
                props.setUpdateFlag(!props.updateFlag);
                props.setClickedBlock(null);
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }

    return (
        <div className="EventDialog" style={
            {
                "left": Math.min(window.innerWidth - 575, props.clickedX) + "px",
                "top": Math.min(window.innerHeight - 360, props.clickedY) + "px",
                "height": "300px",
                "background-color": props.clickedBlock.props.color
            }}>
            <button className="closeButton" onClick={() => props.setClickedBlock(null)}/>
            <div className="Title">
                {props.clickedBlock.props.title}
            </div>
            <div className="ContentContainer">
                <div className="Content">
                    {props.clickedBlock.props.isCommitment ? <div/> : <div>{"progress: " + progress}%<br/></div>}
                    {props.clickedBlock.props.desc}
                    <div className="DialogButtons">
                        {props.clickedBlock.props.isCommitment ? <div/> : <button onClick={showProgressSlider}>Adjust Progress</button>}
                        {(adjustingProgress && !props.clickedBlock.props.isCommitment) ? adjustingProgressSlider : <div/>}
                        <button style={{"color": "red"}} onClick={deleteBlock}>Mark As Finished</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EventDialog;