package zenika.cfm.block;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcedureBlock extends Block {

    private final String name;

    private final List<ArgumentProcedure> arguments;

    //
//    "Q!A0TluBHRQ]}vOK05W!": {
//        "opcode": "procedures_prototype",
//                "next": null,
//                "parent": "Z2_JIoipQX+yeFfJG[V^",
//                "inputs": {
//            "e3V;=6|S.0VsX1s|a[~;": [
//            1,
//                    ":A0|K^WW`eiR`:S1.lng"
//            ],
//            ",1Bk_6G2:0ep8}FHeLeI": [
//            1,
//                    "V0n+qk]sYM`xTU{EG5tX"
//            ],
//            ";w8tDQX8Bs%2K,nrJ3HX": [
//            1,
//                    "T|dhfLGxD:mDMPqO^9;N"
//            ]
//        },
//        "fields": {
//
//        },
//        "shadow": true,
//                "topLevel": false,
//                "mutation": {
//            "tagName": "mutation",
//                    "children": [
//
//            ],
//            "proccode": "new Item name: %s sellIn: %s quality: %s",
//                    "argumentids": "[\"e3V;=6|S.0VsX1s|a[~;\",\",1Bk_6G2:0ep8}FHeLeI\",\";w8tDQX8Bs%2K,nrJ3HX\"]",
//                    "argumentnames": "[\"name\",\"sellIn\",\"quality\"]",
//                    "argumentdefaults": "[\"\",\"\",\"\",\"\"]",
//                    "warp": "false"
//        }
//    },

    public ProcedureBlock(String id, Map<String, Object> value) {
        super(id, value);

        Map<String, Object> inputs = (Map<String, Object>) value.get("inputs");

        Map<String, Object> mutation = (Map<String, Object>) value.get("mutation");
        name = (String) mutation.get("proccode");

        mutation.get("argumentnames");

        this.arguments = ((JSONArray) JsonPath.read((String) mutation.get("argumentnames"), "$")).stream()
                .map(name -> new ArgumentProcedure((String) name, ""))
                .collect(Collectors.toList());
    }

    public class ArgumentProcedure {
        private final String name;

        private final String id;

        public ArgumentProcedure(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "ArgumentProcedure{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProcedureBlock{" +
                "name='" + name + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
