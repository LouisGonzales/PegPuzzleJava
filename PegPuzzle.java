//Louis Gonzales
//CSCE 4430
/*Assignment 7
Deadline Monday, November 22 at 10:00am

Write a Java program equivalent to the Python program at:

https://github.com/ptarau/CrackerBarrelPegPuzzle

that implements a solver for the Cracker Barrel Peg Puzzle at:

https://shop.crackerbarrel.com/toys-games/games/travel-games/peg-game/606154

This time you will need to use classes and objects for things like the moves, the board you display them on and the solver itself.

Post your answer on github.com and provide a link to it in canvas. You can reuse the repository created for the same assignment in Prolog or create a new one.

Sources used: https://www.joenord.com/puzzles/peggame/index.html
http://www.cs.ucf.edu/~dmarino/progcontests/cop4516/samplecode/peg2.java
https://github.com/ptarau/CrackerBarrelPegPuzzle
*/


import java.util.*;

public class PegPuzzle{

	final public static boolean DEBUG = false;
	public static HashMap<Integer, Integer > prev;

	final public static int[] HOR = {-1, -1, 0, 0, 1, 1};
	final public static int[] VER = {-1, 0, -1, 1, 0, 1};

	public static void main(String[] args) {

		//starting open hole
		int begin = board(0, 0);
		//hole where last peg will be placed
		int end = getEnd(4, 2);

		prev = new HashMap<Integer,Integer>();
		prev.put(begin, begin);

		//prints out starting board
		System.out.println();
		System.out.print("    _ ");
		System.out.println();
		System.out.print("   X X");
		System.out.println();
		System.out.print("  X X X");
		System.out.println();

		System.out.print(" X X X X");		
		System.out.println();

		System.out.print("X X X X X");
		System.out.println();
		System.out.println();

		//BFS
		LinkedList<Integer> holder = new LinkedList<Integer>();
		holder.offer(begin);

		while(holder.size() > 0){
			
			int current = holder.poll();

			ArrayList<Integer> nextList = getNextPos(current);

			for(int i = 0; i < nextList.size(); i++){
				if(!prev.containsKey(nextList.get(i))) {
					prev.put(nextList.get(i), current);
					holder.offer(nextList.get(i));
			}
		}
		}
		//finds end specified
		if(prev.containsKey(end)) {
			ArrayList<Integer> path = buildpath(prev, end);
			//prints the path
			for(int i = 0; i <path.size(); i++) {
				print(path.get(i));
				System.out.println();

			}
}

	}

		//builds path towards end position
		public static ArrayList<Integer> buildpath(HashMap<Integer, Integer> prev, int finale) {

			ArrayList<Integer> res = new ArrayList<Integer>();
			int current = finale;

			while(prev.get(current) != current) {
				res.add(0, current);
				current = prev.get(current);
			}
			return res;


		}
		//creates a mask of board starting with full board
		public static int board(int row, int col){
			int mask = 0;
			for(int i = 0; i < 5; i++){
				for (int j = 0; j <=i;j++){
					mask = mask | (1<<(5*i+j));
				
				}
			}
			return mask - (1<<(5*row+col));
		}
		//returns end board
		public static int getEnd(int row, int col) {
			return (1<<(5*row+col));
		}

		
		//returns future position
		public static ArrayList<Integer> getNextPos(int mask) {
			ArrayList<Integer> pos = new ArrayList<Integer>();
			
			for(int r = 0; r<5; r++){
				for (int c = 0; c <= 5; c++){
					for(int dir = 0; dir<HOR.length; dir++){
						if(!inbounds(r+2*HOR[dir], c+2*VER[dir])) continue;

						if(on(mask, 5*r+c) && on(mask,5*(r+HOR[dir]) + c + VER[dir]) && !on(mask, 5*(r+2*HOR[dir]) + c + 2*VER[dir])) {
							int newpos = apply(mask, dir, r, c);
							pos.add(newpos);
						}
					}
				}

			}
			return pos;

		}
		//prints out each move made
		public static void print(int mask) {
			for(int i = 0; i < 5; i++){
				for(int j = 0; j <5-1-i;j++) System.out.print(" ");

				
				
				for(int j = 0; j<=i;j++) {
					if(on(mask, 5*i+j)) System.out.print("X ");
					
					
					else System.out.print("_ ");
					
				}
				System.out.println();
			}
			System.out.println();
		}

		//returns movement
		public static int apply(int mask, int dir, int r, int c){
			int start = 5*r +c;
			int mid = 5* (r+HOR[dir]) + c + VER[dir];
			int finale = 5 *(r+2*HOR[dir]) + c + 2*VER[dir];

			return mask - (1<<start) - (1<<mid) + (1<<finale);
		}

		
		public static boolean on(int mask, int bit) {
			return (mask & (1<<bit)) != 0;
		}

		public static boolean inbounds(int myr, int myc) {
			return myr >= 0 && myr < 5 && myc >= 0 && myc <= myr;
		}


}
