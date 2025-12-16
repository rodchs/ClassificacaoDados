import java.util.Scanner;
import java.util.Random;

/* ============================================================
 *                    ÁRVORE B — IMPLEMENTAÇÃO
 * ------------------------------------------------------------
 * Esta versão foi reorganizada com foco pedagógico:
 *  - blocos lógicos claramente divididos
 *  - comentários conceituais (invariantes da Árvore B)
 *  - nomes consistentes
 *  - explicações para os casos de remoção
 *
 * Grau mínimo (t):
 *    - cada nó possui entre t-1 e 2t-1 chaves
 *    - a raiz é exceção e pode ter menos
 *
 * Opera operações clássicas: busca, inserção, split, remoção.
 * ============================================================
 */
class BTreeNode {

    /* ============================================================
     *                       ATRIBUTOS DO NÓ
     * ============================================================
     */
    int[] keys;
    BTreeNode[] children;
    int numKeys;
    boolean leaf;
    final int t;

    /* ============================================================
     *                         CONSTRUTOR
     * ============================================================
     */
    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.numKeys = 0;
    }

    /* ============================================================
     *                          BUSCA
     * ============================================================
     */
    BTreeNode search(int k) {
        int i = 0;

        while (i < numKeys && k > keys[i])
            i++;

        if (i < numKeys && keys[i] == k)
            return this;

        if (leaf)
            return null;

        return children[i].search(k);
    }


    int countKeys() {
        int total = numKeys;
        if (!leaf)
            for (int i = 0; i <= numKeys; i++)
                total += children[i].countKeys();
        return total;
    }


    /* ============================================================
     *                   IMPRESSÃO DA ÁRVORE
     * ============================================================
     */
    void print(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("\t");

        System.out.print("[ ");
        for (int i = 0; i < numKeys; i++)
            System.out.print(keys[i] + " ");
        System.out.println("]");

        if (!leaf) {
            for (int i = 0; i <= numKeys; i++)
                children[i].print(level + 1);
        }
    }

    /* ============================================================
     *                         INSERÇÃO
     * ============================================================
     */
    void insertNonFull(int k) {

        int i = numKeys - 1;

        if (leaf) {

            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i];
                i--;
            }

            keys[i + 1] = k;
            numKeys++;
        }

        else {

            while (i >= 0 && keys[i] > k)
                i--;

            i++;

            if (children[i].numKeys == 2 * t - 1) {
                splitChild(i, children[i]);

                if (keys[i] < k)
                    i++;
            }

            children[i].insertNonFull(k);
        }
    }

    /* ============================================================
     *                         SPLIT
     * ============================================================
     */
    void splitChild(int index, BTreeNode y) {

        BTreeNode z = new BTreeNode(t, y.leaf);
        z.numKeys = t - 1;

        for (int j = 0; j < t - 1; j++)
            z.keys[j] = y.keys[j + t];

        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.children[j] = y.children[j + t];
        }

        y.numKeys = t - 1;

        for (int j = numKeys; j >= index + 1; j--)
            children[j + 1] = children[j];

        children[index + 1] = z;

        for (int j = numKeys - 1; j >= index; j--)
            keys[j + 1] = keys[j];

        keys[index] = y.keys[t - 1];
        numKeys++;
    }

    /* ============================================================
     *                        REMOÇÃO COMPLETA
     * ============================================================
     */
    void remove(int k) {

        int idx = findKey(k);

        if (idx < numKeys && keys[idx] == k) {

            if (leaf) {
                System.out.println("-> remove(): removendo chave " + k + " de nó folha");
                removeFromLeaf(idx);
            }

            else {
                System.out.println("-> remove(): removendo chave " + k + " de nó interno");
                removeFromInternal(idx);
            }
        }

        else {

            if (leaf) {
                System.out.println("Chave " + k + " não encontrada.");
                return;
            }

            boolean removeFromLastChild = (idx == numKeys);

            if (children[idx].numKeys < t)
                fill(idx);

            if (removeFromLastChild && idx > numKeys)
                children[idx - 1].remove(k);
            else
                children[idx].remove(k);
        }
    }


    int findKey(int k) {
        int idx = 0;
        while (idx < numKeys && keys[idx] < k)
            idx++;
        return idx;
    }

    /* ============================================================
     *                  REMOÇÃO EM NÓ FOLHA
     * ============================================================
     */
    void removeFromLeaf(int idx) {

        for (int i = idx + 1; i < numKeys; i++)
            keys[i - 1] = keys[i];

        numKeys--;
    }

    /* ============================================================
     *            REMOÇÃO EM NÓ INTERNO
     * ============================================================
     */
    void removeFromInternal(int idx) {

        int k = keys[idx];

        if (children[idx].numKeys >= t) {

            System.out.println("-> removeFromInternal(): usando PREDECESSOR para remover chave " + k);

            int pred = getPredecessor(idx);
            keys[idx] = pred;
            children[idx].remove(pred);
        }

        else if (children[idx + 1].numKeys >= t) {

            System.out.println("-> removeFromInternal(): usando SUCESSOR para remover chave " + k);

            int succ = getSuccessor(idx);
            keys[idx] = succ;
            children[idx + 1].remove(succ);
        }

        else {

            System.out.println("-> removeFromInternal(): nenhum filho tem >= t chaves — realizando merge");

            merge(idx);
            children[idx].remove(k);
        }
    }

    /* ============================================================
     *                   PREDECESSOR E SUCESSOR
     * ============================================================
     */
    int getPredecessor(int idx) {
        BTreeNode cur = children[idx];
        while (!cur.leaf)
            cur = cur.children[cur.numKeys];
        return cur.keys[cur.numKeys - 1];
    }

    int getSuccessor(int idx) {
        BTreeNode cur = children[idx + 1];
        while (!cur.leaf)
            cur = cur.children[0];
        return cur.keys[0];
    }

    /* ============================================================
     *                         FILL
     * ============================================================
     */
    void fill(int idx) {

        System.out.println("-> fill(): garantindo que o filho " + idx + " tenha pelo menos t chaves");

        if (idx > 0 && children[idx - 1].numKeys >= t) {

            System.out.println("-> fill(): tentando empréstimo do irmão esquerdo");
            borrowFromPrev(idx);
        }

        else if (idx < numKeys && children[idx + 1].numKeys >= t) {

            System.out.println("-> fill(): tentando empréstimo do irmão direito");
            borrowFromNext(idx);
        }

        else {

            System.out.println("-> fill(): nenhum irmão pode emprestar — fusão necessária");

            if (idx < numKeys)
                merge(idx);
            else
                merge(idx - 1);
        }
    }

    /* ============================================================
     *         EMPRESTAR DO IRMÃO ANTERIOR
     * ============================================================
     */
    void borrowFromPrev(int idx) {

        System.out.println("-> borrowFromPrev(): emprestando chave do irmão esquerdo para o filho " + idx);

        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];

        for (int i = child.numKeys - 1; i >= 0; i--)
            child.keys[i + 1] = child.keys[i];

        if (!child.leaf)
            for (int i = child.numKeys; i >= 0; i--)
                child.children[i + 1] = child.children[i];

        child.keys[0] = keys[idx - 1];

        if (!child.leaf)
            child.children[0] = sibling.children[sibling.numKeys];

        keys[idx - 1] = sibling.keys[sibling.numKeys - 1];

        child.numKeys++;
        sibling.numKeys--;
    }

    /* ============================================================
     *         EMPRESTAR DO IRMÃO SEGUINTE
     * ============================================================
     */
    void borrowFromNext(int idx) {

        System.out.println("-> borrowFromNext(): emprestando chave do irmão direito para o filho " + idx);

        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[child.numKeys] = keys[idx];

        if (!child.leaf)
            child.children[child.numKeys + 1] = sibling.children[0];

        keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.numKeys; i++)
            sibling.keys[i - 1] = sibling.keys[i];

        if (!sibling.leaf)
            for (int i = 1; i <= sibling.numKeys; i++)
                sibling.children[i - 1] = sibling.children[i];

        child.numKeys++;
        sibling.numKeys--;
    }

    /* ============================================================
     *                             MERGE
     * ============================================================
     */
    void merge(int idx) {

        System.out.println("-> merge(): realizando fusão dos nós " + idx + " e " + (idx + 1));

        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[t - 1] = keys[idx];

        for (int i = 0; i < sibling.numKeys; i++)
            child.keys[i + t] = sibling.keys[i];

        if (!child.leaf)
            for (int i = 0; i <= sibling.numKeys; i++)
                child.children[i + t] = sibling.children[i];

        for (int i = idx + 1; i < numKeys; i++)
            keys[i - 1] = keys[i];

        for (int i = idx + 2; i <= numKeys; i++)
            children[i - 1] = children[i];

        child.numKeys += sibling.numKeys + 1;
        numKeys--;
    }
}



/* ============================================================
 *                         CLASSE ÁRVORE B
 * ============================================================
 */
public class BTree {

    BTreeNode root;
    final int t;

    public BTree(int t) {
        this.t = t;
        this.root = null;
    }

    public void print() {
        if (root != null){
            root.print(0);
            System.out.println("\nBTree (t=" + this.t + ") com " + root.countKeys() + " registro(s)\n");
        }
        else
            System.out.println("[Árvore vazia]");
    }

    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    /* ============================================================
     *                           INSERÇÃO
     * ============================================================
     */
    public void insert(int k) {

        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.numKeys = 1;
            return;
        }

        if (root.search(k) != null)
            return;

        if (root.numKeys == 2 * t - 1) {

            BTreeNode novaRaiz = new BTreeNode(t, false);

            novaRaiz.children[0] = root;
            novaRaiz.splitChild(0, root);

            int i = (novaRaiz.keys[0] < k) ? 1 : 0;
            novaRaiz.children[i].insertNonFull(k);

            root = novaRaiz;
        }
        else
            root.insertNonFull(k);
    }


    /* ============================================================
     *                           REMOÇÃO
     * ============================================================
     */
    public void remove(int k) {

        if (root == null) {
            System.out.println("A árvore está vazia.");
            return;
        }

        System.out.println("\n========== INICIANDO REMOÇÃO DE " + k + " ==========");
        root.remove(k);

        if (root.numKeys == 0) {
            if (root.leaf)
                root = null;
            else
                root = root.children[0];
        }
    }


    /* ============================================================
     *                               MAIN
     * ============================================================
     */
    public static void main(String[] args) {

        final int ORDEM = 3;
        final int N = 63;

        BTree tree = new BTree(ORDEM);

        for (int i = 1; i <= N; i++)
            tree.insert(i);

        System.out.println("Árvore construída com valores de 1 a " + N);
        tree.print();

        Scanner sc = new Scanner(System.in);
        int chave;

        do {
            System.out.print("Digite chave a remover (0 para sair): ");
            chave = sc.nextInt();
            if (chave != 0) {
                tree.remove(chave);
                tree.print();
            }
        } while (chave != 0);

        sc.close();
    }
}
