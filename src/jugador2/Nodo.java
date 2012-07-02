package jugador2;


import java.util.ArrayList;
import java.util.Iterator;
import tablero.tableroSimple;
import tablero.tipoFichas;


public class Nodo {
	public tableroSimple tablero;
	public Nodo padre;
	public ArrayList<Nodo> hijos;
	public int Accioncolumna;
	public int utilidad;
	public int Jugador; //0->MAX 	1->MIN
		public static int MAX=0;
		public static int MIN=1;
	public int alfa=Integer.MIN_VALUE;
	public int beta=Integer.MAX_VALUE;
	
	public Nodo(tableroSimple tablero){
		this.tablero=clonar(tablero);
		hijos=new ArrayList<Nodo>();
		padre=null;
		Accioncolumna=1;//cualquiera
		utilidad=0;//cualquiera
		Jugador=MAX;//Empieza MAX
	}
	public static tableroSimple clonar(tableroSimple tablero){
		tableroSimple clon=new tableroSimple(tablero.getNumFilas(),tablero.getNumColumnas(),0);// el 0 no se utiliza
		for(int i=1;i<=tablero.getNumColumnas();i++){
			for(int j=0;j<tablero.getNumFilas()-tablero.getLlenado(i);j++){
				clon.colocarFicha(i, tablero.getTipoFicha(tablero.getNumFilas()-j,i));
			}
		}
		return clon;
	}

	public ArrayList<Nodo> getSucesores() {//NOSOTROS SOMOS LOS AZULES 
		//HACER LOS SUCESORES
		ArrayList<Nodo> sucesores=new ArrayList<Nodo>();
		if( tablero.isGanador(tipoFichas.FICHA_AZUL) || tablero.isGanador(tipoFichas.FICHA_ROJA)){
			this.hijos=sucesores;
			return sucesores;
		}
		
		
		Nodo n;
		for(int i=1;i<=tablero.getNumColumnas();i++){
			if(tablero.getLlenado(i)!=0){
				n=new Nodo(tablero);
				n.alfa=this.alfa;
				n.beta=this.beta;
				n.padre=this;
				n.Accioncolumna=i;
				
				if(this.Jugador==MAX){
					n.Jugador=MIN;
					n.tablero.colocarFicha(i, tipoFichas.FICHA_AZUL);
					}
				else{
					n.Jugador=MAX;
					n.tablero.colocarFicha(i, tipoFichas.FICHA_ROJA);
				}

				sucesores.add(n);
			}
		}
		this.hijos=sucesores;//si no tiene hijos--> hoja y hijos=ArrayList vacio(isEmpty)

		
		
		return sucesores;
	}
	public boolean esHoja() {
		if(this.hijos.isEmpty()) return true;
		else return false;
	}
	public boolean tablerolleno() {
		for(int i=1;i<=tablero.getNumColumnas();i++){
			if(tablero.getLlenado(i)!=0){
				return false;
			}
		}
		return true;
	}


	public void EligeMax() {
		// Elige el mejor de sus hijos
		int max=Integer.MIN_VALUE;
		Iterator<Nodo> iterador=this.hijos.iterator();
		while(iterador.hasNext()){
			Nodo actual=iterador.next();
			if(actual.utilidad>max){
				max=actual.utilidad;
			}
		}
		this.utilidad=max;
		//System.out.println("Utilidad MAX:"+this.utilidad);

	}
	
	public void EligeMin() {
		// Elige el peor de sus hijos
		int min=Integer.MAX_VALUE;
		Iterator<Nodo> iterador=this.hijos.iterator();
		while(iterador.hasNext()){
			Nodo actual=iterador.next();
			if(actual.utilidad<min){
				min=actual.utilidad;
			}
		}
		this.utilidad=min;
		//System.out.println("Utilidad MIN:"+this.utilidad);
	}
	public void calcula_utilidad() {
		int azul,rojo;
		azul=voiGanando(tipoFichas.FICHA_AZUL);
		rojo=voiGanando(tipoFichas.FICHA_ROJA);
		this.utilidad=azul-rojo;
	}
	   public int voiGanando(tipoFichas ficha) {
		   int util=0;   
		   
		      for (int i = 0; i < this.tablero.getTablero().length; i++) {
		         for (int j = 0; j < this.tablero.getTablero()[0].length; j++) {

		            int horizontal = 0;
		            int vertical = 0;
		            int diagonalDerecha = 0;
		            int diagonalIzquierda = 0;
		            int LONG_SOLUCION=4;//<-----------deberia ser un 4
		            for (int k = 0; k < LONG_SOLUCION; k++) {

		               if (i + k < this.tablero.getTablero().length) {
		                  if (this.tablero.getTablero()[i + k][j] == ficha) {
		                     horizontal++;
		                  }
		               }

		               if (j + k < this.tablero.getTablero()[0].length) {
		                  if (this.tablero.getTablero()[i][j + k] == ficha) {
		                     vertical++;
		                  }
		               }

		               if (i + k < this.tablero.getTablero().length && j + k < this.tablero.getTablero()[0].length) {
		                  if (this.tablero.getTablero()[i + k][j + k] == ficha) {
		                     diagonalDerecha++;
		                  }
		               }

		               if (i - k >= 0 && j + k < this.tablero.getTablero()[0].length) {
		                  if (this.tablero.getTablero()[i - k][j + k] == ficha) {
		                     diagonalIzquierda++;
		                  }
		               }

		               if (horizontal == LONG_SOLUCION || 
		            		   vertical == LONG_SOLUCION || 
		            		   diagonalDerecha == LONG_SOLUCION || 
		            		   diagonalIzquierda == LONG_SOLUCION) {
		                //  this.setPartidaTerminada(true);
		                  return Integer.MAX_VALUE;//SI HE GANADO SIGO ESTE CAMINO SEGURO
		               }
		               if (horizontal == LONG_SOLUCION-1 || 
		            		   vertical == LONG_SOLUCION-1 || 
		            		   diagonalDerecha == LONG_SOLUCION-1 || 
		            		   diagonalIzquierda == LONG_SOLUCION-1) {
		            	   util++;
		            	   
		               }
		               
		            }
		         }
		      }
		    //  this.setPartidaTerminada(false);
		      return util*util;
		   }
	   public void setAlfaBeta(int a,int b){
		   this.alfa=a;
		   this.beta=b;
	   }
}
