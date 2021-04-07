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

	private class Player {
		// Player stub class
	}

	private class Game {
		// Game stub class
		public Player P1 = new Player();
		public Player P2 = new Player();
		public Game(Player P1) {
			this.P1 = P1;
		}
	}


	HashMap<String, Game> games = new HashMap<String, Game>();
	HashMap<String, Player> players = new HashMap<String, Player>();

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);
	}

	@PostMapping("/create")
	public String create() {
		Player p1 = new Player();
		Game newGame = new Game(p1);

		String user = UUID.randomUUID().toString().replace("-", "");
		String room = UUID.randomUUID().toString().replace("-", "");

		this.games.put(room, newGame);
		this.players.put(user, p1);

		System.out.printf("game created: %s\nby user: %s\n\n", room, user);
		return String.format("{\"user\": \"%s\", \"room\": \"%s\"}", user, room);
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

		Player p2 = new Player();
		game.P2 = p2;

		String user = UUID.randomUUID().toString().replace("-", "");;
		this.players.put(user, p2);

		System.out.printf("game joined: %s\nby user: %s\n\n", room, user);
		return String.format("{\"user\": \"%s\", \"room\": \"%s\"}", user, room);
	}

	@PostMapping("/set-fleet")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/")
	public String setFleet(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/player")
	public Player getPlayer(@RequestParam(value = "room", defaultValue = "") String room, @RequestParam(value = "player", defaultValue = "1") int player) {

		Game game = this.games.get(room);
		if (game == null) {
			return null;
		}

		if (player == 1) {
			return game.P1;
		} else if (player == 2) {
			return game.P2;
		} else {
			return null;
		}
	}
}
