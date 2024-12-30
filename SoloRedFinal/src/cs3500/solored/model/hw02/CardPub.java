package cs3500.solored.model.hw02;

/**
 * extending the card interface to add new methods to form the physical cards,
 * with a number and a color.  Uses the Colors enum for color representation and calls
 * a getter for the numbers.
 */
public interface CardPub extends Card {
  int getNum();

  Colors getCol();
}
