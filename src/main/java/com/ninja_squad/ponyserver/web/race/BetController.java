package com.ninja_squad.ponyserver.web.race;

import com.ninja_squad.ponyserver.web.BadRequestException;
import com.ninja_squad.ponyserver.web.Database;
import com.ninja_squad.ponyserver.web.pony.Pony;
import com.ninja_squad.ponyserver.web.security.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller allowing the user to place or remove bets on ponies in races. Only one pony of a race can have a bet
 * from a given user. Placing a bet on another pony removes the previous bet, if any.
 * @author JB Nizet
 */
@Api("Place and cancel bets")
@RestController
@RequestMapping(value = "/api/races/{raceId}/bets", produces = MediaType.APPLICATION_JSON_VALUE)
public class BetController {

    @Autowired
    private Database database;

    @Autowired
    private CurrentUser currentUser;

    /**
     * Places a bet. If the race has already started, a 400 response with an error message is sent.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Places a bet on a pony in a race")
    @ApiResponses(@ApiResponse(code = 400, message = "The race doesn't accept bets, or the pony is not part of the race"))
    public RaceWithBet placeBet(HttpServletResponse response, @RequestBody PonyInRace ponyInRace, @PathVariable("raceId") Long raceId) throws IOException {
        ponyInRace.setRaceId(raceId);
        Race race = database.getRace(raceId);
        Pony betPony = database.getPony(ponyInRace.getPonyId());
        if (race.getStatus() != RaceStatus.READY) {
            throw new BadRequestException("The race doesn't accept bets anymore");
        }
        if (!race.getPonies().contains(betPony)) {
            throw new BadRequestException("The pony is not part of the race");
        }
        database.addBet(currentUser.getLogin(), ponyInRace);
        return new RaceWithBet(race, betPony.getId());
    }

    /**
     * Deletes a bet on a race.
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Cancels the current bet the given race")
    @ApiResponses(@ApiResponse(code = 400, message = "The race doesn't accept bets"))
    public void deleteBet(@PathVariable("raceId") Long raceId) {
        Race race = database.getRace(raceId);
        if (race.getStatus() != RaceStatus.READY) {
           throw new BadRequestException("The race doesn't accept bets anymore");
        }

        database.deleteBet(currentUser.getLogin(), raceId);
    }
}
