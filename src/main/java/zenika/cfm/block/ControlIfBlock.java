package zenika.cfm.block;

import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControlIfBlock extends Block {


    private final List<SubBlock> subBlocks;
//    "Py9)XUJ,@TbJQ((bh36`": {
//        "opcode": "control_if",
//                "next": null,
//                "parent": "rbZj\/wx?E3s%fjc*PX1L",
//                "inputs": {
//            "SUBSTACK": [
//            2,
//                    "UA71|Ptrf]:eS=K3dzJa"
//            ],
//            "CONDITION": [
//            2,
//                    "r.s`s)Qo^XVcv9_^eK9-"
//            ]
//        },
//        "fields": {
//
//        },
//        "shadow": false,
//                "topLevel": false
//    },


    public ControlIfBlock(String id, Map<String, Object> value) {
        super(id, value);

        Map<String, Object> inputs = (Map<String, Object>) value.get("inputs");
        subBlocks = inputs.entrySet().stream()
            .map(entry -> new SubBlock(entry.getKey(), (String) ((JSONArray) entry.getValue()).get(1)))
            .collect(Collectors.toList());

    }

    private class SubBlock {
        private String key;

        private String idReferencedBlock;

        public SubBlock(String key, String idReferencedBlock) {
            this.key = key;
            this.idReferencedBlock = idReferencedBlock;
        }

        public String getIdReferencedBlock() {
            return idReferencedBlock;
        }

        public void setIdReferencedBlock(String idReferencedBlock) {
            this.idReferencedBlock = idReferencedBlock;
        }

        @Override
        public String toString() {
            return "SubBlock{" +
                    "key='" + key + '\'' +
                    ", idReferencedBlock='" + idReferencedBlock + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ControlIfBlock{" +
                "subBlocks=" + subBlocks +
                '}';
    }
}
