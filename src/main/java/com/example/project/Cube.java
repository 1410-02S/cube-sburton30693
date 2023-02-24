package com.example.project;

public class Cube {
    static String[][][] cube = {
        {{"w", "w", "w"},
        {"w", "w", "w"},
        {"w", "w", "w"}},
        
        {{"r", "r", "r"},
        {"r", "r", "r"},
        {"r", "r", "r"}},
        
        {{"b", "b", "b"},
        {"b", "b", "b"},
        {"b", "b", "b"}},
        
        {{"o", "o", "o"},
        {"o", "o", "o"},
        {"o", "o", "o"}},
        
        {{"g", "g", "g"},
        {"g", "g", "g"},
        {"g", "g", "g"}},
        
        {{"y", "y", "y"},
        {"y", "y", "y"},
        {"y", "y", "y"}}
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

    static void showCubeNet(String[][][] cube) {
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

    static void showCubeList(String[][][] cube) {
        int[] faceOrder = new int[] {1, 2, 3, 4, 5, 0};

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(cube[faceOrder[i]][j][0] + "|" + cube[faceOrder[i]][j][1] + "|" + cube[faceOrder[i]][j][2]);
                System.out.println();
            }

            if(i < 5) {
                System.out.println();
            }
        }
    }

    static void rotateFace(String[][] face, boolean clockwise) {
        String[][] oldFace = new String[3][3];

        //Copy face into another array
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                oldFace[i][j] = face[i][j];
            }
        }

        //Copy the face back, but rotated
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

    static void shiftPieces(int[] faces, int[] sides, String[][][] cube, boolean clockwise) {
        String[] buffer = new String[12];
        String[] temp = new String[12];

        //Copy colors from the sides into the buffer
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                switch(sides[j]) {
                    case 0 : buffer[j * 3 + i] = cube[faces[j]][0][i]; break;
                    case 1 : buffer[j * 3 + i] = cube[faces[j]][i][2]; break;
                    case 2 : buffer[j * 3 + i] = cube[faces[j]][2][i]; break;
                    case 3 : buffer[j * 3 + i] = cube[faces[j]][i][0]; break;
                }
            }
        }

        //Shift the colors in the buffer, with a temporary copy
        for(int i = 0; i < 12; i++) {
            temp[i] = buffer[i];
        }

        int shift = clockwise ? 9 : 3;
        for(int i = 0; i < 12; i++) {
            buffer[i] = temp[(i + shift) % 12];
        }

        //Copy shifted colors back into the cube sides
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                switch(sides[j]) {
                    case 0 : cube[faces[j]][0][i] = buffer[j * 3 + i]; break;
                    case 1 : cube[faces[j]][i][2] = buffer[j * 3 + i]; break;
                    case 2 : cube[faces[j]][2][i] = buffer[j * 3 + i]; break;
                    case 3 : cube[faces[j]][i][0] = buffer[j * 3 + i]; break;
                }
            }
        }
    }

    static void move(String[][][] cube, String input) {
        boolean clockwise = true;
        int face = 0;
        int[] faces, sides;
        
        switch(input) {
            case "u'" : clockwise = false;
            case "u" :
                face = 0;
                faces = new int[] {4, 3, 2, 1};
                sides = new int[] {0, 0, 0, 0};
                break;
            case "d'" : clockwise = false;
            case "d" :
                face = 5;
                faces = new int[] {1, 2, 3, 4};
                sides = new int[] {2, 2, 2, 2};
                break;
            case "f'" : clockwise = false;
            case "f" :
                face = 2;
                faces = new int[] {0, 3, 5, 1};
                sides = new int[] {2, 3, 0, 1};
                break;
            case "b'" : clockwise = false;
            case "b" :
                face = 4;
                faces = new int[] {0, 1, 5, 3};
                sides = new int[] {0, 3, 2, 1};
                break;
            case "r'" : clockwise = false;
            case "r" :
                face = 3;
                faces = new int[] {2, 0, 4, 5};
                sides = new int[] {1, 1, 3, 1};
                break;
            case "l'" : clockwise = false;
            case "l" :
                face = 1;
                faces = new int[] {2, 5, 4, 0};
                sides = new int[] {3, 3, 1, 3};
                break;
            default:
                System.out.println("Unknown move " + input + "!");
                return;
        }

        //Rotate the face and shift the pieces around that are connected to that face
        rotateFace(cube[face], clockwise);
        shiftPieces(faces, sides, cube, clockwise);
    }

    public static void main(final String[] args) {
        if(args.length == 0) {
            System.out.println("No moves provided");
            return;
        }

        //Do all moves then print the cube state
        for(int i = 0; i < args.length; i++) {
            move(cube, args[i]);
        }
        showCubeList(cube);

        //'Solution'
        System.out.println();
        for(int i = args.length - 1; i >= 0; i--) {
            if(args[i].length() > 1 && args[i].charAt(1) == '\'') {
                System.out.print(args[i].charAt(0) + " ");
            } else {
                System.out.print(args[i] + "' ");
            }
        }
    }
}