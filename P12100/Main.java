package P12100;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 데이터 입력 받기
        Scanner scanner = new Scanner(System.in);
        int boardLength = scanner.nextInt();
        int[][] board = new int[boardLength][boardLength];
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                board[i][j] = scanner.nextInt();
            }
        }

        printBoard(board);

        moveRight(board);

    }

    public static void moveUp(int[][] board) {
        boolean flag;
        int i = 0;
        for (int j = 0; j < board.length; j++) {
            flag = false;
            while (i + 1 < board.length) {
                flag = mergeUp(board, i, j, i + 1, j, flag);
                i++;
            }
        }
    }

    public static boolean mergeUp(int[][] board, int targetI, int targetJ, int sourceI, int sourceJ, boolean isBeforeMerged) {
        System.out.printf("merge %d:%d(%d) <- %d:%d(%d)%n", targetI, targetJ, board[targetI][targetJ], sourceI, sourceJ, board[sourceI][sourceJ]);

        if (board[targetI][targetJ] == board[sourceI][sourceJ]) {
            if (isBeforeMerged) {
                board[targetI - 1][targetJ] = board[targetI][targetJ] * 2;
            } else {
                board[targetI][targetJ] *= 2;
            }
            return true;
        } else {
            return false;
        }
    }

    public static void moveRight(int[][] board) {
        boolean flag;
        for (int i = 0; i < board.length; i++) {
            flag = false;
            for (int j = board.length - 1; j >= 0; j--) {
                flag = mergeRight(board, i, j, i, j - 1, flag);
            }
        }
    }

    public static boolean mergeRight(int[][] board, int targetI, int targetJ, int sourceI, int sourceJ, boolean isBeforeMerged) {
        if (sourceJ < 0) {
            System.out.printf("merge %d:%d(%d) <- (0)%n", targetI, targetJ, board[targetI][targetJ]);
            if (isBeforeMerged) {
                board[targetI][targetJ] = 0;
            }
            return false;
        } else {
            System.out.printf("merge %d:%d(%d) <- %d:%d(%d)%n", targetI, targetJ, board[targetI][targetJ], sourceI, sourceJ, board[sourceI][sourceJ]);
        }

        if (board[targetI][targetJ] == board[sourceI][sourceJ]) {
            if (isBeforeMerged) {
                board[targetI][targetJ + 1] = board[targetI][targetJ] * 2;
            } else {
                board[targetI][targetJ] *= 2;
            }
            return true;
        } else {
            return false;
        }
    }

    public static void printBoard(int[][] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
