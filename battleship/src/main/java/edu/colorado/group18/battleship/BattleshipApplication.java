package edu.colorado.group18.battleship;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@SpringBootApplication
@RestController
public class BattleshipApplication {

	HashMap<String, Game> games = new HashMap<String, Game>();

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);
	}

	public Game verifyGame(String room) {
		return this.games.get(room);
	}

	public AbilityPlayer verifyPlayer(String room, int userID) {
		Game game = verifyGame(room);
		AbilityPlayer player = null;

		if (userID == 1) {
			player = game.P1;
		} else if (userID == 2) {
			player = game.P2;
		}

		return player;
	}

	@GetMapping("/create")
	public String create() {
		Game newGame = new Game();

		String room = UUID.randomUUID().toString().replace("-", "");

		this.games.put(room, newGame);

		System.out.printf("game created: %s\n\n", room);
		return String.format("{\"player\": %s, \"room\": \"%s\"}", 1, room);
	}

	@GetMapping("/join")
	public String join(@RequestParam(value = "room", defaultValue = "") String room) {
		if (room.length() == 0) {
			return "Room ID Required";
		}

		Game game = verifyGame(room);
		if (game == null) {
			return "Room Not Found";
		}

		System.out.printf("game joined: %s\n\n", room);
		return String.format("{\"player\": %s, \"room\": \"%s\"}", 2, room);
	}

	private static class PlaceShipParams {
		int ship;
		int y;
		int x;
		char orient;
		boolean submerged;

		@JsonCreator
		public PlaceShipParams(@JsonProperty("ship") int ship, @JsonProperty("y") int y, @JsonProperty("x") int x, @JsonProperty("orient") char orient, @JsonProperty("submerged") boolean submerged) {
			this.ship = ship;
			this.y = y;
			this.x = x;
			this.orient = orient;
			this.submerged = submerged;
		}
	}

	private static class SetFleetParams {
		String room;
		int player;
		PlaceShipParams[] fleet;
		@JsonCreator
		public SetFleetParams(@JsonProperty("room") String room, @JsonProperty("player") int player, @JsonProperty("fleet") PlaceShipParams[] fleet) {
			this.room = room;
			this.player = player;
			this.fleet = fleet;
		}
	}

	@PostMapping("/set-fleet") //@RequestBody(value = "room") String room, @RequestBody(value = "player") int userID, @RequestBody(value = "placedFleet") String json
	public String setFleet(@RequestBody String bodyJson) {
			try {
				// convert JSON string to list of PlaceShipParams
				//PlaceShipParams[] paramsList = new ObjectMapper().readValue(json, new TypeReference<PlaceShipParams[]>(){});
				SetFleetParams body = new ObjectMapper().readValue(bodyJson, SetFleetParams.class);
				AbilityPlayer player = verifyPlayer(body.room, body.player);
				if (player != null) {
					for (PlaceShipParams p : body.fleet) {
						player.placeShip(player.getFleet()[p.ship], p.y, p.x, p.orient, p.submerged);
					}
					return "Fleet added to board";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "Error setting fleet";
			}
			return "Error setting fleet";
		}

	@GetMapping("/")
	public String update(@RequestParam(value = "room") String room, @RequestParam(value = "player") int userID) {
		AbilityPlayer player = verifyPlayer(room, userID);
		return String.format("{\"your_turn\": %b, \"data\": %s}", verifyGame(room).isTurn(userID), player.toJson());
	}

	@GetMapping("/player")
	public String getPlayer(@RequestParam(value = "room", defaultValue = "") String room, @RequestParam(value = "player") int userID) {
		AbilityPlayer player = verifyPlayer(room, userID);
		//player.placeShip(player.getFleet()[2], 0, 0, 'h'); //this line can be used to test with ShipCells
		return player.toJson();
	}
}
