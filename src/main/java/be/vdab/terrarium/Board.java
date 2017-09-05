
package be.vdab.terrarium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import be.vdab.entities.Carnivore;
import be.vdab.entities.Herbivore;
import be.vdab.entities.Organism;
import be.vdab.entities.Plant;
import com.sun.javafx.scene.traversal.Direction;

public enum Board {

	INSTANCE;
	private static final int ROW = 6;
	private static final int COL = 6;
	private static Organism[][] organisms = new Organism[ROW][COL];
	static int aantalOrganism = 0;

	protected static void print()
	{
		System.out.println();
		for (int i = 0; i < organisms.length; i++) {
			for (int j = 0; j < organisms[i].length; j++) {
				if (organisms[i][j] == null) {
					System.out.print(".\t");
				} else {
					System.out.print(organisms[i][j].toString() + "\t");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	protected static void setTestPositions() {
//		organisms[0][0] = new Herbivore(1, false);
//		organisms[0][1] = new Plant(1, false);
//		organisms[0][3] = new Herbivore(1, false);
//		organisms[0][4] = new Herbivore(1, false);
//		organisms[0][0] = new Herbivore(1, false);
//		organisms[1][2] = new Plant(1, false);
//		organisms[0][0] = new Herbivore(1, false);
//		organisms[1][2] = new Plant(1, false);
		organisms[0][0] = new Herbivore(1, false); 		aantalOrganism++;
		organisms[0][1] = new Carnivore(1, false);		aantalOrganism++;
		organisms[1][0] = new Herbivore(1, false);		aantalOrganism++;



	}

	protected static void nextDay() {

		// Set all HasActed on false

		for (int i = 0; i < organisms.length; i++) {
			for (int j = 0; j < organisms[i].length; j++) {
				if (organisms[i][j] instanceof Organism){
					organisms[i][j].setHasActed(false);
				}
			}
		}


		// Do actions and set HasActed on true

		generateNewPlants();
		for (int i = 0; i < organisms.length; i++) {
			for (int j = 0; j < organisms[i].length; j++) {

				if (organisms[i][j] instanceof Organism) {
					if (!organisms[i][j].isHasActed()) {
						// No Actions performed.. DO ACTION
						organisms[i][j].setHasActed(true);
						if (organisms[i][j] instanceof Herbivore) {
							if ((j == organisms[i].length - 1) || (organisms[i][j + 1] == null) // vermijdt
								// ArrayIndexOutOfBoundsException
								|| organisms[i][j + 1] instanceof Carnivore) {
								move(i,j);
							} else if (organisms[i][j + 1] instanceof Plant) {
								eat(i,j,i,j+1);
							} else if (organisms[i][j + 1] instanceof Herbivore) {
								mate();
							}
						} else if (organisms[i][j] instanceof Carnivore) {
							if ((j == organisms[i].length - 1) || (organisms[i][j + 1] == null)
								|| organisms[i][j + 1] instanceof Plant) {
								move(i,j);
							} else if (organisms[i][j + 1] instanceof Carnivore) {
								fight(i,j);
							} else if (organisms[i][j + 1] instanceof Herbivore) {
								eat(i,j,i,j+1);
							}
						}
					}
				}
			}
		}

	}

	protected static void fight(int i, int j) {
		if (organisms[i][j].getLife() < organisms[i][j+1].getLife()){
			eat(i,j+1,i,j);
		} else if (organisms[i][j].getLife() > organisms[i][j+1].getLife()){
			eat(i,j,i,j+1);
		}

	}

	protected static void mate() {

		if (aantalOrganism < ROW*COL)
		{
			int x;
			int y;
			do {
				x = (int) (Math.random() * ROW);
				y = (int) (Math.random() * COL);
			} while (organisms[x][y] != null);
			organisms[x][y] = new Herbivore(0, true);
			aantalOrganism++;
		}
		else
		{
			System.out.println("Organism Overload;;");
		}



	}

	protected static void eat(int i, int j, int i2, int j2) {
		organisms[i][j].setLife(organisms[i][j].getLife() + organisms[i2][j2].getLife());
		organisms[i2][j2] = null;

	}

	protected static void move(int i, int j) {
		List<Direction> directionsToTry = new ArrayList<>(
			Arrays.asList(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));
		int x;
		int y;
		do {

			x = i;
			y = j;

			int randomNum = ThreadLocalRandom.current().nextInt(0, directionsToTry.size());
			switch (directionsToTry.get(randomNum)) {
				case LEFT:
					y--;
					System.out.print("links");
					break;
				case RIGHT:
					y++;
					System.out.print("rechts");
					break;
				case UP:
					x--;
					System.out.print("up");
					break;
				case DOWN:
					x++;
					System.out.print("down");
					break;
			}
			System.out.print(isEmptyPosition(x, y));
			if (isEmptyPosition(x, y)) {
				organisms[x][y] = organisms[i][j];
				organisms[i][j] = null;

				break;
			} else {
				directionsToTry.remove(randomNum);

			}
		} while (!directionsToTry.isEmpty());
		System.out.print("\n");
	}

	protected static boolean isEmptyPosition(int x, int y) {
		try {
			if (organisms[x][y] == null) {
				return true;
			} else {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			return false;
		}
	}


	protected static void generateNewPlants() {
		// TODO Auto-generated method stub

	}
}