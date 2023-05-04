package com.gameserver.tictactoe.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gameserver.user.User;
import com.gameserver.user.UserService;

@Service
public class TTTScoreService {

    @Autowired
    private UserService userService;

    @Autowired
    private TTTScoreRepository tttScoreRepository;

    public List<TTTScore> getUserGames(String username) {
        User user = userService.getUserByUserName(username);
        return tttScoreRepository.findByUser(user);
    }

    public List<TTTScoreAggregate> getLeaderboard() {
        return tttScoreRepository.getLeaderboard();
    }

    public void persistGameResult(char winner, Map<Character, String> players) {
        List<TTTScore> tttScores = new ArrayList<>();
        for (Map.Entry<Character, String> playerEntry : players.entrySet()) {
            User user = userService.getUserByUserName(playerEntry.getValue());
            boolean didWin = winner == playerEntry.getKey().charValue() ? true : false;
            tttScores.add(new TTTScore(user, didWin));
        }
        tttScoreRepository.saveAll(tttScores);
    }

}
