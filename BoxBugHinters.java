import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.Color;

public class BoxBugHinters extends Bug {
    private int treasROW;
    private int treasCOL;
    private String gptQ;
    private static ActorWorld myWorld;
    private static String subject;
    private List<Integer> availableRows;
    private List<Integer> availableCols;

    protected int getTreasROW() {
        return this.treasROW;
    }

    protected int getTreasCOL() {
        return this.treasCOL;
    }

    protected ActorWorld getWorld() {
        return this.myWorld;
    }

    protected List<Integer> getAvailableRows() {
        return this.availableRows;
    }

    protected List<Integer> getAvailableCols() {
        return this.availableCols;
    }

    public BoxBugHinters(ActorWorld world, int firstRAND, int secRAND) {
        this.treasROW = firstRAND;
        this.treasCOL = secRAND;
        this.gptQ = "";
        this.myWorld = world;
        this.subject = "movies";

        availableRows = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        availableCols = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

        availableRows.remove(firstRAND);
        availableCols.remove(secRAND);
    }

    public static class BoxBug extends Bug {
        public BoxBug() {}

        public String setTopic(String inp) {
            subject = inp;
            return "Your topic is " + subject + ". Click on any of the ladybugs to get a trivia question about " + subject + ".";
        }

    }

    protected static void createInstructionBug() {
        BoxBug instructionBug = new BoxBug();
        myWorld.add(new Location(9, 0), instructionBug);
    }

    public String getQuestion() {
        try {
            String query = "Give me a trivia question about " + subject + " with a one word answer. Type the question, type '###' as a delimiter, and then type the answer. Put the response all on one line without any extra whitespace.";
            gptQ = chatGPT(query);
            String question = gptQ.split("###")[0];
            return question;
        } catch (Exception e) {
            e.printStackTrace();
            getQuestion();
        }
        return "";
    }

    public String answerQuestion(String inp) {
        String answer = gptQ.split("###")[1];
        inp = inp.replaceAll("\\s", "");
        answer = answer.replaceAll("\\s", "");

        String formattedInp = inp.toLowerCase();
        String formattedAns = answer.toLowerCase();
        if (formattedInp.equals(formattedAns)) {
            String ret = "Correct! This is your hint: ";
            return ret + getHints();
        }
        return "Incorrect! You get no hints!";
    }

    protected String getHints() {
        if (availableRows.isEmpty()) {
            return "Cannot narrow rows any further";
        }

        Random rand = new Random();
        Grid<Actor> gr = getGrid();

        int rowV = availableRows.get(rand.nextInt(availableRows.size()));
        availableRows.remove(Integer.valueOf(rowV));
        String ret = "The treasure is NOT in row " + (rowV + 1);

        for (int i = 0; i < 10; i++) {
            Rockie flow = new Rockie();
            flow.setColor(Color.GRAY);
            Location myLOC = new Location(rowV, i);

            if (!(gr.get(myLOC) instanceof BoxBug) && !(gr.get(myLOC) instanceof BoxBugHinters)) {
                myWorld.add(myLOC, flow);
            }
        }
        return ret;
    }



    public String finalGuess(Location loc1) {
        Treasure treasure = new Treasure();
        treasure.setColor(Color.PINK);
        myWorld.add(new Location(treasROW, treasCOL), treasure);
        if (loc1.getRow() - 1 == treasROW && loc1.getCol() - 1 == treasCOL) {
            return "You found the treasure! You WIN!";
        } else {
            return "Sorry, that is incorrect. You LOSE :(. The treasure was at (" + (treasROW + 1) + ", " + (treasCOL + 1) + ").";
        }
    }

    public void act() {
        return;
    }

    private static String chatGPT(String text) throws Exception {
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer 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");

        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        return (new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
    }

}
