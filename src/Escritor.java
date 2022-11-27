import java.io.FileWriter;
import java.io.IOException;

public class Escritor {
    //Almacenar la unica instancia del escritor
    private static Escritor instance;
    private static FileWriter escritorsalida;

    private Escritor(){
        try{
            //Almacenar escritor
            escritorsalida = new FileWriter("output/salida.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Obtener la instancia del unico escritor
    public static Escritor getInstance(){
        if (instance == null) {
            instance = new Escritor();
        }
        return instance;
    }

    //Metodo para cerrar la escritura
    public void escribir(String cadena){
        try {
            escritorsalida.write(cadena);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Metodo para cerrar la escritura
    public void cerrarEscritura(){
        try {
            escritorsalida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
