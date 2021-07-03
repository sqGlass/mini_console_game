package com.consoleGame.bot;

public class Bot {
    private char[][] charMap;
    private int mapWidth;
    private int mapHeight;
    private int[][] botPos;
    private int[][] wayMap;
    private char wall;
    private char bot;
    private char empty;
    private char hero;
    private boolean mode;
    private char exit;
    public Bot(char[][] charMap, int[][] botPos, boolean enemyMode,
               char wall, char bot, char empty, char hero, char exit) {
        this.exit = exit;
        this.charMap = charMap;
        this.mapHeight = charMap.length;
        this.mapWidth = charMap[0].length;
        this.botPos = botPos;
        this.mode = enemyMode;
        this.wall = wall;
        this.bot = bot;
        this.empty = empty;
        this.hero = hero;
    }

    public void move(int targetX, int targetY)
    {
        for (int i = 0; i < botPos.length; i++)
            step(botPos[i][1], botPos[i][0], targetX, targetY, i);
    }

    private void step(int startY, int startX, int targetX, int targetY, int botIndex) {
        boolean add = true;
        int[][] cMap = new int[mapHeight][mapWidth];
        int x, y, step = 0;
        for (y = 0; y < mapHeight; y++) {
            for (x = 0; x < mapWidth; x++) {
                if (charMap[y][x] == wall || charMap[y][x] == exit)
                    cMap[y][x] = -2;
                else
                    cMap[y][x] = -1;
            }
        }
        cMap[targetY][targetX] = 0;
        while (add==true)
        {
            add = false;
            for (y = 0; y < mapWidth; y++)
                for (x = 0; x < mapHeight; x++)
                {
                    if (cMap[x][y] == step)
                    {
                        if (x - 1 >= 0 && cMap[x - 1][y] != -2 && cMap[x - 1][y] == -1)
                            cMap[x - 1][y] = step + 1;
                        if (y - 1 >= 0 && cMap[x][y - 1] != -2 && cMap[x][y - 1] == -1)
                            cMap[x][y - 1] = step + 1;
                        if (x + 1 < mapWidth && cMap[x + 1][y] != -2 && cMap[x + 1][y] == -1)
                            cMap[x + 1][y] = step + 1;
                        if (y + 1 < mapHeight && cMap[x][y + 1] != -2 && cMap[x][y + 1] == -1)
                            cMap[x][y + 1] = step + 1;
                    }
                }
            step++;
            add = true;
            if (cMap[startY][startX] != -1)
                add = false;
            if (step > mapWidth * mapHeight)
                add = false;
        }
        if (startY + 1 < mapHeight && cMap[startY + 1][startX] != -2 && cMap[startY + 1][startX] != -1
                && cMap[startY + 1][startX] < cMap[startY][startX]) {
            charMap[startY][startX] = empty;
            charMap[startY + 1][startX] = bot;
            botPos[botIndex][1] = startY + 1;
        }
        else if (startY - 1 > 0 && cMap[startY - 1][startX] != -2 && cMap[startY - 1][startX] != -1
                && cMap[startY - 1][startX] < cMap[startY][startX])
        {
            charMap[startY][startX] = empty;
            charMap[startY - 1][startX] = bot;
            botPos[botIndex][1] = startY - 1;
        }
        else if (startX - 1 > 0 && cMap[startY][startX - 1] != -2 && cMap[startY][startX - 1] != -1
                && cMap[startY][startX - 1] < cMap[startY][startX])
        {
            charMap[startY][startX] = empty;
            charMap[startY][startX - 1] = bot;
            botPos[botIndex][0] = startX - 1;
        }
        else if (startX + 1 < mapWidth && cMap[startY][startX + 1] != -2 && cMap[startY][startX + 1] != -1
                && cMap[startY][startX + 1] < cMap[startY][startX])
        {
            charMap[startY][startX] = empty;
            charMap[startY][startX + 1] = bot;
            botPos[botIndex][0] = startX + 1;
        }
        if (botPos[botIndex][0] == targetX && botPos[botIndex][1] == targetY)
        {
            System.out.println("YOU LOSE");
            System.exit(0);
        }
    }
}
