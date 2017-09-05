package be.vdab.terrarium;

import java.util.Scanner;

import be.vdab.entities.Organism;

public class Gui {

  public static void main(String[] args)
  {
    Board.setTestPositions();
    Board.print();

    Scanner scanner = new Scanner(System.in);
    System.out.println("Druk op <ENTER> voor een volgende dag,\nDruk op <S> + <ENTER> om te stoppen.");
    String invoer = scanner.nextLine();

    while (! invoer.equals("S"))
    {
      Board.nextDay();
      Board.print();

      System.out.println("Druk op <ENTER> voor een volgende dag,\nDruk op <S> + <ENTER> om te stoppen.");
      invoer = scanner.nextLine();
    }
  }
}