package build.jenesis.modules;

import module java.base;

public interface BatchSource extends Closeable {

    Batch next() throws IOException, InterruptedException;

    record Batch(List<Coordinate> coordinates, long endPosition, boolean exhausted) {

        public Batch {
            coordinates = List.copyOf(coordinates);
        }

        public static final Batch EMPTY_OPEN = new Batch(List.of(), -1L, false);

        public boolean isEmpty() {
            return coordinates.isEmpty();
        }
    }
}
