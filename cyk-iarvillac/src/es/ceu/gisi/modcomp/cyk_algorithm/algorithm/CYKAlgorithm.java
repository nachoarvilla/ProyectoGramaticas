package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;

import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta clase contiene la implementación de la interfaz CYKAlgorithmInterface
 * que establece los métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class CYKAlgorithm implements CYKAlgorithmInterface {

    List<Character> nonTerminals = new ArrayList<>();
    List<Character> terminals = new ArrayList<>();
    char startSymbol;
    HashMap<Character, List<String>> productions = new HashMap<>();
    HashMap<String, List<Character>> inverseProductions = new HashMap<>();
    
    @Override
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento no es una letra mayúscula.
     */
    public void addNonTerminal(char nonterminal) throws CYKAlgorithmException {
        if(!Character.isUpperCase(nonterminal)){
            throw new CYKAlgorithmException();
        }else if(nonTerminals.contains(nonterminal)){
            throw new CYKAlgorithmException();
        }else if(Character.isUpperCase(nonterminal)){
            nonTerminals.add(nonterminal);
        }
    }

    @Override
    /**
     * Método que añade los elementos terminales de la gramática.
     *
     * @param terminal Por ejemplo, 'a'
     * @throws CYKAlgorithmException Si el elemento no es una letra minúscula.
     */
    public void addTerminal(char terminal) throws CYKAlgorithmException {
        if(!Character.isLowerCase(terminal)){
            throw new CYKAlgorithmException();
        }else if(terminals.contains(terminal)){
            throw new CYKAlgorithmException();
        }else if(Character.isLowerCase(terminal)){
            terminals.add(terminal);
        }
    }

    @Override
    /**
     * Método que indica, de los elementos no terminales, cuál es el axioma de
     * la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento insertado no forma parte del
     * conjunto de elementos no terminales.
     */
    public void setStartSymbol(char nonterminal) throws CYKAlgorithmException {
        if(nonTerminals.contains(nonterminal)){
            startSymbol = nonterminal;
        }else if(!nonTerminals.contains(nonterminal)){
            throw new CYKAlgorithmException();
        }
    }

    @Override
    /**
     * Método utilizado para construir la gramática. Admite producciones en FNC,
     * es decir de tipo A::=BC o A::=a
     *
     * @param nonterminal A
     * @param production "BC" o "a"
     * @throws CYKAlgorithmException Si la producción no se ajusta a FNC o está
     * compuesta por elementos (terminales o no terminales) no definidos
     * previamente.
     */
    public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
        
        if(nonTerminals.contains(nonterminal) && production.length() == 2){
            if(production.equals(production.toUpperCase())){
                if(nonTerminals.contains(production.charAt(0)) && nonTerminals.contains(production.charAt(1))){
                    if(productions.containsKey(nonterminal)){
                        List<String> productionsOfNonterminal = productions.get(nonterminal);
                        
                        if(productionsOfNonterminal.contains(production)){
                            throw new CYKAlgorithmException();
                        }else{
                            productionsOfNonterminal.add(production);
                        }
                    }else{
                        List<String> productionsOfNonterminal = new ArrayList<>();
                        productions.put(nonterminal, productionsOfNonterminal);
                        productionsOfNonterminal.add(production);
                    }
                }else{
                    throw new CYKAlgorithmException();
                }
            }else{
                throw new CYKAlgorithmException();
            }
        }else if(nonTerminals.contains(nonterminal) && production.length() == 1){
            if(production.equals(production.toLowerCase())){
                if(terminals.contains(production.charAt(0))){
                    if(productions.containsKey(nonterminal)){
                        List<String> productionsOfNonterminal = productions.get(nonterminal);
                        
                        if(productionsOfNonterminal.contains(production)){
                            throw new CYKAlgorithmException();
                        }else{
                            productionsOfNonterminal.add(production);
                        }
                    }else{
                        List<String> productionsOfNonterminal = new ArrayList<>();
                        productions.put(nonterminal, productionsOfNonterminal);
                        productionsOfNonterminal.add(production);
                    }
                }else{
                    throw new CYKAlgorithmException();
                }
            }else{
                throw new CYKAlgorithmException();
            }
        }else if(!nonTerminals.contains(nonterminal)){
            throw new CYKAlgorithmException();
        }else if(production.length() > 2){
            throw new CYKAlgorithmException();
        }
        
        //Esta parte es para rellenar el HashMap inverso que se necesitará para hacer el algoritmo
        if(nonTerminals.contains(nonterminal) && production.length() == 2){
            if(production.equals(production.toUpperCase())){
                if(nonTerminals.contains(production.charAt(0)) && nonTerminals.contains(production.charAt(1))){
                    if(inverseProductions.containsKey(production)){
                        List<Character> nonTerminalsForProduction = inverseProductions.get(production);
                        
                        if(nonTerminalsForProduction.contains(nonterminal)){
                            throw new CYKAlgorithmException();
                        }else{
                            nonTerminalsForProduction.add(nonterminal);
                        }
                    }else{
                        List<Character> nonTerminalsForProduction = new ArrayList<>();
                        inverseProductions.put(production, nonTerminalsForProduction);
                        nonTerminalsForProduction.add(nonterminal);
                    }
                }else{
                    throw new CYKAlgorithmException();
                }
            }else{
                throw new CYKAlgorithmException();
            }
        }else if(nonTerminals.contains(nonterminal) && production.length() == 1){
            if(production.equals(production.toLowerCase())){
                if(terminals.contains(production.charAt(0))){
                    if(inverseProductions.containsKey(production)){
                        List<Character> nonTerminalsForProduction = inverseProductions.get(production);
                        
                        if(nonTerminalsForProduction.contains(nonterminal)){
                            throw new CYKAlgorithmException();
                        }else{
                            nonTerminalsForProduction.add(nonterminal);
                        }
                    }else{
                        List<Character> nonTerminalsForProduction = new ArrayList<>();
                        inverseProductions.put(production, nonTerminalsForProduction);
                        nonTerminalsForProduction.add(nonterminal);
                    }
                }else{
                    throw new CYKAlgorithmException();
                }
            }else{
                throw new CYKAlgorithmException();
            }
        }else if(!nonTerminals.contains(nonterminal)){
            throw new CYKAlgorithmException();
        }else if(production.length() > 2){
            throw new CYKAlgorithmException();
        }
        
    }

    @Override
    /**
     * Método que indica si una palabra pertenece al lenguaje generado por la
     * gramática que se ha introducido.
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return TRUE si la palabra pertenece, FALSE en caso contrario
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public boolean isDerived(String word) throws CYKAlgorithmException {
        int n = word.length();
        List<List<Set<Character>>> table = new ArrayList<>();
        
        for(int i = 0; i < n; i++){
            if(!terminals.contains(word.charAt(i))){
                throw new CYKAlgorithmException();
            }
        }
        for (int i = 0; i < n; i++) {
            table.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                table.get(i).add(new HashSet<>());
            }
        }

        for (int i = 0; i < n; i++) {
            char terminal = word.charAt(i);
            List<Character> nonTerminals = inverseProductions.get(Character.toString(terminal));
            if (nonTerminals != null) {
                for (char nonTerminal : nonTerminals) {
                    table.get(i).get(0).add(nonTerminal);
                }
            }
        }

        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                for (int k = 0; k < j; k++) {
                    Set<Character> nonTerminals1 = table.get(i).get(k);
                    Set<Character> nonTerminals2 = table.get(i + k + 1).get(j - k - 1);
                    for (char nonTerminal1 : nonTerminals1) {
                        for (char nonTerminal2 : nonTerminals2) {
                            String production = Character.toString(nonTerminal1) + Character.toString(nonTerminal2);
                            List<Character> nonTerminals = inverseProductions.get(production);
                            if (nonTerminals != null) {
                                table.get(i).get(j).addAll(nonTerminals);
                            }
                        }
                    }
                }
            }
        }

        return table.get(0).get(n - 1).contains(startSymbol);
    }
    

    @Override
    /**
     * Método que, para una palabra, devuelve un String que contiene todas las
     * celdas calculadas por el algoritmo (la visualización debe ser similar al
     * ejemplo proporcionado en el PDF que contiene el paso a paso del
     * algoritmo).
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return Un String donde se vea la tabla calculada de manera completa,
     * todas las celdas que ha calculado el algoritmo.
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public String algorithmStateToString(String word) throws CYKAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
        productions.clear();
        nonTerminals.clear();
        terminals.clear();
    }

    @Override
    /**
     * Devuelve un String que representa todas las producciones que han sido
     * agregadas a un elemento no terminal.
     *
     * @param nonterminal
     * @return Devuelve un String donde se indica, el elemento no terminal, el
     * símbolo de producción "::=" y las producciones agregadas separadas, única
     * y exclusivamente por una barra '|' (no incluya ningún espacio). Por
     * ejemplo, si se piden las producciones del elemento 'S', el String de
     * salida podría ser: "S::=AB|BC".
     */
    public String getProductions(char nonterminal) {
        if(productions.containsKey(nonterminal)){
            String productiontoString = nonterminal + "::=";
            for(int i = 0; i < productions.get(nonterminal).size(); i++){
                productiontoString = productiontoString += productions.get(nonterminal).get(i) + "|";
            }
            return productiontoString;
        }else{
            return "";
        }
    }

    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
