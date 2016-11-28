
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class Game implements ActionListener{
	
	private static final int HOLE_NUM = 16;		//4x4�� �� 16���� Ȧ ����
	//private static final String HOLE = " ";
	private static final Color HOLE_COLOR = Color.GREEN;
	//private static final String MOLE = "M";
	private static final Color MOLE_COLOR = Color.RED;
	//private static final String HIT = "HH";
	private static final Color HIT_COLOR = Color.PINK;
	private static final Color HITX_COLOR = Color.BLACK;
	private static final Color CHANCE = Color.YELLOW;
	
	private static final int COUNT = 45;

	private JButton startButton;	//���� ��ư
	private JTextField timeField;   //�ð� ������ �ʵ�
	private JTextField scoreField;  //���� ������ �ʵ�
	private JButton [] holes; 		//��ư ������ �迭
	private JPanel holePanel;		//��ư ������ �г�
	private JPanel controlPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel downPanel;
	
	private JTextField lifeField; 	//��� ������ �ʵ�
	private JTextField gradeField;	//���� ������ �ʵ�
	private JButton gradeButton;	//���� ������ ��
	private JButton lifeButton;		//������ ��Ʈ �̹���
	private JButton stageButton; 	//�������� �ؽ�Ʈ �����ٲ���

	private Font buttonFont = new Font("Helvetica", Font.BOLD, 12);
	private Font labelFont = new Font("TimesRoman", Font.BOLD, 16);
	private Font textFont = new Font("Courier New", Font.BOLD, 12);
	private Font startFont = new Font("TimesRoman",Font.BOLD,24);
	
	private MoleThread [] moleThreads;
	
	public Game(){ //������ �������� ȭ�� ����
	
		Toolkit tk=Toolkit.getDefaultToolkit();   //�⺻ ��Ŷ�� �����ɴϴ�. 
		Image img=tk.getImage("./resource/hammer.png"); 
		Cursor cu=tk.createCustomCursor(img,new Point(10,10),"stick");  //���콺�� Ŀ�� ����
		/*----------------------------------------------------------------------------------*/

		JFrame gameFrame = new JFrame("Hit Lure!");	//������ ����
		gameFrame.setSize(760, 905);		//���� ȭ�� ������
		gameFrame.setResizable(true);		//������ ũ�� ���� �Ұ�
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//�����ִ� ������ â ����
		
		JPanel table = new JPanel(new BorderLayout());  //North, Center, South, West, East �� 5���� �κ����� ȭ���� ����������

		//Control Panel
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Color.WHITE);
		//stage��ư �ؽ�Ʈ ����
		stageButton = new JButton("");
		stageButton.setIcon(new ImageIcon("./resource/stage1.png"));
		stageButton.setOpaque(false);
		stageButton.setContentAreaFilled(false);
		stageButton.setBorderPainted(false);	
		
		startButton = new JButton("START");		//���۹�ư
		startButton.setFont(startFont);
		//startButton.setIcon(new ImageIcon("./resource/start.png"));
		startButton.addActionListener(this);			//�ʵ忡 ���̱�
		JLabel timeLabel = new JLabel("Time left:");	//�ð� �ؽ�Ʈ
		timeLabel.setFont(labelFont);
		timeField = new JTextField(8);					//�ð��귯�� ĭ 8�� ������
		timeField.setFont(textFont);
		timeField.setEditable(false);					//����ڰ� �ؽ�Ʈ �Է� �Ұ�
		JLabel scoreLabel = new JLabel("Score:");		//���� �ؽ�Ʈ
		scoreLabel.setFont(labelFont);
		scoreField = new JTextField(8);					//���� ������ ĭ
		scoreField.setFont(textFont);		
		scoreField.setEditable(false);					//����ڰ� �ؽ�Ʈ �Է� �Ұ�
		//�߰�====================================
		JPanel downPanel = new JPanel();
		//downPanel.setSize(470, 300);
		JLabel lifeLabel = new JLabel("Life: ");
		lifeLabel.setFont(labelFont);
		lifeField = new JTextField(4);
		lifeField.setFont(labelFont);
		lifeField.setEditable(false);
		
		//JPanel gradePanel = new JPanel();
		JLabel gradeLabel = new JLabel("Grade: ");
		gradeLabel.setFont(labelFont);
		gradeField = new JTextField(4);					//������ ���� ������ �κ�
		gradeField.setFont(labelFont);
		gradeField.setEditable(false);
		
		//Dimension d = new Dimension(100,100);
		gradeButton = new JButton("");
		gradeButton.setIcon(new ImageIcon("./resource/gagebar.png"));
		gradeButton.setOpaque(false);			//��ư ����
		gradeButton.setContentAreaFilled(false);//��ư ���� 
		gradeButton.setBorderPainted(false); 	//��ư ���� - �̹��� �������ϱ� Ƽ�ȳ���
		//downPanel.setBackground(Color.YELLOW); 		//�г� ��� ����
		//���� ��ư ������� ����
		lifeButton = new JButton("");
		lifeButton.setIcon(new ImageIcon("./resource/life_full.png"));
		lifeButton.setOpaque(false);
		lifeButton.setContentAreaFilled(false);
		lifeButton.setBorderPainted(false);
		
		JPanel westPanel = new JPanel();
		westPanel.setBackground(Color.WHITE);
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.WHITE);
			
		//====================================
		/*<���̾ƿ�>*/
		controlPanel.add(stageButton);
		controlPanel.add(startButton);					//���۹�ư ���̱�
		controlPanel.add(timeLabel);					//�ð� �ؽ�Ʈ ���̱�
		controlPanel.add(timeField);					//�ð� ������ ĭ ���̱�
		//controlPanel.add(scoreLabel);					//���� �ؽ�Ʈ ���̱�
		//controlPanel.add(scoreField);					//���� ������ ĭ ���̱�
		controlPanel.add(lifeButton);
		//�߰�====================================
		//downPanel.add(gradeLabel);	
		//downPanel.add(gradeField);					//���� ��Ÿ���� ĭ, �ؽ�Ʈ��
		//downPanel.add(lifeLabel);
		//downPanel.add(lifeField);						//������ ��Ÿ���� ĭ, ���ڷ�
		//downPanel.add(gradeImage);
		//downPanel.add(lifeButton);						//������ �̹��� �����ֱ� ���� ����
		downPanel.add(gradeButton);						//���� ��ư ����� �̹��� ������� ����
		downPanel.setBackground(Color.WHITE);
	    //========================================
		
		//Hole Panel
		holePanel = new JPanel(new GridLayout(4, 4)); 	//������ ���Ϻκ� �׸��� ���̾ƿ� ����
		holePanel.setCursor(cu);						//Ŀ�� ����, cu�� ��ġ �̹����� �ٲ۰�
		holes = new JButton[HOLE_NUM];					//16�� ��ư ����
		for(int i=0; i<HOLE_NUM; i++){					//16�� �ݺ��ؼ�
			holes[i] = new JButton();					//��ư���� �ٲ�
			holes[i].setIcon(new ImageIcon("./resource/down2.png"));		//�ƹ��͵� �������� ���� ���·� ����
			holes[i].setEnabled(false);		//��Ȱ��ȭ
			holePanel.add(holes[i]);		//�гο� �߰���Ŵ
		}
		
		table.add(controlPanel, BorderLayout.PAGE_START);	//������ ó���� ��ġ
		table.add(holePanel, BorderLayout.CENTER);			//������ �߰��� ��ġ

		//=========================================
		table.add(downPanel,BorderLayout.SOUTH);			//������ ���� ��ġ
		
		table.add(westPanel,BorderLayout.WEST);				
		table.add(eastPanel,BorderLayout.EAST);
		//==========================================
		
		gameFrame.setContentPane(table);		//�����ӿ� ����� ����Ʈ �� ����
		gameFrame.setVisible(true);				//â�� ȭ�鿡 ����
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game n = new Game();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {			//��ư ���� �̺�Ʈ �߻� �� ȣ��Ǵ� �޼���
		// TODO Auto-generated method stub
		System.out.println("You've clicked start!");
		Thread myThread = Thread.currentThread();			//���� �������� ������Ʈ�� ������ ������(?)
		myThread.setName("main game thread");
		System.out.println("In the main thread: "+myThread.getName());
		startButton.setEnabled(false);
		scoreField.setText("0");		//����ĭ �ʱⰪ 0���� ����
		timeField.setText("45");		//�ð�ĭ �ʱⰪ 45���� ����
		lifeField.setText("5");			//����� 5���� ����
	
		TimerThread timer = new TimerThread(timeField,startButton,COUNT, holes);		//TimeThread �θ� 
		timer.start();		//�ð� ��� ����
		
		moleThreads = new MoleThread[HOLE_NUM];
		for(int i=0; i<HOLE_NUM;i++){
			holes[i].setEnabled(true);
			holes[i].setFont(buttonFont);
			holes[i].setOpaque(false);		//���̾� �� �������� �ʱ� ����(?)
			
			holes[i].setBackground(Color.GREEN);

			moleThreads[i] = new MoleThread(holes[i], timeField, scoreField, holePanel, lifeField, gradeField, 
					gradeButton, lifeButton, stageButton,controlPanel, westPanel, eastPanel, downPanel);
			moleThreads[i].setName("molehole"+i);
			moleThreads[i].start();
		}

		
		
	}
	


}
