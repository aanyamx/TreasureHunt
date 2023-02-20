import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.Random;
import java.awt.Color;

public class AdjacentHinter extends BoxBugHinters {

    public AdjacentHinter(ActorWorld world, int firstRAND, int secRAND) {
        super(world, firstRAND, secRAND);
    }

    protected String getHints() {
        Random rand = new Random();
        Grid<Actor> gr = getGrid();
        Location myLOC = getLocation();
        String ret = "";

        int[][] adjJumps = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

        int[][] neighbors = new int[8][2];

        boolean treasureIsNeighbor = false;

        for (int i = 0; i < adjJumps.length; i++) {
            int[] jump = adjJumps[i];
            int xJump = jump[0];
            int yJump = jump[1];


            int neighborRow =  myLOC.getRow() + xJump;
            neighbors[i][0] = neighborRow;

            int neighborCol = myLOC.getCol() + yJump;
            neighbors[i][1] = neighborCol;

            if (neighborRow == getTreasROW() && neighborCol == getTreasCOL()) {
                treasureIsNeighbor = true;
            }
        }
        

        if (treasureIsNeighbor) {
            ret = "The treasure is adjacent to me!";
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    boolean rowGroup = i == myLOC.getRow() - 1 || i == myLOC.getRow() || i == myLOC.getRow() + 1;
                    boolean colGroup = j == myLOC.getCol() - 1 || j == myLOC.getCol() || j == myLOC.getCol() + 1;
                    if ((!rowGroup) || (!colGroup)) {
                        Rockie flow = new Rockie();
                        flow.setColor(Color.GRAY);
                        Location setter = new Location(i, j);
        
                        if (!(gr.get(setter) instanceof BoxBug) && !(gr.get(setter) instanceof BoxBugHinters)) {
                            getWorld().add(setter, flow);
                        }
                    }
                }
            }

        } else {
            ret = "The treasure is NOT adjacent to me!";
            for (int[] neighbor : neighbors) {
                Rockie flow = new Rockie();
                flow.setColor(Color.GRAY);
                Location setter = new Location(neighbor[0], neighbor[1]);

                if (!(gr.get(setter) instanceof BoxBug) && !(gr.get(setter) instanceof BoxBugHinters)) {
                    getWorld().add(setter, flow);
                }
            }
        }
        return ret;
    }
}
