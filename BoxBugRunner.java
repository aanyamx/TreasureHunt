import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;
import info.gridworld.actor.Bug;
import java.util.Random;

public class BoxBugRunner extends Bug {
    public static void main( String[] args )
    {
        ActorWorld world = new CharacterWorld();

        Random rand = new Random(); 
        int firstRAND = rand.nextInt(9);
        int secRAND = rand.nextInt(9); 


        BoxBugHinters first = new BoxBugHinters(world, firstRAND, secRAND);
        first.setColor(Color.BLUE);
        world.add(new Location(5, 7), first);

        BoxBugHinters second = new ColHinter(world, firstRAND, secRAND);
        second.setColor(Color.GREEN);
        world.add(new Location(2, 9), second);

        BoxBugHinters third = new AdjacentHinter(world, firstRAND, secRAND);
        third.setColor(Color.ORANGE);
        world.add(new Location(8, 3), third);

        
        BoxBugHinters fourth = new AdjacentHinter(world, firstRAND, secRAND);
        fourth.setColor(Color.ORANGE);
        world.add(new Location(2, 3), fourth);
        
        BoxBugHinters.createInstructionBug();

        world.show();
        
    }
}