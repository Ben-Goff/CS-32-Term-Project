import '../App.css';
import ProgressNavbar from "./ProgressNavbar";
import ProgressBar from "./ProgressBar";
import React, {useState, useEffect} from 'react'
import axios from "axios";

function Progress() {
    const [displayBars, setDisplayBars] = useState(<div/>);

    useEffect(() => {
        requestProgress();
    }, [])

    const requestProgress = () => {

        const toSend = {
            id: "",
            progress: ""
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
                let newDisplayBars = [];
                let progressInfo = response.data["tasks"];
                for (let i = 0; i < progressInfo.length; i++) {
                    let blockInfo = progressInfo[i];
                    let blockName = blockInfo[0];
                    let blockID = blockInfo[2];
                    let blockProgress = blockInfo[3];
                    let blockColor = blockInfo[4];
                    newDisplayBars.push(<ProgressBar title={blockName} identifier={blockID} progress={blockProgress} color={blockColor}/>)
                }
                setDisplayBars(newDisplayBars);
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }


    return (
        <div className="Progress">
            <ProgressNavbar/>
            {displayBars.length !== 0 ?
                <div className="Bars">
                    {displayBars}
                </div>:
                <div>Add some tasks to track progress!</div>}

        </div>
    );
}

export default Progress;