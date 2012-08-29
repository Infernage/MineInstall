/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author Reed
 */
public class Fechas {
    public String dia;
    public String mes;
    public String año;
    public String hora;
    public String minuto;
    public String segundo;
    public String mili;
    public Fechas (String temp){
        //Separamos cada parámetro
        StringTokenizer token = new StringTokenizer (temp, "-");
        StringTokenizer fec = new StringTokenizer (token.nextToken(), "/");
        StringTokenizer hor = new StringTokenizer (token.nextToken(), "/");
        dia = fec.nextToken();
        mes = fec.nextToken();
        año = fec.nextToken();
        hora = hor.nextToken();
        minuto = hor.nextToken();
        segundo = hor.nextToken();
        mili = hor.nextToken();
    }
    public String toString(){
        StringBuilder build = new StringBuilder().append("Copia de ").append(dia).append("/").append(mes).append("/").append(año).append(" - ").append(hora).append(":").append(minuto).append(":").append(segundo);
        return build.toString();
    }
}
