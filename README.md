TreasureHunt is a Java-based interactive game built with GridWorld. It displays a 10×10 grid containing a hidden treasure, which you find through the trivia-asking ladybugs that provide smart hints powered by the ChatGPT API. Answer questions correctly to uncover clues and find the treasure!

## Project Overview
This game places a hidden treasure at a random location on a 10x10 grid. The player must use specific actors to narrow down the coordinates. The project uses inheritance, polymorphism, and external API integration.

**Key Components**
AI Logic: Utilizes the OpenAI text-davinci-003 model for content generation and verification.

ColHinter: Eliminates specific columns from the search area.

AdjacentHinter: Informs the player if the treasure is within a 1-block radius of the bug’s current location.

Rockie: A specialized Rock subclass used to visually "block out" eliminated grid sections.

Treasure: A specialized Flower subclass representing the treasure goal.

## Prerequisites
Java Development Kit (JDK) 8 or higher
GridWorld Library: The gridworld.jar must be included in your project's build path.
JSON Library: org.json for parsing API responses.

**API Configuration**
To enable question generation, you must provide your own OpenAI API key in BoxBugHinters.java:
con.setRequestProperty("Authorization", "Bearer YOUR_API_KEY_HERE");

## How to Play
Initialize: Launch the application to see the 10x10 grid.
Interact: Click on the various colored bugs to trigger their specific hint logic.
Blue Bug: Provides column-based hints.
Orange Bug: Provides adjacency-based hints.
Narrow Down: As you receive hints, the grid will populate with Rockie actors to show which areas do not contain the treasure.
