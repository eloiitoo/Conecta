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
		Nodo ini=new Nodo(tablero);
		return Completo(ini);
	}
	
	public static int Completo(Nodo ini){
		Completo_CreaArbol(ini);
		Completo_CalcularUtilidad(ini);
		Iterator<Nodo> iterador=ini.hijos.iterator();
		
		System.out.println("Utilidad de la raiz:"+ini.utilidad);
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
		System.out.println("ALEATORIO      ALEATORIO     ALEATORIO");
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


	

}
