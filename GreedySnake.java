package com.igeek.greedy_snake;

/**
 * 人力贪吃蛇,wasd控制移动,enter输入确定移动
 * @author NFUE
 * @date 2019.3.24
 */

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
        String[][] field = new String[h][l + 2];            //根据设置的场地长宽建立二维数组场地
        Snake tail;
        int a = r.nextInt(field.length);
        int b = r.nextInt(field[0].length - 2) + 1;
        al.add(new Snake(a, b));                            //在场地上随机生成第一个蛇的坐标
        Snake food = respawn(al, field);                    //在场地上随机生成一个食物坐标
        update(field, al, food);                            //确定蛇和食物的坐标之后,刷新场地
        update(field, al, food);                            //蛇坐标的场地显示为蛇,空白场地显示为空格,食物坐标显示为〇
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        update(field, al, food);
        String option = sc.next();
        while(true) {
            switch (option) {                               //根据用户输入的字符串分别进行移动
            case "w":
                tail = moveUp(al);
                if (suicide(al, field)) {                   //移动之后如果判定碰撞,则游戏失败
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.exit(0);
                }
                if (eat(al, food, tail)) {                  //如果移动之后吃掉了食物,更新蛇的坐标
                    food = respawn(al, field);              //生成新的食物坐标
                    update(field, al, food);                //确定了蛇和食物的坐标之后,刷新场地
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
            update(field, al, food);                //如果没有吃掉食物,跳出switch之后刷新场地(食物无需生成,蛇坐标已更新)
            option = sc.next();
        }
    }
    
    //打印当前战场的方法
    public static void update(String[][] field, ArrayList<Snake> al, Snake food) {
        for (int i = 0; i < field.length; i ++) {                       //先把整个场地清理为初始场地
            field[i][0] = "燚";
            field[i][field[0].length - 1] = "燚";
            for (int j = 1; j < field[0].length - 1; j ++) {
                field[i][j] = "　";
            }
        }
        field[food.x][food.y] = "〇";                                    //食物坐标对应的场地为"〇"
        for (int i = 0; i < al.size() - 1; i ++) {                      //蛇坐标对应的场地为"回"
            field[al.get(i).x][al.get(i).y] = "回";
        }
        field[al.get(al.size() - 1).x][al.get(al.size() - 1).y] = "口";  //便于区分,将蛇头和蛇尾特殊标记
        field[al.get(0).x][al.get(0).y] = "圞";
        System.out.println("手动贪吃蛇v1.3");
        /*
         * 所有的坐标都确定之后,将场地刷新出蛇和食物
         */
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
            if (s.x == al.get(i).x && s.y == al.get(i).y) {         //生成了食物之后如果食物与蛇坐标重合,判定碰撞
                return true;
            }
        }
        return false;
    }
    //生成食物的方法
    public static Snake respawn(ArrayList<Snake> al, String[][] field) {
        Random r = new Random();
        Snake s = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);   //随机生成食物
        while (hit(s,al)) {                                                                 //一直生成新食物直到生成不与蛇碰撞的食物
            if (al.size() == field.length * (field[0].length - 2)) {                        //如果蛇已经把场地填充满,则判断通关
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
        if (x == food.x && y == food.y) {   //移动之后,如果蛇头坐标与食物相同,判定true
            al.add(tail);                   //将上一次移动的蛇尾对象加在蛇尾
            return true;
        }
        return false;
    }
    //右移
    public static Snake moveRight(ArrayList<Snake> al) {
        Snake tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);        
        if (al.size() == 1) {               //蛇长度只有1时,直接移动
            al.get(0).y ++;
        } else if (al.get(0).y + 1 != al.get(1).y) {            //蛇长度大于1时,只要不是回头就允许移动,并返回本次的蛇尾巴以便判断吃掉食物
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
        if (al.get(0).x == -1 || al.get(0).x == field.length || al.get(0).y == field[0].length - 1 || al.get(0).y == 0 ) {    //如果蛇头与蛇自身或者边界重合,判定true
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