import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Thistlethwaite {

    HashMap<Character, Color> colorToFace;
    Cube cube;
    Random r;
    HashMap<Character, Character> translate;



    public Thistlethwaite() {
        this.r = new Random();
    }

    public Cube solve(Cube cube) {
        this.cube = cube;
        colorToFace = this.pairSides();
        this.phaseOne();
        this.phaseTwo();

        return cube;
    }


    public HashMap<Character, Color> pairSides() {
        HashMap<Character, Color> encoder = new HashMap<>();
        encoder.put('F', Color.GREEN);
        encoder.put('U', Color.WHITE);
        encoder.put('R', Color.RED);
        encoder.put('L', Color.ORANGE);
        encoder.put('D', Color.YELLOW);
        encoder.put('B', Color.BLUE);
        return encoder;
    }

    /**
     * Orients cube so that no U or D quarter turns are required, moving us into group 1
     */
    private void phaseOne() {
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));

        int countBad = 1;
        ArrayList<Edge> badEdges;
        while (countBad > 0) {
            this.translate = this.horizontalMap('F');
            countBad = 0;
            badEdges = new ArrayList();
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if ((x == 1 && y == 0) || (x == 1 && y == 2) || (x == 0 && y == 1) || (x == 2 && y == 1)) {
                        Character edgeF = frontFace.squares[x][y];
                        if (edgeF.equals(this.colorToFace.get('L').getLetter())
                                || edgeF.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(Edge.edgeNumber('F', x, y));
                        } else if (edgeF.equals(this.colorToFace.get('U').getLetter())
                                || edgeF.equals(this.colorToFace.get('D').getLetter())) {
                            Character oppEdgeF = null;
                            if (y == 1) {
                                if (x == 0) {oppEdgeF = leftFace.getSquares()[2][1];}
                                if (x == 2) {oppEdgeF = rightFace.getSquares()[0][1];}
                            }
                            else if (x == 1) {
                                if (y == 0) {oppEdgeF = downFace.getSquares()[1][2];}
                                if (y == 2) {oppEdgeF = upFace.getSquares()[1][0];}
                            }
                            if (oppEdgeF.equals(this.colorToFace.get('F').getLetter()) ||
                                oppEdgeF.equals(this.colorToFace.get('B').getLetter())) {
                                countBad = countBad + 1;
                                badEdges.add(Edge.edgeNumber('F', x, y));
                            }
                        }

                        Character edgeB = backFace.squares[x][y];
                        if (edgeB.equals(this.colorToFace.get('L').getLetter()) ||
                                edgeB.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(Edge.edgeNumber('B', x, y));
                        } else if (edgeB.equals(this.colorToFace.get('U').getLetter())
                                || edgeB.equals(this.colorToFace.get('D').getLetter())) {
                            Character oppEdgeB = null;
                            if (y == 1) {
                                if (x == 0) {oppEdgeB = rightFace.getSquares()[2][1];}
                                if (x == 2) {oppEdgeB = leftFace.getSquares()[0][1];}
                            }
                            else if (x == 1) {
                                if (y == 0) {oppEdgeB = downFace.getSquares()[1][0];}
                                if (y == 2) {oppEdgeB = upFace.getSquares()[1][2];}
                            }
                            if (oppEdgeB.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeB.equals(this.colorToFace.get('B').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('B', x, y));
                            }
                        }
                        if ((x == 0 && y == 1) || (x == 2 && y == 1)) {
                            Character edgeU = upFace.squares[x][y];
                            if (edgeU.equals(this.colorToFace.get('L').getLetter()) ||
                                    edgeU.equals(this.colorToFace.get('R').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('U', x, y));
                            } else if (edgeU.equals(this.colorToFace.get('U').getLetter())
                                    || edgeU.equals(this.colorToFace.get('D').getLetter()))  {
                                Character oppEdgeU = null;
                                if (x == 0) {oppEdgeU = leftFace.getSquares()[1][2];}
                                if (x == 2) {oppEdgeU = rightFace.getSquares()[1][2];}
                                if (oppEdgeU.equals(this.colorToFace.get('F').getLetter()) ||
                                        oppEdgeU.equals(this.colorToFace.get('B').getLetter())) {
                                    countBad++;
                                    badEdges.add(Edge.edgeNumber('U', x, y));
                                }
                            }

                            Character edgeD = downFace.squares[x][y];
                            if (edgeD.equals(this.colorToFace.get('L').getLetter()) ||
                                    edgeD.equals(this.colorToFace.get('R').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('D', x, y));
                            } else if (edgeD.equals(this.colorToFace.get('U').getLetter())
                                    || edgeD.equals(this.colorToFace.get('D').getLetter()))  {
                                Character oppEdgeD = null;
                                if (x == 0) {oppEdgeD = leftFace.getSquares()[1][0];}
                                if (x == 2) {oppEdgeD = rightFace.getSquares()[1][0];}
                                if (oppEdgeD.equals(this.colorToFace.get('F').getLetter()) ||
                                        oppEdgeD.equals(this.colorToFace.get('B').getLetter())) {
                                    countBad++;
                                    badEdges.add(Edge.edgeNumber('D', x, y));
                                }
                            }
                        }
                    }
                }
            }

            //Now that we have our edges, we have to act
            int uCount = 0;
            int dCount= 0;
            int midCount = 0;
            ArrayList<Edge> upEdges = new ArrayList<>();
            ArrayList<Edge> downEdges = new ArrayList<>();
            ArrayList<Edge> midEdges = new ArrayList<>();
            for (Edge e : badEdges) {
                if (this.oneOnSide('U', e)) {
                    uCount++;
                    upEdges.add(e);
                } else if (this.oneOnSide('D', e)) {
                    dCount++;
                    downEdges.add(e);
                } else {
                    midCount++;
                    midEdges.add(e);
                }
            }
            ArrayList<Move> moves = new ArrayList<Move>();
            translate = horizontalMap('F');
            switch(badEdges.size()) {
                case 0:
                    //By some miracle(.05% chance) no edges are bad! Do nothing.
                    countBad = 0;
                    break;
                case 2:
                    if (uCount == 2) {
                        Edge upOne = upEdges.get(0);
                        Edge upTwo = upEdges.get(1);
                        Character faceValue;
                        if (upOne.primary.equals('U')) {
                            faceValue = upOne.secondary;
                        } else {
                            faceValue = upOne.primary;
                        }
                        Character otherValue;
                        if (upTwo.primary.equals('U')) {
                            otherValue = upTwo.secondary;
                        } else {
                            otherValue = upTwo.primary;
                        }
                        translate = this.horizontalMap(faceValue);
                        Character arrangement = translate.get(otherValue);

                        if ((faceValue == 'F' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.uCounter);
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uCounter);
                        } else if ((faceValue == 'F' && otherValue == 'B') || (faceValue == 'L' && otherValue == 'R') || (faceValue == 'B' && otherValue == 'F') || (faceValue == 'R' && otherValue == 'L')) {
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uClock);
                            moves.add(Move.getMove(translate.get('L'), Direction.Clockwise));
                            moves.add(Move.uCounter);
                        }  else if ((faceValue == 'F' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.uClock);
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uCounter);
                        }
                    } else if (dCount == 2) {
                        Edge downOne = downEdges.get(0);
                        Edge downTwo = downEdges.get(1);
                        Character faceValue;
                        if (downOne.primary.equals('D')) {
                            faceValue = downOne.secondary;
                        } else {
                            faceValue = downOne.primary;
                        }
                        Character otherValue;
                        if (downTwo.primary.equals('D')) {
                            otherValue = downTwo.secondary;
                        } else {
                            otherValue = downTwo.primary;
                        }
                        translate = this.horizontalMap(faceValue);
                        Character arrangement = translate.get(otherValue);
                        if ((faceValue == 'F' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.dClock);
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.dCounter);
                        } else if ((faceValue == 'F' && otherValue == 'B') || (faceValue == 'L' && otherValue == 'R') || (faceValue == 'B' && otherValue == 'F') || (faceValue == 'R' && otherValue == 'L')) {
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.dClock);
                            moves.add(Move.getMove(translate.get('R'), Direction.Clockwise));
                            moves.add(Move.dCounter);
                        }  else if ((faceValue == 'F' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.uCounter);
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uClock);
                        }
                    }
                    else if (uCount == 1) {
                        if (dCount == 1) {
                            Edge up = upEdges.get(0);
                            Edge down = downEdges.get(0);
                            Character matters;
                            if (down.primary.equals('D')) {
                                matters = down.secondary;
                            } else {
                                matters = down.primary;
                            }
                            if (this.shareFace(up, down)) {
                                moves.add(Move.uEighty);
                                moves.add(Move.getMove(matters, Direction.OneEighty));
                            } else {
                                moves.add(Move.getMove(matters, Direction.OneEighty));
                            }
                        } else if (midCount == 1) {
                            Edge m = midEdges.get(0);
                            Edge u = upEdges.get(0);
                            switch (m) {
                                case FL:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.fClock);
                                        moves.add(Move.uClock);
                                    }
                                    else {
                                        moves.add(Move.uClock);
                                        moves.add(Move.fClock);
                                        moves.add(Move.uCounter);
                                    }
                                    break;
                                case FR:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.rClock);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.rClock);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.uClock);
                                    }
                                    else {
                                        moves.add(Move.uClock);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.uCounter);
                                    }
                                    break;
                                case BL:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.lClock);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.lClock);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.uClock);
                                    }
                                    else {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.uClock);
                                    }
                                    break;
                                case BR:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.bClock);
                                        moves.add(Move.uCounter);
                                    }
                                    else {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.bClock);
                                        moves.add(Move.uClock);
                                    }
                                    break;
                            }
                        }
                    } else if (dCount == 1) {
                        if (midCount == 1) {
                            Edge m = midEdges.get(0);
                            Edge d = downEdges.get(0);
                            switch (m) {
                                case FL:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.lClock);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.lClock);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.dCounter);
                                    }
                                    else {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.dClock);
                                    }
                                    break;
                                case FR:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.fClock);
                                        moves.add(Move.dCounter);
                                    }
                                    else {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.fClock);
                                        moves.add(Move.dClock);
                                    }
                                    break;
                                case BL:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.bClock);
                                        moves.add(Move.dCounter);
                                    } else {
                                        moves.add(Move.dClock);
                                        moves.add(Move.bClock);
                                        moves.add(Move.dCounter);
                                    }
                                    break;
                                case BR:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.rClock);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.rClock);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.dClock);
                                    }
                                    else {
                                        moves.add(Move.dClock);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.dCounter);
                                    }
                                    break;
                            }
                        }
                    } else if (midCount == 2) {
                        Edge mid = midEdges.get(0);
                        moves.add(Move.getMove(mid.primary, Direction.Random));
                    }
                    break;
                case 4:
                    moves = this.fourBad(moves, upEdges, midEdges, downEdges);
                    break;
                case 6:
                    if (uCount >= 3 || dCount >= 3) {
                        if (uCount >= 3) {
                            moves.add(Move.uClock);
                        }
                        if (dCount >= 3) {
                            moves.add(Move.dClock);
                        }
                    } else {
                        ArrayList<Edge> upEdgesLocal = new ArrayList<>();
                        ArrayList<Edge> downEdgesLocal = new ArrayList<>();
                        ArrayList<Edge> midEdgesLocal = new ArrayList<>();
                        int additive = r.nextInt(6);
                        for (int i = 0; i < 4; i++) {
                            Edge e = badEdges.get((i + additive) % 6);
                            if (this.oneOnSide('U', e)) {
                                upEdgesLocal.add(e);
                            } else if (this.oneOnSide('D', e)) {
                                downEdgesLocal.add(e);
                            } else {
                                midEdgesLocal.add(e);
                            }
                        }
                        moves = this.fourBad(moves, upEdgesLocal, midEdgesLocal, downEdgesLocal);
                    }
                    break;
                case 8:
                    ArrayList<Edge> upEdgesLocalEight = new ArrayList<>();
                    ArrayList<Edge> downEdgesLocalEight = new ArrayList<>();
                    ArrayList<Edge> midEdgesLocalEight = new ArrayList<>();
                    int additive = r.nextInt(6);
                    for (int i = 0; i < 4; i++) {
                        Edge e = badEdges.get((i + additive) % 8);
                        if (this.oneOnSide('U', e)) {
                            upEdgesLocalEight.add(e);
                        } else if (this.oneOnSide('D', e)) {
                            downEdgesLocalEight.add(e);
                        } else {
                            midEdgesLocalEight.add(e);
                        }
                    }
                    moves = this.fourBad(moves, upEdgesLocalEight, midEdgesLocalEight, downEdgesLocalEight);
                    break;
                case 10:
                    if (midCount == 2) {
                        moves.add(Move.uRandom);
                        moves.add(Move.dRandom);
                    } else {
                        ArrayList<Edge> goodEdges = new ArrayList<>();
                        for (Edge e : Edge.values()) {
                            if (!badEdges.contains(e))  {
                                goodEdges.add(e);
                            }
                        }
                        for (Edge ge : goodEdges) {
                            switch (ge) {
                                case FU: moves.add(Move.fRandom); break;
                                case FD: moves.add(Move.fRandom); break;
                                case BU: moves.add(Move.bRandom); break;
                                case BD: moves.add(Move.bRandom); break;
                                case UL: moves.add(Move.lRandom); break;
                                case UR: moves.add(Move.rRandom); break;
                                case DL: moves.add(Move.lRandom); break;
                                case DR: moves.add(Move.rRandom); break;
                            }
                        }
                        moves.add(Move.uRandom);
                        moves.add(Move.dRandom);
                    }
                    break;
                case 12:
                    moves.add(Move.dClock);
                    moves.add(Move.bClock);
                    moves.add(Move.fClock);
                    moves.add(Move.uClock);
                    moves.add(Move.rCounter);
                    moves.add(Move.lCounter);
                    moves.add(Move.dClock);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + badEdges.size());
            }
            System.out.println(countBad);
            this.translateAndExecute(translate, moves);
            System.out.println(cube.toString());
        }
    }

    private HashMap<Character,Character> horizontalMap(Character c) {
        HashMap<Character, Character> result = new HashMap<Character, Character>();

        switch(c) {
            case 'F':
                result.put('F', 'F');
                result.put('R', 'R');
                result.put('B', 'B');
                result.put('L', 'L');
                break;
            case 'L':
                result.put('F', 'L');
                result.put('R', 'F');
                result.put('B', 'R');
                result.put('L', 'B');
                break;
            case 'B':
                result.put('F', 'B');
                result.put('R', 'L');
                result.put('B', 'F');
                result.put('L', 'R');
                break;
            case 'R':
                result.put('F', 'R');
                result.put('R', 'B');
                result.put('B', 'L');
                result.put('L', 'F');
                break;
            default:
                result.put('F', 'F');
                result.put('R', 'R');
                result.put('B', 'B');
                result.put('L', 'L');
        }
        result.put('U', 'U');
        result.put('D', 'D');
        return result;
    }

    private ArrayList<Move> fourBad(ArrayList<Move> moves, ArrayList<Edge> upEdges, ArrayList<Edge> midEdges, ArrayList<Edge> downEdges) {
        int uCount = upEdges.size();
        int midCount = midEdges.size();
        int dCount = downEdges.size();
        if (uCount == 4) {
            moves.add(Move.uClock);
        } else if (dCount == 4) {
            moves.add(Move.dClock);
        } else if (midCount == 4) {
            moves.add(Move.lClock);
            moves.add(Move.rClock);
            moves.add(Move.uClock);
            moves.add(Move.dClock);
        } else if (uCount == 2 && dCount == 2) {
            // Check if there are any down edges that dont share with up edges
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            Character importantFaceOne;
            Character importantFaceTwo;
            if (downOne.primary.equals('U') || downOne.primary.equals('D')) {
                importantFaceOne = downOne.secondary;
            } else {
                importantFaceOne = downOne.primary;
            }
            if (downTwo.primary.equals('U') || downTwo.primary.equals('D')) {
                importantFaceTwo = downTwo.secondary;
            } else {
                importantFaceTwo = downTwo.primary;
            }
            if ((!this.shareFace(upOne, downOne) && !this.shareFace(upTwo, downOne)) ||
                    (!this.shareFace(upOne, downTwo) && !this.shareFace(upTwo, downTwo))) {
                if (!this.shareFace(upOne, downOne) && !this.shareFace(upTwo, downOne)) {
                    moves.add(Move.getMove(importantFaceOne, Direction.OneEighty));
                }
                if (!this.shareFace(upOne, downTwo) && !this.shareFace(upTwo, downTwo)) {
                    moves.add(Move.getMove(importantFaceTwo, Direction.OneEighty));
                }
            } else {
                moves.add(Move.dClock);
            }
        } else if (midCount == 2 && uCount == 2) {
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
            boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
            Character sharedFace;
            if (samePrim || sameSecon) {
                if (samePrim) {
                    sharedFace = midEdges.get(0).primary;
                } else {
                    sharedFace = midEdges.get(0).secondary;
                }
                translate = this.horizontalMap(sharedFace);
                if (this.onSide(sharedFace, upOne, upTwo)) {
                    if (this.onSide(translate.get('L'), upOne, upTwo)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        // moves.add(Move.dCounter);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bCounter);
                        moves.add(Move.uClock);
                    } else if (this.onSide(translate.get('R'), upOne, upTwo)) {
                        moves.add(Move.lCounter);
                        moves.add(Move.rClock);
                        // moves.add(Move.dClock);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bClock);
                        moves.add(Move.uClock);
                    } else if (this.onSide(translate.get('B'), upOne, upTwo)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        moves.add(Move.uClock);
                    }
                } else if (this.onSide(translate.get('L'), upOne, upTwo)) {
                    if (this.onSide(translate.get('R'), upOne, upTwo)) {
                        moves.add(Move.getMove(sharedFace, Direction.Random));
                    } else if (this.onSide(translate.get('B'), upOne, upTwo)) {
                        moves.add(Move.rClock);
                        moves.add(Move.fClock);
                        moves.add(Move.uClock);
                    }
                } else if (this.onSide(translate.get('B'), upOne, upTwo)) {
                    if (this.onSide(translate.get('R'), upOne, upTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.fCounter);
                        moves.add(Move.uClock);
                    }
                }
            } else {
                Character base = null;
                if (this.onSide(midEdges.get(0).primary, upOne, upTwo)) {
                    base = midEdges.get(0).primary;
                } else if (this.onSide(midEdges.get(0).secondary, upOne, upTwo)) {
                    base = midEdges.get(0).secondary;
                } else if (this.onSide(midEdges.get(1).primary, upOne, upTwo)) {
                    base = midEdges.get(1).primary;
                } else if (this.onSide(midEdges.get(1).secondary, upOne, upTwo)) {
                    base = midEdges.get(1).secondary;
                }
                translate = this.horizontalMap(base);
                if (this.onSide(translate.get('B'), upOne, upTwo)) {
                    moves.add(Move.rClock);
                    moves.add(Move.lClock);
                } else if (this.onSide(translate.get('L'), upOne, upTwo)) {
                    moves.add(Move.rClock);
                    moves.add(Move.bCounter);
                } else if (this.onSide(translate.get('R'), upOne, upTwo)) {
                    moves.add(Move.lClock);
                    moves.add(Move.uClock);
                    moves.add(Move.rClock);
                }
            }
        } else if (midCount == 2 && dCount == 2) {
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
            boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
            Character sharedFace;
            if (samePrim || sameSecon) {
                if (samePrim) {
                    sharedFace = midEdges.get(0).primary;
                } else {
                    sharedFace = midEdges.get(0).secondary;
                }
                translate = this.horizontalMap(sharedFace);
                if (this.onSide(sharedFace, downOne, downTwo)) {
                    if (this.onSide(translate.get('L'), downOne, downTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        // moves.add(Move.dClock);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bClock);
                        moves.add(Move.dCounter);
                    } else if (this.onSide(translate.get('R'), downOne, downTwo)) {
                        moves.add(Move.lClock);
                        moves.add(Move.rClock);
                        // moves.add(Move.dCounter);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bCounter);
                        moves.add(Move.dCounter);
                    } else if (this.onSide(translate.get('B'), downOne, downTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        moves.add(Move.dCounter);
                    }
                } else if (this.onSide(translate.get('L'), downOne, downTwo)) {
                    if (this.onSide(translate.get('R'), downOne, downTwo)) {
                        moves.add(Move.getMove(sharedFace, Direction.Random));
                    } else if (this.onSide(translate.get('B'), downOne, downTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.fCounter);
                        moves.add(Move.dCounter);
                    }
                } else if (this.onSide(translate.get('B'), downOne, downTwo)) {
                    if (this.onSide(translate.get('R'), downOne, downTwo)) {
                        moves.add(Move.lClock);
                        moves.add(Move.uClock);
                        moves.add(Move.dCounter);
                    }
                }
            } else {
                Character base = null;
                if (this.onSide(midEdges.get(0).primary, downOne, downTwo)) {
                    base = midEdges.get(0).primary;
                } else if (this.onSide(midEdges.get(0).secondary, downOne, downTwo)) {
                    base = midEdges.get(0).secondary;
                } else if (this.onSide(midEdges.get(1).primary, downOne, downTwo)) {
                    base = midEdges.get(1).primary;
                } else if (this.onSide(midEdges.get(1).secondary, downOne, downTwo)) {
                    base = midEdges.get(1).secondary;
                }
                translate = this.horizontalMap(base);
                if (this.onSide(translate.get('B'), downOne, downTwo)) {
                    moves.add(Move.rCounter);
                    moves.add(Move.lCounter);
                } else if (this.onSide(translate.get('L'), downOne, downTwo)) {
                    moves.add(Move.rCounter);
                    moves.add(Move.bClock);
                } else if (this.onSide(translate.get('R'), downOne, downTwo)) {
                    moves.add(Move.lCounter);
                    moves.add(Move.uCounter);
                    moves.add(Move.rCounter);
                }
            }
        } else if (midCount == 2 && uCount == 1 && dCount == 1) {
            Edge upOne = upEdges.get(0);
            Edge downOne = downEdges.get(0);
            boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
            boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
            Character sharedFace;
            if (samePrim || sameSecon) {
                if (samePrim) {
                    sharedFace = midEdges.get(0).primary;
                } else {
                    sharedFace = midEdges.get(0).secondary;
                }
                translate = this.horizontalMap(sharedFace);
                if (sharedFace.equals(upOne.primary) || sharedFace.equals(upOne.secondary)) {
                    if (sharedFace.equals(downOne.primary) || sharedFace.equals(downOne.secondary)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.dEighty);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    } else if (translate.get('R').equals(downOne.primary) || translate.get('R').equals(downOne.secondary)) {
                        //moves.add(Move.dClock);
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    } else if (translate.get('L').equals(downOne.primary) || translate.get('L').equals(downOne.secondary)) {
                        //moves.add(Move.dCounter);
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    } else if (translate.get('B').equals(downOne.primary) || translate.get('B').equals(downOne.secondary)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    }
                } else
                if (sharedFace.equals(downOne.primary) || sharedFace.equals(downOne.secondary)) {
                    if (translate.get('R').equals(upOne.primary) || translate.get('R').equals(upOne.secondary)) {
                        //moves.add(Move.uCounter);
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        //moves.add(Move.bEighty);
                        moves.add(Move.dClock);
                    } else if (translate.get('L').equals(upOne.primary) || translate.get('L').equals(upOne.secondary)) {
                        //moves.add(Move.uClock);
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        //moves.add(Move.bEighty);
                        moves.add(Move.dClock);
                    } else if (translate.get('B').equals(upOne.primary) || translate.get('B').equals(upOne.secondary)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        //moves.add(Move.bEighty);
                        moves.add(Move.dClock);
                    }
                } else {
                    moves.add(Move.getMove(sharedFace, Direction.Clockwise));
                }
            } else {
                moves.add(Move.fEighty);
            }
        } else if (midCount == 1 && dCount == 1 && uCount == 2) {
            Edge mid = midEdges.get(0);
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            if (this.onSide(mid.primary, upOne, upTwo)) {
                if (this.onSide(mid.secondary, upOne, upTwo)) {
                    moves.add(Move.uClock);
                } else {
                    moves.add(Move.getMove(mid.secondary, Direction.Clockwise));
                }
            } else if (this.onSide(mid.secondary, upOne, upTwo)) {
                moves.add(Move.getMove(mid.primary, Direction.Counterclockwise));
            } else {
                switch (mid) {
                    case FR:
                        moves.add(Move.fCounter);
                        break;
                    case FL:
                        moves.add(Move.fClock);
                        break;
                    case BR:
                        moves.add(Move.bClock);
                        break;
                    case BL:
                        moves.add(Move.bCounter);
                        break;
                }
            }
        } else if (midCount == 1 && uCount == 3) {
            Edge mid = midEdges.get(0);
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            Edge upThree = upEdges.get(2);
            if (this.onSide(mid.primary, upOne, upTwo) || this.oneOnSide(mid.primary, upThree)) {
                moves.add(Move.uClock);
            } else {
                moves.add(Move.getMove(mid.primary, Direction.Clockwise));
                moves.add(Move.uClock);
            }
        } else if (midCount == 1 && dCount == 2 && uCount == 1) {
            Edge mid = midEdges.get(0);
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            if (this.onSide(mid.primary, downOne, downTwo)) {
                if (this.onSide(mid.secondary, downOne, downTwo)) {
                    moves.add(Move.dClock);
                } else {
                    moves.add(Move.getMove(mid.secondary, Direction.Counterclockwise));
                }
            } else if (this.onSide(mid.secondary, downOne, downTwo)) {
                moves.add(Move.getMove(mid.primary, Direction.Clockwise));
            } else {
                switch (mid) {
                    case FR:
                        moves.add(Move.fClock);
                        break;
                    case FL:
                        moves.add(Move.fCounter);
                        break;
                    case BR:
                        moves.add(Move.bCounter);
                        break;
                    case BL:
                        moves.add(Move.bClock);
                        break;
                }
            }
        } else if (midCount == 1 && dCount == 3) {
            Edge mid = midEdges.get(0);
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            Edge downThree = downEdges.get(2);
            if (this.onSide(mid.primary, downOne, downTwo) || this.oneOnSide(mid.primary, downThree)) {
                moves.add(Move.dClock);
            } else {
                moves.add(Move.getMove(mid.primary, Direction.Counterclockwise));
                moves.add(Move.dClock);
            }
        } else if (midCount == 3 && uCount == 1) {
            Edge up = upEdges.get(0);
            Edge midOne = midEdges.get(0);
            Edge midTwo = midEdges.get(1);
            Edge midThree = midEdges.get(2);
            Character blocked;
            if (up.primary == 'U') {
                blocked = up.secondary;
            } else {
                blocked = up.primary;
            }
            if (midOne.primary.equals(blocked)) {
                moves.add(Move.getMove(midOne.secondary, Direction.Clockwise));
            } else if (midOne.secondary.equals(blocked)) {
                moves.add(Move.getMove(midOne.primary, Direction.Counterclockwise));
            }
            if (midTwo.primary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.secondary, Direction.Clockwise));
            } else if (midTwo.secondary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.primary, Direction.Counterclockwise));
            }
            if (midThree.primary.equals(blocked)) {
                moves.add(Move.getMove(midThree.secondary, Direction.Clockwise));
            } else if (midThree.secondary.equals(blocked)) {
                moves.add(Move.getMove(midThree.primary, Direction.Counterclockwise));
            }
        }
        else if (midCount == 3 && dCount == 1) {
            Edge down = downEdges.get(0);
            Edge midOne = midEdges.get(0);
            Edge midTwo = midEdges.get(1);
            Edge midThree = midEdges.get(2);
            Character blocked;
            if (down.primary == 'D') {
                blocked = down.secondary;
            } else {
                blocked = down.primary;
            }
            if (midOne.primary.equals(blocked)) {
                moves.add(Move.getMove(midOne.secondary, Direction.Clockwise));
            }
            else if (midOne.secondary.equals(blocked)) {
                moves.add(Move.getMove(midOne.primary, Direction.Counterclockwise));
            } if (midTwo.primary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.secondary, Direction.Clockwise));
            }
            else if (midTwo.secondary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.primary, Direction.Counterclockwise));
            } if (midOne.primary.equals(blocked)) {
                moves.add(Move.getMove(midOne.secondary, Direction.Clockwise));
            }
            else if (midThree.secondary.equals(blocked)) {
                moves.add(Move.getMove(midThree.primary, Direction.Counterclockwise));
            }
        }
        else if (dCount == 1 && uCount == 3) {
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            Edge upThree = upEdges.get(2);
            Edge downOne = downEdges.get(0);
            if (this.onSide(downOne.primary, upOne, upTwo) || this.onSide(downOne.secondary, upOne, upTwo) || this.oneOnSide(downOne.primary, upThree) || this.oneOnSide(downOne.secondary, upThree)) {
                moves.add(Move.uClock);
            } else {
                Character downturn;
                if (downOne.primary.equals('D')) {
                    downturn = downOne.secondary;
                } else {
                    downturn = downOne.primary;
                }
                moves.add(Move.getMove(downturn, Direction.OneEighty));
                moves.add(Move.uClock);
            }
        } else if (dCount == 3 && uCount == 1) {
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            Edge downThree = downEdges.get(2);
            Edge upOne = upEdges.get(0);
            if (this.onSide(upOne.primary, downOne, downTwo) || this.onSide(upOne.secondary, downOne, downTwo) || this.oneOnSide(upOne.primary, downThree) || this.oneOnSide(upOne.secondary, downThree)) {
                moves.add(Move.dClock);
            } else {
                Character upturn;
                if (upOne.primary.equals('U')) {
                    upturn = upOne.secondary;
                } else {
                    upturn = upOne.primary;
                }
                moves.add(Move.getMove(upturn, Direction.OneEighty));
                moves.add(Move.dClock);
            }
        }
        return moves;
    }

    private boolean onSide(Character c, Edge one, Edge two) {
        return c.equals(one.primary) || c.equals(one.secondary) || c.equals(two.primary) || c.equals(two.secondary);
    }

    private boolean oneOnSide(Character c,Edge e) {
        return c.equals(e.primary) || c.equals(e.secondary);
    }

    private boolean shareFace(Edge one, Edge  two) {
        return one.primary.equals(two.primary) || one.primary.equals(two.secondary) || one.secondary.equals(two.primary) || one.secondary.equals(two.secondary);
    }

    private void translateAndExecute(HashMap<Character, Character> map, List<Move> moves) {
        for (Move move : moves) {
            Character actual = map.get(move.face);
            System.out.println("Original = " + move.face + " Actual = " + actual);
            Color turning = colorToFace.get(actual);
            switch (move.dir) {
                case Random:
                    this.cube.rotate(turning, r.nextBoolean());
                    break;
                case Clockwise:
                    this.cube.rotate(turning, true);
                    break;
                case Counterclockwise:
                    this.cube.rotate(turning, false);
                    break;
                case OneEighty:
                    this.cube.oneEighty(turning);
                    break;
            }
        }

    }

    private void phaseTwo() {
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));
        ArrayList<Edge> fronts = new ArrayList<>();

       for (Edge e: Edge.values()) {
           switch (e) {
               case FU:
                   if (frontFace.squares[1][2] == 'F' || upFace.squares[1][0] == 'F') {fronts.add(e);} break;
               case FD:
                   if (frontFace.squares[1][0] == 'F' || downFace.squares[1][2] == 'F') {fronts.add(e);} break;
               case FR:
                   if (frontFace.squares[2][1] == 'F' || rightFace.squares[0][1] == 'F') {fronts.add(e);} break;
               case FL:
                   if (frontFace.squares[0][1] == 'F' || leftFace.squares[2][1] == 'F') {fronts.add(e);} break;
               case BU:
                   if (backFace.squares[1][2] == 'F' || upFace.squares[1][2] == 'F') {fronts.add(e);} break;
               case BD:
                   if (backFace.squares[1][0] == 'F' || downFace.squares[1][0] == 'F') {fronts.add(e);} break;
               case BR:
                   if (backFace.squares[0][1] == 'F' || rightFace.squares[2][1] == 'F') {fronts.add(e);} break;
               case BL:
                   if (backFace.squares[2][1] == 'F' || leftFace.squares[0][1] == 'F') {fronts.add(e);} break;
               case UR:
                   if (upFace.squares[2][1] == 'F' || rightFace.squares[1][2] == 'F') {fronts.add(e);} break;
               case UL:
                   if (upFace.squares[0][1] == 'F' || leftFace.squares[1][2] == 'F') {fronts.add(e);} break;
               case DR:
                   if (downFace.squares[2][1] == 'F' || rightFace.squares[1][0] == 'F') {fronts.add(e);} break;
               case DL:
                   if (downFace.squares[0][1] == 'F' || leftFace.squares[1][0] == 'F') {fronts.add(e);} break;
           }
       }

        int uCount = 0;
        int dCount= 0;
        int midCount = 0;
        ArrayList<Edge> upEdges = new ArrayList<>();
        ArrayList<Edge> downEdges = new ArrayList<>();
        ArrayList<Edge> midEdges = new ArrayList<>();
        for (Edge e : fronts) {
            if (this.oneOnSide('U', e)) {
                uCount++;
                upEdges.add(e);
            } else if (this.oneOnSide('D', e)) {
                dCount++;
                downEdges.add(e);
            } else {
                midCount++;
                midEdges.add(e);
            }
        }
        boolean notDone = true;
        while (notDone) {
            ArrayList<Move> moves = new ArrayList<Move>();
            if (uCount == 4) {
                moves.add(Move.fEighty);
                moves.add(Move.dEighty);
                moves.add(Move.rClock);
                moves.add(Move.lCounter);
                moves.add(Move.bClock);
                notDone = false;
            } else if (dCount == 4) {
                moves.add(Move.fEighty);
                moves.add(Move.dEighty);
                moves.add(Move.lClock);
                moves.add(Move.rCounter);
                moves.add(Move.fClock);
                notDone = !notDone;
            } else if (uCount == 3) {
                ArrayList<Character> middles = new ArrayList();
                middles.add('F');
                middles.add('R');
                middles.add('B');
                middles.add('L');
                Edge upOne = upEdges.get(0);
                Edge upTwo = upEdges.get(1);
                Edge upThree = upEdges.get(2);
                middles.remove(this.notUpOrDown(upOne));
                middles.remove(this.notUpOrDown(upTwo));
                middles.remove(this.notUpOrDown(upThree));
                if (dCount == 1) {
                    Edge down = downEdges.get(0);
                    Character main = this.notUpOrDown(down);
                    translate = this.horizontalMap(main);
                    Character other = translate.get(middles.get(0));
                    switch (other) {
                        case 'R':
                            moves.add(Move.uClock);
                            break;
                        case 'L':
                            moves.add(Move.uCounter);
                            break;
                        case 'B':
                            moves.add(Move.uEighty);
                            break;
                    }
                    moves.add(Move.rClock);
                    moves.add(Move.lCounter);
                    moves.add(Move.fClock);
                    notDone = !notDone;
                } else if (midCount == 1) {
                    Edge mid = midEdges.get(0);
                    Character main = mid.primary;
                    translate = this.horizontalMap(main);
                    Character other = translate.get(middles.get(0));
                    if ((mid.equals(Edge.FL) && other.equals()))


                    
                    switch (other) {
                        case 'R':
                            moves.add(Move.rCounter);
                            break;
                        case 'L':
                            moves.add(Move.uEighty);
                            moves.add(Move.rCounter);
                            break;
                        case 'B':
                            moves.add(Move.uClock);
                            moves.add(Move.rCounter);
                            break;
                        case 'F':
                            moves.add(Move.getMove(main, Direction.Clockwise));
                            notDone = !notDone;
                            break;
                    }
                }
            } else if (dCount == 3) {
                ArrayList<Character> middles = new ArrayList();
                middles.add('F');
                middles.add('R');
                middles.add('B');
                middles.add('L');
                Edge downOne = downEdges.get(0);
                Edge downTwo = downEdges.get(1);
                Edge downThree = downEdges.get(2);
                middles.remove(this.notUpOrDown(downOne));
                middles.remove(this.notUpOrDown(downTwo));
                middles.remove(this.notUpOrDown(downThree));
                if (uCount == 1) {
                    Edge up = downEdges.get(0);
                    Character main = this.notUpOrDown(up);
                    translate = this.horizontalMap(main);
                    Character other = translate.get(middles.get(0));
                    switch (other) {
                        case 'R':
                            moves.add(Move.dCounter);
                            break;
                        case 'L':
                            moves.add(Move.dClock);
                            break;
                        case 'B':
                            moves.add(Move.dEighty);
                            break;
                    }
                    moves.add(Move.rCounter);
                    moves.add(Move.lClock);
                    moves.add(Move.fClock);
                    notDone = !notDone;
                } else if (midCount == 1) {
                    Edge mid = midEdges.get(0);
                    Character main = mid.primary;
                    translate = this.horizontalMap(main);
                    Character other = translate.get(middles.get(0));
                    switch (other) {
                        case 'R':
                            moves.add(Move.rClock);
                            break;
                        case 'L':
                            moves.add(Move.dEighty);
                            moves.add(Move.rClock);
                            break;
                        case 'B':
                            moves.add(Move.dCounter);
                            moves.add(Move.rClock);
                            break;
                        case 'F':
                            moves.add(Move.getMove(main, Direction.Counterclockwise));
                            notDone = !notDone;
                            break;
                    }
                }
            } else if (uCount == 2 && dCount == 2) {
                Edge upOne = upEdges.get(0);
                Edge upTwo = upEdges.get(1);
                Edge downOne = downEdges.get(0);
                Edge downTwo =downEdges.get(1);
                Character upOneFace = this.notUpOrDown(upOne);
                Character upTwoFace = this.notUpOrDown(upTwo);
                if (this.onSide(upOneFace, downOne, downTwo)) {
                    if (this.onSide(upTwoFace, downOne, downTwo)) {
                        moves.add(Move.dRandom);
                    } else {
                        moves.add(Move.getMove(upTwoFace, Direction.OneEighty));
                    }
                } else {
                    moves.add(Move.getMove(upOneFace, Direction.OneEighty));
                }
            } else if (uCount == 2 && dCount == 1 && midCount == 1) {
                Edge upOne = upEdges.get(0);
                Edge upTwo = upEdges.get(1);
                Edge downOne = downEdges.get(0);
                Character downOneFace = this.notUpOrDown(downOne);
                if (this.onSide(downOneFace, upOne, upTwo)) {
                    moves.add(Move.dClock);
                } else {
                    moves.add(Move.getMove(downOneFace, Direction.OneEighty));
                }
            } else if (dCount == 2 && uCount == 1 && midCount == 1) {
                Edge downOne = downEdges.get(0);
                Edge downTwo = downEdges.get(1);
                Edge upOne = upEdges.get(0);
                Character upOneFace = this.notUpOrDown(upOne);
                if (this.onSide(upOneFace, downOne, downTwo)) {
                    moves.add(Move.uClock);
                } else {
                    moves.add(Move.getMove(upOneFace, Direction.OneEighty));
                }
            } else if (midCount == 3) {
                Edge midOne = midEdges.get(0);
                Edge midTwo = midEdges.get(1);
                Edge midThree = midEdges.get(2);
                if (uCount == 1) {
                    Edge up = upEdges.get(0);
                    Character valuedSide = this.notUpOrDown(up);
                    translate = this.horizontalMap(valuedSide);
                    if (this.onSide(valuedSide, midOne, midTwo) || this.onSide(valuedSide, midTwo, midThree) || this.onSide(valuedSide, midOne, midThree)) {
                        moves.add(Move.uEighty);
                    } else {
                        if (this.oneOnSide(valuedSide, midOne)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midOne, up);
                        } else if (this.oneOnSide(valuedSide, midTwo)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midTwo, up);
                        } else if (this.oneOnSide(valuedSide, midThree)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midThree, up);
                        }
                    }
                    notDone = !notDone;
                }
                else if (dCount == 1) {
                    Edge down = downEdges.get(0);
                    Character valuedSide = this.notUpOrDown(down);
                    translate = this.horizontalMap(valuedSide);
                    if (this.onSide(valuedSide, midOne, midTwo) || this.onSide(valuedSide, midTwo, midThree) || this.onSide(valuedSide, midOne, midThree)) {
                        moves.add(Move.uEighty);
                    } else {
                        if (this.oneOnSide(valuedSide, midOne)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midOne, down);
                        } else if (this.oneOnSide(valuedSide, midTwo)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midTwo, down);
                        } else if (this.oneOnSide(valuedSide, midThree)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midThree, down);
                        }
                        notDone = !notDone;
                    }
                }
            }
            this.translateAndExecute(translate, moves);
        }


    }

    private ArrayList<Move> threeMidHelperUp(ArrayList<Move> moves, Character valuedSide, Edge mid, Edge up) {
        if (this.oneOnSide(valuedSide, mid)) {
            if ((up.equals(Edge.FU) && mid.equals(Edge.FR)) || (up.equals(Edge.UR) && mid.equals(Edge.BR)) || (up.equals(Edge.BU) && mid.equals(Edge.BL)) || (up.equals(Edge.UL) && mid.equals(Edge.FL))) {
                moves.add(Move.getMove(translate.get('R'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('R'), Direction.Counterclockwise));
            } else if ((up.equals(Edge.FU) && mid.equals(Edge.FL)) || (up.equals(Edge.UL) && mid.equals(Edge.BL)) || (up.equals(Edge.BU) && mid.equals(Edge.BR)) || (up.equals(Edge.UR) && mid.equals(Edge.FR))) {
                moves.add(Move.getMove(translate.get('L'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('L'), Direction.Clockwise));
            }
        }
        return moves;
    }

    private ArrayList<Move> threeMidHelperDown(ArrayList<Move> moves, Character valuedSide, Edge mid, Edge down) {
        if (this.oneOnSide(valuedSide, mid)) {
            if ((down.equals(Edge.FD) && mid.equals(Edge.FL)) || (down.equals(Edge.DR) && mid.equals(Edge.FR)) || (down.equals(Edge.BD) && mid.equals(Edge.BR)) || (down.equals(Edge.DL) && mid.equals(Edge.BL))) {
                moves.add(Move.getMove(translate.get('L'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('L'), Direction.Clockwise));
            } else if ((down.equals(Edge.FD) && mid.equals(Edge.FR)) || (down.equals(Edge.DL) && mid.equals(Edge.FL)) || (down.equals(Edge.BD) && mid.equals(Edge.BL)) || (down.equals(Edge.DR) && mid.equals(Edge.BR))) {
                moves.add(Move.getMove(translate.get('R'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('R'), Direction.Counterclockwise));
            }
        }
        return moves;
    }

    private Character notUpOrDown(Edge e) {
        if (e.primary == 'U' || e.primary == 'D') {
            return e.secondary;
        } else {
            return e.primary;
        }
    }



}
