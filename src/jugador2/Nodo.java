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
	
	public Nodo(tableroSimple tablero){
		this.tablero=clonar(tablero);
		hijos=null;
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
			if(tablero.getLlenado(i)>3){
				n=new Nodo(tablero);
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
		if(this.hijos.isEmpty())return true;
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
	public boolean iguales(Nodo n) {
		for(int i=1;i<=tablero.getNumColumnas();i++){
			if(n.tablero.getLlenado(i)!=this.tablero.getLlenado(i)){
				return false;
			}
		}
		
		for(int i=1;i<=this.tablero.getNumColumnas();i++){
			for(int j=0;j<this.tablero.getNumFilas()-this.tablero.getLlenado(i);j++){
				if(this.tablero.getTipoFicha(this.tablero.getNumFilas()-j,i) != n.tablero.getTipoFicha(n.tablero.getNumFilas()-j,i)) 
					return false;
			}
		}
		
		return true;
	}
	public void sethijos(ArrayList<Nodo> sucesores) {
		this.hijos=sucesores;
		
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
	}
	
}