import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        displayInstructions();

        char[][] gameBoard = {
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '}
        };    

        int[] chosenSpots = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Please enter which spot you would like to insert your piece (1-9): ");
        int position = keyboard.nextInt();
        while(position < 1 || position > 9){
            System.out.println("Please enter a value from 1 through 9: ");
            position = keyboard.nextInt();
        }

        gameBoard = placePiece(gameBoard, position, "user");
        chosenSpots[position - 1] = 1;
        displayBoard(gameBoard);

        System.out.println("CPU turn...\n");

        Random ram = new Random();
        int cpuPosition = ram.nextInt(9) + 1;
        while(chosenSpots[cpuPosition - 1] == 1){
            cpuPosition = ram.nextInt(9) + 1;
        }
        chosenSpots[cpuPosition - 1] = 1;
        gameBoard = placePiece(gameBoard, cpuPosition, "cpu");
        displayBoard(gameBoard);

        boolean continueGame = false;
        winnerReturnObject checkObj = new winnerReturnObject(false, ' ');

        while(continueGame == false){
            System.out.println("Please enter which spot you would like to insert your piece (1-9): ");
            position = keyboard.nextInt();

            while((position < 1 || position > 9) || (chosenSpots[position - 1] == 1)){
                if(position < 1 || position > 9){
                    System.out.println("Please enter a value from 1 through 9: ");
                    position = keyboard.nextInt();
                } else if(chosenSpots[position - 1] == 1){
                    System.out.println("That spot is already marked, please choose another one: ");
                    position = keyboard.nextInt();
                }
            }

            chosenSpots[position - 1] = 1;
            gameBoard = placePiece(gameBoard, position, "user");
            displayBoard(gameBoard);

            checkObj = checkForWinner(gameBoard);
            continueGame = checkObj.getCheck();
            if(continueGame == true){
                break; // ends game if user connects 3 marks before cpu move
            }

            if(fullGameBoard(chosenSpots) == true){
                System.out.println("The result is a draw! Try again!");
                break;
            }

            System.out.println("CPU turn...\n");

            cpuPosition = ram.nextInt(9) + 1;
            while(chosenSpots[cpuPosition - 1] == 1){
                cpuPosition = ram.nextInt(9) + 1;
                //System.out.println("Another round");
            }
            chosenSpots[cpuPosition - 1] = 1;
            gameBoard = placePiece(gameBoard, cpuPosition, "cpu");
            displayBoard(gameBoard);

            checkObj = checkForWinner(gameBoard);
            continueGame = checkObj.getCheck();

            if(fullGameBoard(chosenSpots) == true){
                System.out.println("The result is a draw! Try again!");
                break;
            }
        }

        if(checkObj.getSymb() == 'X'){
            System.out.println("Congratulations, you won!");
        } else if(checkObj.getSymb() == 'O') {
            System.out.println("The CPU won! Try again!");
        }
        
    }

    public static void displayInstructions(){
        System.out.println("Hello, welcome to Tic Tac Toe in Java!");
        System.out.println("The board and board spaces appear as the following: ");
        System.out.println("1 | 2 | 3");
        System.out.println("- + - + -");
        System.out.println("4 | 5 | 6");
        System.out.println("- + - + -");
        System.out.println("7 | 8 | 9");
        System.out.println("The first turn will be yours, then the cpu, then the game continues back and forth.");
        System.out.println("You are required to enter an integer value from 1 through 9, to enter your mark in the corresponding space.");
        System.out.println("The first player to successfully connect 3 of their marks in a row, either vertically, horizontally, or diagonally, wins.");
        System.out.println("Good luck!");
    }

    public static void displayBoard(char[][] gameBoard){
        for(char[] row: gameBoard){
            for(char c: row){
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println("__________\n");
    } 

    public static char[][] placePiece(char[][] gameBoard, int position, String player){

        char symbol = ' ';

        if(player.equals("user")){
            symbol = 'X';
        } else if(player.equals("cpu")){
            symbol = 'O';
        }
        switch(position){
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }

        return gameBoard;
    }

    public static winnerReturnObject checkForWinner(char[][] gameBoard){

        boolean winnerFound = false;
        char winningSymbol = ' ';
        winnerReturnObject container = new winnerReturnObject(winnerFound, winningSymbol);

        // horizontal checks
        if(gameBoard[0][0] == gameBoard[0][2] && gameBoard[0][2] == gameBoard[0][4] && gameBoard[0][0] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[0][0];   
        } 
        if(gameBoard[2][0] == gameBoard[2][2] && gameBoard[2][2] == gameBoard[2][4] && gameBoard[2][0] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[2][0];
        }
        if(gameBoard[4][0] == gameBoard[4][2] && gameBoard[4][2] == gameBoard[4][4] && gameBoard[4][0] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[4][0];
        } 
        // vertical checks
        if(gameBoard[0][0] == gameBoard[2][0] && gameBoard[2][0] == gameBoard[4][0] && gameBoard[0][0] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[0][0];
        }
        if(gameBoard[0][2] == gameBoard[2][2] && gameBoard[2][2] == gameBoard[4][2] && gameBoard[0][2] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[0][2];
        }
        if(gameBoard[0][4] == gameBoard[2][4] && gameBoard[2][4] == gameBoard[4][4] && gameBoard[0][4] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[0][4];
        } 
        // diagonal checks
        if(gameBoard[0][0] == gameBoard[2][2] && gameBoard[2][2] == gameBoard[4][4] && gameBoard[0][0] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[0][0];
        }
        if(gameBoard[0][4] == gameBoard[2][2] && gameBoard[2][2] == gameBoard[4][0] && gameBoard[0][4] != ' '){
            winnerFound = true;
            winningSymbol = gameBoard[0][4];
        }
        
        
        container.setCheck(winnerFound);
        container.setSymb(winningSymbol);

        return container;
    }

    public static boolean fullGameBoard(int[] array){
        boolean result = false;
        int[] compare = {1, 1, 1, 1, 1, 1, 1, 1, 1};

        // for(int i: array){
        //     if(i != 1){
        //         result = true;
        //     }
        // }

        if(Arrays.equals(compare, array)){
            result = true;
        }

        return result;
    }
}
