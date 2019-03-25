package com.igeek.greedy_snake;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Snake {
    int x;
    int y;
    
    //构造方法
    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class GreedySnake {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        ArrayList<Snake> al = new ArrayList<Snake>();
        System.out.println("请设置场地长:");
        int l = sc.nextInt();
        System.out.println("请设置场地高度:");
        int h = sc.nextInt();
        String[][] field = new String[h][l + 2];
        Snake tail;
        int a = r.nextInt(field.length);
        int b = r.nextInt(field[0].length - 2) + 1;
        al.add(new Snake(a, b));
        Snake food = respawn(al, field);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        String option = sc.next();
        while(true) {
            switch (option) {
            case "w":
                tail = moveUp(al);
                if (suicide(al, field)) {
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                }
                break;
            case "s":
                tail = moveDown(al);
                if (suicide(al, field)) {
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                } 
                break;
            case "a":
                tail = moveLeft(al);
                if (suicide(al, field)) {
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                }
                break;
            case "d":
                tail = moveRight(al);
                if (suicide(al, field)) {
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {
                    food = respawn(al, field);
                    update(field, al, food);
                    option = sc.next();
                }
                break;
            case "quit":
                System.out.println("正在退出!");
                System.out.println("正在退出!");
                System.out.println("正在退出!");
                System.out.println("正在退出!");
                System.out.println("正在退出!");
                System.out.println("已退出!");
                System.exit(0);
            }
            update(field, al, food);
            option = sc.next();
        }
        
        
    }
    
    //打印当前战场的方法
    public static void update(String[][] field, ArrayList<Snake> al, Snake food) {
        for (int i = 0; i < field.length; i ++) {
            field[i][0] = "燚";
            field[i][field[0].length - 1] = "燚";
            for (int j = 1; j < field[0].length - 1; j ++) {
                field[i][j] = "　";
            }
        }
        field[food.x][food.y] = "〇";
        for (int i = 1; i < al.size() - 1; i ++) {
            field[al.get(i).x][al.get(i).y] = "回";
        }
        field[al.get(al.size() - 1).x][al.get(al.size() - 1).y] = "口";
        field[al.get(0).x][al.get(0).y] = "圞";
        System.out.println("手动贪吃蛇v1.2");
        for (int i = 1; i <= field[0].length ; i ++) {
            System.out.print("燚");
        }
        System.out.println();
        for (int i = 0; i < field.length; i ++) {
            for (int j = 0; j < field[0].length; j ++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        for (int i = 1; i <= field[0].length ; i ++) {
            System.out.print("燚");
        }
        System.out.println();
        System.out.println("请用wasd移动移动:(d:右   a:左  w:上   s:下     quit:退出)");
    }
    
    //食物碰撞检查
    public static boolean hit(Snake s, ArrayList<Snake> al) {
        for (int i = 0; i < al.size(); i ++) {
            if (s.x == al.get(i).x && s.y == al.get(i).y) {
                return true;
            }
        }
        return false;
    }
    //生成食物的方法
    public static Snake respawn(ArrayList<Snake> al, String[][] field) {
        Random r = new Random();
        Snake s = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);
        while (hit(s,al)) {
            if (al.size() == field.length * (field[0].length - 2)) {
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.exit(0);
            }
            s = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);
        }
        return s;
    }
    //判断吃掉的条件
    public static boolean eat(ArrayList<Snake> al, Snake food, Snake tail) {
        int x = al.get(0).x;
        int y = al.get(0).y;        
        if (x == food.x && y == food.y) {
            al.add(tail);
            return true;
        }
        return false;
    }
    //右移
    public static Snake moveRight(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);        
        if (al.size() == 1) {
            al.get(0).y ++;
        } else if (al.get(0).y + 1 != al.get(1).y) {
            Snake head = new Snake(al.get(0).x, al.get(0).y + 1);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //左移
    public static Snake moveLeft(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);      
        if (al.size() == 1) {
            al.get(0).y --;
        } else if (al.get(0).y - 1 != al.get(1).y) {
            Snake head = new Snake(al.get(0).x, al.get(0).y - 1);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //上移
    public static Snake moveUp(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);       
        if (al.size() == 1) {
            al.get(0).x --;
        } else if (al.get(0).x - 1!= al.get(1).x) {
            Snake head = new Snake(al.get(0).x - 1, al.get(0).y);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //下移
    public static Snake moveDown(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);     
        if (al.size() == 1) {
            al.get(0).x ++;
        } else if (al.get(0).x + 1 != al.get(1).x) {
            Snake head = new Snake(al.get(0).x + 1, al.get(0).y);
            al.remove(al.get(al.size() - 1));
            al.add(0, head);
        }
        return tail;
    }
  //蛇自体碰撞检查和边界碰撞检查
    public static boolean suicide(ArrayList<Snake> al, String[][] field) {
        if (al.get(0).x == -1 || al.get(0).x == field.length || al.get(0).y == field[0].length - 1 || al.get(0).y == 0 ) {
            return true;
        }
        for (int i = 1; i < al.size() - 1; i ++) {
            if (al.get(i).x == al.get(0).x && al.get(i).y == al.get(0).y) {
                return true;
            }
        }
        return false;
    }
}
