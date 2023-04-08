import DayCard from "./DayCard"

function History({ days }) {
    return (
        <div>
            <div className="carousel mx-auto w-96 bg-base-200">
                <div id="item1" className="carousel-item">
                    <DayCard today={days[0]} values={days[0].values} />
                </div>
                <div id="item2" className="carousel-item">
                    <DayCard today={days[1]} values={days[1].values} />
                </div>
                <div id="item3" className="carousel-item">
                    <DayCard today={days[2]} values={days[2].values} />
                </div>
                <div id="item4" className="carousel-item">
                    <DayCard today={days[3]} values={days[3].values} />
                </div>
                <div id="item5" className="carousel-item">
                    <DayCard today={days[4]} values={days[4].values} />
                </div>
                <div id="item6" className="carousel-item">
                    <DayCard today={days[5]} values={days[5].values} />
                </div>
            </div>
            <div className="flex justify-center py-2 gap-2">
                <a href="#item1" className="btn btn-xs">{days[0].date.substring(0, 10)}</a>
                <a href="#item2" className="btn btn-xs">{days[1].date.substring(0, 10)}</a>
                <a href="#item3" className="btn btn-xs">{days[2].date.substring(0, 10)}</a>
                <a href="#item4" className="btn btn-xs">{days[3].date.substring(0, 10)}</a>
                <a href="#item5" className="btn btn-xs">{days[4].date.substring(0, 10)}</a>
                <a href="#item6" className="btn btn-xs">{days[5].date.substring(0, 10)}</a>
            </div>
        </div>
    )
}

export default History