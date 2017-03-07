package com.ninja_squad.ponyserver.web.race;

import com.ninja_squad.ponyserver.web.BadRequestException;
import com.ninja_squad.ponyserver.web.Database;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by blefoulgoc on 3/7/17.
 */
@Api("Boost a pony")
@RestController
@RequestMapping(value = "api/races", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoostController {

    @Autowired
    private Database database;

    /**
     * Boost a pony
     */
    @RequestMapping(value = "/{raceId}/boosts", method = RequestMethod.POST)
    @ApiOperation("Boost a pony in a race")
    @ApiResponses(@ApiResponse(code = 400, message = "Cannot boost this pony, not running in this race"))
    private void boost(@PathVariable("raceId") Long raceId, @RequestBody PonyInRace ponyInRace) {
        Race race = database.getRace(raceId);
        if (race.getStatus() != RaceStatus.STARTED) {
            throw new BadRequestException("The pony is not running");
        }
        database.boostPony(ponyInRace.getPonyId());
    }

}
