package build.jenesis.modules;

import module java.base;

public final class StatusWriter implements CheckpointListener {

    private final Path target;
    private final Instant runStart;
    private final AtomicLong startPosition;

    public StatusWriter(Path target) {
        this(target, Instant.now());
    }

    public StatusWriter(Path target, Instant runStart) {
        this.target = Objects.requireNonNull(target, "target");
        this.runStart = Objects.requireNonNull(runStart, "runStart");
        this.startPosition = new AtomicLong(-1L);
    }

    @Override
    public synchronized void onCheckpoint(State state, Statistics statistics) throws IOException {
        startPosition.compareAndSet(-1L, Math.max(0L, state.worklistPosition() - statistics.processed()));
        Instant now = Instant.now();
        long elapsedSeconds = Math.max(1L, Duration.between(runStart, now).toSeconds());
        long deltaPosition = state.worklistPosition() - startPosition.get();
        double rate = deltaPosition > 0 ? (double) deltaPosition / (double) elapsedSeconds : 0d;
        long remaining = Math.max(0L, state.worklistTotal() - state.worklistPosition());
        double percentage = state.worklistTotal() > 0L
                ? 100d * state.worklistPosition() / state.worklistTotal()
                : 0d;
        Duration eta = rate > 0d ? Duration.ofSeconds((long) (remaining / rate)) : null;

        StringBuilder builder = new StringBuilder();
        builder.append("# Crawl status\n\n");
        builder.append("- Updated: ").append(now).append('\n');
        builder.append("- Sync mode: ").append(statistics.syncMode()).append('\n');
        builder.append("- Position: ").append(state.worklistPosition())
                .append(" / ").append(state.worklistTotal())
                .append(String.format(Locale.ROOT, " (%.2f%%)", percentage)).append('\n');
        builder.append("- This run: processed=").append(statistics.processed())
                .append(", modular=").append(statistics.modular())
                .append(", failed=").append(statistics.failed()).append('\n');
        builder.append("- Throughput: ").append(String.format(Locale.ROOT, "%.0f", rate)).append(" coordinates/sec\n");
        if (eta != null) {
            builder.append("- ETA to finish current worklist at this rate: ").append(formatDuration(eta)).append('\n');
        }
        if (state.sweepStartedAt() != null) {
            builder.append("- Sweep started: ").append(state.sweepStartedAt()).append('\n');
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
