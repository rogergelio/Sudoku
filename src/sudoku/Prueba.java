/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

public class Prueba {

  
  public static void llena(int[][] a){
      int sum=0;
      for(int i=0; i<a.length; i++){
          sum=sum+1;
          for(int j=0; j<a.length; j++){
              sum=sum+1;
              a[i][j]=sum;
          }
      }
  }
  
  
  	public static boolean estaEnFilaCol(int fila, int col, int[][] valores, int val) {
		ArraySet c= new ArraySet();
                boolean res=true;
           //Busca en los renglones y columnas
               for(int i=0; i<9; i++){
                   c.add(valores[fila][i]);
                   c.add(valores[i][col]);
               }
               if(!c.contains(val)){
                   res=false;
                   valores[fila][col]=val;
               }

               return res;    
	}
	
	
	
  
  
  public static void imprimeMatriz(int [][] a){
      for (int x=0; x < a.length; x++) {
  System.out.print("|");
  for (int y=0; y < a[x].length; y++) {
    System.out.print (a[x][y]);
    if (y!=a[x].length-1) System.out.print("\t");
  }
  System.out.println("|");
}
  }
          
    public static void main(String[] args) {
        int [][] a= new int[9][9];
        llena(a);
        imprimeMatriz(a);
        System.out.println(estaEnFilaCol(3,4,a,90));
        System.out.println(estaEnFilaCol(3,4,a,32));

    }
  

}
