

public class Rubiks {

    static String[][] face = {
        {"1", "2", "3"},
        {"4", "5", "6"},
        {"7", "8", "9"}
    };

    static String[][][] cube = {
        {{"g", "g", "g"},
        {"g", "0", "g"},
        {"g", "g", "g"}},
        
        {{"y", "y", "y"},
        {"y", "1", "y"},
        {"y", "y", "y"}},
        
        {{"r", "r", "r"},
        {"r", "2", "r"},
        {"r", "r", "r"}},
        
        {{"w", "w", "w"},
        {"w", "3", "w"},
        {"w", "w", "w"}},
        
        {{"o", "o", "o"},
        {"o", "4", "o"},
        {"o", "o", "o"}},
        
        {{"b", "b", "b"},
        {"b", "5", "b"},
        {"b", "b", "b"}}
    };
    
    static void showFace(String[][] face, int padding) {
        for(String[] i : face) {
            for(int p = 0; p < padding; p++) {
                System.out.print(" ");
            }
            System.out.print(i[0] + "|" + i[1] + "|" + i[2]);
            System.out.println();
        }
    }

    static void showCube(String[][][] cube) {
        showFace(cube[0], 6);

        for(int i = 0; i < 3; i++) {
            for(int j = 1; j < 5; j++) {
                System.out.print(cube[j][i][0] + "|" + cube[j][i][1] + "|" + cube[j][i][2]);
                System.out.print(" ");
            }
            System.out.println();
        }

        showFace(cube[5], 6);
    }

    static void move(String[][][] cube, String input) {
        boolean clockwise = true;
        int face = 0;
        
        switch(input) {
            case "u'" : clockwise = false;
            case "u" :
                face = 0;
                break;
            case "d'" : clockwise = false;
            case "d" :
                face = 5;
                break;
            case "f'" : clockwise = false;
            case "f" :
                face = 2;
                break;
            case "b'" : clockwise = false;
            case "b" :
                face = 4;
                break;
            case "r'" : clockwise = false;
            case "r" :
                face = 3;
                break;
            case "l'" : clockwise = false;
            case "l" :
                face = 1;
                break;
            default:
                System.out.println("Unknown move " + input + "!");
                return;
        }

        //Rotate the face
        rotateFace(cube[face], clockwise);

        //Shift the pieces around that are connected to that face
        for(int i = 0; i < 3; i++) {
            if(clockwise) {
                String temp = cube[1][0][i];
                cube[1][0][i] = cube[2][0][i];
                cube[2][0][i] = cube[3][0][i];
                cube[3][0][i] = cube[4][0][i];
                cube[4][0][i] = temp;
            } else {
                String temp = cube[4][0][i];
                cube[4][0][i] = cube[3][0][i];
                cube[3][0][i] = cube[2][0][i];
                cube[2][0][i] = cube[1][0][i];
                cube[1][0][i] = temp;    
            }
        }
    }

    static void rotateFace(String[][] face, boolean clockwise) {
        String[][] oldFace = new String[3][3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                oldFace[i][j] = face[i][j];
            }
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(clockwise) {
                    face[i][j] = oldFace[2 - j][i];
                } else {
                    face[i][j] = oldFace[j][2 - i];
                }
            }
        }

        //Clockwise
        // face[0][0] = oldFace[2][0];
        // face[0][1] = oldFace[1][0];
        // face[0][2] = oldFace[0][0];
        // face[1][2] = oldFace[0][1];
        // face[2][2] = oldFace[0][2];
        // face[2][1] = oldFace[1][2];
        // face[2][0] = oldFace[2][2];
        // face[1][0] = oldFace[2][1];
        
        //Counter-Clockwise
        // face[0][0] = oldFace[0][2];
        // face[0][1] = oldFace[1][2];
        // face[0][2] = oldFace[2][2];
        // face[1][2] = oldFace[2][1];
        // face[2][2] = oldFace[2][0];
        // face[2][1] = oldFace[1][0];
        // face[2][0] = oldFace[0][0];
        // face[1][0] = oldFace[0][1];
    }

    public static void main(String[] args) {
        showFace(face, 0);
        System.out.println();
        rotateFace(face, true);
        showFace(face, 0);
        System.out.println();
        move(cube, args[0]);
        showCube(cube);
    }

}