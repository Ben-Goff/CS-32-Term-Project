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

    if (props.clickedBlock === null) {
        return <div/>;
    }

    const showProgressSlider = () => {
        setAdjustingProgress(true);
    }

    const submitNewProgress = () => {
        setAdjustingProgress(false);
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
                "backgroundColor": props.clickedBlock.props.color
            }}>
            <button className="closeButton" onClick={() => props.setClickedBlock(null)}/>
            <div className="Title">
                {props.clickedBlock.props.title}
            </div>
            <div className="ContentContainer">
                <div className="Content">
                    {props.clickedBlock.props.isCommitment ? <div/> : (

                        <div>
                            <div className="progress-bar-back">
                                <div className="progress-bar-front" style={{"backgroundColor": props.clickedBlock.props.color}}/>
                            </div>
                            <div className="progress-bar-text">
                                {adjustingProgress ?
                                    <div>
                                        <form className="adjust-progress-form" onSubmit={submitNewProgress}>
                                            <label htmlFor="new-progress" style={{"display": "hidden"}}>New progress: </label>
                                            <input type="text" style={{"width": "20px"}}/>%
                                            <input id="progress-submit" type="submit" value="Set new progress"/>
                                        </form>
                                    </div> :
                                    <div className="progress-label">
                                        {progress * 100}% done <button className="adjust-progress-button" onClick={showProgressSlider}>Adjust</button>
                                    </div>}
                            </div>
                        </div>)
                        }

                    {props.clickedBlock.props.desc}
                    <div className="DialogButtons">
                        <button style={{"color": "red"}} onClick={deleteBlock}>Delete all blocks for this event</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EventDialog;