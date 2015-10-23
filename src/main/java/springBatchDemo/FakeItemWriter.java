package springBatchDemo;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.ItemWriter;

import java.io.Console;
import java.util.List;

public class FakeItemWriter implements ItemWriter {

    @Override
    public void write(List list) throws Exception {
        System.console().writer().write("Just wrote " + list.size() + " items");
    }
}
