package umg.dem1.Metodos.VolumenSolido;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;

import java.util.Scanner;

public class Cascarones {

    public void funcionamiento(){
        Scanner sc = new Scanner(System.in);
        char eje = '\u0000';
        char variable = '\u0000';

        System.out.println("\n\nDefine el eje de rotación, ingresando:\n" +
                "1. Si el eje de rotación es el X.\n" +
                "2. Si el eje de rotación es el Y.\n");
        String respuestaEje=sc.nextLine();

        if(respuestaEje.equals("1")){ //EJE x
            eje = 'x';
            variable = 'y';
            System.out.println("\nIngresa la función en términos de y (ejemplo: y*y para f(y) = y^2): ");
            String inputFuncion = sc.nextLine();

            System.out.println("\nIngresa el limite inferior (a): ");
            double limInferior= Double.parseDouble(sc.nextLine());

            System.out.println("\nIngresa el limite superior (b): ");
            double limSuperior= Double.parseDouble(sc.nextLine());

            UnivariateFunction funcion = crearFuncion(inputFuncion, variable);
            double volumen = calcularVolumen(funcion, limInferior, limSuperior, variable);
            double volumenRedondeado = Math.round(volumen * 1000.0) / 1000.0;

            System.out.println("\n\nEl volumen del sólido es: "+volumenRedondeado+" unidades cubicas.");

            sc.close();
        }else if(respuestaEje.equals("2")){ //EJE Y
            eje = 'y';
            variable = 'x';
            System.out.println("\nIngresa la función en términos de x (ejemplo: x*x para f(x) = x^2): ");
            String inputFuncion = sc.nextLine();

            System.out.println("\nIngresa el limite inferior (a): ");
            double limInferior= Double.parseDouble(sc.nextLine());

            System.out.println("\nIngresa el limite superior (b): ");
            double limSuperior= Double.parseDouble(sc.nextLine());

            UnivariateFunction funcion = crearFuncion(inputFuncion, variable);
            double volumen = calcularVolumen(funcion, limInferior, limSuperior, variable);
            double volumenRedondeado = Math.round(volumen * 1000.0) / 1000.0;

            System.out.println("\n\nEl volumen del sólido es: "+volumenRedondeado+" unidades cubicas.");

            sc.close();
        }else{
            System.out.println("Ingresa una opción valida.");
        }
    }


    private double calcularVolumen(UnivariateFunction function, double a, double b, char variable){
        SimpsonIntegrator integrator = new SimpsonIntegrator();
        return 2 * Math.PI * integrator.integrate(10000, (v) -> v * function.value(v), a, b);
    }


    private UnivariateFunction crearFuncion(String input, char variable) {
        return new UnivariateFunction() {
            @Override
            public double value(double valorVariable) {
                return evalFuncion(input, valorVariable, variable);
            }
        };
    }


    private double evalFuncion(String input, double valorVariable, char variable) {
        String expresion2 = null;

        if(variable=='x'){
            expresion2 = input.replace("x", String.valueOf(valorVariable));
        }
        else if (variable=='y') {
            expresion2 = input.replace("y", String.valueOf(valorVariable));
        }

        return evalSimpleExpression(expresion2, variable);
    }


    private double evalSimpleExpression(String expresion, char variable) {
        try {
            return new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < expresion.length()) ? expresion.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double valor = parseExpression();
                    if (pos < expresion.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return valor;
                }

                double parseExpression() {
                    double valor = parseTerm();
                    for (;;) {
                        if (eat('+')) valor += parseTerm(); // Suma
                        else if (eat('-')) valor -= parseTerm(); // Resta
                        else return valor;
                    }
                }

                double parseTerm() {
                    double valor = parseFactor();
                    for (;;) {
                        if (eat('*')) valor *= parseFactor(); // Multiplicación
                        else if (eat('/')) valor /= parseFactor(); // División
                        else return valor;
                    }
                }

                double parseFactor() {
                    if (eat('+')) return parseFactor(); // +valor
                    if (eat('-')) return -parseFactor(); // -valor

                    double x;
                    int startPos = this.pos;

                    if (eat('s')) { // Detectar 'sqrt'
                        if (eat('q') && eat('r') && eat('t') && eat('(')) {
                            x = Math.sqrt(parseExpression());
                            eat(')');
                        } else {
                            throw new RuntimeException("Unexpected function: " + (char) ch);
                        }
                    } else if (eat('(')) { // Paréntesis
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') { // Números
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(expresion.substring(startPos, this.pos));
                    } else if (ch == variable) { // Variable 'x' o 'y'
                        nextChar();
                        x = 0;
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }
                    if (eat('^')) {
                        x = Math.pow(x, parseFactor());}

                    return x;
                }
            }.parse();
        } catch (Exception e) {
            System.out.println("Error al evaluar la expresión: " + e.getMessage());
            return 0;
        }
    }

}
