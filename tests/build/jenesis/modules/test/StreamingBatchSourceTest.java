package build.jenesis.modules.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.modules.BatchSource;
import build.jenesis.modules.Coordinate;
import build.jenesis.modules.StreamingBatchSource;
import build.jenesis.modules.WorklistStream;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamingBatchSourceTest {

    @Test
    public void groups_queue_items_into_batches_up_to_size() throws InterruptedException {
        BlockingQueue<WorklistStream.QueueItem> queue = new ArrayBlockingQueue<>(32);
        for (int i = 1; i <= 5; i++) {
            queue.put(new WorklistStream.QueueItem(coordinate("g", "a", i + ".0"), i));
        }
        queue.put(WorklistStream.QueueItem.POISON);

        StreamingBatchSource source = new StreamingBatchSource(queue, 3, Duration.ofMillis(100L));

        BatchSource.Batch first = source.next();
        assertThat(first.coordinates()).hasSize(3);
        assertThat(first.endPosition()).isEqualTo(3L);
        assertThat(first.exhausted()).isFalse();

        BatchSource.Batch second = source.next();
        assertThat(second.coordinates()).hasSize(2);
        assertThat(second.endPosition()).isEqualTo(5L);
        assertThat(second.exhausted()).isTrue();

        BatchSource.Batch tail = source.next();
        assertThat(tail.coordinates()).isEmpty();
        assertThat(tail.exhausted()).isTrue();
    }

    @Test
    public void returns_empty_open_batch_when_queue_is_temporarily_empty() throws InterruptedException {
        BlockingQueue<WorklistStream.QueueItem> queue = new ArrayBlockingQueue<>(4);
        StreamingBatchSource source = new StreamingBatchSource(queue, 4, Duration.ofMillis(20L));

        BatchSource.Batch waited = source.next();

        assertThat(waited.coordinates()).isEmpty();
        assertThat(waited.exhausted()).isFalse();
    }

    @Test
    public void marks_exhausted_when_poison_arrives_mid_batch() throws InterruptedException {
        BlockingQueue<WorklistStream.QueueItem> queue = new ArrayBlockingQueue<>(8);
        queue.put(new WorklistStream.QueueItem(coordinate("g", "a", "1.0"), 1));
        queue.put(new WorklistStream.QueueItem(coordinate("g", "a", "2.0"), 2));
        queue.put(WorklistStream.QueueItem.POISON);

        StreamingBatchSource source = new StreamingBatchSource(queue, 8, Duration.ofMillis(100L));
        BatchSource.Batch batch = source.next();

        assertThat(batch.coordinates()).hasSize(2);
        assertThat(batch.endPosition()).isEqualTo(2L);
        assertThat(batch.exhausted()).isTrue();
    }

    private static Coordinate coordinate(String groupId, String artifactId, String version) {
        return new Coordinate(groupId, artifactId, version, null, "jar", 0L, 0L);
    }
}
