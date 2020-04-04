package de.klschlitzohr.stickfight.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoardManager {

    private GameManager gameManager;

    public ScoreBoardManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void updateScoreBoard(Arena arena, boolean show) {
        for (Player player : arena.getPlayersinarena().keySet()) {
            if (show) {
                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                Objective objective = scoreboard.registerNewObjective("abcd", "abcd");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName("\u00a73\u00a7lScore");
                objective.getScore( "\u00a7a\u00a7l" + arena.getPlayersinarena().get(player) + " \u00a7a" + player.getName())
                        .setScore(2);
                Player otherPlayer = arena.getOtherPlayer(player);
                objective.getScore("\u00a7c\u00a7l" + arena.getPlayersinarena().get(otherPlayer) + " \u00a7c" +
                        otherPlayer.getName()).setScore(1);
                player.setScoreboard(scoreboard);
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }
}
