public class Traduccion extends JavaScriptParserBaseListener{

    // Declaracion de una variable
    @Override
    public void enterVariableDeclarationList(JavaScriptParser.VariableDeclarationListContext ctx){
        String traduccion = new String();

        //Parte inicial de la traduccion
        traduccion += "You've declared ";

        //Ver si declaro una constante o una
        if (ctx.varModifier().getText().compareTo("const") == 0){
            //Ver si son una o varias constantes las que se declaran
            if (ctx.comma(0) == null){
                traduccion += "a constant";
            } else {
                traduccion += "some constants";
            }
        } else {
            //Ver si son una o varias variables las que se declaran
            if (ctx.comma(0) == null){
                //Ver si es tipo var o tipo let
                if (ctx.varModifier().getText().compareTo("let") == 0){
                    traduccion += "a let type variable";
                }else {
                    traduccion += "a var type variable";
                }
            } else {
                //Ver si es tipo var o tipo let
                if (ctx.varModifier().getText().compareTo("let") == 0){
                    traduccion += "some let type variables";
                }else {
                    traduccion += "some var type variables";
                }
            }
        }

        //Traer instancia para escribir en archivo de salida
        try{
            Escritor.getInstance().escribir(traduccion);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void exitVariableDeclarationList(JavaScriptParser.VariableDeclarationListContext ctx){
        //Traer instancia para escribir en archivo de salida
        //Para escribir un salto de linea al terminar de leer la lista
        try{
            Escritor.getInstance().escribir("\n");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //Traducir la declaracion de una variable despues de un modificador
    @Override
    public void enterVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx){
        String traduccion = new String();

        //Imprimir el nombre de la variable
        traduccion += " "+ctx.assignable().getText();

        //Verificar si la variable se inicializa o no
        if (ctx.Assign() != null){
            traduccion += " with value " + ctx.singleExpression().getText();
        }

        //Traer instancia para escribir en archivo de salida
        try{
            Escritor.getInstance().escribir(traduccion);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //Ver si entra a una comma durante una lista de declaraciones
    @Override
    public void enterComma(JavaScriptParser.CommaContext ctx){
        //Traer instancia para escribir en archivo de salida
        //Remplazar commas por la palabra ,also
        try{
            Escritor.getInstance().escribir(", also");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // Asignacion de una variable ya declarada
    @Override
    public void enterAssignmentExpression(JavaScriptParser.AssignmentExpressionContext ctx){
        String traduccion = new String();

        //Ver si es un string individual el que se esta asignando a la variable
        if (ctx.singleExpression(1).getText().charAt(0) == '\"'){
            //Parte inicial de la traduccion
            traduccion += "You've assigned the string ";
        } else {
            //Parte inicial de la traduccion
            traduccion += "You've assigned the value ";
        }

        //Imprimir la expresion a asignar
        traduccion += ctx.singleExpression(1).getText();

        //Imprimir la expresion a asignar
        traduccion +=" to the variable ";

        //Imprimir el identificador
        traduccion +=ctx.singleExpression(0).getText();

        //Traer instancia para escribir de salida
        try{
            Escritor.getInstance().escribir(traduccion+"\n");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //Entrar a una estructura condicional IF
    @Override
    public void enterIfStatement(JavaScriptParser.IfStatementContext ctx){
        String traduccion = new String();

        //Verificar si es IF o solo IF-ELSE
        if (ctx.else_() == null){
            //Entraste a un if con cierta condicion
            traduccion += "You just got in an IF statement, with the condition ";
        } else {
            //Entraste a un if con cierta condicion
            traduccion += "You just got in an IF-ELSE statement, with the condition ";
        }

        //Agregar la expresion condicional
        traduccion += ctx.expressionSequence().getText() + "\n";

        //Decir que inician los substatements
        traduccion += "The IF sub-statements are: ";

        //Traer instancia para escribir de salida
        try{
            Escritor.getInstance().escribir(traduccion+"\n");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //En caso de entrar a un else
    @Override
    public void enterElse(JavaScriptParser.ElseContext ctx){
        String traduccion = new String();
        //Decir que termino el if
        traduccion += "The IF sub-statements are over \n";

        //Decir que inicio un else
        traduccion += "The ELSE sub-statements are:";

        //Traer instancia para escribir de salida
        try{
            Escritor.getInstance().escribir(traduccion+"\n");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //Salir de la estructura IF,  IF-ELSE
    @Override
    public void exitIfStatement(JavaScriptParser.IfStatementContext ctx){
        String traduccion = new String();

        //Decir que termino la estructura condicional
        traduccion += "The conditional structure is over";

        //Traer instancia para escribir de salida
        try{
            Escritor.getInstance().escribir(traduccion+"\n");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
