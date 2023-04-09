import { useEffect, useState } from 'react'
import axios from 'axios';
import DayCard from './DayCard';
import './App.css'
import Forecast from './Forecast';
import History from './History';


function App() {
  const [show, setShow] = useState(0);

  const [location, setLocation] = useState('Lisbon');

  const [today, setToday] = useState({})
  const getToday = (location) => {
    const uri = 'http://localhost:8080/api/' + location + '/today'
    axios.get(uri).then((response) => {
      setToday(response.data)
      console.log(response.data)
      setShow(0)
    }).catch((error) => {
      setToday(null)
      console.log(error)
    })
  }

  const [forecast, setForecast] = useState([])
  const getForecast = (location) => {
    const uri = 'http://localhost:8080/api/' + location + '/forecast'
    axios.get(uri).then((response) => {
      setForecast(response.data)
      console.log(response.data)
      setShow(1)
    }).catch((error) => {
      setForecast([])
      console.log(error)
    })
  }

  const [history, setHistory] = useState([])
  const getHistory = (location) => {
    const uri = 'http://localhost:8080/api/' + location + '/history'
    axios.get(uri).then((response) => {
      setHistory(response.data)
      console.log(response.data)
      setShow(2)
    }).catch((error) => {
      setHistory([])
      console.log(error)
    })
  }

  useEffect(() => {
    getToday(location);
  }, [])

  return (
    <div className="App bg-base-200 min-h-screen">
      <div className="hero min-w-screen pt-20 pb-5">
        <div className="hero-content text-center">
          <div className="max-w-lg">
            <h1 className="text-5xl font-bold">AirQuality by Mankings</h1>
            <input type="text" placeholder="Location" value={location} className="input input-bordered w-full max-w-xs my-4" onChange={(e) => setLocation(e.target.value)} />
            <div className="space-x-4">
              <button className="btn btn-primary" id="today" onClick={() => getToday(location)}>Today</button>
              <button className="btn btn-primary" id="forecast" onClick={() => getForecast(location)}>Forecast</button>
              <button className="btn btn-primary" id="history" onClick={() => getHistory(location)}>History</button>
            </div>
          </div>
        </div>
      </div>

      {show == 0 && today && today.values && (
        <DayCard today={today} values={today.values} />
      )}

      {show == 1 && forecast && forecast.length > 0 && (
        <Forecast days={forecast} />
      )}

      {show == 2 && history && history.length > 0 && (
        <History days={history} />
      )}
    </div>
  )
}

export default App
