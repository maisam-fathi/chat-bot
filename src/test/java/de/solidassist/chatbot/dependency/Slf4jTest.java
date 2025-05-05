package de.solidassist.chatbot.dependency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a basic SLF4J logging test to verify that log messages are properly
 * routed through the default logging implementation used by LangChain4j.
 *
 * <p>LangChain4j internally depends on SLF4J and typically brings in a logging bridge
 * such as Log4j-to-SLF4J or JUL-to-SLF4J via its transitive dependencies.</p>
 *
 * <p>In this setup, we deliberately avoid including an explicit SLF4J binding (such as
 * <code>slf4j-simple</code> or <code>logback-classic</code>) to let LangChain4j’s own
 * logging configuration take effect. This ensures compatibility and avoids multiple
 * binding conflicts at runtime.</p>
 *
 * <p>Running this test helps confirm that logging still works correctly through the
 * dependencies brought by LangChain4j alone, without requiring an additional logger
 * implementation.</p>
 *
 * <p><b>Note:</b> If the project requires advanced logging configuration in the future,
 * it is recommended to explicitly add and configure a logging backend such as Log4j2
 * or Logback, and provide a corresponding configuration file.</p>
 */
public class Slf4jTest {

    // Create a logger instance (no need to redefine it in main)
    private static final Logger logger = LoggerFactory.getLogger(Slf4jTest.class);

    public static void main(String[] args) {
        // Log test message
        logger.info("Test log after removing slf4j-simple and using LongChain4j's logging");
    }
}
