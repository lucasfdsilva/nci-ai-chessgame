import java.io.File;
import java.util.*;

import javax.swing.JOptionPane;

public class AIAgent {
  Random rand;

  public AIAgent() {
    rand = new Random();
  }

  /*
   * The method randomMove takes as input a stack of potential moves that the AI
   * agent can make. The agent uses a random number generator to randomly select a
   * move from the inputted Stack and returns this to the calling agent.
   */

  public Move randomMove(Stack possibilities) {

    int moveID = rand.nextInt(possibilities.size());
    System.out.println("Agent randomly selected move: " + moveID);

    for (int i = 1; i < (possibilities.size() - (moveID)); i++) {
      possibilities.pop();
    }

    Move selectedMove = (Move) possibilities.pop();
    return selectedMove;
  }


  

  /*
   * Preparing the Next Best Move AI Agent
   */

  public Move nextBestMove(Stack whitePossibilities, Stack squaresWithBlackPieces) {

    Stack whiteStack = (Stack) whitePossibilities.clone();
    Stack blackStack = (Stack) squaresWithBlackPieces.clone();

    Move whiteMove, normalMove, bestMove;
    Square positionBlack;

    double points = 0;
    double selectedPiece = 0;
    bestMove = null;

    // Loops through the stack of possible moves for the White Player
    while (!whitePossibilities.empty()) {
      whiteMove = (Move) whitePossibilities.pop();

      // Assigns the NormalMove to be the whiteMove from the possibilities stack
      normalMove = whiteMove;

      if ((normalMove.getStart().getYC() < normalMove.getLanding().getYC()) && (normalMove.getLanding().getXC() == 3)
          && (normalMove.getLanding().getYC() == 3)
          || (normalMove.getLanding().getXC() == 4) && (normalMove.getLanding().getYC() == 3)
          || (normalMove.getLanding().getXC() == 3) && (normalMove.getLanding().getYC() == 4)
          || (normalMove.getLanding().getXC() == 4) && (normalMove.getLanding().getYC() == 4)) {
        points = 0.5;

        // assign best move
        if (points > selectedPiece) {
          selectedPiece = points;
          bestMove = normalMove;
        }
      }

      // Loops through the stack of squares that contains enemy pieces which the White player pieces could reach. This Stack was populated using the findBlackPieces method in the ChessProject.java
      while (!blackStack.isEmpty()) {
        points = 0;
        positionBlack = (Square) blackStack.pop();

        if ((normalMove.getLanding().getXC() == positionBlack.getXC())
            && (normalMove.getLanding().getYC() == positionBlack.getYC())) {

          // Defining how valuable each different piece is so I can use that as a
          // parameter to determine which piece the AI should prioritize

          // Converting the positionBlack to a File type as I couldn't get the PieceName
          // out of the positionBlack variable, it would return the entire file path
          File blackPieceName = new File(positionBlack.getName());

          // Attributing value to the Black Pawn
          if (blackPieceName.getName().equals("BlackPawn")) {
            points = 1;
          }

          // Attributing value to the Bishop and Knight
          else if (blackPieceName.getName().equals("BlackBishop") || blackPieceName.getName().equals("BlackKnight")) { 
            points = 3;
          }

          // Attributing value to the Rook
          else if (blackPieceName.getName().equals("BlackRook")) {
            points = 5;
          }

          // Attributing value to the Queen
          else if (blackPieceName.getName().equals("BlackQueen")) {
            points = 9;
          }

          // Attributing value to the King
          else if (blackPieceName.getName().equals("BlackKing")) {
            points = 10;
          }
        }

        // Update the BestMove to be the move that took into consideration the pieces weight
        if (points > selectedPiece) {
          selectedPiece = points;
          bestMove = normalMove;
        }
      }

      blackStack = (Stack) squaresWithBlackPieces.clone();
    }

    // If the best move value is anything higher than 0, the AI agent will pick the
    // best move available, otherwise it will default to the random move.

    // This returns the best move
    if (selectedPiece > 0) {
      System.out.println("Best move: " + selectedPiece);
      return bestMove;
    }

    // This returns the random move
    System.out.println("random move");
    return randomMove(whiteStack);// return random move
  }




  /*
   * Preparing the two levels deep AI Agent
   */

  public Move twoLevelsDeep(Stack possibilities){
    Move selectedMove = new Move();
    return selectedMove;
  }




  /*
    The method below is only used to be able to still play normal 1v1 games without the involvement of the AI agents
    I did this to be able to test the movements more easily and to play the game with my wife during the development for fun
  */
  public Move noAIAgent(Stack possibilities){
    Move selectedMove = new Move();
    return selectedMove;
  }


  /*
  public Move twoLevelsDeep(Stack whitePossibilities, Stack blackPossibilities) {

    Stack white = (Stack) whitePossibilities.clone();
    Stack black = (Stack) blackPossibilities.clone();

    Stack value = new Stack();

    Move blackMove = (Move) black.pop();
    
    int scoreWhite = 0;
    int scoreBlack = 0;
    int scoreHighestWhite = 0;
    int scoreHighestBlack = 0;

    for (int i = 0; i < whitePossibilities.size(); i++) {
      Move whiteMove = (Move) white.pop();
      if (whiteMove.getLanding().getName().isEmpty()
          && (whiteMove.getLanding().getYC() == 3 || whiteMove.getLanding().getYC() == 4)) {
        scoreWhite = 1;
      }
      if (whiteMove.getLanding().getName().contains("Pawn")) {
        scoreWhite = 2;
      }
      if (whiteMove.getLanding().getName().contains("Rook")) {
        scoreWhite = 5;
      }
      if (whiteMove.getLanding().getName().contains("Knight")) {
        scoreWhite = 3;
      }
      if (whiteMove.getLanding().getName().contains("Queen")) {
        scoreWhite = 9;
      }
      if (whiteMove.getLanding().getName().contains("Bishop")) {
        scoreWhite = 3;
      }
      if (whiteMove.getLanding().getName().contains("King")) {
        scoreWhite = 10;
      }
      if (scoreWhite > scoreHighestWhite) {
        scoreHighestWhite = scoreWhite;
      }
    }

    return nextBestMove(blackPossibilities);
  }
  */
}