package io.github.bluething.java.cachingproxy.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "caching-proxy", description = "Start a caching proxy server")
@RequiredArgsConstructor
class CachingProxyCommand implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    @CommandLine.Option(names = {"--port"}, description = "Port number for the proxy server")
    private Integer port;

    @CommandLine.Option(names = {"--origin"}, description = "Origin server URL")
    private String origin;

    @CommandLine.Option(names = {"--clear-cache"}, description = "Clear the cache")
    private boolean clearCache;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Show help message")
    private boolean helpRequested;

    @Override
    public void run(String... args) throws Exception {
        CommandLine cli = new CommandLine(this);
        int exitCode = cli.execute(args);

        if (clearCache) {
            //TODO clear redis cache
        }

        if (port != null && origin != null) {
            System.setProperty("server.port", String.valueOf(port));
            System.setProperty("server.origin", origin);
            System.out.println("Starting caching proxy server on port " + port);
            System.out.println("Origin server: " + origin);
        } else if (!helpRequested) {
            System.err.println("Please provide both --port and --origin options, or use --clear-cache");
            SpringApplication.exit(applicationContext, () -> 1);
        }
    }
}
