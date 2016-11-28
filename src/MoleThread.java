/**
 * jiaxix
 * 08600
 * 2014-10-03
 */

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.*;

//you can add a ref to the timerThread, to get the remaining time
public class MoleThread extends Thread implements ActionListener,MouseListener {	//�������� �δ��� ���� �ٷ�
	
	private JButton moleHole;	//�δ��� ��ư
	private JTextField timeField;//�ð������� ����
	private JTextField scoreField;//���� ������ ����
	private JPanel holePanel;//�δ��� ���� �г�
	private JTextField lifeField;
	private JTextField gradeField;
	private Random random = new Random(); //***�����Լ� �̿�***
	private JButton gradeButton;
	private JButton lifeButton;
	private JButton stageButton;
	private JPanel controlPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel downPanel;
	
	Toolkit tk=Toolkit.getDefaultToolkit();  
	//java.net.URL imgURL = this.getClass().getResource("./resource/hammer.png");
	Image img=tk.getImage("./resource/hammer.png"); 					// ��ġ �̹���
	Cursor cu=tk.createCustomCursor(img,new Point(10,10),"stick"); 		//  Ŀ�� ��ǥ���, Ŀ���̸��� stick
	//java.net.URL imgURL1 = this.getClass().getResource("./resource/hammer1.png");
	Image img2=tk.getImage("./resource/hammer1.png"); 					//��ġ ����ģ �̹���
	Cursor cu2=tk.createCustomCursor(img2,new Point(10,10),"stick"); 
	
	Image grade_a=tk.getImage(".resourse/grade_A.png");
	protected Get_Sound gs = new Get_Sound();
	protected Clip c_button = gs.sound(gs.SOUND_BUTTON);
	public MoleThread(JButton hole, JTextField timeField, JTextField scoreField, JPanel holePanel, JTextField lifeField, JTextField gradeField, 
			JButton gradeButton, JButton lifeButton, JButton stageButton, JPanel controlPanel,JPanel westPanel, JPanel eastPanel, JPanel downPanel ) {
		// TODO Auto-generated constructor stub
		this.moleHole = hole;
		this.timeField = timeField;
		this.scoreField = scoreField;
		this.holePanel = holePanel;
		this.lifeField = lifeField;
		this.gradeField = gradeField;
		this.gradeButton = gradeButton;
		this.lifeButton = lifeButton;
		this.stageButton = stageButton;
		this.controlPanel = controlPanel;
		this.westPanel = westPanel;
		this.eastPanel = eastPanel;
		this.downPanel = downPanel;
	}
	
	public void run() {	//������ �޼ҵ�

		System.out.println("Start one molehole for:" + Thread.currentThread().getName());
		moleHole.addActionListener(this);		//�̺�Ʈ ó��
		moleHole.addMouseListener(this);		//���콺 �̺�Ʈ ó���ϱ� ���� �ٿ���
		
		long randomStartTime;	//���۽ð� �������� ����
		long randomUpTime;		//���ۿ��� �ö���� �ð�
		long randomDownTime;	//������� �ð�
		long remainingTime;		//�����ִ� �ð�
		
		randomStartTime = (long)random.nextInt(4000);		//0�� 4000 ������ int�� ���� �߻�
		try {
			Thread.sleep(randomStartTime);
		} catch (InterruptedException e) {
			e.printStackTrace();		//�޼ҵ�� �ѱ�� ���α׷��� �� �޼ҵ忡���� ȣ�⽺���� �μ�
		}
		
		// attention for the up-status of mole. if the ramdon up time is 3s but the game will stop in 1s, choose the min of the two
		while(!timeField.getText().equals("0")){	//�ð��� ����� �� ����(���� ���� ����)
			// UP~~
			
			if(timeField.getText().equals(""))
				continue;
			
			moleHole.setBackground(Color.PINK);
			remainingTime = Long.parseLong(timeField.getText())*1000;		//�����ִ� �ð�
			
			int speed = 100;
			if(remainingTime <= 45000 && remainingTime > 30000){
	            speed = 3500;
	            System.out.println("now speed is "+speed);
	            stageButton.setIcon(new ImageIcon("./resource/stage1.png"));
	         }
	         else if(remainingTime <=30000 && remainingTime > 15000){
	            speed = 2000;
	            System.out.println("now speed is "+speed);
	            stageButton.setIcon(new ImageIcon("./resource/stage2.png"));
	           /* �������� �ٲ�� ���� �ٲ�� �ϰ������.. 
	            controlPanel.setBackground(Color.YELLOW);
	            westPanel.setBackground(Color.YELLOW);
	            eastPanel.setBackground(Color.YELLOW);
	            downPanel.setBackground(Color.YELLOW);
	            */
	         }
	         else if(remainingTime <=15000 && remainingTime > 0){
	            speed = 500;
	            System.out.println("now speed is "+speed);
	            stageButton.setIcon(new ImageIcon("./resource/stage3.png"));
	         }
	         else if(remainingTime == 0)
	            break;
			
			randomUpTime = (long)random.nextInt(speed);	//3500���� �������� ����
			randomUpTime +=500;	//���� uptime
			
			if (randomUpTime<2300) {
				moleHole.setIcon(new ImageIcon("./resource/up.png"));
			}
			else if(randomUpTime<3000){
				moleHole.setIcon(new ImageIcon("./resource/up1.png"));
			}
			else if(randomUpTime<5000){
				moleHole.setIcon(new ImageIcon("./resource/up2.png"));
			}
/*
			else if(randomUpTime<800){
				moleHole.setIcon(new ImageIcon("./resource/up3.png"));
			}
			else {
				moleHole.setIcon(new ImageIcon("./resource/up4.png"));
			}
			*/
			if(randomUpTime>remainingTime){
				try {
					Thread.sleep(remainingTime);	//�����ִ� �ð����� �����带 ���ŷ
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//moleHole.setText(" ");
				break;
			}
			
			else{
				try {
					Thread.sleep(randomUpTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/*-----------------------------hit(x)-----------------------------*/
			
			if(timeField.getText().equals(""))
				continue;
			
			moleHole.setBackground(Color.BLACK);
			
			randomUpTime = (long)random.nextInt(1600);	
			randomUpTime +=500;	//�� ���ڸ� ���̸� �����ִ½ð��� ª��

			if(randomUpTime<800){
				moleHole.setIcon(new ImageIcon("./resource/up3.png"));
			}
			else if(randomUpTime<1600){
				moleHole.setIcon(new ImageIcon("./resource/up4.png"));
			}

			remainingTime = Long.parseLong(timeField.getText())*1000;		//�����ִ� �ð�
			if(remainingTime == 0)
				break;
			
			if(randomUpTime>remainingTime){
				try {
					Thread.sleep(remainingTime);	//�����ִ� �ð����� �����带 ���ŷ
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//moleHole.setText(" ");
				break;
			}
			
			else{
				try {
					Thread.sleep(randomUpTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

			/************************ prof start************************/
//			if(remainingTime <15000 && randomUpTime <600){
//				
//					moleHole.setIcon(new ImageIcon("./resource/prof.png"));
//					lifeButton.setIcon(new ImageIcon("./resource/life_0.png"));
//					synchronized (lifeField) {	//����ȭ �ϱ� ����
//					lifeField.setText(String.valueOf(0));
//					}
//			}
			/************************ prof end*************************/
			
			/*---------------------------chance!!!!!!!!!!----------------------------*/
			if(timeField.getText().equals(""))
				continue;
			
			moleHole.setBackground(Color.YELLOW);
			
			randomUpTime = (long)random.nextInt(4000);	
			randomUpTime +=500;	//�� ���ڸ� ���̸� �����ִ½ð��� ª��
			
			if(randomUpTime<1000){
				moleHole.setIcon(new ImageIcon("./resource/chance.png"));
			}

			remainingTime = Long.parseLong(timeField.getText())*1000;		//�����ִ� �ð�
			if(remainingTime == 0)
				break;
			
			if(randomUpTime>remainingTime){
				try {
					Thread.sleep(remainingTime);	//�����ִ� �ð����� �����带 ���ŷ
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//moleHole.setText(" ");
				break;
			}
			
			else{
				try {
					Thread.sleep(randomUpTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// DOWN~~
			moleHole.setBackground(Color.GREEN);
			//moleHole.setText(" ");
			moleHole.setIcon(new ImageIcon("./resource/down2.png"));
			
			randomDownTime = (long)random.nextInt(2000);
			randomDownTime += 2000;
			remainingTime = Long.parseLong(timeField.getText())*1000;
			if(remainingTime == 0)
				break;
			
			if(randomDownTime > remainingTime){
				try {
					Thread.sleep(remainingTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			else{
				try {
					Thread.sleep(randomDownTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			
		}


		//System.out.println("End game for:" + Thread.currentThread().getName());		
		moleHole.setBackground(Color.GRAY);
		moleHole.setIcon(new ImageIcon("./resource/down2.png"));
		moleHole.setEnabled(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	//��ưŬ���� ����

		//System.out.println("Clicked button:"+Thread.currentThread().getName());
		int score,grade,life;
		if((moleHole.getBackground() == Color.PINK)&&(!timeField.getText().equals("0"))){//Hit�ϰ�, �ð��� 0�� �ƴϸ�
			c_button.start();
			System.out.println("HHHHHIT");
			score = Integer.parseInt(scoreField.getText());	//�����ǿ�
			score=score+2;	//���� ����
			synchronized (scoreField) {	//����ȭ �ϱ� ����
				scoreField.setText(String.valueOf(score));
			}
			System.out.println("Score is:" + scoreField.getText());
			//moleHole.setBackground(Color.ORANGE);
			moleHole.setIcon(new ImageIcon("./resource/hit0.png"));
			
			/*-------------------������ �������� ��ȯ----------------------------*/
			switch(score/5){
	         case 20:
	         case 19:
	         case 18:
	         case 17:
	         case 16:
	         case 15:
	         case 14://70-100
	            gradeField.setText("A+");
	            System.out.println("Grade is: A+");
	            gradeButton.setIcon(new ImageIcon("./resource/A+.png"));
	            break;
	         case 13: //65-69
	            gradeField.setText("A");
	            System.out.println("Grade is: A");
	            gradeButton.setIcon(new ImageIcon("./resource/A.png"));
	            break;
	         case 12:
	         case 11: //55-64
	            gradeField.setText("B+");
	            System.out.println("Grade is: B+");
	            gradeButton.setIcon(new ImageIcon("./resource/B+.png"));
	            break;
	         case 10: //50-54
	            gradeField.setText("B");
	            System.out.println("Grade is: B");
	            gradeButton.setIcon(new ImageIcon("./resource/B.png"));
	            break;
	         case 9://45-49
	            gradeField.setText("C+");
	            System.out.println("Grade is: C+");
	            gradeButton.setIcon(new ImageIcon("./resource/C+.png"));
	            break;
	         case 8: 
	         case 7: //35-44
	            gradeField.setText("C");
	            System.out.println("Grade is: C");
	            gradeButton.setIcon(new ImageIcon("./resource/C.png"));
	            break;
	         case 6: 
	         case 5: //25-34
	            gradeField.setText("D+");
	            System.out.println("Grade is: D+");
	            gradeButton.setIcon(new ImageIcon("./resource/D+.png"));
	            break;
	         case 4:       
	         case 3: 
	         case 2: //10-24
	            gradeField.setText("D");
	            System.out.println("Grade is: D");
	            gradeButton.setIcon(new ImageIcon("./resource/D.png"));
	            break;
	         default:
	            gradeField.setText("F");
	            System.out.println("Grade is: F");
	            gradeButton.setIcon(new ImageIcon("./resource/F.png"));
	            break;
	         }
		}
		/*-------------------������ life �پ��---------------------------------*/
		else if((moleHole.getBackground() == Color.BLACK)&&(!timeField.getText().equals("0"))){
			System.out.println("NO!!");
			life = Integer.parseInt(lifeField.getText());
			if(life>0){
				life--;	
			}
			else {
				TimerThread.counter=1;
			}
			
				//������ �پ��� ��Ʈ �̹����� �ٲ�� �ϰ� ����   
				if(life==4){
					lifeButton.setIcon(new ImageIcon("./resource/life_4.png"));
				}
				else if(life==3){
					lifeButton.setIcon(new ImageIcon("./resource/life_3.png"));
				}
				else if(life==2){
					lifeButton.setIcon(new ImageIcon("./resource/life_2.png"));
				}
				else if(life==1){
					lifeButton.setIcon(new ImageIcon("./resource/life_1.png"));
				}
				else if(life==0){
					lifeButton.setIcon(new ImageIcon("./resource/life_0.png"));
				}
				moleHole.setIcon(new ImageIcon("./resource/hit(X).png"));
				System.out.println("life is:" + lifeField.getText());
				
					synchronized (lifeField) {	//����ȭ �ϱ� ����
						lifeField.setText(String.valueOf(life));
					}
				
			}
		/*---------------------����� chance ������ �� ��Ʈ �ٽ� ����---------------------------------*/
		else if((moleHole.getBackground() == Color.YELLOW) && (!timeField.getText().equals("0"))){
			life = Integer.parseInt(lifeField.getText());
			life ++;
			moleHole.setIcon(new ImageIcon("./resource/hit0.png"));
			System.out.println("life is:" + lifeField.getText());
			
				synchronized (lifeField) {	//����ȭ �ϱ� ����
					lifeField.setText(String.valueOf(life));
				}
				if(life==4){
					lifeButton.setIcon(new ImageIcon("./resource/life_4.png"));
				}
				else if(life==3){
					lifeButton.setIcon(new ImageIcon("./resource/life_3.png"));
				}
				else if(life==2){
					lifeButton.setIcon(new ImageIcon("./resource/life_2.png"));
				}
				else if(life==1){
					lifeButton.setIcon(new ImageIcon("./resource/life_1.png"));
				}
				else if(life==0){
					lifeButton.setIcon(new ImageIcon("./resource/life_0.png"));
				}
			
		}
		
			
	
		if(timeField.getText().equals("0")){	//�ð��� 0�̵Ǹ� 
			moleHole.setBackground(Color.GRAY);	//ȸ������ ����
			moleHole.setIcon(new ImageIcon("./resource/down2.png"));
			moleHole.setEnabled(false);	//��ư ��Ȱ��ȭ
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		holePanel.setCursor(cu2);	//���콺 ������ �� Ŀ�����
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		holePanel.setCursor(cu);	//���콺 ���� �� Ŀ�� ���
	}
}

