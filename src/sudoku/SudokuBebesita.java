package sudoku;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class SudokuBebesita extends JFrame implements ActionListener {
	private Celdas[][] Celdas;
	private int[][] valores;
        private  ArraySet[] columnas= new ArraySet[9];
        private ArraySet[] filas= new ArraySet[9];
        private ArraySet[] grupos= new ArraySet[9];
	public SudokuBebesita() {
		super("Sudoku");
		
		interfazSudoku();
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	private void interfazSudoku() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel texto=new JLabel("¡Feliz navidad, de parte de las palomas de Costa Rica!"); 
		
		JPanel sudokuPanel = new JPanel();
		sudokuPanel.setLayout(new GridLayout(3, 3, 1, 1));

		JPanel[] cajones = new JPanel[9];
		cajones = interfaz3x3(sudokuPanel, cajones);
		
		valores = new int[9][9];
		Celdas = new Celdas[9][9];
		celdasIntefaz(cajones);

		
		JPanel botones = new JPanel();
                getContentPane().setBackground(Color.green);
		JButton resolver = new JButton("Resolver"); 
                resolver.setBackground(Color.red);
		JButton borrar = new JButton("Borrar"); 
                borrar.setBackground(Color.red);
		
		botones.add(resolver);
		botones.add(borrar);		
		
		resolver.addActionListener(this);
		borrar.addActionListener(this);
		
		panel.add(sudokuPanel);
		panel.add(botones);
                panel.add(texto); 
		add(panel);
	}
	
	private JPanel[] interfaz3x3(JPanel sudokuPanel, JPanel[] cajones) {
		for (int i = 0; i < 9; i++) {
			cajones[i] = new JPanel();
			cajones[i].setLayout(new GridLayout(3, 3, 0, 0));
			cajones[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			sudokuPanel.add(cajones[i]);
		}
		return cajones;
	}
	
	private void celdasIntefaz(JPanel[] cajones) {
		int k = 0;
		
		for (int i = 0; i < 9; i++) {
			if (i <= 2)
				k = 0;
			else 
                            if (i <= 5)
				k = 3;
                            else
				k = 6;
			
			for (int j = 0; j < 9; j++) {
				Celdas[i][j] = new Celdas(i, j);
				cajones[k + (j / 3)].add(Celdas[i][j]);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		String buttonType = button.getText();
		
		if (buttonType.equals("Resolver"))
			empezarResolver();
		else 
                    if (buttonType.equals("Borrar"))
                        
			borrar();
	}
	
	
	private void empezarResolver() {
            boolean band=true;
              for(int i=0; i<9; i++){
          columnas[i]= new ArraySet<Integer>();
             }
            for(int i=0; i<9; i++){
          filas[i]= new ArraySet<Integer>();
            }
      
             for(int i=0; i<9; i++){
          grupos[i]= new ArraySet<Integer>();
             }
             
             
		if (estaLLeno()) {
			JOptionPane.showMessageDialog(getRootPane(),"No hay celdas disponibles.", "Solving Sudoku", JOptionPane.ERROR_MESSAGE);
			return;
		}
                
		if (valido() && band) {
                    
                       
                            if (!Resolver(0, 0))
				JOptionPane.showMessageDialog(getRootPane(), "No se pudo resolver.", "Solving Sudoku", JOptionPane.ERROR_MESSAGE);
                    }
                    
			
		
                else 
			JOptionPane.showMessageDialog(getRootPane(), "No es un sudoku válido.", "Solving Sudoku", JOptionPane.ERROR_MESSAGE);
	}
	
	private void borrar() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if (valores[i][j] != 0) {
					if (Celdas[i][j].editable) {
						Celdas[i][j].setText("");
						valores[i][j] = 0;
					}
				}
			}
	}

        
	private boolean valido() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (valores[i][j] != 0)
					if (estaEn(i,j,valores[i][j]))
						return false;
                                       
                                        
		return true;
	}
	

  
  
  private boolean estaEn(int fila, int col, int val){
      boolean res=true;
    
      int indx= (col/3)+(fila/3)*3;
          if(!columnas[col].contains(val) && !filas[fila].contains(val)){
              if(!grupos[indx].contains(val)){
                  columnas[col].add(val);
                  filas[fila].add(val);
                  grupos[indx].add(val);
                  res=false;
              }
                  }
          
      return res;
  }
	

	
	private boolean estaLLeno() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (valores[i][j] == 0)
					return false;
		return true;
	}
	
	
	private boolean Resolver(int fila, int col) {
		if (fila == 9)
			return true;
		
		if (valores[fila][col] != 0) {
			if (Resolver(col == 8? (fila + 1): fila, (col + 1) % 9))
				return true;
		} else {

			Integer[] al = GenerarNumerosAl();
                        
			for (int i = 0; i < 9; i++) {
				        
				if (!estaEn(fila,col,al[i])) {
					valores[fila][col] = al[i];
					Celdas[fila][col].setText(String.valueOf(al[i]));
                                        
                                        
                                        
                                        
					if (Resolver(col == 8? (fila + 1) : fila, (col + 1) % 9)){
						
                                                 
                                            
                                            return true;
                                        }
					else { 
                                             
                                            int indx=(col/3)+(fila/3)*3;
                                              columnas[col].remove(al[i]);    
                                              filas[fila].remove(al[i]);
                                              grupos[indx].remove(al[i]);
                                            
						valores[fila][col] = 0;
						Celdas[fila][col].setText("");
					}
				}
			}
		}
                
		return false;
	}
	
	
	private Integer[] GenerarNumerosAl() {
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++)
			al.add(i + 1);
		Collections.shuffle(al);
		
		return al.toArray(new Integer[9]);
	}
	
	
	private class Celdas extends JTextField {
            
		private boolean editable;

		public Celdas(final int fila, final int col) {
                    
			editable = true;
			
			setBorder(BorderFactory.createLineBorder(Color.GRAY));
			setFont(new Font("Lucida Console", Font.BOLD, 28));
				
			addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					if (editable)
						if (e.getKeyChar() >= '1' && e.getKeyChar() <= '9') {
							setEditable(true);
							setText(""); 
							valores[fila][col] = e.getKeyChar() - 48;
						} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
							setEditable(true);
							setText("0"); 
							valores[fila][col] = 0;
						} else
							setEditable(false);
				}
			});
		}
	}
	
	
	public static void main(String[] args) {
		new SudokuBebesita();
	}

}