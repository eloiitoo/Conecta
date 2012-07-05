package jugador1;

import java.util.Random;
import java.util.Scanner;

import tablero.tableroSimple;


public class Alg_Aleatorio {

	public static boolean manual=false;//true;

	
	public static int ElegirColumna(tableroSimple tablero) {
		int columnaEscogida = 0;
		Random rand = new Random();
		
		if(manual){	
		
			do {
			System.out.println("[1]  [2]  [3]  [4]  [5]  [6]  [7] ");
			System.out.println("Introduzca la columna donde desea insertar la ficha");
			Scanner ser=new Scanner(System.in);
			columnaEscogida=ser.nextInt();
	         } while ( columnaEscogida<0 || columnaEscogida>tablero.getNumColumnas() || tablero.getLlenado(columnaEscogida) == 0);
			
			}
		else{
		    do {
		        columnaEscogida = 1 + rand.nextInt(tablero.getNumColumnas());
		        
		     } while (tablero.getLlenado(columnaEscogida) == 0);
			}
		return columnaEscogida;
		
	}
	
	
	
	
	
}
