
package p08e04;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Astorga Dario - Coria Gaston - Tissera Adrian
 */
public class Util {
    static int r, a, b, m;
    static Double predatorStartValue, preyStartValue, simTime, step, time;

    /**
     * LLeva a cabo la simulacion y escribe los archivos 'prey', 'predator' y 
     * 'predator-prey' con los datos de las poblaciones de las especies
     * pre: deben estar inicializadas todas las variables globales.
     */
    public static void lotkaVolterraSimulation() {
        try{
            Euler method = new Euler(step, preyStartValue, predatorStartValue);
            method.setParameters(r, a, b, m);

            FileWriter preyFile = null, predatorFile = null, composedFile = null;
            PrintWriter preyWriter = null, predatorWriter = null, predatorPreyWriter = null;
            
            //Runtime.getRuntime().exec("rm -r output");
            Runtime.getRuntime().exec("mkdir output");
            preyFile = new FileWriter("output/prey.dat");
            preyWriter = new PrintWriter(preyFile);

            predatorFile = new FileWriter("output/predator.dat");
            predatorWriter = new PrintWriter(predatorFile);

            composedFile = new FileWriter("output/predator-prey.dat");
            predatorPreyWriter = new PrintWriter(composedFile);

            Double prey, predator;
            while (time < simTime) {
                prey = method.getNextValue(time, true);
                predator = method.getNextValue(time, false);
                predatorPreyWriter.append(predator + " " + prey + "\n");
                preyWriter.append(time + " " + prey + "\n");
                predatorWriter.append(time + " " + predator + "\n");
                time = time + step;
            }
            preyWriter.close();
            preyFile.close();
            predatorWriter.close();
            predatorFile.close();
            predatorPreyWriter.close();
            composedFile.close();
            showGraphics();
        }
        catch(Exception e){
        }
    }

    /**
     * Inicializa todas las variables involucradas en la simulacion con valores
     * pasados por consola.
     */
    public static void setInitialState() {
        String[] ids = {"Tasa de crecimiento del número de presas (r)",
                        "Coeficiente de depredación (a)",
                        "Tasa de crecimiento del número de depredadores (b)",
                        "Tasa de mortalidad de los depredadores (m)",
                        "Valor inicial de presas",
                        "Valor inicial de depredadores",
                        "Duración de la simulación",
                        "Paso de integración"};
        Double[] variables = new Double[4];
        int[] variables2 = new int[4];
        Scanner sc = new Scanner(System.in);
        int aux;
        Double aux2;
        for (int i = 0; i < 8; i++) {
            System.out.println("Ingrese " + ids[i]);
            if (i < 4) {
                aux = sc.nextInt();
                variables2[i] = (aux < 0) ? 0 : aux;
            } else {
                aux2 = sc.nextDouble();
                variables[i - 4] = (aux2 < 0) ? 0 : aux2;
            }
        }
        time = 0.0;
        predatorStartValue = variables[0];
        preyStartValue = variables[1];
        simTime = variables[2];
        step = variables[3];
        r = variables2[0];
        a = variables2[1];
        b = variables2[2];
        m = variables2[3];
    }
    
    /**
     * Inicializa todas las variables involucradas en la simulacion con valores
     * por defecto. La duracion de la simulacion y del paso se configuran ma-
     * nualmente con valores pasados por consola.
     */
    public static void setDefaults() {
        time = 0.0;
        setSimConfig();
        predatorStartValue = 2.0;
        preyStartValue = 2.0;
        r = 3;
        a = 2;
        b = 1;
        m = 2;
    }
    
    /**
     * Metodo que ejecuta GnuPlot y realiza los graficos usando los datos 
     * guardados previamente en los archivos 'prey.dat', 'predator.dat' y 
     * 'predator-prey.dat'. En 'graph.png' guarda el grafico de las presas en
     * funcion de los depredadores y en 'graphSeparateVariables.png' estan
     * los graficos de las 2 variables separadas en funcion del tiempo.
     */
    private static void showGraphics() {
        try{
            String[] s = {"/usr/bin/gnuplot", "-e", " set style data lines; plot 'output/prey.dat' linewidth 3 linecolor rgb 'blue';set term png;  set output 'output/graphSeparateVariables.png'; replot 'output/predator.dat' linewidth 3 linecolor rgb 'gray';  set output 'output/graph.png'; set xlabel 'Predators'; set ylabel 'Preys'; plot 'output/predator-prey.dat' linewidth 3 linecolor rgb 'gray' show xlabel show ylabel;"};
            Runtime.getRuntime().exec(s);
        }catch(Exception e){
        }
    }
    
    /**
     * Inicializa la duracion de la simulacion y del paso de integracion.
     */
    private static void setSimConfig() {
        String[] ids = {"la duración de la simulación", "el valor del paso de integración"};
        Double[] variables = new Double[2];
        Scanner sc = new Scanner(System.in);
        Double aux;
        for (int i = 0; i < 2; i++) {
            System.out.println("Ingrese " + ids[i]);
            aux = sc.nextDouble();
            variables[i] = (aux < 0) ? 0 : aux;
        }
        simTime = variables[0];
        step = variables[1];
    }

}
