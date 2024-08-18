package minesweeper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {

    @Test
    public void testMinePlacement() {
        Minesweeper game = new Minesweeper(4, 3);
        int mineCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.grid[i][j] == '*') {
                    mineCount++;
                }
            }
        }
        assertEquals(3, mineCount);
    }

    @Test
    public void testUncoverSquare() {
        Minesweeper game = new Minesweeper(4, 0);
        assertTrue(game.uncoverSquare(0, 0));
        assertTrue(game.revealed[0][0]);
    }

    @Test
    public void testGameWon() {
        Minesweeper game = new Minesweeper(4, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                game.uncoverSquare(i, j);
            }
        }
        assertTrue(game.isGameWon());
    }
}
