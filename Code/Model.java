import java.util.Arrays;

public class Model {
    private String[][] currentBoard;
    private String[][] prevBoard;
    private String player;
    private View view;
    private int p1Undo;
    private int p2Undo;
    private int undoCount;

    public Model()
    {
        start();
    }

    //Initialization of variables
    public void start()
    {
        player = "X";
        currentBoard = new String[3][3];
        prevBoard = new String[3][3];
        for(int i = 0; i < currentBoard.length; i ++)
            for(int j = 0; j < currentBoard.length; j ++){
                currentBoard[i][j] = "";
            }
        setBoard(prevBoard,currentBoard);
    }

    //Switch to next player
    public void setNextPlayer()
    {
        if(player.equals("X")) {
            player = "O";
        }
        else if(player.equals("O")) {
            player = "X";
        }
    }
    public String[][] getCurrentBoard() {
        return currentBoard;
    }

    public String[][] getPrevBoard() {
        return prevBoard;
    }

    public String getPlayer()
    {
        return player;
    }

    public void setView(View view)
    {
        this.view = view;
    }

    //Reset player's undo count
    public void resetUndo()
    {
        if(undoCount != 1) {
            view.enableUndo();
            undoCount = 0;
        } else {
            undoCount++;
        }
        
        if(player.equals("X"))
            p1Undo = 0;
        else
            p2Undo = 0;
    }

    //Increment player's undo count for turn
    public void setUndo()
    {
        if(player.equals("X"))
            p2Undo++;
        else
            p1Undo++;
        System.out.println("Undo Button Pressed");
        System.out.println("P1: " + p1Undo);
        System.out.println("P2: " + p2Undo);
    }

    //Undo last move if player has undo's left
    public void undo()
    {
        if(p1Undo < 3 && p2Undo < 3)
        {

            if(!Arrays.deepEquals(currentBoard,prevBoard)) {
                setUndo();
                setNextPlayer();
            }

            if(p1Undo == 3 || p2Undo == 3) {
                view.disableUndo();
                undoCount++;
            }
            

            setBoard(currentBoard, prevBoard);
            view.update();
        }
        else {
            System.out.println(player);
        }
    }

    //Sets value at coordinates (x,y) to current player's symbol
    public void setValue(int x, int y)
    {
        setBoard(prevBoard,currentBoard);
        currentBoard[x][y] = player;
        view.update();
        if(hasWinner())
            view.gameEnd("Winner");
        else if(boardFull())
            view.gameEnd("Game Ended in Tie");


        System.out.println(Arrays.deepToString(currentBoard));
        System.out.println(Arrays.deepToString(prevBoard));
    }

    //Checks if winner is on board
    public boolean hasWinner()
    {
        return checkDiagonal() || checkRow();
    }

    //Checks if board is full (tie)
    public boolean boardFull()
    {
        int count = 0;
        for(int i = 0; i < currentBoard.length; i ++)
            for(int j = 0; j < currentBoard[i].length; j ++)
                if(!currentBoard[i][j].equals(""))
                    count ++;

        return count == 9;
    }

    //Checks for possible diagonal winners
    public boolean checkDiagonal()
    {
        int rightDiagonal = 0;
        int leftDiagonal = 0;
        for(int i = 0; i < currentBoard.length; i ++)
        {
            if (currentBoard[i][i].equals(player))
                rightDiagonal++;
            if (currentBoard[currentBoard.length - 1-i][i].equals(player))
                leftDiagonal++;
        }
        return rightDiagonal == 3 || leftDiagonal == 3;

    }

    //Checks for possible row/column winners
    public boolean checkRow()
    {
        for(int i = 0; i < currentBoard.length; i ++)
        {
            if(!currentBoard[i][0].equals("") && currentBoard[i][0].equals(currentBoard[i][1]) && currentBoard[i][1].equals(currentBoard[i][2]))
                return true;
            else if(!currentBoard[0][i].equals("") && currentBoard[0][i].equals(currentBoard[1][i]) && currentBoard[1][i].equals(currentBoard[2][i]))
                return true;
        }
        return false;
    }
    
    //deep copy of a2 to a1
    public void setBoard(String[][] a1, String[][] a2)
    {
        for(int i = 0; i < a1.length; i ++)
            for(int j = 0; j < a1[i].length; j ++)
                a1[i][j] = a2[i][j];
    }

}