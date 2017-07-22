package GrafoBD;

import GrafoBD.Put;
import GrafoBD.Get;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gustavomahlow on 11/07/17.
 */
public class CopyCatStateMachine extends StateMachine {

    private Map<Integer, String> stateMachine;

    public CopyCatStateMachine() {
        this.stateMachine = new HashMap<Integer, String>(){
            private static final long serialVersionUID = 243496280539242747L;

            @Override
            public String put(Integer key, String value) {
                return super.put(key, value);
            }
        };
    }

    public String put(Commit<Put> commit) {
        try {
            return stateMachine.put(commit.operation().getKey(), commit.operation().getValue());
        } finally {
            commit.release();
        }
    }

    public String get(Commit<Get> commit) {
        try {
            return stateMachine.get(commit.operation().getKey());
        } finally {
            commit.release();
        }
    }
}
