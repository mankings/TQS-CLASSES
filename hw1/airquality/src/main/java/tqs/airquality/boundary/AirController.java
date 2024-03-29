package tqs.airquality.boundary;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tqs.airquality.model.AirStats;
import tqs.airquality.model.CacheStats;
import tqs.airquality.service.AirService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class AirController {
    @Autowired
    private AirService service;

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String logstr = "[ CONTROLLER ] GET {0}";

    @Operation(summary = "Get air quality stats for today at {location}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Air quality stats for today at {location}", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Location not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{location}/today")
    public ResponseEntity<AirStats> today(@PathVariable(value = "location") String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, logstr, "/" + location + "/today");
        
        AirStats stats = service.today(location);
        HttpStatus code;
        if (stats != null) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(stats, code);
    }

    @Operation(summary = "Get air quality stats forecast at {location}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Air quality stats forecast at {location}", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Location not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{location}/forecast")
    public ResponseEntity<List<AirStats>> forecast(@PathVariable(value = "location") String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, logstr, "/" + location + "/forecast");

        List<AirStats> weekStats = service.forecast(location);
        HttpStatus code;
        if (!weekStats.isEmpty()) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.NOT_FOUND;
        }

        logger.log(Level.INFO, logstr, code.toString() + "/" + location + "/forecast");
        return new ResponseEntity<>(weekStats, code);
    }

    @Operation(summary = "Get air quality stats history at {location}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Air quality stats history at {location}", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Location not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{location}/history")
    public ResponseEntity<List<AirStats>> history(@PathVariable(value = "location") String location) throws URISyntaxException, ParseException, IOException {
        logger.log(Level.INFO, logstr, "/" + location + "/history");
        
        List<AirStats> historyStats = service.history(location);
        HttpStatus code;
        if (!historyStats.isEmpty()) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.NOT_FOUND;
        }

        logger.log(Level.INFO, logstr, code.toString() + "/" + location + "/history");
        return new ResponseEntity<>(historyStats, code);
    }

    @Operation(summary = "Get cache stats")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns cache total requests, hits, and average times", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/cache")
    public ResponseEntity<CacheStats> cache() {
        logger.log(Level.INFO, logstr, "/cache");
        CacheStats stats = service.cacheStats();

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
