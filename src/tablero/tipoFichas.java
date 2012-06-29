package tablero;

import java.io.Serializable;



/**
 * Estados en los que pueden encontrarse cada celda de un tablero de juego
 * @author Marco Antonio Martin Callejo
 */
public enum tipoFichas implements Serializable
{
    CELDA_VACIA,

    FICHA_ROJA,

    FICHA_AZUL;
}
