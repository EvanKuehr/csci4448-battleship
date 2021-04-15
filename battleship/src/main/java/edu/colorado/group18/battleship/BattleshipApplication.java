package edu.colorado.group18.battleship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@SpringBootApplication
@RestController
public class BattleshipApplication {

	HashMap<String, Game> games = new HashMap<String, Game>();

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);
	}

	@PostMapping("/create")
	public String create() {
		Game newGame = new Game();

		String room = UUID.randomUUID().toString().replace("-", "");

		this.games.put(room, newGame);

		System.out.printf("game created: %s\n\n", room);
		return String.format("{\"player\": \"%s\", \"room\": \"%s\"}", "p1", room);
	}

	@PostMapping("/join")
	public String join(@RequestParam(value = "room", defaultValue = "") String room) {
		if (room.length() == 0) {
			return "Room ID Required";
		}

		Game game = this.games.get(room);
		if (game == null) {
			return "Room Not Found";
		}

		System.out.printf("game joined: %s\n\n", room);
		return String.format("{\"player\": \"%s\", \"room\": \"%s\"}", "p2", room);
	}

	@PostMapping("/set-fleet")
	public String setFleet(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/")
	public String update(@RequestParam(value = "room") String room, @RequestParam(value = "player") String user) {
		Game game = this.games.get(room);
		if (game == null) {
			return "Room Not Found";
		}

		AbilityPlayer player;

		if (user == "p1") {
			player = game.P1; 
		} else if (user == "p2") {
			player = game.P2; 
		} else {
			return String.format("Player must be `p1` or `p2` but you passed `%s`!", user);
		}

		return String.format("{\"your_turn\": %b, \"data\": %s}", game.isTurn(user), player.toJson());
	}

	@GetMapping("/player")
	public String getPlayer(@RequestParam(value = "room", defaultValue = "") String room, @RequestParam(value = "player", defaultValue = "1") int player) {

		Game game = this.games.get(room);
		if (game == null) {
			return null;
		}

		if (player == 1) {
			//game.P1.placeShip(game.P1.getFleet()[2], 0, 0, 'h'); //this line can be used to test with ShipCells
			return game.P1.toJson();
		} else if (player == 2) {
			return game.P2.toJson();
		} else {
			return null;
		}
	}
}
