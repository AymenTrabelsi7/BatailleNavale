package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import BatailleNavale.Tableau;

public class Testtouche {
	
	public static HashMap<Integer,Character> conv = new HashMap<Integer,Character>(0);
	
	public static String unconvert(int[] coord) {
		return conv.get(coord[0]).toString() + (coord[1]+1);
	}
	
	public static void main(String[] args) throws IOException {
		conv.put(0, 'A');
		conv.put(1, 'B');
		conv.put(2, 'C');
		conv.put(3, 'D');
		conv.put(4, 'E');
		conv.put(5, 'F');
		conv.put(6, 'G');
		conv.put(7, 'H');
		conv.put(8, 'I');
		conv.put(9, 'J');
		int[] s = {2,4};
		System.out.println(unconvert(s));
	}
}
