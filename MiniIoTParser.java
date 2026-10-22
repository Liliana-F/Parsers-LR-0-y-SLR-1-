import java.util.*;

/** Parser SLR(1) para la gramática Mini-IoT. Tabla ACTION/GOTO codificada directamente. */
public class MiniIoTParser {

    static final String[] TERMS = {"sensor", "device", "id", "=", "activate", "(", ")", ";", "if", "{", "}", "report", "num", "true", "+", "-", "*", ">", "==", "$"};
    static final String[] NONTERMS = {"Program", "Stmts", "Stmt", "Cond", "Expr", "Term", "Atom"};

    // ACTION: filas = estados I0..I50; columnas = TERMS. dN=shift, rN=reduce, acc=accept.
    static final String[][] ACTION = {
        {"d9", "d5", "d6", "", "d4", "", "", "", "d7", "", "", "d8", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "acc"},
        {"d9", "d5", "d6", "", "d4", "", "", "", "d7", "", "r3", "d8", "", "", "", "", "", "", "", "r3"},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "r1"},
        {"", "", "", "", "", "d11", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "d12", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "d13", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "d14", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "d15", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "d16", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "r2", "", "", "", "", "", "", "", "", "r2"},
        {"", "", "d17", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "d18", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "d29", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "d30", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"r5", "r5", "r5", "", "r5", "", "", "", "r5", "", "r5", "r5", "", "", "", "", "", "", "", "r5"},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "r16", "r16", "", "", "", "", "", "", "r16", "r16", "r16", "r16", "r16", ""},
        {"", "", "", "", "", "", "", "d34", "", "", "", "", "", "", "d32", "d33", "", "", "", ""},
        {"", "", "", "", "", "", "r14", "r14", "", "", "", "", "", "", "r14", "r14", "d35", "r14", "r14", ""},
        {"", "", "", "", "", "", "r17", "r17", "", "", "", "", "", "", "r17", "r17", "r17", "r17", "r17", ""},
        {"", "", "", "", "", "", "r18", "r18", "", "", "", "", "", "", "r18", "r18", "r18", "r18", "r18", ""},
        {"", "", "", "", "", "", "r20", "r20", "", "", "", "", "", "", "r20", "r20", "r20", "r20", "r20", ""},
        {"", "", "", "", "", "", "d36", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "d32", "d33", "", "d38", "d37", ""},
        {"", "", "", "", "", "", "d39", "", "", "", "", "", "", "", "d32", "d33", "", "", "", ""},
        {"r4", "r4", "r4", "", "r4", "", "", "", "r4", "", "r4", "r4", "", "", "", "", "", "", "", "r4"},
        {"", "", "", "", "", "", "", "d40", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "d41", "", "", "", "", "", "", "", "d32", "d33", "", "", "", ""},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"r6", "r6", "r6", "", "r6", "", "", "", "r6", "", "r6", "r6", "", "", "", "", "", "", "", "r6"},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "", "", "d45", "", "", "", "", "", "", "", "", "", ""},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "d23", "", "", "d19", "", "", "", "", "", "", "d24", "d25", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", "d48", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"r7", "r7", "r7", "", "r7", "", "", "", "r7", "", "r7", "r7", "", "", "", "", "", "", "", "r7"},
        {"", "", "", "", "", "", "r19", "r19", "", "", "", "", "", "", "r19", "r19", "r19", "r19", "r19", ""},
        {"", "", "", "", "", "", "r12", "r12", "", "", "", "", "", "", "r12", "r12", "d35", "r12", "r12", ""},
        {"", "", "", "", "", "", "r13", "r13", "", "", "", "", "", "", "r13", "r13", "d35", "r13", "r13", ""},
        {"", "", "", "", "", "", "r15", "r15", "", "", "", "", "", "", "r15", "r15", "r15", "r15", "r15", ""},
        {"d9", "d5", "d6", "", "d4", "", "", "", "d7", "", "", "d8", "", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "r11", "", "", "", "", "", "", "", "d32", "d33", "", "", "", ""},
        {"", "", "", "", "", "", "r10", "", "", "", "", "", "", "", "d32", "d33", "", "", "", ""},
        {"r9", "r9", "r9", "", "r9", "", "", "", "r9", "", "r9", "r9", "", "", "", "", "", "", "", "r9"},
        {"", "", "", "", "", "", "", "", "", "", "d50", "", "", "", "", "", "", "", "", ""},
        {"r8", "r8", "r8", "", "r8", "", "", "", "r8", "", "r8", "r8", "", "", "", "", "", "", "", "r8"},
    };

    // GOTO: filas = estados I0..I50; columnas = NONTERMS. -1 significa celda vacía.
    static final int[][] GOTO = {
        {1, 3, 2, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, 10, 2, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, 21, 22, 20},
        {-1, -1, -1, 26, 27, 22, 20},
        {-1, -1, -1, -1, 28, 22, 20},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, 31, 22, 20},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, 42, 20},
        {-1, -1, -1, -1, -1, 43, 20},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, 44},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, 46, 22, 20},
        {-1, -1, -1, -1, 47, 22, 20},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, 49, 2, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1},
    };

    static final String[] LHS = {"S'", "Program", "Stmts", "Stmts", "Stmt", "Stmt", "Stmt", "Stmt", "Stmt", "Stmt", "Cond", "Cond", "Expr", "Expr", "Expr", "Term", "Term", "Atom", "Atom", "Atom", "Atom"};

    static final String[][] RHS = {
        {"Program"},
        {"Stmts"},
        {"Stmt", "Stmts"},
        {"Stmt"},
        {"sensor", "id", ";"},
        {"device", "id", ";"},
        {"id", "=", "Expr", ";"},
        {"activate", "(", "id", ")", ";"},
        {"if", "(", "Cond", ")", "{", "Stmts", "}"},
        {"report", "(", "Expr", ")", ";"},
        {"Expr", ">", "Expr"},
        {"Expr", "==", "Expr"},
        {"Expr", "+", "Term"},
        {"Expr", "-", "Term"},
        {"Term"},
        {"Term", "*", "Atom"},
        {"Atom"},
        {"id"},
        {"num"},
        {"(", "Expr", ")"},
        {"true"},
    };
    static class Token {
        final String type, lexeme; final int position;
        Token(String type, String lexeme, int position) { this.type=type; this.lexeme=lexeme; this.position=position; }
        public String toString() { return type + "('" + lexeme + "')"; }
    }
    static class Node {
        final String label; final List<Node> children = new ArrayList<>();
        Node(String label) { this.label=label; }
        void print(String prefix, boolean last) {
            System.out.println(prefix + (last ? "└── " : "├── ") + label);
            for (int i=0; i<children.size(); i++) children.get(i).print(prefix + (last ? "    " : "│   "), i==children.size()-1);
        }
    }

    static List<Token> lex(String input) {
        List<Token> out = new ArrayList<>(); int i=0;
        while (i<input.length()) {
            char c=input.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            int start=i;
            if (Character.isLetter(c) || c=='_') {
                i++; while (i<input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i)=='_')) i++;
                String word=input.substring(start,i);
                String type = Arrays.asList("sensor","device","activate","if","report","true").contains(word) ? word : "id";
                out.add(new Token(type,word,start));
            } else if (Character.isDigit(c)) {
                i++; while (i<input.length() && Character.isDigit(input.charAt(i))) i++;
                out.add(new Token("num",input.substring(start,i),start));
            } else if (c=='=' && i+1<input.length() && input.charAt(i+1)=='=') {
                i+=2; out.add(new Token("==","==",start));
            } else if ("=();{}+-*>".indexOf(c)>=0) {
                i++; out.add(new Token(String.valueOf(c),String.valueOf(c),start));
            } else throw new IllegalArgumentException("Carácter no reconocido '"+c+"' en posición "+i);
        }
        out.add(new Token("$","$",input.length())); return out;
    }

    static int termIndex(String t) { for(int i=0;i<TERMS.length;i++) if(TERMS[i].equals(t)) return i; return -1; }
    static int nontermIndex(String n) { for(int i=0;i<NONTERMS.length;i++) if(NONTERMS[i].equals(n)) return i; return -1; }

    static Node parse(String source) {
        List<Token> tokens=lex(source); Deque<Integer> states=new ArrayDeque<>(); states.push(0);
        Deque<String> symbols=new ArrayDeque<>(); Deque<Node> nodes=new ArrayDeque<>(); int pos=0;
        while(true) {
            int state=states.peek(); Token tok=tokens.get(pos); int ti=termIndex(tok.type);
            if(ti<0) throw new IllegalStateException("Terminal sin columna ACTION: "+tok.type);
            String act=ACTION[state][ti];
            if(act.equals("acc")) {
                System.out.println("Cadena válida.");
                Node root=nodes.peek(); System.out.println("AST (árbol sintáctico abstracto):"); root.print("",true); return root;
            }
            if(act.startsWith("d")) {
                int next=Integer.parseInt(act.substring(1)); states.push(next); symbols.push(tok.type);
                nodes.push(new Node(tok.type + (tok.type.equals("id") || tok.type.equals("num") ? "("+tok.lexeme+")" : ""))); pos++;
            } else if(act.startsWith("r")) {
                int rule=Integer.parseInt(act.substring(1)); String lhs=LHS[rule]; String[] rhs=RHS[rule];
                List<Node> children=new ArrayList<>();
                for(int k=0;k<rhs.length;k++) {
                    if(states.size()<=1 || symbols.isEmpty() || nodes.isEmpty()) throw new IllegalStateException("Pila inconsistente al reducir por regla "+rule);
                    states.pop(); symbols.pop(); children.add(nodes.pop());
                }
                Collections.reverse(children); Node parent=new Node(lhs); parent.children.addAll(children);
                int ni=nontermIndex(lhs); int next=GOTO[states.peek()][ni];
                if(next<0) throw new IllegalStateException("GOTO vacío desde I"+states.peek()+" con "+lhs+" tras regla "+rule);
                states.push(next); symbols.push(lhs); nodes.push(parent);
            } else {
                System.out.println("Cadena inválida: error sintáctico en posición "+tok.position+" cerca de "+tok+".");
                System.out.println("Estado actual: I"+state+". Se esperaba uno de: "+expected(state)); return null;
            }
        }
    }

    static String expected(int state) {
        List<String> xs=new ArrayList<>(); for(int i=0;i<TERMS.length;i++) if(!ACTION[state][i].isEmpty()) xs.add(TERMS[i]);
        return xs.toString();
    }
    
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in); System.out.println("Mini-IoT — analizador SLR(1)");
        System.out.println("Escribe el programa a analizar en una sola línea (ej.: sensor temp; report(5+2);)");
        System.out.print("> "); String source=sc.nextLine();
        try { parse(source); } catch(IllegalArgumentException ex) { System.out.println("Error léxico: "+ex.getMessage()); }
    }
}