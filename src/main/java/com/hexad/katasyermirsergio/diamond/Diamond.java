package com.hexad.katasyermirsergio.diamond;

import java.util.ArrayList;
import java.util.List;

public class Diamond {

    private static final char FIRST_LETTER = 'A';

    public void printDiamond(char widestPointLetter) {

        StringBuilder output = new StringBuilder();

        StringBuilder currentLine = new StringBuilder();

        List<String> contentStrings = new ArrayList<>();

        // Draw from the top to the middle line

        drawDiamondTopCentre(widestPointLetter, output, currentLine, contentStrings);


        // Draw after the middle line to the bottom
        drawDiamondBottom(output, contentStrings);

        // Print the generated output
        System.out.print(output.toString());
    }

    private void drawDiamondBottom(StringBuilder output, List<String> contentStrings) {
        for (int i = contentStrings.size()-1; i>=0; i--) {
            output.append(contentStrings.get(i));
        }
    }

    private void drawDiamondTopCentre(char widestPointLetter, StringBuilder output, StringBuilder currentLine, List<String> contentStrings) {
        char currentLetter = FIRST_LETTER;
        int paddingSpaces = widestPointLetter - FIRST_LETTER;
        int fillingSpaces = -1;     // -1 so that the first line (top peak of the diamond) has no filling spaces

        while (currentLetter <= widestPointLetter) {
            // Delete de content of the current line buffer
            deleteBuffer(currentLine);

            // Draw the figure
            drawLine(currentLine, currentLetter, paddingSpaces, fillingSpaces);

            // Append the line to the output
            output.append(currentLine);

            // Store the line to draw the top of the diamond in reverse
            // Only if it's not the middle line, because it only needs to be painted once
            if (currentLetter != widestPointLetter) {
                contentStrings.add(currentLine.toString());
            }

            currentLetter++;
            paddingSpaces--;
            // Each new line needs 2 more filling spaces than the last one
            // Except for the first one, that only needs 1
            // That's why the initial value for fillingSpaces is -1
            fillingSpaces += 2;
        }
    }

    private void drawLine(StringBuilder currentLine, char currentLetter, int paddingSpaces, int fillingSpaces) {
        appendSpaces(currentLine, paddingSpaces);    // Padding
        currentLine.append(currentLetter);           // Letter

        // If we don't need any filling spaces (top line), skip the filling and the last letter.
        if (fillingSpaces > 0) {
            appendSpaces(currentLine, fillingSpaces);    // Filling
            currentLine.append(currentLetter);           // Letter
        }

        currentLine.append(System.lineSeparator());  // End of line
    }

    private void deleteBuffer(StringBuilder currentLine) {
        currentLine.delete(0, currentLine.length());
    }

    private void appendSpaces(StringBuilder output, int numberOfSpaces) {
        for (int i=0; i<numberOfSpaces; i++) {
            output.append(' ');
        }
    }

}
