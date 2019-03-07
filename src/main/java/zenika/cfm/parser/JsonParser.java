package zenika.cfm.parser;

import com.jayway.jsonpath.JsonPath;
import io.vertx.core.buffer.Buffer;
import zenika.cfm.block.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonParser {

    public void readScratchProject(Buffer content) {
        String project = content.toString(Charset.forName("UTF-8"));

        Map<String, Object> blocks = JsonPath.read(project, "$.targets[1].blocks");

        List<Block> blockList = blocks.entrySet().stream()
                .map(entry -> {

                    String key = entry.getKey();
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();

                    String opcode = (String) value.get("opcode");
                    if (opcode.contains("event_")) {
                        return new EventBlock(key, value);
                    } else if (opcode.contains("operator_and")) {
                        return new OperatorAnd(key, value);
                    } else if (opcode.contains("procedures_prototype")) {
                        return new ProcedureBlock(key, value);
                    } else if (opcode.contains("control_if")) {
                        return new ControlIfBlock(key, value);
                    } else {
                        return new Block(key, value);
                    }
                })
                .collect(Collectors.toList());


        Map<String, List<Block>> blockByParentId = blockList.stream().filter(block -> block.getParentid() != null).collect(Collectors.groupingBy(Block::getParentid));

        blockList.stream().filter(block -> block.getParentid() == null)
                .forEach(block -> {
                    showBlocks(0, List.of(block), blockByParentId);
                    System.out.println("\n\n");
                });
    }

    public static void showBlocks(int counter, List<Block> blocks, Map<String, List<Block>> blockByParentId) {
        System.out.print("|");
        for (int i = 0; i < counter; i++) {
            System.out.print("-");
        }
        System.out.println(blocks);
        counter++;
        for (Block parent: blocks) {
            List<Block> childs = blockByParentId.get(parent.getId());
            if (childs != null) {
                showBlocks(counter, childs, blockByParentId);
            }
        }
    }
}
