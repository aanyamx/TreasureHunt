import info.gridworld.actor.ActorWorld;

public class CharacterWorld extends ActorWorld {
    private static final String DEFAULT_MESSAGE = "Welcome to a Treasure Hunt!! To get started, click on the red ladybug to set a\ntopic. Click on the green bug to get a hint about the row, blue to get a column,\nand yellow to get adjace.";
    public void show()
    {
        if (getMessage() == null)
            setMessage(DEFAULT_MESSAGE);
        super.show();
    }
}
