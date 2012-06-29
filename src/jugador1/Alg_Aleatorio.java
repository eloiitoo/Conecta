package jugador1;

import java.util.Random;

import tablero.tableroSimple;

public class Alg_Aleatorio {

	
	public static int ElegirColumna(tableroSimple tablero) {
		int columnaEscogida = 0;
		Random rand = new Random();
		
        do {
            columnaEscogida = 1 + rand.nextInt(tablero.getNumColumnas());
            
         } while (tablero.getLlenado(columnaEscogida) == 0);
		
		return columnaEscogida;
	}
	
	
	
	
	
}
