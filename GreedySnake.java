package com.igeek.greedy_snake;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * 
* @typename Snake  
* @author NFUE  
* @Description: 加入多线程的半自动贪吃蛇小游戏
* @date 2019年4月13日 下午3:40:48    
* @Company https://github.com/cptbaker233
*
 */

class Snake {
    int x;
    int y;
    
    //构造方法
    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class GreedySnake implements Runnable {
    private static String direction;
    private static ArrayList<Snake> al = new ArrayList<Snake>();
    private static Snake food;
    private static String[][] field;
    private static Snake tail;
    private static int speed = 600;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        System.out.println("请设置场地长:");
        int l = sc.nextInt();
        System.out.println("请设置场地高度:");
        int h = sc.nextInt();
        System.out.println("请设置移动速度:                 (默认600,数字越小越快)");
        speed = sc.nextInt();
        field = new String[h][l + 2];            //根据设置的场地长宽建立二维数组场地
        int a = r.nextInt(field.length);
        int b = r.nextInt(field[0].length - 2) + 1;
        al.add(new Snake(a, b));                            //在场地上随机生成第一个蛇的坐标
        respawn();                    //在场地上随机生成一个食物坐标
        update();                            //确定蛇和食物的坐标之后,刷新场地
        update();                            //蛇坐标的场地显示为蛇,空白场地显示为空格,食物坐标显示为〇
        update();
        update();
        update();
        update();
        update();
        update();
        update();
        new Thread(new GreedySnake()).start();
        while(true) {
            String newDir = sc.next();
            if (newDir.equals("quit")) {
                System.out.println("退出游戏!!!!!!!!!!!!");
                System.out.println("退出游戏!!!!!!!!!!!!");
                System.out.println("退出游戏!!!!!!!!!!!!");
                System.out.println("退出游戏!!!!!!!!!!!!");
                System.exit(0);
            } else if (direction == null) {
                direction = newDir;
                tail = move();
                if (suicide()) {
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.exit(0);
                }
                update();
                eat();
            } else if (!newDir.equals(oppositeDir())) {
                direction = newDir;
            }
        }
    }
    
    //打印当前战场的方法
    public static void update() {
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
        System.out.println("半自动贪吃蛇v1.3");
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
    public static boolean hit() {
        if (food != null) {
            for (int i = 0; i < al.size(); i ++) {
                if (food.x == al.get(i).x && food.y == al.get(i).y) {         //生成了食物之后如果食物与蛇坐标重合,判定碰撞
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
    //生成食物的方法
    public static void respawn() {
        Random r = new Random();
        food = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);   //随机生成食物
        while (hit()) {                                                                 //一直生成新食物直到生成不与蛇碰撞的食物
            if (al.size() == field.length * (field[0].length - 2)) {                        //如果蛇已经把场地填充满,则判断通关
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.out.println("恭喜您通关!");
                System.exit(0);
            }
            food = new Snake(r.nextInt(field.length), r.nextInt(field[0].length - 2) + 1);
        }
    }
    //判断吃掉的条件
    public static boolean eat() {
        if (al.get(0).x == food.x && al.get(0).y == food.y) {   //移动之后,如果蛇头坐标与食物相同,判定true
            al.add(tail);                   //将上一次移动的蛇尾对象加在蛇尾
            respawn();
            update(); 
            return true;
        }
        return false;
    }
    
    //得到相反方向的方法
    public static String oppositeDir() {
        switch (direction) {
        case "w":
            return "s";
        case "s" :
            return "w";
        case "a" : 
            return "d";
        case "d" : 
            return "a";
        default:
            return direction;
        }
    }
    //移动的方法
    public static Snake move() {
        switch (direction) {
        case "d":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);        
            if (al.size() == 1) {               //蛇长度只有1时,直接移动
                al.get(0).y ++;
            } else {            
                Snake head = new Snake(al.get(0).x, al.get(0).y + 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        case "a":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);      
            if (al.size() == 1) {
                al.get(0).y --;
            } else {
                Snake head = new Snake(al.get(0).x, al.get(0).y - 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        case "w":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);       
            if (al.size() == 1) {
                al.get(0).x --;
            } else {
                Snake head = new Snake(al.get(0).x - 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        case "s":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);     
            if (al.size() == 1) {
                al.get(0).x ++;
            } else {
                Snake head = new Snake(al.get(0).x + 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
            }
            return tail;
            
        default:
            return tail;
        }
    }
  //蛇自体碰撞检查和边界碰撞检查
    public static boolean suicide() {
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

    //重写run方法实现多线程蛇自动前进
    @Override 
    public void run() {
        if(direction != null) {
            while (true) {
                tail = move();  
                if (suicide()) {
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.out.println("您死了!游戏失败!");
                    System.exit(0);
                }
                update();
                eat();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Thread.sleep(10);       //游戏开始的时候没有初始方向,则延时死循环等待用户输入方向
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run();
        }
    }
}