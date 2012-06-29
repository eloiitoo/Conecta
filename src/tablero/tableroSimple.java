package tablero;


import java.io.Serializable;


/**
 * 
 * Representaci?n del tablero no gr?fica, contiene sus dimensiones, la cantidad
 * de espacios libres en cada columna, la cantidad de espacios libres totales(para facilitar
 * la deteccion de empates) y una matriz donde cada celda indica el tipo de la ficha que tiene o no tiene.
 * 
 */

@SuppressWarnings("serial")
public class tableroSimple implements Serializable {	
	
   private tipoFichas[][] tablero;
   private int[] llenado;
   private int numFilas;
   private int numColumnas;
   private int numEspacios;
   private int LONG_SOLUCION = 3;//=4

   public tableroSimple(int _numFilas, int _numColumnas,int c) {
      tablero = new tipoFichas[_numFilas][_numColumnas];
      llenado = new int[tablero[0].length];
      this.numFilas = _numFilas;
      this.numColumnas = _numColumnas;
      this.numEspacios = this.numColumnas * this.numFilas;
      this.resetear();
   }

   /**************************************
    *
    **************************************/
   public final void resetear() {
      int i = 0;
      int j = 0;
      for (i = 0; i < llenado.length; i++) {
         llenado[i] = tablero.length;

      }

      for (i = 0; i < this.numFilas; i++) {
         for (j = 0; j < this.numColumnas; j++) {
            tablero[i][j] = tipoFichas.CELDA_VACIA;
         }
      }
      this.numEspacios = this.numColumnas * this.numFilas;
   }

   /**
    *
    * @param columna
    * @param tipoFicha
    */
   public void colocarFicha(int columna, tipoFichas tipoFicha) {
      int columnaReal = columna - 1;
      boolean celdaVacia = false;
      int i = tablero.length - 1;

      do {
         if (tablero[i][columnaReal] == tipoFichas.CELDA_VACIA) {
            tablero[i][columnaReal] = tipoFicha;
            restLlenado(columnaReal);
            this.numEspacios--;
            celdaVacia = true;
         }
         i--;
      }
      while ((i >= 0) && !celdaVacia);

     // if (this.numEspacios == 0) {
     //    this.partidaTerminada = true;
     // }
   }

   public int getEspacios() {
      return this.numEspacios;
   }

   /**
    *
    * @param ficha
    * @return boolean
    */
   public boolean isGanador(tipoFichas ficha) {
      
      for (int i = 0; i < tablero.length; i++) {
         for (int j = 0; j < tablero[0].length; j++) {

            int horizontal = 0;
            int vertical = 0;
            int diagonalDerecha = 0;
            int diagonalIzquierda = 0;

            for (int k = 0; k < LONG_SOLUCION; k++) {

               if (i + k < tablero.length) {
                  if (tablero[i + k][j] == ficha) {
                     horizontal++;
                  }
               }

               if (j + k < tablero[0].length) {
                  if (tablero[i][j + k] == ficha) {
                     vertical++;
                  }
               }

               if (i + k < tablero.length && j + k < tablero[0].length) {
                  if (tablero[i + k][j + k] == ficha) {
                     diagonalDerecha++;
                  }
               }

               if (i - k >= 0 && j + k < tablero[0].length) {
                  if (tablero[i - k][j + k] == ficha) {
                     diagonalIzquierda++;
                  }
               }

               if (horizontal == LONG_SOLUCION || 
            		   vertical == LONG_SOLUCION || 
            		   diagonalDerecha == LONG_SOLUCION || 
            		   diagonalIzquierda == LONG_SOLUCION) {
                //  this.setPartidaTerminada(true);
                  return true;
               }
            }
         }
      }
    //  this.setPartidaTerminada(false);
      return false;
   }

   /**
    *
    * @return
    */
   public boolean isTableroCompleto() {
      return (this.numEspacios == 0);
   }

   /**
    *
    * @return TipoFichas[][]
    */
   public tipoFichas[][] getTablero() {
      return tablero;
   }

   public int getNumFilas() {
      return this.numFilas;
   }

   public int getNumColumnas() {
      return this.numColumnas;
   }

   public tipoFichas getTipoFicha(int fila, int columna) {
      return this.tablero[fila - 1][columna - 1];
   }

   /**
    *
    * @param tablero
    */
   public void setTablero(tipoFichas[][] tablero) {
      this.tablero = tablero;
   }

   /**
    *
    * @return
    */
   //public boolean isPartidaTerminada() {
  //    return partidaTerminada;
  // }

   /**
    *
    * @param partidaTerminada
    */
   //public void setPartidaTerminada(boolean partidaTerminada) {
   //   this.partidaTerminada = partidaTerminada;
  // }

   /**
    *
    * @return
    */
   public int getLlenado(int columna) {
      return llenado[columna - 1];
   }

   /**
    *
    * @param llenado
    */
   private void restLlenado(int columna) {
      if (this.llenado[columna] > 0) {
         this.llenado[columna]--;
      }
   }

   public void imprimirTablero() {
      for (int i = 0; i < tablero.length; i++) {
         for (int j = 0; j < tablero[0].length; j++) {
            System.out.print(tablero[i][j] + "\t");
         }
         System.out.println("");
      }
   }
}
