import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * @author PCZ
 *
 */
public class MainClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = 1;
		for(int t = 1; t <= T; t++){
			int R = Integer.parseInt(JOptionPane.showInputDialog("How many rows?")); // sc.nextInt(); //No. of rows
			int C = Integer.parseInt(JOptionPane.showInputDialog("How many columns?")); //sc.nextInt(); //No. of columns
			int M = Integer.parseInt(JOptionPane.showInputDialog("How many mines?")); //sc.nextInt(); //No. of mines
			int[] slots = new int[R*C];
			/*for(int i = 0; i < R*C; i++){
				slots[i] = 0;
			}*/
			while(M > 0){
				M--;
				int loc = (int) Math.round(R*C*Math.random());
				loc = (loc == 24) ? 23 : loc;
				while(slots[loc] < 0){
					loc = (int) Math.round(R*C*Math.random());
					loc = (loc == 24) ? 23 : loc;
				}
				slots[loc] = -1;
			}
			slots = populateSlots(slots, R, C);
			MSGui gui = new MSGui(slots, R, C);
		}
	}
	
	private static int[] populateSlots(int[] slots, int R, int C){
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				if(slots[i*C + j] < 0){continue;}
				if(j != 0){
					//Check Left
					if(slots[i*C + j - 1] < 0){slots[i*C + j] += 1;}
				}
				if(j != C-1){
					//Check Right
					if(slots[i*C + j + 1] < 0){slots[i*C + j] += 1;}
				}
				if(i != 0){
					//Check Up
					if(slots[(i-1)*C + j] < 0){slots[i*C + j] += 1;}
				}
				if(i != R-1){
					//Check Down
					if(slots[(i+1)*C + j] < 0){slots[i*C + j] += 1;}
				}
				if(j != 0 && i != 0){
					//Check Up-Left
					if(slots[(i-1)*C + j - 1] < 0){slots[i*C + j] += 1;}
				}
				if(j != C-1 && i != 0){
					//Check Up-Right
					if(slots[(i-1)*C + j + 1] < 0){slots[i*C + j] += 1;}
				}
				if(j!= 0 && i != R-1){
					//Check Down-Left
					if(slots[(i+1)*C + j - 1] < 0){slots[i*C + j] += 1;}
				}
				if(j!= C-1 && i != R-1){
					//Check Down-Right
					if(slots[(i+1)*C + j + 1] < 0){slots[i*C + j] += 1;}
				}
			}
		}
		return slots;
	}

}

class MSGui{
	private JFrame frame;
	private JPanel pnl;
	private JButton[][] btns;
	private JLabel lbl;
	private int width=800, height=600;
	private int score = 0;
	public MSGui(int slots[], int R, int C){
		frame = new JFrame("Minesweeper");
		frame.setSize(width, height);
		frame.setLayout(new GridLayout(R+1,C));
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			} 
		});
		lbl = new JLabel("Score: 0");
		btns = new JButton[R][C];
		frame.add(lbl);
		for(int i = 0; i <= R; i++){
			for(int j = 1; j <= C; j++){
				if(i == 0){
					if(j == C){continue;}
					frame.add(new JLabel(""));
				}
				else{
					btns[i-1][j-1] = new JButton(i+","+j);
					btns[i-1][j-1].addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							JButton b = (JButton) e.getSource();
							String[] s = b.getText().split(",");
							if(s.length > 1){
								int x = slots[(Integer.parseInt(s[0])-1)*C + (Integer.parseInt(s[1])-1)];
								if(x < 0){
									b.setText("X");
									gameOver();
								}
								else{
									b.setText(slots[(Integer.parseInt(s[0])-1)*C + (Integer.parseInt(s[1])-1)] + "");
									score += (int) Math.round(99*Math.random()) + 1;
									lbl.setText("Score: " + score);
								}
							}
						}
						
					});
					frame.add(btns[i-1][j-1]);
				}
			}
		}
		frame.setVisible(true);
	}
	
	public void gameOver(){
		JOptionPane.showMessageDialog(null, "Game Over!\nFinal Score: "+score);
		System.exit(0);
	}
}
