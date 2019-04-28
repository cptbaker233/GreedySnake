package com.igeek.greedy_snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**  
* @typename ClientUI  
* @author NFUE  
* @Description: TODO(这里用一句话描述这个类的作用)
* @date 2019年4月17日 下午7:13:59    
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

class GreedySnake implements Runnable {
    public static String direction = "空";
    public static String lastDirection = "空";
    public static ArrayList<Snake> al = new ArrayList<Snake>();
    
    public static Snake food = null;
    public static String[][] field;
    public static Snake tail;
    public static int speed = 200;
    static {
        field = new String[28][34];
        Random r = new Random();
        int a = r.nextInt(field.length -2) + 1;
        int b = r.nextInt(field[0].length - 2) + 1;
        al.add(new Snake(a, b));                            //在场地上随机生成第一个蛇的坐标
        respawn();
        ClientUI.food.setBounds(10 + food.y * 16, 18 + food.x * 18, 16, 18);
        update();
    }
    
    //打印当前战场的方法
    public static synchronized void update() {
        for (int i = 0; i < field.length; i ++) {                       //先把整个场地清理为初始场地,全部为空
            for (int j = 0; j < field[0].length; j ++) {
                field[i][j] = "丶 ";
            }
        }
        //把边框属性修改
        for (int i = 0; i < field[0].length; i ++) {
            field[0][i] = "燚 ";
        }
        for (int i = 0; i < field[field.length - 1].length; i ++) {
            field[field.length - 1][i] = "燚 ";
        }
        for (int i = 0; i < field.length; i ++) {
            field[i][0] = "&nbsp&nbsp燚 ";
            field[i][field[0].length - 1] = "燚 ";
        }
        
        field[food.x][food.y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";                                    //食物坐标对应的场地为"〇"
        for (int i = 0; i < al.size() - 1; i ++) {                      //蛇坐标对应的场地为"回"
            field[al.get(i).x][al.get(i).y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";
        }
        field[al.get(al.size() - 1).x][al.get(al.size() - 1).y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";  //便于区分,将蛇头和蛇尾特殊标记
        field[al.get(0).x][al.get(0).y] = "&nbsp&nbsp&nbsp&nbsp&nbsp";
        for (int i = 0; i < ClientUI.body.size(); i ++) {
            ClientUI.body.get(i).setBounds(10 + al.get(i).y * 16, 18 + al.get(i).x * 18, 16, 18);
        }
        
        String res = "<html>";
        for (String[] temp : field) {
            for (String str: temp) {
                res += str;
            }
            res += "<br />";
        }
        res += "</html>";
        ClientUI.yard.setText(res);
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
        food = new Snake(r.nextInt(field.length - 2) + 1, r.nextInt(field[0].length - 2) + 1);   //随机生成食物
        while (hit()) {                                                                 //一直生成新食物直到生成不与蛇碰撞的食物
            if (al.size() == (field.length - 1) * (field[0].length - 1)) {                        //如果蛇已经把场地填充满,则判断通关
                ClientUI.opened = false;
                ClientUI.yard.setText("<html><p style=\"font-size:100px; align:center; color:red\">YOU WIN!!!</p></html>");
                Thread.currentThread().stop();
            }
            food = new Snake(r.nextInt(field.length - 2) + 1, r.nextInt(field[0].length - 2) + 1);
        }
    }
    //判断吃掉的条件
    public static synchronized boolean eat() {
        if (al.get(0).x == food.x && al.get(0).y == food.y) {   //移动之后,如果蛇头坐标与食物相同,判定true
            ClientUI.food.setBounds(10 + tail.y * 16, 18 + tail.x * 18, 16, 18);
            ClientUI.body.add(ClientUI.food);
            al.add(tail);                   //将上一次移动的蛇尾对象加在蛇尾
            respawn();
            ClientUI.respawn();
            update(); 
            return true;
        }
        return false;
    }
    
    //得到相反方向的方法
    public static String oppositeDir() {
        switch (lastDirection) {
        case "w":
            return "s";
        case "s" :
            return "w";
        case "a" : 
            return "d";
        case "d" : 
            return "a";
        default:
            return lastDirection;
        }
    }
    //移动的方法
    public static Snake move() {
        switch (direction) {
        case "d":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);        
            if (al.size() == 1) {               //蛇长度只有1时,直接移动
                al.get(0).y ++;
                lastDirection = direction;
            } else {            
                Snake head = new Snake(al.get(0).x, al.get(0).y + 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        case "a":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);      
            if (al.size() == 1) {
                al.get(0).y --;
                lastDirection = direction;
            } else {
                Snake head = new Snake(al.get(0).x, al.get(0).y - 1);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        case "w":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);       
            if (al.size() == 1) {
                al.get(0).x --;
                lastDirection = direction;
            } else {
                Snake head = new Snake(al.get(0).x - 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        case "s":
            tail = new Snake(al.get(al.size() - 1).x, al.get(al.size() - 1).y);     
            if (al.size() == 1) {
                al.get(0).x ++;
                lastDirection = direction;
            } else {
                Snake head = new Snake(al.get(0).x + 1, al.get(0).y);
                al.remove(al.get(al.size() - 1));
                al.add(0, head);
                lastDirection = direction;
            }
            return tail;
            
        default:
            return tail;
        }
    }
  //蛇自体碰撞检查和边界碰撞检查
    public static boolean suicide() {
        if (al.get(0).x == 0 || al.get(0).x == field.length - 1 || al.get(0).y == field[0].length - 1 || al.get(0).y == 0 ) {    //如果蛇头与蛇自身或者边界重合,判定true
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
        if(!direction.equals("空") && !direction.equals("死亡")) {
            while (true) {
                tail = move();  
                if (suicide()) {
                    ClientUI.opened = false;
                    ClientUI.yard.setText(" ");
                    ClientUI.contentPane.add(ClientUI.failure);
                    Thread.currentThread().stop();
                }
                //ClientUI.contentPane.add(ClientUI.food);
                eat();
                update();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (direction.equals("空")) {
            try {
                Thread.sleep(100);       //游戏开始的时候没有初始方向,则延时死循环等待用户输入方向
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            update();
            run();
        } else {
            try {
                Thread.sleep(100);       //游戏开始的时候没有初始方向,则延时死循环等待用户输入方向
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run();
        }
    }
}

class MyPanel extends JPanel implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (ClientUI.opened == true) {
            if (GreedySnake.direction == null) {
                GreedySnake.direction = "" + e.getKeyChar();
                ClientUI.currentDir.setText("当前方向:  " + GreedySnake.direction + "          W:上    A:左    S:下   D:右");
            }
            if (e.getKeyChar() == 'w' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "w";
                ClientUI.currentDir.setText("当前方向:  " + GreedySnake.direction + "          W:上    A:左    S:下   D:右");
                return;
            }
            if (e.getKeyChar() == 'a' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "a";
                ClientUI.currentDir.setText("当前方向:  " + GreedySnake.direction + "          W:上    A:左    S:下   D:右");
                return;
            }
            if (e.getKeyChar() == 's' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "s";
                ClientUI.currentDir.setText("当前方向:  " + GreedySnake.direction + "          W:上    A:左    S:下   D:右");
                return;
            }
            if (e.getKeyChar() == 'd' && !("" + e.getKeyChar()).equals(GreedySnake.oppositeDir())) {
                GreedySnake.direction = "d";
                ClientUI.currentDir.setText("当前方向:  " + GreedySnake.direction + "          W:上    A:左    S:下   D:右");
                return;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}


public class ClientUI extends JFrame {
    /**  
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
    */  
    private static final long serialVersionUID = 2689655315523047948L;
    public static MyPanel contentPane = new MyPanel();
    public static JLabel currentDir;
    public static JLabel yard = new JLabel();
    public static Icon headIC = new ImageIcon("src/com/igeek/greedy_snake/蛇.png");
    public static Icon failureIC = new ImageIcon("src/com/igeek/greedy_snake/蔡徐坤.png");
    public static Icon basketball = new ImageIcon("src/com/igeek/greedy_snake/篮球.png");
    public static ArrayList<JLabel> body = new ArrayList<JLabel>();
    public static JLabel head = new JLabel(headIC, JLabel.CENTER);
    public static JLabel food = new JLabel(basketball, JLabel.CENTER);
    public static JLabel failure = new JLabel(failureIC, JLabel.CENTER);
    public static boolean opened = true;
    static {
        contentPane.setBounds(50, 50, 600, 600);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        
        currentDir = new JLabel("当前方向:  " + GreedySnake.direction + "          W:上    A:左    S:下   D:右");
        currentDir.setBounds(5, 5, 400, 15);
        contentPane.add(currentDir);
        
        yard.setBounds(5, 20, 550, 500);
        contentPane.add(yard);
        
        failure.setBounds(0,20, 580, 500);
        
        contentPane.add(head);
        contentPane.add(food);
        
        body.add(head);
    }

    public void init() {
        this.setTitle("贪吃篮球--蔡徐坤  v1.0                  作者:国家一级未注册工程师");
        this.setSize(575, 565);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(contentPane);
        this.addKeyListener(contentPane);
        this.setVisible(true);
    }
    
    public ClientUI() {
        init();
    }

    public static void respawn() {
        food = new JLabel(basketball, JLabel.CENTER);
        food.setBounds(10 + GreedySnake.food.y * 16, 18 + GreedySnake.food.x * 18, 16, 18);
        contentPane.add(food);
    }
    public static void main(String[] args) {
        new ClientUI();
        Thread t1 = new Thread(new GreedySnake());
        t1.start();
    }
}
