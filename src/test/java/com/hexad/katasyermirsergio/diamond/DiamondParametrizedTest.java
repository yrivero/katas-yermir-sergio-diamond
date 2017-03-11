package com.hexad.katasyermirsergio.diamond;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;


@RunWith(Parameterized.class)
public class DiamondParametrizedTest {

    @Parameterized.Parameters
    public static List<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {
                        'A',

                        "A\n"
                },
                {
                        'B',

                        " A\n" +
                        "B B\n" +
                        " A\n"
                },
                {
                        'C',

                        "  A\n" +
                        " B B\n" +
                        "C   C\n" +
                        " B B\n" +
                        "  A\n"
                }
        });
    }

    @Parameterized.Parameter(0)
    public char widestPointLetter;
    @Parameterized.Parameter(1)
    public String expectedSystemOutOutput;




    private PrintStream originalSystemOut;
    private ByteArrayOutputStream internalBuffer;
    private Diamond diamond;

    @Before
    public void setUp() {
        diamond = new Diamond();
        // Backup original System.out
        originalSystemOut = System.out;

        internalBuffer = new ByteArrayOutputStream();

        PrintStream ps = new PrintStream(internalBuffer) {
            /**
             * Overriding this method so that we can also see the output on the console.
             */
            @Override
            public void write(byte[] buf, int off, int len) {
                super.write(buf, off, len);
                originalSystemOut.write(buf, off, len);
            }
        };

        System.setOut(ps);
    }

    @After
    public void tearDown() {
        // Restore the original System.out
        System.setOut(originalSystemOut);
    }

    @Test
    public void givenTheInputPrintOutTheOutputAndCheck() {
        diamond.printDiamond(widestPointLetter);

        assertOutputEquals(expectedSystemOutOutput);
    }

    private void assertOutputEquals(String expectedOutput) {
        // Replacing new lines with the system's default line separator
        // That way the tests are easier to write :-)
        expectedOutput = canonizeNewLines(expectedOutput);

        byte[] expectedOutputBytes = expectedOutput.getBytes();
        byte[] actualOutputBytes = internalBuffer.toByteArray();
        assertArrayEquals("Output should match with expected output", expectedOutputBytes, actualOutputBytes);
    }

    private String canonizeNewLines(String expectedOutput) {
        return expectedOutput.replaceAll("\n", System.lineSeparator());
    }

}
