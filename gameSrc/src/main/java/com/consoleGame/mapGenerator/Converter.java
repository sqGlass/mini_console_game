package com.consoleGame.mapGenerator;

import java.io.IOException;
import java.util.Scanner;

import com.consoleGame.bot.Bot;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Converter {
    int tmp_pos_i;
    int tmp_pos_j;
    int temp = 0;
    int temp_i;
    int temp_j;
    int _enemies_count;
    int _walls_count;
    int _size;

    int pos_i;
    int pos_j;

    int[][] enemy_ar;

    char _enemy;
    char _player;
    char _wall ;
    char _goal ;
    char _empty ;

    String _enemy_color ;
    String _player_color ;
    String _wall_color ;
    String _goal_color ;
    String _empty_color ;

    String _mode;

    Bot bot;

    public void take_mode(String mode)
    {
        _mode = mode;
    }



    public void take_parav(int enemies_count, int walls_count, int size)
    {
        _enemies_count = enemies_count;
        System.out.println(_enemies_count);
        _walls_count = walls_count;
        _size = size;
    }

    public void take_chars(char enemy, char player, char wall, char goal, char empty)
    {
        _enemy = enemy;
        _player = player;
        _wall = wall;
        _goal = goal;
        _empty = empty;
    }

    public void take_colors(String enemy_color, String player_color, String wall_color, String goal_color, String empty_color)
    {
        _enemy_color = enemy_color;
        _player_color =player_color;
        _wall_color = wall_color;
        _goal_color = goal_color;
        _empty_color = empty_color;
    }




    public void checkpatameters()
    {

        if (_enemies_count + _walls_count + 30 >= _size * _size)
        {
            System.out.println("Illegal map");
            System.out.println("here");
            System.exit(1);
        }
        else
        {
            System.out.println("Map is okey");
        }
    }

    public void create_enemy_arr()
    {
        enemy_ar = new int[_enemies_count][2];
    }



    public void seeBMPImage() throws IOException {

        ColoredPrinter enem = new ColoredPrinter.Builder(1, false)
                .background(Ansi.BColor.valueOf(_enemy_color))
                .build();
        ColoredPrinter playe = new ColoredPrinter.Builder(1, false)
                .background(Ansi.BColor.valueOf(_player_color))
                .build();
        ColoredPrinter wally = new ColoredPrinter.Builder(1, false)
                .background(Ansi.BColor.valueOf(_wall_color))   //setting format
                .build();
        ColoredPrinter goaly = new ColoredPrinter.Builder(1, false)
                .background(Ansi.BColor.valueOf(_goal_color))
                .build();
        ColoredPrinter empt = new ColoredPrinter.Builder(1, false)
                .background(Ansi.BColor.valueOf(_empty_color))
                .build();
        ColoredPrinter ln = new ColoredPrinter.Builder(1, false)
                .background(Ansi.BColor.NONE)
                .build();
        ln.clear();

        char[][] map = new char[_size][_size];
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                    map[i][j] = _empty;
            }
        }

        while (temp < _walls_count)
        {
            temp_i = (int) ( Math.random() * _size);
            temp_j = (int) (Math.random() * _size);

            if (map[temp_i][temp_j] != _wall)
            {
                map[temp_i][temp_j] = _wall;
                temp++;
            }
        }
        temp = 0;
        while (temp < _enemies_count)
        {
            temp_i = (int) ( Math.random() * _size);
            temp_j = (int) (Math.random() * _size);

            if (map[temp_i][temp_j] != _wall && map[temp_i][temp_j] != _enemy)
            {
                map[temp_i][temp_j] = _enemy;
                enemy_ar[temp][0] = temp_j;
                enemy_ar[temp][1] = temp_i;
                temp++;
            }
        }
        temp = 0;

        while (true)
        {
            temp_i = (int) ( Math.random() * _size);
            temp_j = (int) (Math.random() * _size);

            if (map[temp_i][temp_j] != _wall && map[temp_i][temp_j] != _enemy)
            {
                map[temp_i][temp_j] = _player;
                pos_i = temp_i;
                pos_j = temp_j;
                break;
            }
        }

        while (true)
        {
            temp_i = (int) ( Math.random() * _size);
            temp_j = (int) (Math.random() * _size);

            if (map[temp_i][temp_j] != _wall && map[temp_i][temp_j] != _enemy && map[temp_i][temp_j] != _player)
            {
                map[temp_i][temp_j] = _goal;
                break;
            }
        }



        for (int xPixel = 0; xPixel < _size; xPixel++)
        {
            for (int yPixel = 0; yPixel < _size; yPixel++)
            {
                if (map[xPixel][yPixel] == _wall)
                    wally.print(map[xPixel][yPixel]);
                else if (map[xPixel][yPixel] == _empty)
                    empt.print(map[xPixel][yPixel]);
                else if (map[xPixel][yPixel] == _enemy)
                    enem.print(map[xPixel][yPixel]);
                else if (map[xPixel][yPixel] == _goal)
                    goaly.print(map[xPixel][yPixel]);
                else
                    playe.print(map[xPixel][yPixel]);

            }
            ln.println("");
        }




        Scanner scanner = new Scanner(System.in);
        String move;

        Bot bot = new Bot(map, enemy_ar, true, _wall, _enemy, _empty, _player, _goal);
        while(!(move = scanner.nextLine()).equals("9"))
        {
            tmp_pos_i = pos_i;
            tmp_pos_j = pos_j;
            if (move.equals("w"))
            {
                System.out.println(move);
                if (pos_i - 1 != -1 && map[pos_i - 1][pos_j] != _wall)
                {
                    if (map[pos_i - 1][pos_j] == _enemy)
                    {
                        System.out.println("You died");
                        System.exit(1);
                    }
                    if(map[pos_i - 1][pos_j] == _goal)
                    {
                        System.out.println("Player win");
                        System.exit(1);
                    }
                    map[pos_i][pos_j] = _empty;
                    map[pos_i - 1][pos_j] = _player;
                    pos_i--;

                }
            }
            if (move.equals("s"))
            {
                System.out.println(move);
                if (pos_i + 1 != _size && map[pos_i + 1][pos_j] != _wall)
                {
                    if (map[pos_i + 1][pos_j] == _enemy)
                    {
                        System.out.println("You died");
                        System.exit(1);
                    }
                    if(map[pos_i + 1][pos_j] == _goal)
                    {
                        System.out.println("Player win");
                        System.exit(1);
                    }
                    map[pos_i][pos_j] = _empty;
                    map[pos_i + 1][pos_j] = _player;
                    pos_i++;

                }
            }

            if (move.equals("d"))
            {
                System.out.println(move);
                if (pos_j + 1 != _size && map[pos_i][pos_j + 1] != _wall)
                {
                    if (map[pos_i][pos_j + 1] == _enemy)
                    {
                        System.out.println("You died");
                        System.exit(1);
                    }
                    if(map[pos_i][pos_j + 1] == _goal)
                    {
                        System.out.println("Player win");
                        System.exit(1);
                    }
                    map[pos_i][pos_j] = _empty;
                    map[pos_i][pos_j + 1] = _player;
                    pos_j++;

                }
            }

            if (move.equals("a"))
            {
                System.out.println(move);
                if (pos_j - 1 != -1 && map[pos_i][pos_j - 1] != _wall)
                {
                    if (map[pos_i][pos_j - 1] == _enemy)
                    {
                        System.out.println("You died");
                        System.exit(1);
                    }
                    if(map[pos_i][pos_j - 1] == _goal)
                    {
                        System.out.println("Player win");
                        System.exit(1);
                    }
                    map[pos_i][pos_j] = _empty;
                    map[pos_i][pos_j - 1] = _player;
                    pos_j--;

                }

            }
            if (tmp_pos_j == pos_j && tmp_pos_i == pos_i)
            {
                continue;
            }
            for (int xPixel = 0; xPixel < _size; xPixel++)
            {
                for (int yPixel = 0; yPixel < _size; yPixel++)
                {
                    if (map[xPixel][yPixel] == _wall)
                        wally.print(map[xPixel][yPixel]);
                    else if (map[xPixel][yPixel] == _empty)
                        empt.print(map[xPixel][yPixel]);
                    else if (map[xPixel][yPixel] == _enemy)
                        enem.print(map[xPixel][yPixel]);
                    else if (map[xPixel][yPixel] == _goal)
                        goaly.print(map[xPixel][yPixel]);
                    else
                        playe.print(map[xPixel][yPixel]);

                }
                ln.println("");
            }
            bot.move(pos_j, pos_i);
        }

    }
}
