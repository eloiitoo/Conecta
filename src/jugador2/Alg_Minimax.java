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
		int columnaEscogida = 1;
		Nodo ini=new Nodo(tablero);

		
		columnaEscogida=Completo(ini);
		//columnaEscogida=Heurisico(ini);
		//columnaEscogida=Poda(ini);
		
		//ALEATORIO
		/*
		Random rand = new Random();
		
        do {
            columnaEscogida = 1 + rand.nextInt(tablero.getNumColumnas());
            
         } while (tablero.getLlenado(columnaEscogida) == 0);
		*/
		return columnaEscogida;
	}
	
	public static int Completo(Nodo ini){
		Completo_CreaArbol(ini);
		Completo_CalcularUtilidad(ini);
		Iterator<Nodo> iterador=ini.hijos.iterator();
		int accion=1;
		
		System.out.println("Utilidad de la raiz:"+ini.utilidad);
		
		while(iterador.hasNext()){
			Nodo actual=iterador.next();

			if(ini.utilidad==actual.utilidad){
				accion=actual.Accioncolumna;
			}
		}
		return accion;
	}


	private static void Completo_CalcularUtilidad(Nodo actual) {
		// Explande la utilidad de los nodos hojas hasta la raiz

		Iterator<Nodo> iterador=actual.hijos.iterator();
		while(iterador.hasNext()){				
			Completo_CalcularUtilidad(iterador.next());
		}
		if(!actual.esHoja()){
			if(actual.Jugador==Nodo.MAX){
				actual.EligeMax();
			}
			else{
				actual.EligeMin();
			}
			
		}
		
	}

	public static void Completo_CreaArbol(Nodo ini){
		ArrayList<Nodo> sucesores= ini.getSucesores();
		if(sucesores.isEmpty()){
			if(ini.tablerolleno()) ini.utilidad=0;
			if(ini.tablero.isGanador(tipoFichas.FICHA_AZUL)){
				ini.utilidad=1;
			}
			if(ini.tablero.isGanador(tipoFichas.FICHA_ROJA)){
				ini.utilidad=-1;
			}
		}
		else{
			Iterator<Nodo> it=sucesores.iterator();
			while(it.hasNext()){
				Nodo actual=it.next();
				Completo_CreaArbol(actual);
			}
		}
	}
	
	
	public static ArrayList<Nodo> QuitarRepetidos(ArrayList<Nodo> l1,ArrayList<Nodo> l2){//Quita de l1 los Nodos que estan en l2
		Iterator<Nodo> it=l1.iterator();
		ArrayList<Nodo> resultado=new ArrayList<Nodo>();
		Nodo n;
		while(it.hasNext())
		{
			n=it.next();
			if(!esta(n,l2))
				resultado.add(n);
		}
		return resultado;
	}
	private static boolean esta(Nodo n, ArrayList<Nodo> l){
		//No se usa el contains porque compara los hashcode
		Iterator<Nodo> it=l.iterator();
		Nodo aux;
		while(it.hasNext())
		{
			aux=it.next();
			if(aux.iguales(n)){
				return true;
				}
		}
		return false;
	}
}
