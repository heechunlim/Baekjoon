package P3190;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 데이터 입력 받기
        Scanner scanner = new Scanner(System.in);
        int boardLength = scanner.nextInt();
        int appleLocationCount = scanner.nextInt();
        List<Point> appleLocationList = new ArrayList<>();
        for (int i = 0; i < appleLocationCount; i++) {
            appleLocationList.add(new Point(scanner.nextInt(), scanner.nextInt()));
        }

        int snakeDirectionCount = scanner.nextInt();
        List<SnakeDirection> snakeDirectionList = new ArrayList<>();
        for (int i = 0; i < snakeDirectionCount; i++) {
            snakeDirectionList.add(new SnakeDirection(scanner.nextInt(), scanner.next().equals("D")));
        }

        // 보드 생성
        String[][] board = new String[boardLength][boardLength];
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                board[i][j] = "N";
            }
        }

        // 뱀 세팅
        board[1 - 1][boardLength - 1] = "S";

        // 사과 세팅
        for (Point point : appleLocationList) {
            board[point.y - 1][boardLength - point.x] = "A";
        }

        // 시간 체크 변수 (결과)
        int timeCount = 0;

        // 뱀
        List<Point> snake = new ArrayList<>();
        // 뱀 초기화
        snake.add(new Point(1 - 1, boardLength - 1));
        // 뱀이 이동하는 방향 (U, D, L, R)
        String direction = "R";
        Iterator<SnakeDirection> snakeDirectionIterator = snakeDirectionList.iterator();
        SnakeDirection current = snakeDirectionIterator.next();
        while (!checkSnakeDie(board, snake, direction)) {
            timeCount++;

            // 뱀 이동
            moveSnake(board, snake, direction);

            // 방향 바꿔야하는지 확인
            if (current.time == timeCount) {
                direction = changeDirection(direction, current.isRight);
                if (snakeDirectionIterator.hasNext()) {
                    current = snakeDirectionIterator.next();
                }
            }

//            for (int i = 0; i < boardLength; i++) {
//                for (int j = 0; j < boardLength; j++) {
//                    System.out.print(board[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("-----------------------------");
        }

        System.out.println(timeCount + 1);
    }

    static String changeDirection(String direction, boolean isRight) {
        switch (direction) {
            case "U":
                if (isRight) {
                    return "R";
                } else {
                    return "L";
                }
            case "D":
                if (isRight) {
                    return "L";
                } else {
                    return "R";
                }
            case "L":
                if (isRight) {
                    return "U";
                } else {
                    return "D";
                }
            case "R":
                if (isRight) {
                    return "D";
                } else {
                    return "U";
                }
        }
        return null;
    }

    static void moveSnake(String[][] board, List<Point> snake, String direction) {
        Point snakeHead = snake.get(0);
        // 다음 위치 구하기
        int x = 0;
        int y = 0;
        switch (direction) {
            case "U":
                x = snakeHead.x;
                y = snakeHead.y + 1;
                break;
            case "D":
                x = snakeHead.x;
                y = snakeHead.y - 1;
                break;
            case "L":
                x = snakeHead.x - 1;
                y = snakeHead.y;
                break;
            case "R":
                x = snakeHead.x + 1;
                y = snakeHead.y;
                break;
        }

        Point snakeNextHead = new Point(x, y);
        snake.add(0, snakeNextHead);

        // 다음 위치가 사과인 경우
        if (board[x][y].equals("A")) {
            board[x][y] = "S";
        } else { // 아닌 경우
            board[x][y] = "S";
            Point snakeTail = snake.get(snake.size() - 1);
            board[snakeTail.x][snakeTail.y] = "N";
            snake.remove(snakeTail);
        }
    }

    static boolean checkSnakeDie(String[][] board, List<Point> snake, String direction) {
        int boardLength = board.length;
        Point snakeHead = snake.get(0);

        // 다음 위치 구하기
        int x = 0;
        int y = 0;
        switch (direction) {
            case "U":
                x = snakeHead.x;
                y = snakeHead.y + 1;
                break;
            case "D":
                x = snakeHead.x;
                y = snakeHead.y - 1;
                break;
            case "L":
                x = snakeHead.x - 1;
                y = snakeHead.y;
                break;
            case "R":
                x = snakeHead.x + 1;
                y = snakeHead.y;
                break;
        }

        // 다음 위치가 죽는 경우인지 확인
        if (x == -1 || x == boardLength || y == -1 || y == boardLength || board[x][y].equals("S")) {
            return true;
        }

        return false;
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class SnakeDirection {
    int time;
    boolean isRight;

    public SnakeDirection(int time, boolean isRight) {
        this.time = time;
        this.isRight = isRight;
    }
}