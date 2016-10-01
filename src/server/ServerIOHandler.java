package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import model.Distribution;
import model.Game;
import model.Model;
import model.Player;
import utils.Constants;

/**
 * Handles incoming client messages and Server UI events
 * @author Max Buster
 */

public class ServerIOHandler {
	private Model model;
	private Player player;
	private DataInputStream in;
	private DataOutputStream out;
	private static Object waitObject = new Object();

	public ServerIOHandler(Model model, Socket socket) {
		this.model = model;
		this.player = model.new_player();

		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			// FIXME crash the client handler
		}
	}

	/**
	 * Interact with client connection
	 */
	public void handleIO() {
		write_start_info();
		while (!model.game_started()) {
			sleep(200);
		}
		model.set_player_info(player);
		write_player_info();
		write_game_info(); // FIXME do in response to increment game?
		write_voter_dist();
		while (true) {
			/**
			 * TODO add io statements:
			 * Purchase info request
			 * Player done with buy1, buy2
			 * Player voted straw, first, second
			 * Error?
			 */
		}
	}
	
	/**
	 * Writes the player # and num games to start all games
	 */
	private void write_start_info() {
		int[] message = new int[]{player.getPlayer_number(), model.get_num_games()};
		write_message(Constants.START_INFO , message);
	}
	
	/**
	 * Writes player info for the start of a game
	 */
	private void write_player_info() {
		int[] message = new int[] {
				player.getPlayer_party(), player.getIdeal_point()
		};
		write_message(Constants.PLAYER_INFO, message);
	}
	
	/**
	 * Writes the general info for a new game
	 */
	private void write_game_info() {
		Game current_game = model.get_current_game();
		int[] message = new int[] {
				current_game.getGameNumber(), current_game.getBudget(),
				current_game.getCandidates().size()
		};
		write_message(Constants.GAME_INFO, message);
	}
	
	private void write_voter_dist() {
		Game current_game = model.get_current_game();
		Distribution current_dist = current_game.getDistribution();
		int[] message = current_dist.get_dist();
		write_message(Constants.VOTER_DIST, message);
	}
	
	// TODO write voter dist
	
	// TODO write candidate info
	
	// TODO write new round
	
	// TODO write vote results
	
	// TODO write end game
	
	// TODO write end all games

	// FIXME don't catch error or respond to the error by removing client
	private boolean write_message(int message_type, int[] messages) {
		try {
			out.writeInt(Constants.MESSAGE_START);
			out.writeInt(message_type);
			out.writeInt(messages.length);
			for (int i=0; i<messages.length; i++) {
				out.writeInt(messages[i]);
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public int getPlayerNum() {
		return player.getPlayer_number();
	}

	private void sleep(long sleep_time_ms) {
		try {
			Thread.sleep(sleep_time_ms);
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		}
	}

	/**
	 * Listen to changes from the GUI
	 */
	class ChangeListener implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent PCE) {
			/**
			 * FIXME should some of this get thrown from model and retrieved from gui?
			 * Game started
			 * Remove player
			 * Player removed due to io
			 * New player
			 * New round
			 * New game
			 * Game over
			 * Write data
			 * End game
			 * All games over
			 */
		}
	}
}
