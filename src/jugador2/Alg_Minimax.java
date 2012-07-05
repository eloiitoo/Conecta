package jugador2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


import tablero.tableroSimple;
import tablero.tipoFichas;

public class Alg_Minimax {

	static int Cols = 0;
	
	public static int ElegirColumna(tableroSimple tablero) {
		// COLOCAR AQUI EL CODIGO!!
		int columna;
		
		//Medimos el tiempo de ejecuci�n
		long tiempoInicio = System.currentTimeMillis();
		
		
		Nodo ini=new Nodo(tablero);
		//columna=Completo(ini);
		columna=Heuristica(ini,5);
		//columna=Poda(ini,5);

		
		//Medimos el tiempo de ejecuci�n
		long totalTiempo = System.currentTimeMillis() - tiempoInicio;
		System.out.println("El tiempo de resoluci�n es :" + totalTiempo + " miliseg");
		
		
		return columna;
	}


	//////////////Generacion infinita del arbol. (con Mini-Servidor)//////////////Generacion infinita del arbol. (con Mini-Servidor)////////////
	public static int Completo(Nodo ini){
		Completo_CreaArbol(ini);
		Completo_CalcularUtilidad(ini);
		Iterator<Nodo> iterador=ini.hijos.iterator();
		
		//System.out.println("Utilidad de la raiz:"+ini.utilidad);
		while(iterador.hasNext()){//INTENTO GANAR 
			Nodo actual=iterador.next();

			if(actual.utilidad==1){
				return actual.Accioncolumna;
			
			
			}
		}
		iterador=ini.hijos.iterator();
		while(iterador.hasNext()){//SI NO UEDO GANAR INTENTO EMPATAR
			Nodo actual=iterador.next();

			if(actual.utilidad==0){
				return actual.Accioncolumna;
			}
		}
		
		

		//Si no puedo ganar ni empartar --> Aleatorio
		//System.out.println("ALEATORIO      ALEATORIO     ALEATORIO");
		Random rand = new Random();
		int accion=1;
		do {
            accion = 1 + rand.nextInt(ini.tablero.getNumColumnas());
            
         } while (ini.tablero.getLlenado(accion) == 0);
		
		
		return accion;
	}

	public static void Completo_CreaArbol(Nodo ini){
		ArrayList<Nodo> sucesores= ini.getSucesores();
		Iterator<Nodo> it=sucesores.iterator();
		while(it.hasNext()){
			Completo_CreaArbol(it.next());
		}
	}
	
	public static void Completo_CalcularUtilidad(Nodo ini) {
		// Explande la utilidad de los nodos hojas hasta la raiz

		Iterator<Nodo> iterador=ini.hijos.iterator();
		while(iterador.hasNext()){				
			Completo_CalcularUtilidad(iterador.next());
		}
		
		if(ini.esHoja()){
			if(ini.tablerolleno()) ini.utilidad=0;
			if(ini.tablero.isGanador(tipoFichas.FICHA_AZUL)){
				ini.utilidad=1;
			}
			if(ini.tablero.isGanador(tipoFichas.FICHA_ROJA)){
				ini.utilidad=-1;
			}
		}
		else{
			if(ini.Jugador==Nodo.MAX){
				ini.EligeMax();
			}
			else{
				ini.EligeMin();
			}
			
		}
		
	}

/////////////////Generacion limitada con heuristica./////////////////Generacion limitada con heuristica./////////////////
	public static int Heuristica(Nodo ini,int limite){
		Heuristica_CreaArbol(ini,limite);
		Heutistica_CalcularUtilidad(ini);
		Iterator<Nodo> iterador=ini.hijos.iterator();
		//System.out.println("Utilidad de la raiz:"+ini.utilidad);
		while(iterador.hasNext()){//INTENTO GANAR 
			Nodo actual=iterador.next();

			if(actual.utilidad==ini.utilidad){
				return actual.Accioncolumna;
			}
		}
		
		//Si algo falla --> Aleatorio
		System.out.println("HEURISTICA  ALEATORIO    ALEATORIO     ALEATORIO");
		Random rand = new Random();
		int accion=1;
		do {
            accion = 1 + rand.nextInt(ini.tablero.getNumColumnas());
            
         } while (ini.tablero.getLlenado(accion) == 0);
		
		
		return accion;
	}

	public static void Heuristica_CreaArbol(Nodo ini,int limite) {
		if(limite!=0){
			ArrayList<Nodo> sucesores= ini.getSucesores();
			Iterator<Nodo> it=sucesores.iterator();
			while(it.hasNext()){
				Heuristica_CreaArbol(it.next(),limite-1);
			}
		}
	}

	public static void Heutistica_CalcularUtilidad(Nodo ini) {
		// Expande la utilidad de los nodos hojas hasta la raiz

		Iterator<Nodo> iterador=ini.hijos.iterator();
		while(iterador.hasNext()){				
			Heutistica_CalcularUtilidad(iterador.next());
		}
		
		if(ini.esHoja()){
			ini.calcula_utilidad();
		}
		else{
			if(ini.Jugador==Nodo.MAX){
				ini.EligeMax();
			}
			else{
				ini.EligeMin();
			}
		}
	}
	
	///////PODA/////////PODA/////////PODA/////////PODA/////////PODA/////////PODA/////////PODA////
	public static int Poda(Nodo ini, int limite){
		Poda_CreaArbol(ini,limite);
		Iterator<Nodo> iterador=ini.hijos.iterator();
		//System.out.println("Utilidad de la raiz:"+ini.utilidad);
		while(iterador.hasNext()){
			Nodo actual=iterador.next();

			if(actual.utilidad==ini.utilidad){
				return actual.Accioncolumna;
			}
		}
		
		//Si algo falla --> Aleatorio
		System.out.println("PODA  ALEATORIO      ALEATORIO     ALEATORIO");
		Random rand = new Random();
		int accion=1;
		do {
            accion = 1 + rand.nextInt(ini.tablero.getNumColumnas());
            
         } while (ini.tablero.getLlenado(accion) == 0);
		
		
		return accion;
	}
	
	
	private static void Poda_CreaArbol(Nodo ini, int limite) {
		if(limite==0){
			ini.calcula_utilidad();
		}
		else{
			ArrayList<Nodo> sucesores= ini.getSucesores();
			if(ini.esHoja()){
				ini.calcula_utilidad();
			}
			else{
				Iterator<Nodo> it=sucesores.iterator();
				while(it.hasNext()&&ini.alfa<ini.beta){
					Nodo actual=it.next();
					Poda_CreaArbol(actual,limite-1);
					if(ini.Jugador==Nodo.MAX && actual.utilidad>ini.alfa){
						ini.alfa=actual.utilidad;
						ini.utilidad=actual.utilidad;
					}
					if(ini.Jugador==Nodo.MIN && actual.utilidad<ini.beta){
						ini.beta=actual.utilidad;
						ini.utilidad=actual.utilidad;
					}
				}
				//if(ini.alfa<ini.beta) System.out.println("HE PODADO");
			}
		}
			
	}



	
}
