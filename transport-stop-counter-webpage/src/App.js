import './App.css';

import {useState} from 'react';

function App() {
    const [begin, setBegin] = useState(0);
    const [end, setEnd]     = useState(10);
    const [list, setList]   = useState("");

    function handleClick() {
        const init = { method : 'GET' };

        const myRequest = new Request("http://localhost:8080/transports?begin=" + begin + "&end=" + end, init);
        fetch(myRequest)
                .then(function(response) { return response.json(); })
                .catch(function(err) { console.log("Error: " + err) })
            .then(function(body) {
                setList(body.lines.map(line =>
                    <div key={line.index}>
                        <table>
                        <caption>
                        index: {line.index}, {line.lineNumber} {line.transportCode}, Number of stops {line.stops.length}
                        </caption>
                        <tr>
                            <th scope="col">StopId</th>
                            <th scope="col">StopName</th>
                            <th scope="col">StopAre</th>
                        </tr>
                            {line.stops.map(stop =>
                                <tr>
                                    <th scope="row">{stop.id}</th>
                                    <td>{stop.name}</td>
                                    <td>{stop.areaNumber}</td>
                                </tr>
                            )}
                        </table>
                    </div>
                ))
            });
    }


    return (
    <div className="App">
        <header className="App-header">
            <h1>TransportToStopFrequencePage</h1>
            <div>
            <label>
                Begin
            </label>
            <input type="number" value={begin} onChange={
                e => setBegin(e.target.value)}/>
            <label>
                End
            </label>
            <input type="number" value={end} onChange={
                e => setEnd(e.target.value)}/>
            <button onClick={handleClick}>
                Fetch transports
            </button>
            </div>
            {list}
        </header>
    </div>
    );
    }

    export default App;
