package P23288;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static int[][] map;
    static int[][] dice = {{0, 2, 0}, {4, 1, 3}, {0, 5, 0}, {0, 6, 0}};
    static int[][] dist = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int d = 0;
    static int score = 0;
    static int x = 0, y = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (K-- > 0) {
            moveDice();
            getScore();
            changeDist();
        }

        System.out.printf("%d", score);
    }

    public static void getScore() {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        int num = map[x][y];
        int cnt = 1;
        q.add(new int[]{x, y});
        visited[x][y] = true;

        while (!q.isEmpty()) {
            int[] now = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now[0] + dist[i][0];
                int ny = now[1] + dist[i][1];
                if (!check(nx, ny) || visited[nx][ny] || map[nx][ny] != num) continue;
                visited[nx][ny] = true;
                q.add(new int[]{nx, ny});
                cnt++;
            }
        }

        score += cnt * num;
    }

    public static void moveDice() {
        int nx = x + dist[d][0];
        int ny = y + dist[d][1];

        if (!check(nx, ny)) {
            d = (d + 2) % 4;
        }

        int tmp;
        switch (d) {
            case 0:
                tmp = dice[1][2];
                dice[1][2] = dice[1][1];
                dice[1][1] = dice[1][0];
                dice[1][0] = dice[3][1];
                dice[3][1] = tmp;
                break;
            case 1:
                tmp = dice[3][1];
                for (int i = 3; i >= 1; i--) dice[i][1] = dice[i - 1][1];
                dice[0][1] = tmp;
                break;
            case 2:
                tmp = dice[1][0];
                dice[1][0] = dice[1][1];
                dice[1][1] = dice[1][2];
                dice[1][2] = dice[3][1];
                dice[3][1] = tmp;
                break;
            case 3:
                tmp = dice[0][1];
                for (int i = 0; i < 3; i++) dice[i][1] = dice[i + 1][1];
                dice[3][1] = tmp;
                break;
        }

        x += dist[d][0];
        y += dist[d][1];
    }

    public static void changeDist() {
        int under = dice[3][1];
        int num = map[x][y];
        if (under > num) {
            d = (d + 1) % 4;
        } else if (under < num) {
            d = (d - 1);
            if (d < 0) d = 3;
        }
    }

    public static boolean check(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < M;
    }
}