
package com.airhacks.wad.boundary;

import com.airhacks.wad.control.Builder;
import com.airhacks.wad.control.EnterListener;
import com.airhacks.wad.control.FolderWatchService;
import com.airhacks.wad.control.TerminalColors;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;

/**
 *
 * @author airhacks.com
 */
public class WASFlow {
    private final List<Long> buildTimes;

    private final AtomicLong successCounter = new AtomicLong();
    private final AtomicLong buildErrorCounter = new AtomicLong();

    private final Builder builder;

    public WASFlow(Path srcTestJava, URI readinessURI) throws IOException {
        this.builder = new Builder();
        this.buildTimes = new ArrayList<>();
        Runnable changeListener = this::build;
        changeListener.run();
        EnterListener.registerEnterListener(changeListener);
        FolderWatchService.listenForChanges(srcTestJava, changeListener);
    }

    void build() {
        long start = System.currentTimeMillis();
        try {
            System.out.printf("[%s%s%s]", TerminalColors.TIME.value(), currentFormattedTime(), TerminalColors.RESET.value());
            InvocationResult result = this.builder.build();
            if (result.getExitCode() == 0) {
                System.out.printf("[%d]", successCounter.incrementAndGet());
                System.out.print("\uD83D\uDC4D");
                long buildTime = (System.currentTimeMillis() - start);
                buildTimes.add(buildTime);
                System.out.println(" built in " + buildTime + " ms");
                if (buildTimes.size() % 10 == 0) {
                    this.printStatistics();
                }
            } else {
                System.out.printf("[%d] ", buildErrorCounter.incrementAndGet());
                System.out.println("\uD83D\uDC4E ");
            }
        } catch (MavenInvocationException ex) {
            System.err.println(ex.getClass().getName() + " " + ex.getMessage());
        }
    }

    static String currentFormattedTime() {
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .toFormatter();

        return LocalTime.now().format(timeFormatter);
    }

    public LongSummaryStatistics buildTimeStatistics() {
        return this.buildTimes.
                stream().
                mapToLong(t -> t).
                summaryStatistics();
    }

    String statisticsSummary() {

        LongSummaryStatistics buildTimeStatistics = this.buildTimeStatistics();
        long maxTime = buildTimeStatistics.getMax();
        long minTime = buildTimeStatistics.getMin();
        long totalTime = buildTimeStatistics.getSum();
        String buildTimeStats = String.format("Build times: min %d ms, max %d ms, total %d ms\n", minTime, maxTime, totalTime);

        String failureStats;
        long failedBuilds = buildErrorCounter.get();
        if (failedBuilds == 0) {
            failureStats = "Great! Every build was a success!";
        } else {
            failureStats = String.format("%d builds failed", buildErrorCounter.get());
        }
        return buildTimeStats + failureStats;
    }

    void printStatistics() {
        System.out.println(statisticsSummary());
    }

}
