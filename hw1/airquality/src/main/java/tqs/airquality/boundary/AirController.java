package tqs.airquality.boundary;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.airquality.model.AirStats;
import tqs.airquality.service.AirService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/")
public class AirController {
    @Autowired
    private AirService service;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @GetMapping("/{location}/today")
    public ResponseEntity<AirStats> today(@PathVariable(value = "location") String location) throws Exception {
        logger.log(Level.INFO, "[ CONTROLLER ] GET {0}", "/" + location + "/today");
        
        AirStats stats = service.today(location);
        HttpStatus code;
        if (stats != null) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<AirStats>(stats, code);
    }

    @GetMapping("/{location}/week")
    public ResponseEntity<List<AirStats>> week(@PathVariable(value = "location") String location) throws Exception {
        logger.log(Level.INFO, "[ CONTROLLER ] GET {0}", "/" + location + "/week");

        List<AirStats> weekStats = service.week(location);
        HttpStatus code;
        if (weekStats != null) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.NOT_FOUND;
        }

        logger.log(Level.INFO, "[ CONTROLLER ] GET {0}", code.toString() + "/" + location + "/week");
        return new ResponseEntity<List<AirStats>>(weekStats, code);
    }

    @GetMapping("/{location}/history")
    public ResponseEntity<List<AirStats>> history(@PathVariable(value = "location") String location) throws Exception {
        logger.log(Level.INFO, "[ CONTROLLER ] GET {0}", "/" + location + "/history");
        
        List<AirStats> historyStats = service.history(location);
        HttpStatus code;
        if (historyStats != null) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.NOT_FOUND;
        }

        logger.log(Level.INFO, "[ CONTROLLER ] GET {0}", code.toString() + "/" + location + "/history");
        return new ResponseEntity<List<AirStats>>(historyStats, code);
    }

    // TODO cache stats endpoint
}
