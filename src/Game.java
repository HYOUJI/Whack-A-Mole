
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
	
	private static final int HOLE_NUM = 16;		//4x4로 총 16개의 홀 생성
	//private static final String HOLE = " ";
	private static final Color HOLE_COLOR = Color.GREEN;
	//private static final String MOLE = "M";
	private static final Color MOLE_COLOR = Color.RED;
	//private static final String HIT = "HH";
	private static final Color HIT_COLOR = Color.PINK;
	private static final Color HITX_COLOR = Color.BLACK;
	private static final Color CHANCE = Color.YELLOW;
	
	private static final int COUNT = 45;

	private JButton startButton;	//시작 버튼
	private JTextField timeField;   //시간 보여줄 필드
	private JTextField scoreField;  //점수 보여줄 필드
	private JButton [] holes; 		//버튼 저장할 배열
	private JPanel holePanel;		//버튼 보여줄 패널
	private JPanel controlPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel downPanel;
	
	private JTextField lifeField; 	//목숨 보여줄 필드
	private JTextField gradeField;	//학점 보여줄 필드
	private JButton gradeButton;	//학점 게이지 바
	private JButton lifeButton;		//라이프 하트 이미지
	private JButton stageButton; 	//스테이지 텍스트 보여줄꺼야

	private Font buttonFont = new Font("Helvetica", Font.BOLD, 12);
	private Font labelFont = new Font("TimesRoman", Font.BOLD, 16);
	private Font textFont = new Font("Courier New", Font.BOLD, 12);
	private Font startFont = new Font("TimesRoman",Font.BOLD,24);
	
	private MoleThread [] moleThreads;
	
	public Game(){ //게임의 전반적인 화면 구성
	
		Toolkit tk=Toolkit.getDefaultToolkit();   //기본 툴킷을 가져옵니다. 
		Image img=tk.getImage("./resource/hammer.png"); 
		Cursor cu=tk.createCustomCursor(img,new Point(10,10),"stick");  //마우스의 커서 변경
		/*----------------------------------------------------------------------------------*/

		JFrame gameFrame = new JFrame("Hit Lure!");	//프레임 제목
		gameFrame.setSize(760, 905);		//게임 화면 사이즈
		gameFrame.setResizable(true);		//프레임 크기 변경 불가
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//열려있는 윈도우 창 종료
		
		JPanel table = new JPanel(new BorderLayout());  //North, Center, South, West, East 총 5개의 부분으로 화면이 나뉘어진다

		//Control Panel
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Color.WHITE);
		//stage버튼 텍스트 설정
		stageButton = new JButton("");
		stageButton.setIcon(new ImageIcon("./resource/stage1.png"));
		stageButton.setOpaque(false);
		stageButton.setContentAreaFilled(false);
		stageButton.setBorderPainted(false);	
		
		startButton = new JButton("START");		//시작버튼
		startButton.setFont(startFont);
		//startButton.setIcon(new ImageIcon("./resource/start.png"));
		startButton.addActionListener(this);			//필드에 붙이기
		JLabel timeLabel = new JLabel("Time left:");	//시간 텍스트
		timeLabel.setFont(labelFont);
		timeField = new JTextField(8);					//시간흘러갈 칸 8은 사이즈
		timeField.setFont(textFont);
		timeField.setEditable(false);					//사용자가 텍스트 입력 불가
		JLabel scoreLabel = new JLabel("Score:");		//점수 텍스트
		scoreLabel.setFont(labelFont);
		scoreField = new JTextField(8);					//점수 보여줄 칸
		scoreField.setFont(textFont);		
		scoreField.setEditable(false);					//사용자가 텍스트 입력 불가
		//추가====================================
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
		gradeField = new JTextField(4);					//실제로 학점 보여줄 부분
		gradeField.setFont(labelFont);
		gradeField.setEditable(false);
		
		//Dimension d = new Dimension(100,100);
		gradeButton = new JButton("");
		gradeButton.setIcon(new ImageIcon("./resource/gagebar.png"));
		gradeButton.setOpaque(false);			//버튼 투명
		gradeButton.setContentAreaFilled(false);//버튼 투명 
		gradeButton.setBorderPainted(false); 	//버튼 투명 - 이미지 씌웠으니까 티안나게
		//downPanel.setBackground(Color.YELLOW); 		//패널 배경 색깔
		//학점 버튼 만들듯이 만듬
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
		/*<레이아웃>*/
		controlPanel.add(stageButton);
		controlPanel.add(startButton);					//시작버튼 붙이기
		controlPanel.add(timeLabel);					//시간 텍스트 붙이기
		controlPanel.add(timeField);					//시간 보여줄 칸 붙이기
		//controlPanel.add(scoreLabel);					//점수 텍스트 붙이기
		//controlPanel.add(scoreField);					//점수 보여줄 칸 붙이기
		controlPanel.add(lifeButton);
		//추가====================================
		//downPanel.add(gradeLabel);	
		//downPanel.add(gradeField);					//학점 나타내는 칸, 텍스트로
		//downPanel.add(lifeLabel);
		//downPanel.add(lifeField);						//라이프 나타내는 칸, 숫자로
		//downPanel.add(gradeImage);
		//downPanel.add(lifeButton);						//라이프 이미지 보여주기 위해 붙임
		downPanel.add(gradeButton);						//성적 버튼 만들고 이미지 씌우려고 만듬
		downPanel.setBackground(Color.WHITE);
	    //========================================
		
		//Hole Panel
		holePanel = new JPanel(new GridLayout(4, 4)); 	//구멍이 보일부분 그리드 레이아웃 설정
		holePanel.setCursor(cu);						//커서 설정, cu는 망치 이미지로 바꾼거
		holes = new JButton[HOLE_NUM];					//16개 버튼 생성
		for(int i=0; i<HOLE_NUM; i++){					//16번 반복해서
			holes[i] = new JButton();					//버튼으로 바꿈
			holes[i].setIcon(new ImageIcon("./resource/down2.png"));		//아무것도 나와있지 않은 상태로 설정
			holes[i].setEnabled(false);		//비활성화
			holePanel.add(holes[i]);		//패널에 추가시킴
		}
		
		table.add(controlPanel, BorderLayout.PAGE_START);	//페이지 처음에 배치
		table.add(holePanel, BorderLayout.CENTER);			//페이지 중간에 배치

		//=========================================
		table.add(downPanel,BorderLayout.SOUTH);			//페이지 끝에 배치
		
		table.add(westPanel,BorderLayout.WEST);				
		table.add(eastPanel,BorderLayout.EAST);
		//==========================================
		
		gameFrame.setContentPane(table);		//프레임에 연결된 컨텐트 팬 변경
		gameFrame.setVisible(true);				//창을 화면에 보임
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game n = new Game();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {			//버튼 관련 이벤트 발생 시 호출되는 메서드
		// TODO Auto-generated method stub
		System.out.println("You've clicked start!");
		Thread myThread = Thread.currentThread();			//현재 실행중인 오브젝트의 참조를 돌려줌(?)
		myThread.setName("main game thread");
		System.out.println("In the main thread: "+myThread.getName());
		startButton.setEnabled(false);
		scoreField.setText("0");		//점수칸 초기값 0으로 설정
		timeField.setText("45");		//시간칸 초기값 45으로 설정
		lifeField.setText("5");			//목숨은 5개로 설정
	
		TimerThread timer = new TimerThread(timeField,startButton,COUNT, holes);		//TimeThread 부름 
		timer.start();		//시간 재기 시작
		
		moleThreads = new MoleThread[HOLE_NUM];
		for(int i=0; i<HOLE_NUM;i++){
			holes[i].setEnabled(true);
			holes[i].setFont(buttonFont);
			holes[i].setOpaque(false);		//레이어 간 간섭받지 않기 위해(?)
			
			holes[i].setBackground(Color.GREEN);

			moleThreads[i] = new MoleThread(holes[i], timeField, scoreField, holePanel, lifeField, gradeField, 
					gradeButton, lifeButton, stageButton,controlPanel, westPanel, eastPanel, downPanel);
			moleThreads[i].setName("molehole"+i);
			moleThreads[i].start();
		}

		
		
	}
	


}
