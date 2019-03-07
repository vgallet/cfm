package zenika.cfm.parser;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import zenika.cfm.block.EventBlock;
import zenika.cfm.block.OperatorAnd;
import zenika.cfm.block.ProcedureBlock;
import zenika.cfm.variable.Variable;
import zenika.cfm.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    public Parser() {
    }

    public void readScratchProject(Buffer content) {

        JsonObject entries = new JsonObject(content);

        JsonObject blocks = entries.getJsonArray("targets").getJsonObject(1).getJsonObject("blocks");

        List<Block> blockList = new ArrayList<>();

        blocks.forEach(entry -> {
//            String key = entry.getKey();
//            JsonObject jsonObjectBlock = blocks.getJsonObject(key);
//            Map values = ((JsonObject) entry.getValue()).getMap();
//
//            String opcode = (String) values.get("opcode");
//            Block block;
//            if (opcode.contains("event_")) {
//                block = new EventBlock(key, entry);
//            } else if (opcode.contains("operator_and")) {
//                block = new OperatorAnd(key, entry);
//            } else if (opcode.contains("procedures_prototype")) {
//                block = new ProcedureBlock(key, entry, jsonObjectBlock);
//            } else {
//                block = new Block(key, entry, jsonObjectBlock);
//            }
//            blockList.add(block);
        });

        Map<String, List<Block>> blockById = blockList.stream().collect(Collectors.groupingBy(Block::getId));
        Map<String, List<Block>> blockByParentId = blockList.stream().filter(block -> block.getParentid() != null).collect(Collectors.groupingBy(Block::getParentid));

        blockList.stream().filter(block -> block.getParentid() == null)
                .forEach(block -> {
                    showBlocks(0, List.of(block), blockByParentId);
                    System.out.println("\n\n");
                });

        List<Variable> variables = new ArrayList<>();
        JsonObject variablesJson = entries.getJsonArray("targets").getJsonObject(0).getJsonObject("variables");
        variablesJson.getMap().entrySet().forEach(entry -> {
            String variableId = entry.getKey();
            variables.add(new Variable(variableId, (String) ((List) entry.getValue()).get(0)));
        });

        JsonObject listsJson = entries.getJsonArray("targets").getJsonObject(0).getJsonObject("lists");
        listsJson.getMap().entrySet().forEach(entry -> {
            String variableId = entry.getKey();
            variables.add(new Variable(variableId, (String) ((List) entry.getValue()).get(0)));
        });


        variables.stream().forEach(variable -> System.out.println(variable));

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
