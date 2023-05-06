package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;

import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

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
        List<Character> nonTerminalsForTerminal = new ArrayList<>();
        char[][][] algorithmTable = new char[word.length()][word.length()][nonTerminalsForTerminal.size()];
        
        for(int i = 0; i < word.length(); i++){
            for(int j = 0; j < nonTerminals.size(); j++){
                if(productions.get(nonTerminals.get(j)).contains(word.charAt(i))){
                    nonTerminalsForTerminal.add(nonTerminals.get(j));
                }
            }
            algorithmTable[0][i][0] = nonTerminalsForTerminal.get(0);
            algorithmTable[0][i][1] = nonTerminalsForTerminal.get(1);
        }
            
        throw new UnsupportedOperationException("Not supported yet.");
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
            String production1 = productions.get(nonterminal).get(0);
            String production2 = productions.get(nonterminal).get(1);
            
            return nonterminal + "::=" + production1 + "|" + production2;
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
