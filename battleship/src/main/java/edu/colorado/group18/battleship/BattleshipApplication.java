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
		Game retVal = this.games.get(room);
		if(retVal == null) {
			System.out.println("Error finding room " + room);
		}
		return retVal;
	}

	public AbilityPlayer verifyPlayer(String room, int userID) {
		Game game = verifyGame(room);
		AbilityPlayer player = null;

		if (game != null) {
			if (userID == 1) {
				player = game.P1;
			} else if (userID == 2) {
				player = game.P2;
			}
		}

		if (player == null) {
			System.out.println("Error finding player " + userID);
		}

		return player;
	}

	public int getOpponentID(int userID) {
		int oppID = -1;
		if (userID == 1) {
			oppID = 2;
		}
		else {
			oppID = 1;
		}
		return oppID;
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

	@PostMapping("/set-fleet")
	public String setFleet(@RequestBody String bodyJson) {
			try {
				// convert JSON string to list of PlaceShipParams
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

	@GetMapping("/update")
	public String update(@RequestParam(value = "room") String room, @RequestParam(value = "player") int userID) {
		AbilityPlayer player = verifyPlayer(room, userID);
		AbilityPlayer opponent = verifyPlayer(room, getOpponentID(userID));
		//winStatus: -1 if game is in progress, 0 if you lost, 1 if you won
		return String.format("{\"winStatus\": %d, \"yourTurn\": %b, \"your_data\": %s, \"opponent_data\": %s}", verifyGame(room).getWinStatus(userID), verifyGame(room).isTurn(userID), player.toJson(), opponent.toJson());
	}

	private static class LocationParams {
		String room;
		int player;
		int x;
		int y;

		@JsonCreator
		public LocationParams(@JsonProperty("room") String room, @JsonProperty("player") int player, @JsonProperty("y") int y, @JsonProperty("x") int x) {
			this.room = room;
			this.player = player;
			this.y = y;
			this.x = x;
		}
	}

	@PostMapping("/missile")
	public String missile(@RequestBody String bodyJson) {
		try {
			// convert JSON string to LocationParams object
			LocationParams body = new ObjectMapper().readValue(bodyJson, LocationParams.class);
			AbilityPlayer opponent = verifyPlayer(body.room, getOpponentID(body.player));

			if (opponent != null) {
				Missile m = new Missile();
				m.use(opponent, body.x, body.y); //missile takes params in x,y order
				return "Attacked opponent with missile";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error attacking with missile";
		}
		return "Error attacking with missile";
	}

	@PostMapping("/lazer")
	public String lazer(@RequestBody String bodyJson) {
		try {
			// convert JSON string to LocationParams object
			LocationParams body = new ObjectMapper().readValue(bodyJson, LocationParams.class);
			AbilityPlayer opponent = verifyPlayer(body.room, getOpponentID(body.player));

			if (opponent != null) {
				verifyPlayer(body.room, body.player).removeCard("lazer");
				SpaceLazer l = new SpaceLazer();
				l.use(opponent, body.y, body.x);
				return "Attacked opponent with lazer";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error attacking with lazer";
		}
		return "Error attacking with lazer";
	}

	@PostMapping("/repair")
	public String repair(@RequestBody String bodyJson) {
		try {
			// convert JSON string to LocationParams object
			LocationParams body = new ObjectMapper().readValue(bodyJson, LocationParams.class);
			AbilityPlayer player = verifyPlayer(body.room, body.player);

			if (player != null) {
				player.removeCard("repair");
				Repair r = new Repair();
				return "Repair was successful? : " + r.use(player, body.y, body.x);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error with repairing";
		}
		return "Error with repairing";
	}

	@PostMapping("/sonar")
	public String sonar(@RequestBody String bodyJson) { //needs to return a JSON representation of a sonar board
		try {
			// convert JSON string to LocationParams object
			LocationParams body = new ObjectMapper().readValue(bodyJson, LocationParams.class);
			AbilityPlayer player = verifyPlayer(body.room, body.player);
			AbilityPlayer opponent = verifyPlayer(body.room, getOpponentID(body.player));

			if (player != null && opponent != null) {
				player.removeCard("sonar");
				Sonar s = new Sonar();
				Board resultBoard = s.use(player, opponent, body.x, body.y);
				String retJson = "";
				try {
					retJson = new ObjectMapper().writeValueAsString(resultBoard);
				} catch (Exception e) {
					e.printStackTrace();
					return "Error with sonar";
				}
				return retJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error with sonar";
		}
		return "Error with sonar";
	}

}
