import './App.css';

import {useState} from 'react';

import logo from './logo.svg';

function App() {
    const [begin, setBegin] = useState(0);
    const [end, setEnd]     = useState(10);
    const [list, setList]   = useState("");
	const [transports, setTransports] = useState([]);

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
			<label>
				Begin:{' '}
			<input type="text" pattern="[0-9]*" value={begin} onChange={
				e => setBegin(e.target.value)}/>
			</label>
			<label>
				End:{' '}
			<input type="text" pattern="[0-9]*" value={end} onChange={
				e => setEnd(e.target.value)}/>
			</label>
			<button onClick={handleClick}>
				Fetch transports
			</button>
			{list}
		</header>
    </div>
    );
    }

    export default App;
