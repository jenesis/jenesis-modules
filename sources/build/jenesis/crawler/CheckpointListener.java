package build.jenesis.crawler;

import module java.base;

@FunctionalInterface
public interface CheckpointListener {

    CheckpointListener NOOP = (state, statistics) -> {
    };

    void onCheckpoint(State state, Statistics statistics) throws IOException;

    default CheckpointListener andThen(CheckpointListener next) {
        Objects.requireNonNull(next, "next");
        return (state, statistics) -> {
            this.onCheckpoint(state, statistics);
            next.onCheckpoint(state, statistics);
        };
    }

    record Statistics(long processed, long modular, long failed, SyncMode syncMode) {

        public Statistics {
            Objects.requireNonNull(syncMode, "syncMode");
        }
    }
}
