import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


import static org.assertj.core.api.Assertions.*;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;

@DisplayName("Asg-02 Challenge: InterestCalclatorTests")
public class InterestCalculatorTests {

    private PrintStream sysOut;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final LinkedList<String> inputQueue = new LinkedList<>();

    @BeforeEach
    public void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void revertStreams() {
        System.setOut(sysOut);
        this.inputQueue.clear();
    }

    public void provideKeyboardInput() {
        String queuedInput = "";
        for (String s : inputQueue) {
            queuedInput += String.format("%s%n",s);
        }
        System.setIn(new ByteArrayInputStream(queuedInput.getBytes()));
    }


    @DisplayName("console Tests")
    @ParameterizedTest
    @CsvFileSource(resources =  "./data/console.csv", numLinesToSkip = 1)
    public void consoleTests(String acctHolder, String acctNumber, double startBalance, String acctType, String expectedOutput) {
        String[] splitExpectedOutput = expectedOutput.split(" ");

        inputQueue.offer(acctHolder);
        inputQueue.offer(acctNumber);
        inputQueue.offer(String.valueOf(startBalance));
        inputQueue.offer(acctType);
        provideKeyboardInput();

        new InterestCalculator().run();

        String consoleOutput = outContent.toString();

        assertThat(consoleOutput).containsSubsequence(splitExpectedOutput);
    }


}

