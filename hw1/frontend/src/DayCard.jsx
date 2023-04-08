function DayCard({today, values}) {
    const levels = ["Good", "Fair", "Moderate", "Poor", "Very Poor"]
    return (
        <div className="card w-96 bg-base-100 mx-auto">
          <div className="card-body items-center">
            <h2 className="card-title">{today.location}</h2>
            <table className="table table-compact w-72">
            <tbody>
              <tr>
                <td>AQI</td>
                <td>{levels[today.aqi - 1]}</td>
              </tr>
              <tr>
                <td>NO2</td>
                <td>{values.no2} µg/m3</td>
              </tr>
              <tr>
                <td>O3</td>
                <td>{values.o3} µg/m3</td>
              </tr>
              <tr>
                <td>PM10</td>
                <td>{values.pm10} µg/m3</td>
              </tr>
              <tr>
                <td>SO2</td>
                <td>{values.so2} µg/m3</td>
              </tr>
              <tr>
                <td>CO</td>
                <td>{values.co} µg/m3</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
    )
}

export default DayCard

