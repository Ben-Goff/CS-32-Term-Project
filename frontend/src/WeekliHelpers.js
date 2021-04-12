import {useEffect, useRef} from "react";
import axios from "axios";

/**
 * Helper function class for Weekli
 */

// Credit: https://stackoverflow.com/questions/4156434/javascript-get-the-first-day-of-the-week-from-current-date
export function getMonday(d) {
    d = new Date(d);
    let day = d.getDay(),
        diff = d.getDate() - day + (day === 0 ? -6:1); // adjust when day is sunday
    return new Date(d.setDate(diff));
}

/**
 * A function found online that is basically setInterval but works the way it's supposed to,
 * as the native setInterval function was not working as expected.
 *
 * https://overreacted.io/making-setinterval-declarative-with-react-hooks/
 * @param callback The method to call at every interval
 * @param delay The amount of milliseconds between each interval
 */
export function useInterval(callback, delay) {
    const savedCallback = useRef();

    // Remember the latest callback.
    useEffect(() => {
        savedCallback.current = callback;
    }, [callback]);

    // Set up the interval.
    useEffect(() => {
        function tick() {
            savedCallback.current();
        }
        if (delay !== null) {
            let id = setInterval(tick, delay);
            return () => clearInterval(id);
        }
    }, [delay]);
}

export const getSchedule = async(displayMonday) => {

    const toSend = {
        start: displayMonday.getTime(),
        end: (displayMonday.getTime() + (86400000 * 7)) //Number of milliseconds in a week
    };

    // console.log(toSend)

    let config = {
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
        }
    };

    let results = await axios.post(
        "http://localhost:4567/schedule",
        toSend,
        config
    );

    return results;
}