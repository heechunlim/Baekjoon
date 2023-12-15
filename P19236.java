import java.util.Arrays;
import java.util.Scanner;

public class P19236 {
    public static void main(String[] args) {
        // 데이터 입력 받기
        Scanner scanner = new Scanner(System.in);
        Board[][] board = new Board[4][4];

        // 결과
        int result;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = new Board();
                board[i][j].type = Type.FISH;
                board[i][j].fishNum = scanner.nextInt();
                board[i][j].direction = Direction.getD(scanner.nextInt());
            }
        }

//        print(board);

        result = firstSharkEat(board, new Point2(0, 0));

//        print(board);

        while (true) {
            for (int i = 1; i <= 16; i++) {
                Point2 fishPoint = findFish(board, i);
                if (fishPoint != null) {
                    moveFish(board, fishPoint);
                }
            }

//            print(board);

            Point2 sharkPoint = findShark(board);

            if (!checkMove(board, sharkPoint)) {
                System.out.println(result);
                return;
            }

            Point2 next = getMaxMove(board[sharkPoint.x][sharkPoint.y], sharkPoint);
//            System.out.println(next.x);
//            System.out.println(next.y);

            result += moveSharkEat(board, next);
//            System.out.println(result);
        }
    }

    public static Point2 getMaxMove(Board shark, Point2 currentSharkPoint) {
        Point2 expectSharkPoint = new Point2();
        Point2 result;

        while (true) {
            switch (shark.direction) {
                case U:
                    expectSharkPoint.x = currentSharkPoint.x - 1;
                    expectSharkPoint.y = currentSharkPoint.y;
                    break;
                case D:
                    expectSharkPoint.x = currentSharkPoint.x + 1;
                    expectSharkPoint.y = currentSharkPoint.y;
                    break;
                case E:
                    expectSharkPoint.x = currentSharkPoint.x;
                    expectSharkPoint.y = currentSharkPoint.y + 1;
                    break;
                case W:
                    expectSharkPoint.x = currentSharkPoint.x;
                    expectSharkPoint.y = currentSharkPoint.y - 1;
                    break;
                case UW:
                    expectSharkPoint.x = currentSharkPoint.x - 1;
                    expectSharkPoint.y = currentSharkPoint.y - 1;
                    break;
                case DE:
                    expectSharkPoint.x = currentSharkPoint.x + 1;
                    expectSharkPoint.y = currentSharkPoint.y + 1;
                    break;
                case DW:
                    expectSharkPoint.x = currentSharkPoint.x + 1;
                    expectSharkPoint.y = currentSharkPoint.y - 1;
                    break;
                case UE:
                    expectSharkPoint.x = currentSharkPoint.x - 1;
                    expectSharkPoint.y = currentSharkPoint.y + 1;
                    break;
            }

            if (expectSharkPoint.x >= 0
                && expectSharkPoint.x < 3
                && expectSharkPoint.y >= 0
                && expectSharkPoint.y < 3) {
                currentSharkPoint = expectSharkPoint;
            } else {
                result = currentSharkPoint;
                break;
            }
        }

        return result;
    }

    public static boolean checkMove(Board[][] board, Point2 p) {
        Board shark = board[p.x][p.y];
        Point2 p2 = nextDirection(shark, p);
        if (p2.x < 0 || p2.x > 3 || p2.y < 0 || p2.y > 3) {
            return false;
        }
        return true;
    }

    public static Point2 findShark(Board[][] board) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (board[x][y].type == Type.SHARK) {
                    return new Point2(x, y);
                }
            }
        }

        return null;
    }

    public static void print(Board[][] board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print("" + board[i][j].fishNum + board[i][j].direction.data + " ");
            }
            System.out.println();
        }
    }

    public static int firstSharkEat(Board[][] board, Point2 p) {
        int result = 0;
        if (board[p.x][p.y].type == Type.FISH) {
            result = board[p.x][p.y].fishNum;
            board[p.x][p.y].type = Type.SHARK;
            board[p.x][p.y].fishNum = -1;
        }
        return result;
    }

    public static int moveSharkEat(Board[][] board, Point2 p) {
        int result = 0;
        if (board[p.x][p.y].type == Type.FISH) {
            result = board[p.x][p.y].fishNum;
            board[p.x][p.y].type = Type.SHARK;
            board[p.x][p.y].fishNum = -1;
        }
        return result;
    }

    public static Point2 findFish(Board[][] board, int fishNum) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (board[x][y].fishNum == fishNum) {
                    return new Point2(x, y);
                }
            }
        }

        return null;
    }

    public static void moveFish(Board[][] board, Point2 currentPoint) {
        Board fish = board[currentPoint.x][currentPoint.y];

        Point2 expectDirection = nextDirection(fish, currentPoint);
        Point2 nextDirection;
        while (true) {
            if (check(board, expectDirection)) {
                nextDirection = expectDirection;
                break;
            } else {
                expectDirection = currentPoint;
                if (fish.direction.value == 8) {
                    fish.direction = Direction.getD(1);
                } else {
                    fish.direction = Direction.getD(fish.direction.value + 1);
                }
            }

            expectDirection = nextDirection(fish, expectDirection);
        }

        swap(board, currentPoint, nextDirection);
    }

    public static void swap(Board[][] board, Point2 currentPoint, Point2 nextDirection) {
        Board temp = board[nextDirection.x][nextDirection.y];
        board[nextDirection.x][nextDirection.y] = board[currentPoint.x][currentPoint.y];
        board[currentPoint.x][currentPoint.y] = temp;
    }

    public static boolean check(Board[][] board, Point2 nextDirection) {
        if (nextDirection.x < 0
            || nextDirection.x > 3
            || nextDirection.y < 0
            || nextDirection.y > 3
            || board[nextDirection.x][nextDirection.y].type == Type.SHARK) {
            return false;
        }

        return true;
    }

    public static Point2 nextDirection(Board fish, Point2 currentFishPoint) {
        Point2 nextFishPoint = new Point2();
        switch (fish.direction) {
            case U:
                nextFishPoint.x = currentFishPoint.x - 1;
                nextFishPoint.y = currentFishPoint.y;
                break;
            case D:
                nextFishPoint.x = currentFishPoint.x + 1;
                nextFishPoint.y = currentFishPoint.y;
                break;
            case E:
                nextFishPoint.x = currentFishPoint.x;
                nextFishPoint.y = currentFishPoint.y + 1;
                break;
            case W:
                nextFishPoint.x = currentFishPoint.x;
                nextFishPoint.y = currentFishPoint.y - 1;
                break;
            case UW:
                nextFishPoint.x = currentFishPoint.x - 1;
                nextFishPoint.y = currentFishPoint.y - 1;
                break;
            case DE:
                nextFishPoint.x = currentFishPoint.x + 1;
                nextFishPoint.y = currentFishPoint.y + 1;
                break;
            case DW:
                nextFishPoint.x = currentFishPoint.x + 1;
                nextFishPoint.y = currentFishPoint.y - 1;
                break;
            case UE:
                nextFishPoint.x = currentFishPoint.x - 1;
                nextFishPoint.y = currentFishPoint.y + 1;
                break;
        }

        return nextFishPoint;
    }
}

class Point2 {
    int x;
    int y;

    Point2() {

    }

    Point2(int xx, int yy) {
        x = xx;
        y = yy;
    }
}

class Board {
    Type type;
    int fishNum;
    Direction direction;
}

enum Type {
    NONE,
    SHARK,
    FISH
}

enum Direction {
    U(1, "↑"),
    UW(2, "↖"),
    W(3, "←"),
    DW(4, "↙"),
    D(5, "↓"),
    DE(6, "↘"),
    E(7, "→"),
    UE(8, "↗");

    public final int value;
    public final String data;

    Direction(int v, String d) {
        value = v;
        data = d;
    }

    public static Direction getD(int v) {
        return Arrays.stream(values())
            .filter(e -> e.value == v)
            .findFirst()
            .get();
    }
}
