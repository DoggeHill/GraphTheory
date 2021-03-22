/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainWrapper;

import java.io.FileNotFoundException;
import graf1.Graf;
/**
 *
 * @author tomas
 */
public class PrikladGrafy {
  /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException 
    {
        Graf g = Graf.nacitajSubor("C:\\Users\\patri.DESKTOP-FFNU79O\\dev\\GRAFY\\pr4.hrn");
        g.printInfo();
        //g.maticaSusednosti();
        //g.shortestPath(5);
        System.out.println();
       // g.maticaOhodnoteniHran();
       g.floyd(1, 5);   
       //g.labelSet(3);
    }
 
}
