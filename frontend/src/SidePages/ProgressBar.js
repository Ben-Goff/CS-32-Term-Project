import './Progess.css';
import '../App.css';
import React, {useState} from "react";
import axios from "axios";

function ProgressBar(props) {
    let [adjustingProgress, setAdjustingProgress] = useState(false);
    let [newProgress, setNewProgress] = useState(0);

    const showProgressSlider = () => {
        setAdjustingProgress(true);
    }

    const submitNewProgress = (e) => {
        e.preventDefault();
        const toSend = {
            id: props.identifier,
            progress: (newProgress / 100).toString()
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/update",
            toSend,
            config
        )
            .then(response => {
                console.log("adjusted progress to " + newProgress / 100)
                setAdjustingProgress(false);
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }


    return (
        <div className="ProgressBar">
            <div className="progress-title">
                {props.title}
            </div>
            <div className="progress-back">
                <div className="progress-front" style={{"width": (props.progress * 100) + "%", "backgroundColor": props.color}}/>
            </div>
            <div className="progress-bar-text-left">
                {adjustingProgress ?
                    <div>
                        <form className="adjust-progress-form" onSubmit={submitNewProgress}>
                            <label htmlFor="new-progress" style={{"display": "hidden"}}>New progress: </label>
                            <input type="number" min="0" max="100" onChange={(e) => setNewProgress(e.target.value)} style={{"width": "30px"}}/>%
                            <input id="progress-submit" type="submit" value="Set new progress"/>
                        </form>
                    </div> :
                    <div className="progress-label">
                        {props.progress * 100}% done <button className="adjust-progress-button" onClick={showProgressSlider}>Adjust</button>
                    </div>}
            </div>
        </div>
    );
}

export default ProgressBar;