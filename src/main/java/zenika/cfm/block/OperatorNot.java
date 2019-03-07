package zenika.cfm.block;

import net.minidev.json.JSONArray;

import java.util.Map;

public class OperatorNot extends Block {

    private String idReferencedBlock;

//    "Pil,Z^ASB9kcV.S3Sl-r": {
//        "opcode": "operator_not",
//                "next": null,
//                "parent": "UA71|Ptrf]:eS=K3dzJa",
//                "inputs": {
//            "OPERAND": [
//            2,
//                    "o6tg7}B|1owcjr\/_Zy]y"
//            ]
//        },
//        "fields": {
//
//        },
//        "shadow": false,
//                "topLevel": false
//    },

    public OperatorNot(String id, Map<String, Object> value) {
        super(id, value);

        Map<String, Object> inputs = (Map<String, Object>) value.get("inputs");
        JSONArray operand = (JSONArray) inputs.get("OPERAND");
        idReferencedBlock = (String) operand.get(1);

    }

    @Override
    public String toString() {
        return "OperatorNot{" +
                "idReferencedBlock='" + idReferencedBlock + '\'' +
                '}';
    }
}
