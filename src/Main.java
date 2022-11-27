import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import javax.speech.Central;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Synthesizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        try{
            // create a CharStream that reads from standard input / file
            // create a lexer that feeds off of input CharStream
            JavaScriptLexer lexer;

            if (args.length>0)
                lexer = new JavaScriptLexer(CharStreams.fromFileName(args[0]));
            else
                lexer = new JavaScriptLexer(CharStreams.fromStream(System.in));
            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream((TokenSource) lexer);
            // create a parser that feeds off the tokens buffer
            JavaScriptParser parser = new JavaScriptParser(tokens);
            ParseTree tree = parser.program(); // begin parsing at init rule
            // Create a generic parse tree walker that can trigger callbacks
            ParseTreeWalker walker = new ParseTreeWalker();
            // Walk the tree created during the parse, trigger callbacks
            walker.walk(new Traduccion(), tree);

            //Cerrar escritura archivo salida.txt
            Escritor.getInstance().cerrarEscritura();

            //Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
             + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            //Bucle para leer cada linea del archivo y hablarla
            BufferedReader reader;
            try{
                reader = new BufferedReader(new FileReader("output/salida.txt"));
                String line = reader.readLine();
                line = line.replace("*"," Times ");
                line = line.replace("("," Opening Bracket ");
                line = line.replace(")"," Closing Bracket ");
                //Bucle para leer todas las lineas
                while (line != null){
                    // Speaks the given text
                    // until the queue is empty.
                    synthesizer.speakPlainText(
                            line, null);
                    //Leer la siguiente linea
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }

            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);

            // Deallocate the Synthesizer.
            synthesizer.deallocate();



        } catch (Exception e){
            System.err.println("Error (Main): " + e);
        }
    }
}