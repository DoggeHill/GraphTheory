/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graf1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author tomas
 */
public class Graf {
    int n; // pocet vrcholov grafu
    int m; // pocet hran grafu
    int H[][]; // pole udajov o hranach
    int S[];

    public Graf(int paPocetVrcholov, int paPocetHran) {
        n = paPocetVrcholov;
        m = paPocetHran;
        H = new int[1 + m][3];
        S = new int[n + 2];
    }

    /*
     * Nacitanie grafu zo suboru: Na kazdom riadku su tri cisla, prve a druhe cislo
     * su cisla vrcholov a tretie cislo je ohodnotenie hrany. Pocet vrcholov aj
     * pocet hran sa urci automaticky. Pocet hran je rovny poctu riadkov v subore a
     * pocet vrcholov je rovny najvacsiemu cislu vrcholu v udajoch o hranach.
     */
    public static Graf nacitajSubor(String nazovSuboru) throws FileNotFoundException {
        // otvorim subor a pripravim Scanner pre nacitavanie
        Scanner s = new Scanner(new FileInputStream(nazovSuboru));
        // najskor len zistim pocet vrcholov a pocet hran
        int pocetVrcholov = 1;
        int pocetHran = 0;
        // prejdem cely subor
        while (s.hasNext()) {
            // nacitam udaje o hrane
            int u = s.nextInt();
            int v = s.nextInt();
            int c = s.nextInt();
            // nacital som hranu, zvysim ich pocet o 1
            pocetHran++;
            // skontrolujem, ci netreba zvysit pocet vrcholov
            if (pocetVrcholov < u)
                pocetVrcholov = u;
            if (pocetVrcholov < v)
                pocetVrcholov = v;
        }
        // ukoncim nacitavanie zo suboru
        s.close();
        // vytvorim objekt grafu s potrebnym poctom vrcholo v aj hran
        Graf g = new Graf(pocetVrcholov, pocetHran);
        // po druhy krat otvorim ten isty subor,
        // uz pozanm pocet vrcholov aj hran a mam alokovanu pamat
        s = new Scanner(new FileInputStream(nazovSuboru));
        // postune nacitam vsetky hrany
        // tentokrat si ich uz budem aj ukladat do pamate
        for (int j = 1; j <= pocetHran; j++) {
            int u = s.nextInt();
            int v = s.nextInt();
            int c = s.nextInt();
            g.H[j][0] = u;
            g.H[j][1] = v;
            g.H[j][2] = c;
        }
        return g;
    }

    public void printInfo() {
        System.out.println("Pocet vrcholov: " + n);
        System.out.println("Pocet hran: " + m);
    }

    public void maticaSusednosti() {
        int[][] A = new int[n][n];
        final int nula = 0;
        for (int i = 0; i < n; i++) {
            for (int e = 0; e < n; e++) {
                A[i][e] = nula;
            }
        }
        for (int i = 1; i <= m; i++) {
            int ii = this.H[i][0] - 1;
            int jj = this.H[i][1] - 1;
            A[ii][jj] = 1;
        }
        for (int i = 0; i < n; i++) {
            for (int e = 0; e < n; e++) {
                System.out.print(A[i][e] + " ");
            }
            System.out.println();
        }
    }

    public void shortestPath(int start) {

        int x[] = new int[this.n + 1]; // +1 bcs prvý elemnt s ním nepracujeme
        int t[] = new int[this.n + 1]; // +1 bcs prvý elemnt s ním nepracujeme

        for (int i = 1; i <= this.n; i++) {
            x[i] = 0; // predposledný vrchol
            t[i] = 500; // horný odhad dĺžky, nevieme tak nastavíme na nekonečno(500)
        }

        // ?predposledný vrchol si potrebujeme pamatäť, aby sme vedeli cestu
        // ?do nového vrcholu zlepšiť ak je to možné

        // ?horný odhad dĺžky slúži na to, aby sme zisitli či je iná ceste kratšia

        t[5] = 0; // tam kde začíname dáme 0, pretože cesta z toho istého vrcholu
        // do toho istého vrcholu je vždy 0

        // n pocet vrcholov grafu
        // m pocet hran grafu
        // H[][]; pole udajov o hranach
        // *0odkiaľ & 1kam & 2cena
        // ***napr. hrana 1 od U do V s cenou C

        int zlepsenie = 1;

        while (zlepsenie == 1) { // zlepšenie sa nerovná jednej len vtedy, ak sme dorazili na koniec pretože je
                                 // všetko
                                 // nekonečno tak ak vždy existuje cesta a vždy sa nekonečno zlepší
            zlepsenie = 0;
            for (int k = 1; k <= m; k++) { // *iterujeme po všetkých hranách, čiže aj po všetkých vrcholoch

                int i = H[k][0]; // do premennej si uložíme odkiaľ vychádza hrana, počiatočný vrchol
                int j = H[k][1]; // zistíme kam smeruje hrana, cieľový vrchol
                int cij = H[k][2]; // zistíme cenu hrany

                System.out.print("u" + i + " ");
                System.out.print("v" + j + " ");
                System.out.print("c" + cij + " ");
                System.out.print("TJ " + t[j] + " TI " + t[i] + " \n");

                // ak je odhadovaná(nekonečná) cena hrany do nového vrcholu vyššia tak sme našli
                // kratšiu cestu
                // ona je nastavená na nekonečno takže sa isto zlepší ak existuje hrana
                if (t[j] > t[i] + cij) {
                    t[j] = t[i] + cij; // uložíme si novú cestu
                    x[j] = i; // predposledný vrchol posunieme lebo sme sa pohli
                              // tu ak vychada viacero hrán z jedneho vrcholu tak pozíciu nemeníme ale
                              // skontrolujeme všetky hrany
                    System.out.println(
                            "TJ je väčšie ako TI + CIJ, meníme na " + cij + " a posúvame sa na " + x[j] + "  ... ");
                    zlepsenie = 1;
                    System.out.println("\n");
                }
                this.vypis(t);
            }
            System.out.println("");
        }

        for (int i = this.n; i >= 1; i--) {
            if (x[i] == 0)
                continue;
            if (i == this.n) {
                System.out.print("6 ");
            }
            System.out.print(x[i] + " ");
        }

    }

    public void vypis(int t[]) {
        int cnt = 0;
        System.out.print("................. ");
        for (int e : t) {
            System.out.print(++cnt + "-> ");
        }
        System.out.println("");

        System.out.print("tabuľka: ");
        for (int e : t) {
            System.out.print(e + "-> ");
        }
        System.out.println("");
    }

    public void maticaOhodnoteniHran() {
        int[][] C = new int[n][n];
        final int nekonecno = 0;
        for (int i = 0; i < n; i++) {
            for (int e = 0; e < n; e++) {
                C[i][e] = nekonecno;
            }
        }
        for (int i = 1; i <= m; i++) {
            int ii = this.H[i][0] - 1;
            int jj = this.H[i][1] - 1;
            int cc = this.H[i][2];
            C[ii][jj] = cc;
        }
        for (int i = 0; i < n; i++) {
            for (int e = 0; e < n; e++) {
                System.out.print(C[i][e] + " ");
            }
            System.out.println();
        }
    }

    public void floyd(int zaciatok, int koniec) {
        // zostrojíme dve matice
        int[][] C = new int[n + 1][n + 1];
        int[][] X = new int[n + 1][n + 1];

        final int nekonecno = 500;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j) {
                    C[i][j] = nekonecno;
                    X[i][j] = nekonecno;

                } else {
                    // na diagonalach sú iba nuly
                    C[i][j] = 0;
                    X[i][j] = i;
                }
            }
        }

        // ?tam kde je hrana prepíšeme nekonečno na cenu
        for (int i = 1; i <= m; i++) {
            int ii = this.H[i][0];
            int jj = this.H[i][1];
            int cc = this.H[i][2];
            // System.out.println(ii+ " " + jj);
            C[ii][jj] = cc;
            X[ii][jj] = ii;
        }

        for (int k = 1; k <= n; k++) { // krakt k
            for (int i = 1; i <= n; i++) { // matica i
                for (int j = 1; j <= n; j++) { // matica j
                    if (C[i][j] > C[i][k] + C[k][j]) {
                        C[i][j] = C[i][k] + C[k][j];
                        X[i][j] = X[k][j];
                    }

                }
                System.out.println("X");
                for (int i2 = 1; i2 <= n; i2++) {
                    for (int e = 1; e <= n; e++) {
                        System.out.print(X[i2][e] + " ");
                    }
                    System.out.println();
                }
                System.out.println("C");
                for (int i3 = 1; i3 <= n; i3++) {
                    for (int e = 1; e <= n; e++) {
                        System.out.print(C[i3][e] + " ");
                    }
                    System.out.println();
                }
                System.out.println();

            }
        }

        System.out.println("Finálna cesta");
        int j = koniec;
        System.out.print(koniec + " ");
        while (j != zaciatok) {
            j = X[zaciatok][j];
            System.out.print(j + " ");
        }

    }

    public void labelSet(int u) {
        // u v je hrana
        // c je cena

        // n pocet vrcholov grafu
        // m pocet hran grafu
        // H[][]; pole udajov o hranach
        // *0odkiaľ & 1kam & 2cena
        // ***napr. hrana 1 od U do V s cenou C

        int x[] = new int[this.n + 1]; // +1 bcs prvý elemnt s ním nepracujeme
        int t[] = new int[this.n + 1]; // +1 bcs prvý elemnt s ním nepracujeme

        for (int i = 1; i <= this.n; i++) {
            x[i] = 0; // predposledný vrchol
            t[i] = 500; // horný odhad dĺžky, nevieme tak nastavíme na nekonečno(500)
        }

        t[u] = 0;

        ArrayList<Integer> epsilon = new ArrayList<>();
        epsilon.add(u);

        int r = 0;

        while (!epsilon.isEmpty()) {

            r = epsilon.get(0);
            epsilon.remove(0);

            for (int i = 1; i <= m; i++) {
                if (H[i][0] == r) {
                    int cij = H[i][2];
                    int j = H[i][1];

                    System.out.print(j + " ");
                    if (t[j] > t[r] + cij) {
                        t[j] = t[r] + cij;
                        x[j] = r;
                        epsilon.add(j);
                    }
                }
            }
            System.out.println();
        }
        int cnt = 0;
        for (int i : t) {
            if (cnt++ == 0)
                continue;
            System.out.print(i + "  ");
        }
        System.out.println();
    }

    public void poleSmerovnikov() {
        for (int i = 0; i < this.S.length; i++) {
            this.S[i] = 0;
        }
        for (int k = 0; k < this.m; k++) {
            int i = this.H[k][0];
            if (this.S[i] == 0) {
                this.S[i] = k;
            }
        }
        this.S[this.n + 1] = this.m + 1;

        for (int i = this.n; i >= 1; i--) {
            if (this.S[i] == 0) {
                this.S[i] = this.S[i + 1];
            }
        }
        for (int i = 0; i < this.S.length; i++) {
            System.out.println(i + " " + this.S[i]);

        }

    }

    public void vystupneHrany(int vrchol) {

        for (int i = this.S[vrchol]; i < this.S[vrchol + 1]; i++) {
            System.out.println("(" + this.H[i][0] + ", " + this.H[i][1] + ")");
        }

        if (this.S[vrchol] - this.S[vrchol + 1] == 0) {
            System.out.println("Z tohto vrchola nevychádza ziadna hrana.");
        }

    }

}
