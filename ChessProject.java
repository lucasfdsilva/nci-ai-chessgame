/*

Intro to AI Module - NCI

Lucas Felipe da Silva
x17118361

*/

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {

    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;

    int xAdjustment;
    int yAdjustment;
    int startX;
    int startY;
    int initialX;
    int initialY;

    int landingX;
    int landingY;
    int xMovement;
    int yMovement;

    boolean validMove;
    boolean success;

    String pieceName;

    MouseEvent currentEvent;

    JPanel panels;
    JLabel pieces;

    Boolean whitesTurn;

    public ChessProject() {

        Dimension boardSize = new Dimension(600, 600);

        whitesTurn = true;
        this.setTitle("Player playing as White always start...");

        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.white : Color.darkGray);
            else
                square.setBackground(i % 2 == 0 ? Color.darkGray : Color.white);
        }

        // Setting up the Initial Chess board.
        for (int i = 8; i < 16; i++) {
            pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhitePawn.png")));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteRook.png")));
        panels = (JPanel) chessBoard.getComponent(0);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteKnight.png")));
        panels = (JPanel) chessBoard.getComponent(1);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteKnight.png")));
        panels = (JPanel) chessBoard.getComponent(6);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteBishop.png")));
        panels = (JPanel) chessBoard.getComponent(2);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteBishop.png")));
        panels = (JPanel) chessBoard.getComponent(5);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteKing.png")));
        panels = (JPanel) chessBoard.getComponent(3);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteQueen.png")));
        panels = (JPanel) chessBoard.getComponent(4);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/WhiteRook.png")));
        panels = (JPanel) chessBoard.getComponent(7);
        panels.add(pieces);

        for (int i = 48; i < 56; i++) {
            pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackPawn.png")));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackRook.png")));
        panels = (JPanel) chessBoard.getComponent(56);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackKnight.png")));
        panels = (JPanel) chessBoard.getComponent(57);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackKnight.png")));
        panels = (JPanel) chessBoard.getComponent(62);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackBishop.png")));
        panels = (JPanel) chessBoard.getComponent(58);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackBishop.png")));
        panels = (JPanel) chessBoard.getComponent(61);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackKing.png")));
        panels = (JPanel) chessBoard.getComponent(59);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackQueen.png")));
        panels = (JPanel) chessBoard.getComponent(60);
        panels.add(pieces);

        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/BlackRook.png")));
        panels = (JPanel) chessBoard.getComponent(63);
        panels.add(pieces);
    }

    //This method checks if there is a piece present on a particular square.
    private Boolean piecePresent(int x, int y) {
        Component c = chessBoard.findComponentAt(x, y);
        if (c instanceof JPanel) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean piecePresent() {
        return piecePresent(currentEvent.getX(), currentEvent.getY());
    }

    //Check piece is opponent
    private Boolean checkWhiteopponent(int newX, int newY) {
        return checkOpponentIs("Black", newX, newY);
    }

    private Boolean checkBlackopponent(int newX, int newY) {
        return checkOpponentIs("White", newX, newY);
    }

    private boolean checkOpponentIs(String colour, int newX, int newY) {
        Boolean opponent;
        Component c1 = chessBoard.findComponentAt(newX, newY);
        JLabel awaitingPiece = (JLabel) c1;
        String tmp1 = awaitingPiece.getIcon().toString();
        if (((tmp1.contains(colour)))) {
            opponent = true;
        } else {
            opponent = false;
        }
        return opponent;
    }

    //Press Mouse
    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel)
            return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel) c;

        String tmp = chessPiece.getIcon().toString();
        pieceName = new File(tmp).getName();
        pieceName = pieceName.substring(0, (pieceName.length() - 4));

        //Logic to enforce turns. White goes first
        if (whitesTurn) {
            if (!pieceName.contains("White")) {
                JOptionPane.showMessageDialog(null, "This is White's Turn...");
                return;
            }
        } else {
            if (!pieceName.contains("Black")) {
                JOptionPane.showMessageDialog(null, "This is Black's Turn...");
                return;
            }
        }

        initialX = e.getX();
        initialY = e.getY();
        startX = (e.getX() / 75);
        startY = (e.getY() / 75);
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    //Mouse Released
    public void mouseReleased(MouseEvent e) {
        currentEvent = e;
        if (chessPiece == null) return;

        

        chessPiece.setVisible(false);
        success = false;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        String tmp = chessPiece.getIcon().toString();
        pieceName = new File(tmp).getName();
        pieceName = pieceName.substring(0, (pieceName.length() - 4));
        validMove = false;

        System.out.println("Raw: (" + e.getX() + "," + e.getY() + ").");
        landingX = (e.getX()/75);
        landingY  = (e.getY()/75);
        xMovement = Math.abs((e.getX()/75)-startX);
        yMovement = Math.abs((e.getY()/75)-startY);

        System.out.println("----------------------------------------------");
        System.out.println("The piece that is being moved is : "+pieceName);
        System.out.println("The starting coordinates are : "+"( "+startX+","+startY+")");
        System.out.println("The xMovement is : "+xMovement);
        System.out.println("The yMovement is : "+yMovement);
        System.out.println("The landing coordinates are : "+"( "+landingX+","+landingY+")");
        System.out.println("----------------------------------------------");

        String title = whitesTurn ? "This is a White's Turn..." : "This is a Black's Turn...";
        this.setTitle(title);

        switch (pieceName) {
            case "WhitePawn":
            case "BlackPawn":
                movePawn();
                break;
            case "WhiteKnight":
            case "BlackKnight":
                moveKnight();
                break;
            case "WhiteBishop":
            case "BlackBishop":
                moveBishop();
                break;
            case "BlackRook":
            case "WhiteRook":
                moveRook();
                break;
            case "BlackQueen":
            case "WhiteQueen":
                moveQueen();
                break;
            case "BlackKing":
            case "WhiteKing":
                moveKing();
                break;
            default:
                validMove = false;
                break;
        }

        //Checks if the movement was valid before passing on the round to the other player
        if(validMove) { 
            whitesTurn = !whitesTurn;
        }else{
            JOptionPane.showMessageDialog(null, "Invalid Move, please try again...");
        }

        //Apply the consequences depending on player's move
        if (!validMove) {
            putPieceBack();
        } else {
            if (success) {
                String colour = pieceName.contains("White") ? "White" : "Black";
                int location = (colour.equals("White")) ? 56 + (e.getX() / 75) : (e.getX() / 75);;
                Object [] options = {"Pawn", "Rook", "Knight", "Bishop", "King", "Queen"};

                JOptionPane pane = new JOptionPane("Promotion Time: Choose which piece you want to become...", JOptionPane.QUESTION_MESSAGE);
                pane.setWantsInput(true);
                pane.setSelectionValues(options);
                pane.setInitialSelectionValue(options[0]);
                JDialog dialog = pane.createDialog(layeredPane, "Success");
                dialog.setVisible(true);
                String selectedPiece = pane.getInputValue().toString();
                if (c instanceof JLabel) {
                    Container parent = c.getParent();
                    parent.remove(0);
                    pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/" + colour + selectedPiece + ".png")));
                    parent = (JPanel) chessBoard.getComponent(location);
                    parent.add(pieces);
                    pieces.setVisible(true);
                    parent.validate();
                    parent.repaint();
                } else {
                    Container parent = (Container) c;
                    pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("/images/" + colour + selectedPiece + ".png")));
                    parent = (JPanel) chessBoard.getComponent(location);
                    parent.add(pieces);
                    pieces.setVisible(true);
                    parent.validate();
                    parent.repaint();
                }
            } else {
                if (c instanceof JLabel) {
                    Container parent = c.getParent();
                    parent.remove(0);
                    parent.add(chessPiece);
                } else {
                    Container parent = (Container) c;
                    parent.add(chessPiece);
                }
                chessPiece.setVisible(true);
            }
        }
    }

    //Pawn's Move set build
    private void movePawn() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }
        Boolean whitePawn = pieceName.contains("White");
        if (whitePawn) {
            if (landingY < startY) {
                validMove = false;
                return;
            }
        } else {
            if (landingY > startY) {
                validMove = false;
                return;
            }
        }
        Boolean startCondition = whitePawn ? startY == 1 : startY == 6;
        Boolean directionCondition = whitePawn ? startY < landingY : startY > landingY;
        //On the first move, pawn can move 2 spaces.
        //Pawn can only move forward (White = Y+, Black = Y-)
        //Pawn can also take an opponent on his first move.
        if (startCondition) {
            if ((yMovement == 1 || yMovement == 2) && directionCondition && xMovement == 0) {
                if (yMovement == 2) {
                    if ((!piecePresent(currentEvent.getX(), (currentEvent.getY()))) &&
                            (!piecePresent(currentEvent.getX(), (currentEvent.getY() + 75)))) {
                        validMove = true;
                    } else {
                        validMove = false;
                    }
                } else {
                    if ((!piecePresent(currentEvent.getX(), (currentEvent.getY())))) {
                        validMove = true;
                    } else {
                        validMove = false;
                    }
                }
            } else if (xMovement == 1 && yMovement == 1) {
                //Diagonal, trying to take opponent. Check if opponent is there.
                if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement == 1) && (yMovement == 1)) {
                    //If opponent is King, its over!
                    if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
                        String winMessage = whitePawn ? "Game Over - White Wins!!" : "Game Over - Black Wins!!";
                        JOptionPane.showMessageDialog(null, winMessage);
                        System.exit(1);
                    }
                    Boolean opponentCondition = whitePawn ? checkWhiteopponent(currentEvent.getX(), currentEvent.getY()): checkBlackopponent(currentEvent.getX(), currentEvent.getY());
                    if (opponentCondition) {
                        validMove = true;
                    } else {
                        validMove = false;
                    }
                } else {
                    validMove = false;
                }
            }
        } else {
            Boolean p2StartCondition = whitePawn ? (startX - 1 >= 0) || (startX + 1 <= 7) : (startX <= 7) || (startX - 1 == 0);
            if (p2StartCondition) {
                //Enforce that  movement is diagonal, 1 square AND opponent piece is present
                if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement == 1) && (yMovement == 1)) {
                    if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
                        String winMessage = whitePawn ? "Game Over - White Wins!!" : "Game Over - Black Wins!!";
                        JOptionPane.showMessageDialog(null, winMessage);
                        System.exit(1);
                    }
                    Boolean opponentCondition = whitePawn ? checkWhiteopponent(currentEvent.getX(), currentEvent.getY()): checkBlackopponent(currentEvent.getX(), currentEvent.getY());
                    if (opponentCondition) {
                        validMove = true;
                    } else {
                        validMove = false;
                    }
                } else {
                    //Normal move, no piece present, movement only 1 square in the Y direction.
                    if (!piecePresent(currentEvent.getX(), (currentEvent.getY()))) {
                        if (xMovement == 0 && yMovement == 1) {
                            Boolean successStartCondition = whitePawn == true ? startY == 6 : startY == 1;
                            if (successStartCondition) {
                                success = true;
                            }
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        validMove = false;
                    }
                }
            } else {
                validMove = false;
            }
        }
    }

     //Knight's Move set build
    private void moveKnight() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }

        //Knight moves in an L shape, with one leg 2 and one leg 1.
        if ((xMovement == 1 && yMovement == 2) || (xMovement == 2 && yMovement == 1)) {
            completeMove();
        } else {
            validMove = false;
        }
    }

    //Bishop's Move set build
    private void moveBishop() {
        Boolean inTheWay = false;
        int distance = Math.abs(startX-landingX);
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        } else {
            //Bishop can move diagonally, with no piece in the way
            validMove = true;
            if (xMovement == yMovement) {
                inTheWay = inTheWayDiagonal();
                if (inTheWay) {
                    validMove = false;
                } else {
                    completeMove();
                }
            } else {
                validMove = false;
            }
        }
    }

    //Rook's Move set build
    private void moveRook() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }
        //Rook can move vertical or horizontal, with no piece in the way
        if ((xMovement !=0 && yMovement == 0) || (yMovement != 0 && xMovement == 0)) {
            Boolean inTheWay = inTheWayLateral();
            if (inTheWay) {
                validMove = false;
                return;
            } else {
                completeMove();
            }
        } else {
            validMove = false;
        }
    }
    //Queen's Move set build
    private void moveQueen() {
        if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            validMove = false;
            return;
        }
        Boolean inTheWay = false;
        //Queen can move vertical, horizontal and diagonal, with no piece in the way
        if ((xMovement != 0 && yMovement == 0) || (yMovement != 0 && xMovement == 0)) {
            //Lateral
            inTheWay = inTheWayLateral();
        } else if (xMovement == yMovement) {
            //Diagonal
            inTheWay = inTheWayDiagonal();
        } else {
            validMove = false;
            return;
        }
        if (inTheWay) {
            validMove = false;
            return;
        } else {
            completeMove();
        }
    }

    //King's Move set build
    private void moveKing() {
        validMove = true;
        //If either absX or absY movement is larger than 1, we know King has overstepped
        if (xMovement > 1 || yMovement > 1) {
            validMove = false;
            return;
        }
        //Ensure King is not within 1 square of where we are moving to
        if ((checkKingAtLoc(currentEvent.getX() - 75, currentEvent.getY() + 75))
                || (checkKingAtLoc(currentEvent.getX() - 75, currentEvent.getY()))
                || (checkKingAtLoc(currentEvent.getX() - 75, currentEvent.getY() - 75))
                || (checkKingAtLoc(currentEvent.getX(), currentEvent.getY() - 75))
                || (checkKingAtLoc(currentEvent.getX() + 75, currentEvent.getY() - 75))
                || (checkKingAtLoc(currentEvent.getX() + 75, currentEvent.getY()))
                || (checkKingAtLoc(currentEvent.getX() + 75, currentEvent.getY() + 75))
                || (checkKingAtLoc(currentEvent.getX(), currentEvent.getY() + 75))) {
            validMove = false;
            return;
        }
        completeMove();
    }

    //Check if the king is in that location
    private Boolean checkKingAtLoc(int x, int y) {
        try {
            Component c1 = chessBoard.findComponentAt(x, y);
            if (c1 instanceof JPanel) {
                return false;
            }
            JLabel checkingPiece = (JLabel) c1;
            String tmp1 = checkingPiece.getIcon().toString();
            return tmp1.contains("King");
        } catch (Exception e) {
            return false;
        }
    }

    //Complete a move for all pieces except a Pawn
    private void completeMove() {
        if (piecePresent(currentEvent.getX(), currentEvent.getY())) {
            if (pieceName.contains("White")) {
                if (checkWhiteopponent(currentEvent.getX(), currentEvent.getY())) {
                    if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
                        JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
                        System.exit(1);
                    }
                    validMove = true;
                } else {
                    validMove = false;
                }
            } else if (pieceName.contains("Black")) {
                if (checkBlackopponent(currentEvent.getX(), currentEvent.getY())) {
                    if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
                        JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
                        System.exit(1);
                    }
                    validMove = true;
                } else {
                    validMove = false;
                }
            }
        } else {
            validMove = true;
        }
    }

    //Check if there is a piece in the way in any diagonal path
    private boolean inTheWayDiagonal() {
        Boolean inTheWay = false;
        if ((startX-landingX < 0) && (startY-landingY) < 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX+(i*75)), (initialY+(i*75)))) {
                    inTheWay = true;
                }
            }
        } else if ((startX-landingX) < 0 && (startY-landingY) > 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX+(i*75)), (initialY-(i*75)))) {
                    inTheWay = true;
                }
            }
        } else if ((startX-landingX) > 0 && (startY - landingY) > 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX-(i*75)), (initialY-(i*75)))) {
                    inTheWay = true;
                }
            }
        } else if ((startX-landingX) > 0 && (startY - landingY) < 0) {
            for (int i = 0; i < xMovement; i++) {
                if (piecePresent((initialX-(i*75)), (initialY+(i*75)))) {
                    inTheWay = true;
                }
            }
        }
        return inTheWay;
    }

    //Check if a piece is in the way in any lateral path
    private boolean inTheWayLateral() {
        Boolean inTheWay = false;
        if (xMovement != 0) {
            if (startX - landingX > 0) {
                //Moving left on X-Axis
                for (int i = 0 ; i < xMovement ; i++) {
                    if (piecePresent(initialX-(i*75), currentEvent.getY())) {
                        return true;
                    } else {
                        inTheWay = false;
                    }
                }
            } else if (startX - landingX < 0) {
                //Moving right on X-Axis
                for (int i = 0 ; i < xMovement ; i++) {
                    if (piecePresent(initialX+(i*75), currentEvent.getY())) {
                        return true;
                    } else {
                        inTheWay = false;
                    }
                }
            }
        } else if (yMovement != 0) {
            if (startY - landingY > 0) {
                //Moving Up on Y-Axis
                for (int i = 0 ; i < yMovement ; i++) {
                    if (piecePresent(currentEvent.getX(), initialY-(i*75))) {
                        return true;
                    } else {
                        inTheWay = false;
                    }
                }
            } else if (startY - landingY < 0) {
                //Moving down on Y-Axis
                for (int i = 0 ; i < yMovement ; i++) {
                    if (piecePresent(currentEvent.getX(), initialY+(i*75))) {
                        return true;
                    } else {
                        inTheWay = false;
                    }
                }
            }
        }
        return inTheWay;
    }

    //Check if the game is over
    private Boolean isGameOver(int newX, int newY) {
        Boolean kingTaken = false;
        Component c1 = chessBoard.findComponentAt(newX, newY);
        JLabel takenPiece = (JLabel) c1;
        String tmp1 = takenPiece.getIcon().toString();
        if (((tmp1.contains("King")))) {
            kingTaken = true;
        } else {
            kingTaken = false;
        }
        return kingTaken;
    }

    //If the movement is deemed ilegal, the method below returns the piece to it's original location
    private void putPieceBack() {
        int location = 0;
        if (startY == 0) {
            location = startX;
        } else {
            location = (startY * 8) + startX;
        }
        pieces = new JLabel(new ImageIcon(ChessProject.class.getResource("images/" + pieceName + ".png")));
        panels = (JPanel) chessBoard.getComponent(location);
        panels.add(pieces);
        panels.validate();
        panels.repaint();
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}



	//Main method
    public static void main(String[] args) {
        JFrame frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

