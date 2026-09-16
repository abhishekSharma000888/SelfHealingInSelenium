package utilities;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.ArrayList;
import java.util.List;

public class TestLogAppender extends AppenderBase<ILoggingEvent> {

    private final List<String> logs = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent event) {
        logs.add(event.getFormattedMessage());
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

}