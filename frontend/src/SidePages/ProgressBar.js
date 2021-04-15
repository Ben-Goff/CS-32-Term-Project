import './Progess.css';
import '../App.css';
import React, {useState} from "react";

function ProgressBar(props) {
    let [adjustingProgress, setAdjustingProgress] = useState(false);

    const showProgressSlider = () => {
        setAdjustingProgress(true);
    }

    const submitNewProgress = () => {
        setAdjustingProgress(false);
    }

    return (
        <div className="ProgressBar">
            <div className="progress-title">
                {props.title}
            </div>
            <div className="progress-back">
                <div className="progress-front" style={{"backgroundColor": props.color}}/>
            </div>
            <div className="progress-bar-text-left">
                {adjustingProgress ?
                    <div>
                        <form className="adjust-progress-form" onSubmit={submitNewProgress}>
                            <label htmlFor="new-progress" style={{"display": "hidden"}}>New progress: </label>
                            <input type="text" style={{"width": "20px"}}/>%
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