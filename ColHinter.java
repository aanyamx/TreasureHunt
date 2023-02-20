import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.Random;
import java.awt.Color;

public class ColHinter extends BoxBugHinters {

    public ColHinter(ActorWorld world, int firstRAND, int secRAND) {
        super(world, firstRAND, secRAND);
    }

    protected String getHints() {
        if (getAvailableCols().isEmpty()) {
            return "Cannot narrow cols any further";
        }

        //COL!!
        Random rand = new Random();
        Grid<Actor> gr = getGrid();

        int colV = getAvailableCols().get(rand.nextInt(getAvailableCols().size()));
        getAvailableCols().remove(Integer.valueOf(colV));
        String ret = "The treasure is NOT in col " + (colV + 1);


        for (int i = 0; i < 10; i++) {
            Rockie flow = new Rockie();
            flow.setColor(Color.GRAY);
            Location myLOC = new Location(i, colV);

            if (!(gr.get(myLOC) instanceof BoxBug) && !(gr.get(myLOC) instanceof BoxBugHinters)) {
                getWorld().add(myLOC, flow);
            }
        }
        return ret;
    }
}