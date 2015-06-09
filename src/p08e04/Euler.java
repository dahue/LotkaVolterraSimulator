
package p08e04;
import java.util.Hashtable;

/**
 * 
 * @author Astorga Dario - Coria Gaston - Tissera Adrian
 */
public class Euler {
    private int r, a, b, m;
    private Double step;
    private Hashtable<Integer, Double> preyValue;
    private Hashtable<Integer, Double> predatorValue;
    
    /**
     * Constructor de la clase.
     * @param step es el valor del paso de integracion (h en la ecuacion de Euler)
     * @param preyStartValue es el valor inicial de la poblacion de presas
     * @param predatorStartValue es el valor inicial de la poblacion de depredadores
     */
    public Euler(Double step, Double preyStartValue, Double predatorStartValue) {
        this.step = (step <= 0) ? 0.01 : step;
        this.preyValue = new Hashtable<Integer, Double>();
        this.preyValue.put(0, preyStartValue);
        this.predatorValue = new Hashtable<Integer, Double>();
        this.predatorValue.put(0, predatorStartValue);
    }
    
    /**
     * Metodo que guarda el valor en el tiempo 'time' de la poblacion de presas/depredadores.
     * @param prey indica si es presa o no (se supone que si no es presa entonces es depredador)
     * @param value es el valor de la poblacion de presas/depredadores en el tiempo time
     * @param time es el tiempo corriente de la simulacion
     */
    public void addTo(boolean prey, Double value, Double time) {
        if (prey) {
            preyValue.put((int) (time / step), value);
        } else {
            predatorValue.put((int) (time / step), value);
        }
    }
    
    /**
     * 
     * @param time es un momento en particular
     * @param prey es true si es una presa o false si es depredador
     * @return devuelve el valor de la poblacion de presas/depredadores en el momento 'time'
     */
    public Double getValue(Double time, boolean prey) {
        if (prey) {
            return preyValue.get((int) (time / step));
        } else {
            return predatorValue.get((int) (time / step));
        }
    }
    
    /**
     * 
     * @param time es un momento dado de la simulacion
     * @param prey es true si es una presa o false si es depredador
     * @return la poblacion de presas/depredadores en el tiempo 'time'
     */
    public Double getNextValue(Double time, boolean prey) {
        Double eulerValue = getValue(time, prey);
        if (eulerValue != null) {
            return eulerValue;
        } else { 
            
            Double previousTime = time - this.step;

            Double previousValue = getValue(previousTime, prey);

            if (previousValue == null) {
                previousValue = derivedFunction(previousTime, prey);
                addTo(prey, previousValue, previousTime);
            }

            eulerValue = computeEuler(previousValue, this.step, previousTime, prey);

            addTo(prey, eulerValue, time);
            
            return eulerValue;
        }
    }

    /**
     * Metodo que implementa el modelo Lotka-Volterra el cual tiene la forma:
     * p'(t) = r*p(t) - a*p(t)*d(t)
     * d'(t) = b*p(t)*d(t) - m*d(t)
     * @param time es el tiempo de simulacion
     * @param prey si es true calcula p'(t), si es false calcula d'(t) con t = time
     * @return el valor de la funcion derivada para las presas/depredadores
     */
    private Double derivedFunction(Double time, boolean prey) {
        if (time == 0) {
            return getValue(time, prey);
        }

        if (prey) {
            return (r - a * getNextValue(time, !prey)) * getNextValue(time, prey);
        } else {
            return (b * getNextValue(time, !prey) - m) * getNextValue(time, prey);
        }
    }

    /**
     * Inicializa los atributos r, a, b, m de la clase
     * @param r tasa de crecimiento de las presas
     * @param a coeficiente de depredacion
     * @param b tasa de crecimiento de los depredadores
     * @param m tasa de mortalidad de los depredadores
     */
    public void setParameters(int r, int a, int b, int m) {
        this.r = r;
        this.a = a;
        this.b = b;
        this.m = m;
    }
    
    /**
     * Metodo que computa la ecuacion de Euler.
     * @param before tiene la poblacion de presas/depredadores
     * @param step es el intervalo de tiempo que de usa la funcion derivada
     * @param time es el tiempo actual
     * @param prey indica con true o false si se calcula la ecuacion para las presas o los depredadores
     * @return la poblacion de presas/depredadores en el momento "time + step"
     */
    private Double computeEuler(Double before, Double step, Double time, boolean prey){
        return before + step * derivedFunction(time, prey);
    }
}
