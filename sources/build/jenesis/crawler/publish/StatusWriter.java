package build.jenesis.crawler.publish;

import module java.base;
import build.jenesis.crawler.Crawl;
import build.jenesis.crawler.State;

public final class StatusWriter implements CheckpointListener {

    private final Path target;
    private final Instant runStart;

    public StatusWriter(Path target) {
        this(target, Instant.now());
    }

    public StatusWriter(Path target, Instant runStart) {
        this.target = Objects.requireNonNull(target, "target");
        this.runStart = Objects.requireNonNull(runStart, "runStart");
    }

    @Override
    public synchronized void onCheckpoint(State state, Statistics statistics) throws IOException {
        Instant now = Instant.now();
        long elapsedSeconds = Math.max(1L, Duration.between(runStart, now).toSeconds());
        double rate = (double) statistics.processed() / (double) elapsedSeconds;

        StringBuilder builder = new StringBuilder();
        builder.append("# Crawl status\n\n");
        builder.append("- Updated: ").append(now).append('\n');
        builder.append("- Sync mode: ").append(statistics.syncMode()).append('\n');
        builder.append("- This run: processed=").append(statistics.processed())
                .append(", named=").append(statistics.named())
                .append(", automatic=").append(statistics.automatic())
                .append(", failed=").append(statistics.failed()).append('\n');
        builder.append("- Throughput: ").append(String.format(Locale.ROOT, "%.0f", rate)).append(" coordinates/sec\n");
        if (state.sweepStartedAt() != null) {
            builder.append("- Current chunk started: ").append(state.sweepStartedAt()).append('\n');
        }
        builder.append("- Last applied index chunk: ").append(state.indexChunkLastApplied()).append('\n');
        if (state.indexChainId() != null) {
            builder.append("- Index chain id: `").append(state.indexChainId()).append("`\n");
        }
        builder.append("\n");
        builder.append("This file is rewritten at every crawler checkpoint.\n");

        Path parent = target.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = target.resolveSibling(target.getFileName() + ".tmp");
        Files.writeString(temp, builder.toString(), StandardCharsets.UTF_8);
        try {
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static String formatDuration(Duration duration) {
        long total = Math.max(0L, duration.toSeconds());
        long hours = total / 3600L;
        long minutes = (total % 3600L) / 60L;
        long seconds = total % 60L;
        if (hours > 0L) {
            return String.format(Locale.ROOT, "%dh %02dm %02ds", hours, minutes, seconds);
        }
        if (minutes > 0L) {
            return String.format(Locale.ROOT, "%dm %02ds", minutes, seconds);
        }
        return String.format(Locale.ROOT, "%ds", seconds);
    }
}
