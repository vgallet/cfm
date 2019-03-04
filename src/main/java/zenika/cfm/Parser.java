package zenika.cfm;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

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

        blocks.getMap().entrySet().forEach(entry -> {
            String id = entry.getKey();
            Map values = (Map) entry.getValue();

            Block block = new Block(id, (String) values.get("opcode"), (String) values.get("parent"));
            blockList.add(block);
        });

        Map<String, List<Block>> blockById = blockList.stream().collect(Collectors.groupingBy(Block::getId));
        Map<String, List<Block>> blockByParentId = blockList.stream().filter(block -> block.getParentid() != null).collect(Collectors.groupingBy(Block::getParentid));

        List<Block> noParentBlocks = blockList.stream().filter(block -> block.getParentid() == null).collect(Collectors.toList());

        showBlocks(0, noParentBlocks, blockByParentId);
    }

    public static void showBlocks(int counter, List<Block> blocks, Map<String, List<Block>> blockByParentId) {
        System.out.print("|");
        for (int i = 0; i < counter; i++) {
            System.out.print("-");
        }
        System.out.println(blocks);
        counter++;
        for (Block parent: blocks) {
            List<Block> childs = blockByParentId.get(parent.id);
            if (childs != null) {
                showBlocks(counter, childs, blockByParentId);
            }
        }
    }

    private static class Block {
        String id;
        String libelle;
        String parentid;

        public Block(String id, String libelle, String parentid) {
            this.id = id;
            this.libelle = libelle;
            this.parentid = parentid;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        @Override
        public String toString() {
            return libelle;
        }
    }
}
