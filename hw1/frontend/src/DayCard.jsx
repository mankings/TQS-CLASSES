function DayCard({ today }) {
  const levels = ["Good", "Fair", "Moderate", "Poor", "Very Poor", "-"]
  var aqi = today.aqi - 1 > levels.length ? levels.length - 1 : today.aqi - 1
  aqi = aqi < 0 ? 0 : aqi

  var values = {}
  for (let key in today.values) {
    values[key] = today.values[key].toString() + " µg/m3";
  }
  
  return (
    <div className="card w-96 bg-base-100 mx-auto">
      <div className="card-body items-center">
        <h2 className="card-title">{today.location}</h2>
        <table className="table table-compact w-72">
          <tbody>
            <tr>
              <td>AQI</td>
              <td>{levels[aqi]}</td>
            </tr>
            <tr>
              <td>NO2</td>
              <td>{values.no2}</td>
            </tr>
            <tr>
              <td>O3</td>
              <td>{values.o3}</td>
            </tr>
            <tr>
              <td>PM10</td>
              <td>{values.pm10}</td>
            </tr>
            <tr>
              <td>SO2</td>
              <td>{values.so2}</td>
            </tr>
            <tr>
              <td>CO</td>
              <td>{values.co}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default DayCard

